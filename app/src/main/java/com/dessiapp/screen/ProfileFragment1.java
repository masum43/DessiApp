package com.dessiapp.screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterDashboard;
import com.dessiapp.adapter.AdapterProfile;
import com.dessiapp.models.ProfileModel;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.MultipartRequest;
import com.dessiapp.provider.PreferenceManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

//import android.util.JsonReader;

public class ProfileFragment1 extends Fragment {

    View v;
    PreferenceManager prefManager;
    RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefresh;
    ImageView iv_camera;
    File selectFile;
    String selectedPath1;
    String image;
    CircleImageView profilePic;
    String useridStr;
    Button logoutClick;
    TextView memberSinceTxt,
            emailtextview,
            mobiletextview,
            dobTxt,
            gendertextview, userName, userId;
    String profImg;
    RecyclerView recyclerView;
    AdapterProfile adapterProfile;
    LinearLayout editProfile;
    TextView userNameTxt,
            userIdTxt;
    TextView postTxt,
            postFollower,
            postFollowing,
            postLikes,
            postDislikes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile1, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        prefManager = new PreferenceManager(getActivity());
        useridStr = prefManager.getString(getActivity(), Const.userid, "");
        profImg = prefManager.getString(getActivity(), Const.profileimg, "");
        profilePic = v.findViewById(R.id.profilePic);
        swipeRefresh = v.findViewById(R.id.swipeRefresh);
        recyclerView = v.findViewById(R.id.recyclerView);
        iv_camera = v.findViewById(R.id.iv_camera);
        logoutClick = v.findViewById(R.id.logoutClick);
        editProfile = v.findViewById(R.id.editProfile);

        postTxt = v.findViewById(R.id.postTxt);
        postFollower = v.findViewById(R.id.postFollower);
        postFollowing = v.findViewById(R.id.postFollowing);
        postLikes = v.findViewById(R.id.postLikes);
        postDislikes = v.findViewById(R.id.postDislikes);

        userNameTxt = v.findViewById(R.id.userNameTxt);
        userIdTxt = v.findViewById(R.id.userIdTxt);

        userNameTxt.setText(prefManager.getString(getActivity(), Const.username, ""));
        userIdTxt.setText("@" + prefManager.getString(getActivity(), Const.userid, ""));
        loadApi();


        if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getActivity()).load(profImg).into(profilePic);


        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CropImage.activity().setAspectRatio(1, 1).setGuidelines(CropImageView.Guidelines.ON).setAllowRotation(true)
                        .getIntent(getActivity());
                startActivityForResult(intent, 163);
            }
        }); editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditProfileActivity.class));
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApi();
            }
        });


        return v;
    }

    void loadApi() {
        Call<ProfileModel> callApi = ApiCaller.getInstance().getProfileData(useridStr);
        callApi.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, retrofit2.Response<ProfileModel> response) {
                swipeRefresh.setRefreshing(false);
                ProfileModel profileModel = response.body();
                if (profileModel != null && profileModel.getStatus().equals(Const.SUCCESS)) {
                    postTxt.setText(String.valueOf(profileModel.getProfileBody().getTotalposts()));
                    postFollower.setText(String.valueOf(profileModel.getProfileBody().getTotalfollowers()));
                    postFollowing.setText(String.valueOf(profileModel.getProfileBody().getTotalfollowing()));
                    postLikes.setText(String.valueOf(profileModel.getProfileBody().getTotallikes()));
                    postDislikes.setText(String.valueOf(profileModel.getProfileBody().getTotaldislikes()));
                    adapterProfile = new AdapterProfile(getContext(), profileModel.getProfileBody().getPosts());
                    recyclerView.setAdapter(adapterProfile);

                } else {
                    Toast.makeText(getActivity(), "Internet Issues", Toast.LENGTH_SHORT).show();
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
                b = new Compressor(getActivity())
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
        ProgressDialog uploading = ProgressDialog.show(getActivity(), "Uploading File", "Please wait...", false, false);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", useridStr);

        MultipartRequest mr = new MultipartRequest(Api.SET_USER_PIC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                uploading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("success")) {
                        String profilePic = jsonObject.getJSONObject("body").getString("profileimgurl");
                        prefManager.putString(getActivity(), Const.profileimg, profilePic);
                        ((NavigationActivity) getActivity()).updateProfilePic();
                        Toast.makeText(getActivity(), "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Unable to update your profile picture", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }, selectFile, params);
        requestQueue.add(mr).setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0; //retry turn off
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

}