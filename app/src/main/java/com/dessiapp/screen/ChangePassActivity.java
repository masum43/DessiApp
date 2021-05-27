package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dessiapp.R;
import com.dessiapp.adapter.AdapterComment;
import com.dessiapp.models.CommentModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener {

    EditText oldPass,
            newpasswordedittext,
            confpasswordedittext;
    Button reset_button;

    Toolbar toolbar;
    private ImageView imgArrow, imgSearch, imgAdd;
    TextView textTitle;
    ViewDialog alert;
    String passwordPattern = "[a-zA-Z0-9\\!\\@\\#\\$\\%\\&]{8,16}";
    String useridStr;
    PreferenceManager prefManager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chnage_pass);

        dialog = null;
        dialog = new ProgressDialog(ChangePassActivity.this);
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);


        alert = new ViewDialog();
        prefManager = new PreferenceManager(getApplicationContext());
        useridStr = prefManager.getString(getApplicationContext(), Const.userid, "");
        oldPass = findViewById(R.id.oldPass);
        newpasswordedittext = findViewById(R.id.newpasswordedittext);
        confpasswordedittext = findViewById(R.id.confpasswordedittext);
        reset_button = findViewById(R.id.reset_button);

        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Change Password");

        imgArrow.setOnClickListener(this);
        reset_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset_button:
                validate();
                break;
            case R.id.imgArrow:
                onBackPressed();
                break;
        }
    }

    private void validate() {
        if (!InputValidation.isEditTextHasvalue(oldPass)) {
            alert.showDialog(ChangePassActivity.this, "Please enter Old Password");
        } else if (!oldPass.getText().toString().matches(passwordPattern)) {
            alert.showDialog(ChangePassActivity.this, "Please enter minimum 8 to 16 character password. ");
        } else if (!InputValidation.isEditTextHasvalue(newpasswordedittext)) {
            alert.showDialog(ChangePassActivity.this, "Please enter New Password");
        } else if (!newpasswordedittext.getText().toString().matches(passwordPattern)) {
            alert.showDialog(ChangePassActivity.this, "Please enter minimum 8 to 16 character password in new password. ");
        } else if (!newpasswordedittext.getText().toString().contains("@") && !newpasswordedittext.getText().toString().contains("$") && !newpasswordedittext.getText().toString().contains("&") && !newpasswordedittext.getText().toString().contains("#") && !newpasswordedittext.getText().toString().contains("%")) {
            alert.showDialog(ChangePassActivity.this, "New password must contain at least one special character");
        } else if (!InputValidation.isEditTextHasvalue(confpasswordedittext)) {
            alert.showDialog(ChangePassActivity.this, "Please enter confirm password");
        } else if (!newpasswordedittext.getText().toString().equals(confpasswordedittext.getText().toString())) {
            alert.showDialog(ChangePassActivity.this, "Password does not match with confirm password");
        } else {
            updatePass();
        }
    }

    private void updatePass() {
        if(dialog!=null)dialog.show();
        String pass=newpasswordedittext.getText().toString();
        Call<Object> callApi = ApiCaller.getInstance().changePass(useridStr,oldPass.getText().toString(),pass,pass);
        callApi.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                if(dialog!=null)dialog.dismiss();
                try {
                    JSONObject jObj=new JSONObject(String.valueOf(response.body()));
                    if(jObj.getString(Const.STATUS).equals(Const.SUCCESS)){
                        alert.showDialog(ChangePassActivity.this,"Successfully Password Updated");
                    }else{
                        Toast.makeText(ChangePassActivity.this, jObj.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                if(dialog!=null)dialog.dismiss();
            }
        });
    }
}