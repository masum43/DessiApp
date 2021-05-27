package com.dessiapp.screen;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dessiapp.R;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

public class OtpActivity extends AppCompatActivity implements ViewDialog1.ClickEvent {
    private static final String TAG = "LoginActivity";
    Toolbar toolbar;
    private ImageView imgArrow, imgSearch, imgAdd;
    private TextView textTitle, resendotp;
    private EditText otpedittext;
    private Button save_button;
    private ProgressDialog dialog;
    private JSONObject object;
    // Fragment fragment;
    PreferenceManager prefManager;
    private String firstname, username, age, emailid, phoneno, gender, state, city, password, otp, otpenter;
    Bundle bundle;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpedittext = findViewById(R.id.otpedittext);
        save_button = findViewById(R.id.save_button);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        resendotp = findViewById(R.id.resendotp);

        textTitle.setText("OTP");
        otp = getIntent().getExtras().getString(Const.OTP);
        firstname = getIntent().getExtras().getString(Const.NAME);
        username = getIntent().getExtras().getString(Const.USERNAME);
        age = getIntent().getExtras().getString(Const.AGE);
        emailid = getIntent().getExtras().getString(Const.EMAIL);
        phoneno = getIntent().getExtras().getString(Const.PHONE);
        gender = getIntent().getExtras().getString(Const.GENDER);
        state = getIntent().getExtras().getString(Const.STATE,"");
        city = getIntent().getExtras().getString(Const.CITY,"");
        password = getIntent().getExtras().getString(Const.PASS);
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpenter = otpedittext.getText().toString();
                if (!InputValidation.isEditTextHasvalue(otpedittext)) {
                    //Toast.makeText(getApplicationContext(), "Please enter otp", Toast.LENGTH_LONG).show();
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(OtpActivity.this, "Please enter OTP");
                } else {
                    if (checkNetworkConnection()) {
                        if (otp.equals(otpenter)) {
                            new userOtpWebService().execute();
                        } else {
                            ViewDialog alert = new ViewDialog();
                            alert.showDialog(OtpActivity.this, "OTP mismatch");
                        }


                    } else {
                        Intent intent = new Intent(OtpActivity.this, NetworkCheckActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetworkConnection()) {
                    new userresendOtpWebService().execute();
                } else {
                    Intent intent = new Intent(OtpActivity.this, NetworkCheckActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }


    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) OtpActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void getClick() {
        onBackPressed();
    }

    @SuppressLint("StaticFieldLeak")
    private class userOtpWebService extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = null;
            dialog = new ProgressDialog(OtpActivity.this);
            dialog.setMessage("Please Wait");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Api.SIGN_UP);
            httpPost.setHeader(Const.HEAD_TOKEN,Const.TOKEN_KEY);
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("userName", firstname));
                nameValuePairs.add(new BasicNameValuePair("userId", username));
                nameValuePairs.add(new BasicNameValuePair("dob", age));
                nameValuePairs.add(new BasicNameValuePair("email", emailid));
                nameValuePairs.add(new BasicNameValuePair("mobile", phoneno));
                nameValuePairs.add(new BasicNameValuePair("gender", gender));
//                nameValuePairs.add(new BasicNameValuePair("state", state));
//                nameValuePairs.add(new BasicNameValuePair("city", city));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("cnfPassword", password));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                result = EntityUtils.toString(response.getEntity());
                Log.d(TAG, "doInBackground: in loop" + result);
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
                    Log.d(TAG, "onPostExecute: id" + status);
                    message = object.getString("message");

                    Log.d(TAG, "onPostExecute: id" + message);
                    if (status.equals("success")) {
                        ViewDialog1 alert = new ViewDialog1();
                        alert.showDialog(OtpActivity.this, "Register Successfully\n Now you can login", OtpActivity.this);
                    } else {
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(OtpActivity.this, message);
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

    @SuppressLint("StaticFieldLeak")
    private class userresendOtpWebService extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = null;
            dialog = new ProgressDialog(OtpActivity.this);
            dialog.setMessage("Please Wait");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpClient httpClient = new DefaultHttpClient();
            //HttpPost httpPost = new HttpPost("http://neighbrsnook.com/admin/api/master?flag=21");
            HttpPost httpPost = new HttpPost(Api.REG_REQ_OTP);
            httpPost.setHeader(Const.HEAD_TOKEN,Const.TOKEN_KEY);
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", emailid));
                nameValuePairs.add(new BasicNameValuePair("mobile", phoneno));
                nameValuePairs.add(new BasicNameValuePair("userId", username));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                result = EntityUtils.toString(response.getEntity());
                Log.d(TAG, "doInBackground: in loop" + result);
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
                    Log.d(TAG, "onPostExecute: id" + status);
                    message = object.getString("message");
                    Log.d(TAG, "onPostExecute: id" + message);
                    if (status.equals("success")) {
                        String otp1 = object.getString("otp");
                        otp = otp1;

                        Toast.makeText(getApplicationContext(), otp1.toString(), Toast.LENGTH_SHORT).show();
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(OtpActivity.this, message);

                    } else {
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(OtpActivity.this, message);
                        // Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
                        /* Toast.makeText(RegisterActivity.this, "Internet issue", Toast.LENGTH_LONG).show();*/
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
