package com.dessiapp.screen;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterComment;
import com.dessiapp.models.Body;
import com.dessiapp.models.CommentModel;
import com.dessiapp.models.ProfileModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class CommentProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ProfileModel.Post body;
    CircleImageView postUserPic;
    TextView userName,
            userId,
            dateTime, postmsgtextview;
    ImageView postphoto, sendimage;
    EditText commentEdittext;
    RecyclerView commentRecyclerview;
    PreferenceManager prefManager;
    String useridStr;
    AdapterComment adapterComment;

    Toolbar toolbar;
    private ImageView imgArrow, imgSearch, imgAdd;
    TextView textTitle;
    String userImg,usernameStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Comments");

        prefManager = new PreferenceManager(getApplicationContext());
        useridStr = prefManager.getString(getApplicationContext(), Const.userid, "");
        userImg = prefManager.getString(getApplicationContext(), Const.profileimg, "");
        usernameStr = prefManager.getString(getApplicationContext(), Const.username, "");
        body = new Gson().fromJson(getIntent().getStringExtra("model"), ProfileModel.Post.class);
        postUserPic = findViewById(R.id.postUserPic);
        userName = findViewById(R.id.userName);
        userId = findViewById(R.id.userId);
        dateTime = findViewById(R.id.dateTime);
        postmsgtextview = findViewById(R.id.postmsgtextview);
        postphoto = findViewById(R.id.postphoto);
        commentEdittext = findViewById(R.id.commentEdittext);
        sendimage = findViewById(R.id.sendimage);
        commentRecyclerview = findViewById(R.id.commentRecyclerview);
        sendimage.setOnClickListener(this);

        if (!userImg.equals("") && userImg != null && !userImg.equals("null"))
            Glide.with(getApplicationContext()).load(userImg).into(postUserPic);

        if (!body.getFilepath().equals("") && body.getFilepath() != null && !body.getFilepath().equals("null"))
            Glide.with(getApplicationContext()).load(body.getFilepath()).into(postphoto);
        userName.setText(usernameStr);
        userId.setText("@" + useridStr);
        postmsgtextview.setText(body.getPostdesc());
        try {
            dateTime.setText(Const.getDateFormat(body.getPostedon()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        imgArrow.setOnClickListener(this);

        loadComment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendimage:
                if (commentEdittext.getText().toString() != null && commentEdittext.getText().toString() != "") {
                    sendComment();
                } else {
                    Toast.makeText(this, "Please write something", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgArrow:
                onBackPressed();
                break;
        }
    }

    void sendComment() {
        Call<Object> callApi = ApiCaller.getInstance().postComment(body.getPostid(), commentEdittext.getText().toString(), useridStr);
        callApi.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                try {
                    JSONObject jObj = new JSONObject(String.valueOf(response.body()));
                    if (jObj.getString(Const.STATUS).equals(Const.SUCCESS)) {
                        commentEdittext.setText("");
                        loadComment();
                    } else {
                        Toast.makeText(CommentProfileActivity.this, jObj.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
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

    void loadComment() {
        Call<CommentModel> callApi = ApiCaller.getInstance().getComment(body.getPostid());
        callApi.enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, retrofit2.Response<CommentModel> response) {
                CommentModel commentModel = response.body();
                if (commentModel.getStatus().equals(Const.SUCCESS)) {
                    adapterComment = new AdapterComment(getApplicationContext(), commentModel.getBody());
                    commentRecyclerview.setAdapter(adapterComment);
                } else {
                    Toast.makeText(CommentProfileActivity.this, commentModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {

            }
        });
    }
}