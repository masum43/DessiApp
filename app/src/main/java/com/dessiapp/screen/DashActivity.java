package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterDashboard;
import com.dessiapp.models.Body;
import com.dessiapp.models.DashDataModel;
import com.dessiapp.models.DashModel2;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class DashActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    AdapterDashboard adapterDashboard;
    List<DashDataModel> dataModelList;
    LinearLayout whtMind;
    PreferenceManager prefManager;
    String userID;
    //RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefresh;
    CircleImageView cirImg;
    String profImg;

    private InterstitialAd interstitialAd;
    private AdView adView;

    ArrayList<Object> mTargetData = new ArrayList<>();
    //private NativeAdsDataAdapter mAdapter;
    //private FBNativeAdAdapter mAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        prefManager = new PreferenceManager(getApplicationContext());
        userID = prefManager.getString(getApplicationContext(), Const.userid, "");
        profImg = prefManager.getString(getApplicationContext(), Const.profileimg, "");
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        whtMind = findViewById(R.id.whtMind);
        cirImg = findViewById(R.id.cirImg);

        AdSettings.addTestDevice("b0e85d9d-cfd6-4160-a9aa-a843ab4684b8");

        interstitialAd = new InterstitialAd(getApplicationContext(), getApplicationContext().getResources().getString(R.string.fb_interstetial_placement));
        adView = new AdView(getApplicationContext(), getApplicationContext().getResources().getString(R.string.fb_medium_placement), AdSize.RECTANGLE_HEIGHT_250);

        if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getApplicationContext()).load(profImg).into(cirImg);

        //callApi();
        loadApi();
        whtMind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(getApplicationContext(), CreatePostActivity.class), 111);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApi();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        /*mAdapter = new NativeAdsDataAdapter(this, mTargetData);
        recyclerView.setAdapter(mAdapter);*/
        //mAdapter1 = new FBNativeAdAdapter(this, mTargetData);



    }

    void loadApi() {
        //ProgressDialog uploading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
        Call<DashModel2> callApi = ApiCaller.getInstance().getAllPost(userID);
        callApi.enqueue(new Callback<DashModel2>() {
            @Override
            public void onResponse(Call<DashModel2> call, retrofit2.Response<DashModel2> response) {
                //uploading.dismiss();
                swipeRefresh.setRefreshing(false);
                DashModel2 dashModel2 = response.body();
                if (dashModel2 != null && dashModel2.getStatus().equals(Const.SUCCESS)) {
                    /*if(adapterDashboard==null) {
                    adapterDashboard = new AdapterDashboard(getApplicationContext(), dashModel2.getBody(), userID, interstitialAd, adView);
                    recyclerView.setAdapter(adapterDashboard);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    }else{
                        adapterDashboard.setNewData(dashModel2.getBody());
                    }*/
                    mTargetData.clear();
                    for (Body var : dashModel2.getBody())
                    {
                        mTargetData.add(var);
                    }
                   /* mAdapter.notifyDataSetChanged();
                    mAdapter.initNativeAds();*/
                } else {
                    Toast.makeText(getApplicationContext(), (dashModel2.getMessage() != null) ? dashModel2.getMessage() : "Internet Issues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashModel2> call, Throwable t) {

                //uploading.dismiss();
            }
        });
    }


    @Override
    public void onDestroy() {
        if (interstitialAd != null)
            interstitialAd.destroy();
        if(adView!=null)
            adView.destroy();
        super.onDestroy();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whtMind:
                startActivity(new Intent(getApplicationContext(), CreatePostActivity.class));
                break;
            case R.id.whtMind1:
                startActivity(new Intent(getApplicationContext(), CreatePostActivity.class));
                break;
        }
    }

    private void registerClick(int[] buttonResId) {
        for (int i = 0; i < buttonResId.length; i++) {
            findViewById(buttonResId[i]).setOnClickListener(this);
        }
    }


}