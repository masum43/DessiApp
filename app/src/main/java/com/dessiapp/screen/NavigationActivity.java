package com.dessiapp.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.MyAdapter;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.dessiapp.utility.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    MyAdapter adapter;
    PreferenceManager prefManager;
    String profImg;
    CircleImageView cirImg;
    ImageView setting, imgSearch;
    //private AdView mAdView;
    //private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        prefManager = new PreferenceManager(getApplicationContext());
        setToken();
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        cirImg = findViewById(R.id.cirImg);
        //imgAdd = findViewById(R.id.imgAdd);
        imgSearch = findViewById(R.id.imgSearch);
        setting = findViewById(R.id.setting);
        updateProfilePic();
        adapter = new MyAdapter(NavigationActivity.this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);
        Const.strusername = prefManager.getString(getApplicationContext(), Const.username, "");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
        updateStatusBarColor("#ffffff");
    }

    public void updateStatusBarColor(String color) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void setUserToken(String tokenId) {
        Log.e("TAG1", "setUserToken: " + tokenId);
        String userid = prefManager.getString(getApplicationContext(), Const.userid, "");
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("userId", userid);
            mainObj.put("token", tokenId);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.SETFIREBASETOKEN, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // code run is got response
                    Log.e("setUserToken success", response.toString());
                    Const.firebaseToken = tokenId;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("setUserToken error", "onErrorResponse: " + error.getMessage());
                    // code run is got error
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put(Const.HEAD_TOKEN, Const.TOKEN_KEY);
                    return header;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("setUserToken ex", "onErrorResponse: " + e.getMessage());
        }
    }


    void updateProfilePic() {

        profImg = prefManager.getString(getApplicationContext(), Const.profileimg, "");

        if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getApplicationContext()).load(profImg).into(cirImg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onPause() {
        /*if (mAdView != null) {
            mAdView.pause();
        }*/
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (mAdView != null) {
            mAdView.resume();
        }*/
    }

    @Override
    public void onDestroy() {
        /*if (mAdView != null) {
            mAdView.destroy();
        }*/
        super.onDestroy();
    }

    void setToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }
                        // Get new FCM registration token
                        String tokenId = task.getResult();
                        Log.e("token", "" + tokenId);

                        setUserToken(tokenId);


                    }
                });
    }


}