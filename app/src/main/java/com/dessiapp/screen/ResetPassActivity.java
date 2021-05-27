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
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.PreferenceManager;

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

public class ResetPassActivity extends AppCompatActivity {
    private Button reset_button;
    Toolbar toolbar;
    private ProgressDialog dialog;
    PreferenceManager prefManager;
    private JSONObject object;
    private ImageView imgArrow, imgSearch, imgAdd;
    private TextView textTitle;
    private EditText otpedittext, newpasswordedittext, confpasswordedittext;
    private String otppassword, newpassword, userid, confpassword;
    private String passwordPattern = "[a-zA-Z0-9\\!\\@\\#\\$]{8,16}";
    String otp, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        reset_button = findViewById(R.id.reset_button);
        otpedittext = findViewById(R.id.otpedittext);
        newpasswordedittext = findViewById(R.id.newpasswordedittext);
        confpasswordedittext = findViewById(R.id.confpasswordedittext);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Reset Password");
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        prefManager = new PreferenceManager(this);
        userid = prefManager.getString(ResetPassActivity.this, "userenter", null);
        otp = getIntent().getStringExtra("otp");
        email = getIntent().getStringExtra("email");
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otppassword = otpedittext.getText().toString();
                newpassword = newpasswordedittext.getText().toString();
                confpassword = confpasswordedittext.getText().toString();
                if (!InputValidation.isEditTextHasvalue(otpedittext)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "Please enter OTP");
                    /* Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_LONG).show();*/

                }else if (!otp.equals(otppassword)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "Invalid OTP");
                    /* Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_LONG).show();*/

                } else if (!InputValidation.isEditTextHasvalue(newpasswordedittext)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "Please enter new password");
                    // Toast.makeText(getApplicationContext(), "Please enter new password ", Toast.LENGTH_LONG).show();
                } else if (!newpasswordedittext.getText().toString().matches(passwordPattern)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "Please enter minimum 8 to 16 character password");
                    // Toast.makeText(getApplicationContext(), "Please enter minimum 8 to 16 character password", Toast.LENGTH_SHORT).show();
                } else if (!newpassword.contains("@") && !newpassword.contains("$") && !newpassword.contains("&") && !newpassword.contains("#") && !newpassword.contains("%")) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "Password must contain atleast one special character");
                    // Toast.makeText(getApplicationContext(), "Please enter minimum 8 to 16 character password", Toast.LENGTH_SHORT).show();
                } else if (!InputValidation.isEditTextHasvalue(confpasswordedittext)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "Please enter confirm password");
                    // Toast.makeText(getApplicationContext(), "Please enter confirm password ", Toast.LENGTH_LONG).show();
                } else if (!confpasswordedittext.getText().toString().matches(passwordPattern)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "Please enter valid confirm password");
                    // Toast.makeText(getApplicationContext(), "Please enter valid confirm password", Toast.LENGTH_SHORT).show();
                } else if (!newpassword.equals(confpassword)) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ResetPassActivity.this, "password does not match with confirm password");
                    //Toast.makeText(getApplicationContext(), "password does not match with confirm password", Toast.LENGTH_SHORT).show();


                } else {
                    if (checkNetworkConnection()) {
                        new userResetpwWebService().execute();

                    } else {
                        Intent intent = new Intent(ResetPassActivity.this, NetworkCheckActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ResetPassActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @SuppressLint("StaticFieldLeak")
    private class userResetpwWebService extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = null;
            dialog = new ProgressDialog(ResetPassActivity.this);
            dialog.setMessage("Please Wait");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Api.RESET_PASS);
            httpPost.setHeader(Const.HEAD_TOKEN, Const.TOKEN_KEY);
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", email));
                //nameValuePairs.add(new BasicNameValuePair("Old_PWD", newpassword));
                nameValuePairs.add(new BasicNameValuePair("Password", newpassword));
                nameValuePairs.add(new BasicNameValuePair("ConfPWD", newpassword));
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
                        Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
                        //new Cleanbilldata().execute();

                        Intent intent = new Intent(ResetPassActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        /*Intent intent = new Intent(ClaimReimburseActivity.this,ReimbunsementFragment.class);
                        startActivity(intent);
                        finish();*/
                    } else {
                        new ViewDialog().showDialog(ResetPassActivity.this, message);
                        //Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();

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