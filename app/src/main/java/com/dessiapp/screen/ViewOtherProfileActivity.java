package com.dessiapp.screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterProfile;
import com.dessiapp.models.ChangeModel;
import com.dessiapp.models.ProfileModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.CallFor;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ViewOtherProfileActivity extends AppCompatActivity {

    PreferenceManager prefManager;
    SwipeRefreshLayout swipeRefresh;
    ImageView iv_camera;
    File selectFile;
    String selectedPath1;
    String image;
    CircleImageView profilePic;
    String useridStr;
    Button logoutClick;
    String profImg;
    RecyclerView recyclerView;
    AdapterProfile adapterProfile;
    LinearLayout editProfile, followingLinear, followersLinear;
    TextView userNameTxt,
            userIdTxt;
    TextView postTxt,
            postFollower,
            postFollowing,
            postLikes,
            postDislikes;

    Toolbar toolbar;
    private ImageView imgArrow, imgSearch, imgAdd;
    TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_profile);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("View Profile");
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        prefManager = new PreferenceManager(getApplicationContext());
        //useridStr = prefManager.getString(getApplicationContext(), "userID", "");
        useridStr = getIntent().getExtras().getString("userID", "");
        profImg = prefManager.getString(getApplicationContext(), Const.profileimg, "");
        profilePic = findViewById(R.id.profilePic);
        followingLinear = findViewById(R.id.followingLinear);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        iv_camera = findViewById(R.id.iv_camera);
        logoutClick = findViewById(R.id.logoutClick);
        editProfile = findViewById(R.id.editProfile);

        postTxt = findViewById(R.id.postTxt);
        postFollower = findViewById(R.id.postFollower);
        postFollowing = findViewById(R.id.postFollowing);
        postLikes = findViewById(R.id.postLikes);
        postDislikes = findViewById(R.id.postDislikes);

        userNameTxt = findViewById(R.id.userNameTxt);
        userIdTxt = findViewById(R.id.userIdTxt);
        followersLinear = findViewById(R.id.followersLinear);

        /*userNameTxt.setText(prefManager.getString(getApplicationContext(), Const.username, ""));
        userIdTxt.setText("@" + prefManager.getString(getApplicationContext(), Const.userid, ""));
        if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getApplicationContext()).load(profImg).into(profilePic);*/
        loadApi();


        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CropImage.activity().setAspectRatio(1, 1).setGuidelines(CropImageView.Guidelines.ON).setAllowRotation(true)
                        .getIntent(getApplicationContext());
                startActivityForResult(intent, 163);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            }
        });
        followingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FollowingActivity.class));
            }
        });
        followersLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FollowersActivity.class));
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApi();
            }
        });
    }

    void loadApi() {
        Call<ProfileModel> callApi = ApiCaller.getInstance().getProfileData(useridStr);
        callApi.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, retrofit2.Response<ProfileModel> response) {
                swipeRefresh.setRefreshing(false);
                ProfileModel profileModel = response.body();
                if (profileModel != null && profileModel.getStatus().equals(Const.SUCCESS)) {
                    String profImg = profileModel.getProfileBody().getProfileimg();
                    userNameTxt.setText(profileModel.getProfileBody().getUsername());
                    userIdTxt.setText("@" + profileModel.getProfileBody().getUserid());
                    if (profImg != null)
                        Glide.with(getApplicationContext()).load(profileModel.getProfileBody().getProfileimg()).into(profilePic);
                    postTxt.setText(String.valueOf(profileModel.getProfileBody().getTotalposts()));
                    postFollower.setText(String.valueOf(profileModel.getProfileBody().getTotalfollowers()));
                    postFollowing.setText(String.valueOf(profileModel.getProfileBody().getTotalfollowing()));
                    postLikes.setText((!String.valueOf(profileModel.getProfileBody().getTotallikes()).equals("null")) ? String.valueOf(profileModel.getProfileBody().getTotallikes()) : "0");
                    postDislikes.setText((!String.valueOf(profileModel.getProfileBody().getTotaldislikes()).equals("null")) ? String.valueOf(profileModel.getProfileBody().getTotaldislikes()) : "0");
                    adapterProfile = new AdapterProfile(getApplicationContext(), profileModel.getProfileBody().getPosts(), CallFor.OTHER_PROFILE);
                    recyclerView.setAdapter(adapterProfile);

                } else {
                    Toast.makeText(getApplicationContext(), "Internet Issues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 163 && resultCode == RESULT_OK) {
            final CropImage.ActivityResult r = CropImage.getActivityResult(data);

            Uri u = r.getUri();
            profilePic.setImageURI(u);
            //imagesUriList.add(u);
            File f = new File(u.getPath());
            Bitmap b = null;
            try {
                b = new Compressor(getApplicationContext())
                        .compressToBitmap(f);
            } catch (Exception e) {
            }
            selectFile = f;
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 20, ba);  //quality 80 but i put 10
            byte[] mByte1 = ba.toByteArray();
            image = Base64.encodeToString(mByte1, Base64.DEFAULT);
            selectedPath1 = f.getPath();
            changeImage(f);

        }
    }


    void changeImage(File selectFile) {
        ProgressDialog uploading = ProgressDialog.show(getApplicationContext(), "Uploading File", "Please wait...", false, false);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", selectFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), selectFile));
        RequestBody useridBody = RequestBody.create(MediaType.parse("text/plain"),
                useridStr);
        Call<ChangeModel> callApi = ApiCaller.getInstance().changeDp(filePart, useridBody);
        callApi.enqueue(new Callback<ChangeModel>() {
            @Override
            public void onResponse(Call<ChangeModel> call, retrofit2.Response<ChangeModel> response) {
                uploading.dismiss();
                ChangeModel value = response.body();
                if (value.getStatus().equals(Const.SUCCESS)) {
                    prefManager.putString(getApplicationContext(), Const.profileimg, value.getBody().getProfileimgurl());
                    Toast.makeText(getApplicationContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangeModel> call, Throwable t) {
                uploading.dismiss();
            }
        });
    }
}