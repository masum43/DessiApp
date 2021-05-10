package com.dessiapp.screen;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dessiapp.R;
import com.dessiapp.adapter.AdapterDashboard;
import com.dessiapp.models.DashModel2;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    Button updateBtnProfile;
    EditText nameEdt,
            usernameEdt,
            dobEdt,
            emailEdt,
            phnEdt;

    PreferenceManager prefManager;

    //String name,userid,dob,email,phn;
    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    ViewDialog viewDialog;
    String useridDb;

    SimpleDateFormat simpleDateFormat;

    Toolbar toolbar;
    private ImageView imgArrow;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Edit Profile");
        imgArrow.setOnClickListener(this);

        prefManager = new PreferenceManager(getApplicationContext());
        viewDialog = new ViewDialog();
        updateBtnProfile = findViewById(R.id.updateBtnProfile);
        useridDb = prefManager.getString(getApplicationContext(), Const.userid, "");
        nameEdt = findViewById(R.id.nameEdt);
        usernameEdt = findViewById(R.id.usernameEdt);
        dobEdt = findViewById(R.id.dobEdt);
        emailEdt = findViewById(R.id.emailEdt);
        phnEdt = findViewById(R.id.phnEdt);
        updateBtnProfile = findViewById(R.id.updateBtnProfile);
        updateBtnProfile.setOnClickListener(this);
        dobEdt.setOnClickListener(this);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String age = prefManager.getString(getApplicationContext(), Const.AGE, "");

        nameEdt.setText(prefManager.getString(getApplicationContext(), Const.username, ""));
        usernameEdt.setText(prefManager.getString(getApplicationContext(), Const.userid, ""));
        dobEdt.setText(prefManager.getString(getApplicationContext(), Const.AGE, ""));
        emailEdt.setText(prefManager.getString(getApplicationContext(), Const.email, ""));
        phnEdt.setText(prefManager.getString(getApplicationContext(), Const.mobileno, ""));

    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(EditProfileActivity.this)
                .callback(EditProfileActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .maxDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateBtnProfile:
                validate();
                break;
            case R.id.dobEdt:
                callDatePicker();
                break;
            case R.id.imgArrow:
                onBackPressed();
                break;
        }
    }

    void validate() {
        if (nameEdt.getText().toString() == null || nameEdt.getText().toString() == "") {
            viewDialog.showDialog(EditProfileActivity.this, "Please enter name");
        } else if (usernameEdt.getText().toString() == null || usernameEdt.getText().toString() == "") {
            viewDialog.showDialog(EditProfileActivity.this, "Please enter username");
        } /*else if (dobEdt.getText().toString() == null || dobEdt.getText().toString() == "") {
            viewDialog.showDialog(EditProfileActivity.this, "Please enter dob");
        }*/ else if (emailEdt.getText().toString() == null || emailEdt.getText().toString() == "") {
            viewDialog.showDialog(EditProfileActivity.this, "Please enter email id");
        } else if (!emailEdt.getText().toString().trim().matches(emailPattern)) {
            viewDialog.showDialog(EditProfileActivity.this, "Please enter valid email id");
        } else if (phnEdt.getText().toString() == null || phnEdt.getText().toString() == "") {
            viewDialog.showDialog(EditProfileActivity.this, "Please enter mobile no");
        } else if (phnEdt.getText().toString().length() != 10) {
            viewDialog.showDialog(EditProfileActivity.this, "Please enter valid mobile no");
        } else {
            updateApiCall();
        }
    }

    void updateApiCall() {
        Call<Object> callApi = ApiCaller.getInstance().updateProfileData(useridDb, usernameEdt.getText().toString(), nameEdt.getText().toString(), emailEdt.getText().toString(), phnEdt.getText().toString(), dobEdt.getText().toString());
        callApi.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                try {
                    JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                    if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                        JSONObject body = jOb.getJSONObject("body");
                    } else {
                        Toast.makeText(getApplicationContext(), jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    void callDatePicker() {
        Calendar c = Calendar.getInstance();
        showDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), R.style.NumberPickerStyle);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        dobEdt.setText(simpleDateFormat.format(calendar.getTime()));
    }
}