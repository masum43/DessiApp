package com.dessiapp.screen;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dessiapp.R;
import com.dessiapp.models.SpinnerDto;
import com.dessiapp.provider.AdapterSpinner;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.RecyclerItemClickListener;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    MaterialCheckBox termcheckbox;
    TextView checkBox;
    private TextView signin_txtView, textTitletextview;
    private EditText firstnameedittext, age, emailedittext, phonenoedittext, passwordedittext, conpasswordedittext, usernameTxt;
    Toolbar toolbar;
    String txt = "Register";
    private ProgressDialog dialog;
    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String passwordPattern = "[a-zA-Z0-9\\!\\@\\#\\$\\%\\&]{8,16}";
    private String firstname, lastname, emailid, phoneno, password, confirmPassword, checkbox;
    RequestQueue requestQueue;
    ArrayList<SpinnerDto> spinnerDtos = new ArrayList<>();
    AdapterSpinner adapterSpinner;
    SpinnerDto selectState, selectCity, selectGender;
    TextView stateMainTxt, cityMainTxt, genderText;
    ViewDialog alert = new ViewDialog();
    String username;
    String ageStr;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SimpleDateFormat simpleDateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        signin_txtView = findViewById(R.id.signin_txtView);
        firstnameedittext = (EditText) findViewById(R.id.firstnameedittext);
        age = findViewById(R.id.age);
        usernameTxt = findViewById(R.id.usernameTxt);
        emailedittext = findViewById(R.id.emailedittext);
        phonenoedittext = findViewById(R.id.phonenoedittext);
        passwordedittext = findViewById(R.id.passwordedittext);
        conpasswordedittext = findViewById(R.id.conpasswordedittext);
        termcheckbox = findViewById(R.id.termcheckbox);
        signin_txtView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        textTitletextview = findViewById(R.id.txtTitle);
        stateMainTxt = findViewById(R.id.stateMainTxt);
        cityMainTxt = findViewById(R.id.cityMainTxt);
        genderText = findViewById(R.id.genderText);
        textTitletextview.setText(txt);

        int[] value = new int[]{R.id.checkBox, R.id.register, R.id.imgArrow, R.id.stateSpinner, R.id.citySpinner, R.id.genderType, R.id.age, R.id.signin_txtView};
        registerClick(value);
        checkBox = findViewById(R.id.checkBox);
        checkBox.setText(Html.fromHtml("I Agree to your " +
                "<a style=\"background-color:#000000;\"><strong><u>Privacy Policy & Terms & Conditions</u></strong></a>"  /*+", Membership Agreement and Privacy Policy"*/));
    }

    private void registerClick(int[] buttonResId) {
        for (int i = 0; i < buttonResId.length; i++) {
            findViewById(buttonResId[i]).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBox:
                break;
            case R.id.register:
                registerClick();
                break;
            case R.id.imgArrow:
                onBackPressed();
                break;
            case R.id.stateSpinner:
                stateDialog();
                break;
            case R.id.citySpinner:
                cityDialog();
                break;
            case R.id.genderType:
                genderDialog();
                break;
            case R.id.age:
                dateDialog();
                break;
            case R.id.signin_txtView:
                onBackPressed();
                break;
        }
    }

    void stateDialog() {
        final Dialog dialog1 = new Dialog(RegisterActivity.this);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setContentView(R.layout.spinner_view);
        spinnerDtos.clear();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView button = dialog1.findViewById(R.id.iv_mark);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

            }
        });

        final RecyclerView spinnerRecy = dialog1.findViewById(R.id.spinnerRecy);
        final TextView txtHead = dialog1.findViewById(R.id.txtHead);
        txtHead.setText(" Select State ");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Api.STATE_API, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //JSONArray jsonArray = response.getJSONArray("nbdata");
                    String status = response.getString("status");
                    String message = response.getString("message");


                    //if (status.equals("success")) {
                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String id = jsonObject1.getString("id");
                        String post_title = jsonObject1.getString("state");

                        SpinnerDto dto = new SpinnerDto(post_title, id);
                        spinnerDtos.add(dto);
                    }

                    adapterSpinner = new AdapterSpinner(RegisterActivity.this, spinnerDtos, dialog1, "state"/*,onTapAdapter*/);
                    spinnerRecy.setAdapter(adapterSpinner);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(RegisterActivity.this);
//                                mLayoutManager.setReverseLayout(true);
//                                mLayoutManager.setStackFromEnd(true);
                    spinnerRecy.setLayoutManager(mLayoutManager);
                    spinnerRecy.addOnItemTouchListener(new RecyclerItemClickListener(RegisterActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            SpinnerDto spinnerDto = spinnerDtos.get(position);
                            selectState = spinnerDto;
                            stateMainTxt.setText(spinnerDto.getName());
                            dialog1.dismiss();
                        }
                    }));
                    spinnerRecy.setHasFixedSize(true);


                    //
                   /* } else {
                        //  Toast.makeText(CreatePostActivity.this, message, Toast.LENGTH_SHORT).show();
                        //.dismiss();
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getActivity(), "Error is :" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);


        dialog1.show();
    }

    void dateDialog() {
        Calendar c=Calendar.getInstance();
        showDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), R.style.NumberPickerStyle);
        /*final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        age.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        //datePickerDialog.getDatePicker().
        datePickerDialog.show();*/
    }

    void genderDialog() {
        final Dialog dialog1 = new Dialog(RegisterActivity.this);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setContentView(R.layout.spinner_view);
        spinnerDtos.clear();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        spinnerDtos.add(new SpinnerDto("Male", "0"));
        spinnerDtos.add(new SpinnerDto("Female", "1"));
        spinnerDtos.add(new SpinnerDto("Transgender", "2"));
        ImageView button = dialog1.findViewById(R.id.iv_mark);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

            }
        });
        final RecyclerView spinnerRecy = dialog1.findViewById(R.id.spinnerRecy);
        final TextView txtHead = dialog1.findViewById(R.id.txtHead);
        txtHead.setText(" Select Gender ");
        adapterSpinner = new AdapterSpinner(RegisterActivity.this, spinnerDtos, dialog1, "gender"/*,onTapAdapter*/);
        spinnerRecy.setAdapter(adapterSpinner);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(RegisterActivity.this);
        spinnerRecy.setLayoutManager(mLayoutManager);
        spinnerRecy.addOnItemTouchListener(new RecyclerItemClickListener(RegisterActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SpinnerDto spinnerDto = spinnerDtos.get(position);
                selectGender = spinnerDto;
                genderText.setText(spinnerDto.getName());
                dialog1.dismiss();

            }
        }));
        spinnerRecy.setHasFixedSize(true);
        dialog1.show();
    }

    void cityDialog() {
        if (selectState != null) {
            final Dialog dialog1 = new Dialog(RegisterActivity.this);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setContentView(R.layout.spinner_view);
            spinnerDtos.clear();
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ImageView button = dialog1.findViewById(R.id.iv_mark);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();

                }
            });

            final RecyclerView spinnerRecy = dialog1.findViewById(R.id.spinnerRecy);
            final TextView txtHead = dialog1.findViewById(R.id.txtHead);
            txtHead.setText(" Select City ");

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Api.CITY_API + selectState.getName(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        //JSONArray jsonArray = response.getJSONArray("nbdata");
                        String status = response.getString("status");
                        String message = response.getString("message");


                        //if (status.equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("body");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String id = jsonObject1.getString("city_id");
                            String post_title = jsonObject1.getString("city_name");

                            SpinnerDto dto = new SpinnerDto(post_title, id);
                            spinnerDtos.add(dto);
                        }

                        adapterSpinner = new AdapterSpinner(RegisterActivity.this, spinnerDtos, dialog1, "city"/*,onTapAdapter*/);
                        spinnerRecy.setAdapter(adapterSpinner);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(RegisterActivity.this);
                        spinnerRecy.setLayoutManager(mLayoutManager);
                        spinnerRecy.addOnItemTouchListener(new RecyclerItemClickListener(RegisterActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                SpinnerDto spinnerDto = spinnerDtos.get(position);
                                String txttId = spinnerDto.getId();
                                String txtSpinner = spinnerDto.getName();
                                selectCity = spinnerDto;
                                cityMainTxt.setText(spinnerDto.getName());
                                dialog1.dismiss();
                            }
                        }));
                        spinnerRecy.setHasFixedSize(true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(getActivity(), "Error is :" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
            dialog1.show();
        } else {
            alert.showDialog(RegisterActivity.this, "Please choose state");
        }
    }

    private void registerClick() {
        firstname = firstnameedittext.getText().toString();
        ageStr = age.getText().toString();
        username = usernameTxt.getText().toString();
        emailid = emailedittext.getText().toString();
        phoneno = phonenoedittext.getText().toString();
        password = passwordedittext.getText().toString();
        confirmPassword = conpasswordedittext.getText().toString();
        checkbox = termcheckbox.getText().toString();

        if (!InputValidation.isEditTextHasvalue(firstnameedittext)) {
            alert.showDialog(RegisterActivity.this, "Please enter first name");
        } else if (!InputValidation.isEditTextHasvalue(usernameTxt)) {
            alert.showDialog(RegisterActivity.this, "Please enter username");
        } else if (!InputValidation.isEditTextHasvalue(age)) {
            alert.showDialog(RegisterActivity.this, "Please enter age");
        } else if (!InputValidation.isEditTextHasvalue(emailedittext)) {
            alert.showDialog(RegisterActivity.this, "Please enter email Id");
        } else if (!emailedittext.getText().toString().trim().matches(emailPattern)) {
            alert.showDialog(RegisterActivity.this, "Please enter valid email Id");
        } else if (!InputValidation.isEditTextHasvalue(phonenoedittext)) {
            alert.showDialog(RegisterActivity.this, "Please enter mobile no.");
        } else if (phonenoedittext.getText().toString().length() != 10) {
            alert.showDialog(RegisterActivity.this, "Please enter valid mobile no.");
        } else if (selectGender == null) {
            alert.showDialog(RegisterActivity.this, "Please select gender");
        } /*else if (selectState == null) {
            alert.showDialog(RegisterActivity.this, "Please select state");
        } else if (selectCity == null) {
            alert.showDialog(RegisterActivity.this, "Please select city");
        } */else if (!InputValidation.isEditTextHasvalue(passwordedittext)) {
            alert.showDialog(RegisterActivity.this, "Please enter Password");
        } else if (!passwordedittext.getText().toString().matches(passwordPattern)) {
            alert.showDialog(RegisterActivity.this, "Please enter minimum 8 to 16 character password. ");
        } else if (!password.contains("@") && !password.contains("$") && !password.contains("&") && !password.contains("#") && !password.contains("%")) {
            alert.showDialog(RegisterActivity.this, "Password must contain atleast one special character");
        } else if (!InputValidation.isEditTextHasvalue(conpasswordedittext)) {
            alert.showDialog(RegisterActivity.this, "Please enter confirm password");
        } else if (!password.equals(confirmPassword)) {
            alert.showDialog(RegisterActivity.this, "Password does not match with confirm password");
        } else if (!termcheckbox.isChecked()) {
            alert.showDialog(RegisterActivity.this, "Please  accept terms and condition");
        } else {
            if (checkNetworkConnection()) {
                new RegisterButton().execute();
            } else {
                Intent intent = new Intent(RegisterActivity.this, NetworkCheckActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(RegisterActivity.this)
                .callback(RegisterActivity.this)
               // .callback(RegisterActivity.this)
                //.onCancel(RegisterActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .maxDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        age.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @SuppressLint("StaticFieldLeak")
    private class RegisterButton extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = null;
            dialog = new ProgressDialog(RegisterActivity.this);
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
                nameValuePairs.add(new BasicNameValuePair("email", URLEncoder.encode(emailid)));
                nameValuePairs.add(new BasicNameValuePair("mobile", phoneno));
                nameValuePairs.add(new BasicNameValuePair("userId", username));
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
                        JSONArray jArr = object.getJSONArray("body");
                        String otp = jArr.getJSONObject(0).getString("otp");
                        Toast.makeText(RegisterActivity.this, otp, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                        intent.putExtra(Const.NAME, firstname);
                        intent.putExtra(Const.AGE, ageStr);
                        intent.putExtra(Const.USERNAME, username);
                        intent.putExtra(Const.EMAIL, emailid);
                        intent.putExtra(Const.PHONE, phoneno);
                        intent.putExtra(Const.PASS, password);
//                        intent.putExtra(Const.STATE, selectState.getId());
//                        intent.putExtra(Const.CITY, selectCity.getId());
                        intent.putExtra(Const.GENDER, selectGender.getId());
                        intent.putExtra(Const.OTP, otp);

                        startActivity(intent);
                        finish();

                        firstnameedittext.setText("");
                        age.setText("");
                        emailedittext.setText("");
                        phonenoedittext.setText("");
                        passwordedittext.setText("");
                        conpasswordedittext.setText("");

                    } else {

                        alert.showDialog(RegisterActivity.this, message);
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

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}