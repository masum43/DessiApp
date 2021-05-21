package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dessiapp.R;
import com.dessiapp.models.LoginModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity implements ViewDialog1.ClickEvent {

    private TextView regTxtview;
    EditText userIdEdit;
    TextInputEditText passEdit;
    private ProgressDialog dialog;
    String userid, pass;
    ViewDialog viewDialog;
    PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefManager = new PreferenceManager(this);
        viewDialog = new ViewDialog();
        regTxtview = findViewById(R.id.regTxtview);
        userIdEdit = findViewById(R.id.userIdEdit);
        passEdit = findViewById(R.id.passEdit);
        regTxtview.setText(Html.fromHtml("Don't have an account? " +
                "<a style=\"background-color:#000000;\"><strong><u>Register</u></strong></a>"));
    }

    public void RegActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    public void signInPress(View view) {
        userid = userIdEdit.getText().toString();
        pass = passEdit.getText().toString();

        if (!InputValidation.isEditTextHasvalue(userIdEdit)) {
            viewDialog.showDialog(LoginActivity.this, "Please enter userid");
        } else if (!InputValidation.isEditTextHasvalue(passEdit)) {
            viewDialog.showDialog(LoginActivity.this, "Please enter password");
        } else {
            //new SignInService().execute();
            loginApi();
        }
        //startActivity(new Intent(LoginActivity.this,NavigationActivity.class));
    }

    public void forgotAct(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
    }

    @Override
    public void getClick() {
       // Toast.makeText(this, "Bro called", Toast.LENGTH_SHORT).show();
    }

//    @SuppressLint("StaticFieldLeak")
//    private class SignInService extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = null;
//            dialog = new ProgressDialog(LoginActivity.this);
//            dialog.setMessage("Please Wait");
//            dialog.setCancelable(false);
//            dialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String result = "";
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(Api.SIGN_IN);
//            try {
//                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                nameValuePairs.add(new BasicNameValuePair("UserID", userid));
//                nameValuePairs.add(new BasicNameValuePair("Password", pass));
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                HttpResponse response = httpClient.execute(httpPost);
//                result = EntityUtils.toString(response.getEntity());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            if (!s.equals("")) {
//                String status = null;
//                String message = null;
//                try {
//                    JSONObject object = new JSONObject(s);
//                    status = object.getString("status");
//                    message = object.getString("message");
//
//                    if (status.equals("success")) {
//                        JSONArray jsonArray=object.getJSONArray("body");
//                        JSONObject arr = jsonArray.getJSONObject(0);
//
//                        prefManager.putString(getApplicationContext(), Const.serialno,arr.get(Const.serialno).toString());
//                        prefManager.putString(getApplicationContext(), Const.userid,arr.getString(Const.userid));
//                        prefManager.putString(getApplicationContext(), Const.username,arr.getString(Const.username));
//                        prefManager.putString(getApplicationContext(), Const.age,String.valueOf(arr.getInt(Const.age)));
//                        prefManager.putString(getApplicationContext(), Const.gender,String.valueOf(arr.getInt(Const.gender)));
//                        prefManager.putString(getApplicationContext(), Const.password,arr.getString(Const.password));
//                        prefManager.putString(getApplicationContext(), Const.mobileno,arr.getString(Const.mobileno));
//                        prefManager.putString(getApplicationContext(), Const.email,arr.getString(Const.email));
//                        prefManager.putString(getApplicationContext(), Const.role,arr.getString(Const.role));
//                        prefManager.putString(getApplicationContext(), Const.status,arr.getString(Const.status));
//                        prefManager.putString(getApplicationContext(), Const.state,arr.getString(Const.state));
//                        prefManager.putString(getApplicationContext(), Const.city,arr.getString(Const.city));
//                        prefManager.putString(getApplicationContext(), Const.statename,arr.getString(Const.statename));
//                        prefManager.putString(getApplicationContext(), Const.cityname,arr.getString(Const.cityname));
//                        prefManager.putString(getApplicationContext(), Const.profileimg,arr.getString(Const.profileimg));
//                        prefManager.putString(getApplicationContext(), Const.createdon,Const.getDateFormat1(arr.getString(Const.createdon)));
//
//                        startActivity(new Intent(LoginActivity.this,NavigationActivity.class));
//                        finishAffinity();
//
//                    } else {
//
//                        ViewDialog alert = new ViewDialog();
//                        alert.showDialog(LoginActivity.this, message);
//
//                    }
//                } catch (JSONException | ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (dialog != null)
//                dialog.dismiss();
//            super.onPostExecute(s);
//        }
//    }

    void loginApi() {
        dialog = null;
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Please Wait");
            dialog.setCancelable(false);
            dialog.show();
        Call<LoginModel> callApi = ApiCaller.getInstance().loginApi(userid,pass);
        callApi.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                LoginModel loginModel = response.body();
                            if (dialog != null)
                dialog.dismiss();
                if(loginModel!=null && loginModel.getStatus().equals(Const.SUCCESS)){
                    LoginModel.Body arr= loginModel.getBody().get(0);
                    prefManager.putString(getApplicationContext(), Const.serialno,String.valueOf(arr.getSerialno()));
                    prefManager.putString(getApplicationContext(), Const.userid,arr.getUserid());
                    prefManager.putString(getApplicationContext(), Const.username,arr.getUsername());
                    prefManager.putString(getApplicationContext(), Const.age,String.valueOf(arr.getAge()));
                    prefManager.putString(getApplicationContext(), Const.gender,String.valueOf(arr.getGender()));
                    prefManager.putString(getApplicationContext(), Const.password,arr.getPassword());
                    prefManager.putString(getApplicationContext(), Const.mobileno,arr.getMobileno());
                    prefManager.putString(getApplicationContext(), Const.email,arr.getEmail());
                    prefManager.putString(getApplicationContext(), Const.role,arr.getRole());
                    prefManager.putString(getApplicationContext(), Const.status,arr.getStatus());
                    prefManager.putString(getApplicationContext(), Const.state,arr.getState());
                    prefManager.putString(getApplicationContext(), Const.city,arr.getCity());
                    prefManager.putString(getApplicationContext(), Const.statename,arr.getStatename());
                    prefManager.putString(getApplicationContext(), Const.cityname,arr.getCityname());
                    prefManager.putString(getApplicationContext(), Const.profileimg,arr.getProfileimg());
                    try {
                        prefManager.putString(getApplicationContext(), Const.createdon,Const.getDateFormat1(arr.getCreatedon()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(LoginActivity.this,NavigationActivity.class));
                    finishAffinity();
                }else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginActivity.this, loginModel.getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
            if (dialog != null)
                dialog.dismiss();
            }
        });
    }
}