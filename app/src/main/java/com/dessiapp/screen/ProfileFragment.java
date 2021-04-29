package com.dessiapp.screen;

import android.app.Activity;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterImgShow;
import com.dessiapp.adapter.AdapterPeoples;
import com.dessiapp.models.PeoplesModel;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.MultipartRequest;
import com.dessiapp.provider.PreferenceManager;
import com.dessiapp.provider.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

//import android.util.JsonReader;

public class ProfileFragment extends Fragment {

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        prefManager = new PreferenceManager(getActivity());
        useridStr = prefManager.getString(getActivity(), Const.userid, "");
        profImg = prefManager.getString(getActivity(), Const.profileimg, "");
        profilePic = v.findViewById(R.id.profilePic);
        iv_camera = v.findViewById(R.id.iv_camera);
        logoutClick = v.findViewById(R.id.logoutClick);
        memberSinceTxt = v.findViewById(R.id.memberSinceTxt);
        emailtextview = v.findViewById(R.id.emailtextview);
        mobiletextview = v.findViewById(R.id.mobiletextview);
        dobTxt = v.findViewById(R.id.dobTxt);
        gendertextview = v.findViewById(R.id.gendertextview);
        userName = v.findViewById(R.id.userName);
        userId = v.findViewById(R.id.userId);

        emailtextview.setText(prefManager.getString(getActivity(), Const.email, ""));
        mobiletextview.setText(prefManager.getString(getActivity(), Const.mobileno, ""));
        dobTxt.setText(prefManager.getString(getActivity(), Const.age, ""));
        gendertextview.setText(Const.getGenderName(prefManager.getString(getActivity(), Const.gender, "")));
        userId.setText("@" + prefManager.getString(getActivity(), Const.userid, ""));
        userName.setText(prefManager.getString(getActivity(), Const.username, ""));
        memberSinceTxt.setText(prefManager.getString(getActivity(), Const.createdon, ""));

        if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getActivity()).load(profImg).into(profilePic);


        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CropImage.activity().setAspectRatio(1, 1).setGuidelines(CropImageView.Guidelines.ON).setAllowRotation(true)
                        .getIntent(getActivity());
                startActivityForResult(intent, 163);
            }
        });

        logoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.removeKeyValue(getActivity(), Const.serialno);
                ;
                prefManager.removeKeyValue(getActivity(), Const.userid);
                ;
                prefManager.removeKeyValue(getActivity(), Const.username);
                ;
                prefManager.removeKeyValue(getActivity(), Const.age);
                ;
                prefManager.removeKeyValue(getActivity(), Const.gender);
                ;
                prefManager.removeKeyValue(getActivity(), Const.password);
                ;
                prefManager.removeKeyValue(getActivity(), Const.mobileno);
                ;
                prefManager.removeKeyValue(getActivity(), Const.email);
                ;
                prefManager.removeKeyValue(getActivity(), Const.role);
                ;
                prefManager.removeKeyValue(getActivity(), Const.status);
                ;
                prefManager.removeKeyValue(getActivity(), Const.state);
                ;
                prefManager.removeKeyValue(getActivity(), Const.city);
                ;
                prefManager.removeKeyValue(getActivity(), Const.statename);
                ;
                prefManager.removeKeyValue(getActivity(), Const.cityname);
                ;
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finishAffinity();

            }
        });

        return v;
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