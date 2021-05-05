package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dessiapp.R;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    private ImageView imgArrow;
    private TextView textTitle;
    private TextView
            userName,
            userid,
            dateTime;
    CircleImageView postUserPic;

    PreferenceManager prefManage;
    String userIdSt, userNameSt, userPicSt, dateTimeSt;
    LinearLayout changePassLay,aboutUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Settings");

        prefManage = new PreferenceManager(getApplicationContext());
        userIdSt = prefManage.getString(getApplicationContext(), Const.userid, "");
        userNameSt = prefManage.getString(getApplicationContext(), Const.username, "");
        userPicSt = prefManage.getString(getApplicationContext(), Const.profileimg, "");
        dateTimeSt = prefManage.getString(getApplicationContext(), Const.createdon, "");



        userName = findViewById(R.id.userName);
        userid = findViewById(R.id.userid);
        dateTime = findViewById(R.id.dateTime);
        postUserPic = findViewById(R.id.postUserPic);
        changePassLay = findViewById(R.id.changePassLay);
        aboutUS = findViewById(R.id.aboutUS);


        userName.setText(userNameSt);
        userid.setText("@" + userNameSt);
        dateTime.setText("Joined on " + dateTimeSt);


        imgArrow.setOnClickListener(this);
        changePassLay.setOnClickListener(this);
        aboutUS.setOnClickListener(this);
    }

    public void logout(View view) {
        prefManage.removeKeyValue(getApplicationContext(), Const.serialno);
        prefManage.removeKeyValue(getApplicationContext(), Const.userid);
        prefManage.removeKeyValue(getApplicationContext(), Const.username);
        prefManage.removeKeyValue(getApplicationContext(), Const.age);
        prefManage.removeKeyValue(getApplicationContext(), Const.gender);
        prefManage.removeKeyValue(getApplicationContext(), Const.password);
        prefManage.removeKeyValue(getApplicationContext(), Const.mobileno);
        prefManage.removeKeyValue(getApplicationContext(), Const.email);
        prefManage.removeKeyValue(getApplicationContext(), Const.role);
        prefManage.removeKeyValue(getApplicationContext(), Const.status);
        prefManage.removeKeyValue(getApplicationContext(), Const.state);
        prefManage.removeKeyValue(getApplicationContext(), Const.city);
        prefManage.removeKeyValue(getApplicationContext(), Const.statename);
        prefManage.removeKeyValue(getApplicationContext(), Const.cityname);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finishAffinity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changePassLay:
                startActivity(new Intent(getApplicationContext(), ChangePassActivity.class));
                break; case R.id.aboutUS:
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                break;
            case R.id.imgArrow:
                onBackPressed();
                break;
        }
    }
}