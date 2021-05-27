package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dessiapp.R;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ForgotPassActivity extends AppCompatActivity {

    private Button sand_button;
    private ImageView imgArrow, imgSearch, imgAdd;
    private TextView textTitle;
    private EditText advcamount_edittext;
    private ProgressDialog dialog;
    PreferenceManager prefManager;
    private JSONObject object;
    String emailphoneno;
    Toolbar toolbar;
    String regexStr = "^[0-9]*$";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_pass);

        sand_button = findViewById(R.id.sand_button);
        advcamount_edittext = findViewById(R.id.advcamount_edittext);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Forgot Password?");
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        prefManager = new PreferenceManager(ForgotPassActivity.this);
        sand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailphoneno = advcamount_edittext.getText().toString();
                if (!InputValidation.isEditTextHasvalue(advcamount_edittext)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ForgotPassActivity.this, "Please enter email");
                    // Toast.makeText(getApplicationContext(), "Please enter Email or Phoneno.", Toast.LENGTH_LONG).show();


                } else {
                    if (!advcamount_edittext.getText().toString().matches(regexStr)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                    }else if (advcamount_edittext.getText().toString().length()!=10) {
                        Toast.makeText(getApplicationContext(), "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                    } else {
                        if (checkNetworkConnection()) {
                            new userforgotWebService().execute();

                        } else {
                            Intent intent = new Intent(ForgotPassActivity.this, NetworkCheckActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }
            }

        });
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ForgotPassActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @SuppressLint("StaticFieldLeak")
    private class userforgotWebService extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = null;
            dialog = new ProgressDialog(ForgotPassActivity.this);
            dialog.setMessage("Please Wait");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Api.FORGOT_PASS);
            httpPost.setHeader(Const.HEAD_TOKEN, Const.TOKEN_KEY);
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", emailphoneno));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                result = EntityUtils.toString(response.getEntity());

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(String s) {

            if (!s.equals("")) {
                String status = null;
                String message = null;

                try {
                    JSONObject object = new JSONObject(s);
                    status = object.getString("status");
                    message = object.getString("message");
                    if (status.equals("success")) {
                        JSONObject jObj = (JSONObject) object.getJSONArray("body").get(0);
                        String otp = jObj.getString("otp");
                        Intent intent = new Intent(ForgotPassActivity.this, ResetPassActivity.class);
                        intent.putExtra("otp",otp);
                        intent.putExtra("email",emailphoneno);
                        startActivity(intent);
                        advcamount_edittext.setText("");
                        finish();
                        /* finish();*/
                    } else {
                        //Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
                         Toast.makeText(ForgotPassActivity.this, message.toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (dialog != null)
                dialog.dismiss();
            super.onPostExecute(s);
        }
    }
}