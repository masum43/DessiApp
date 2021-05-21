package com.dessiapp.screen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterDashboard;
import com.dessiapp.adapter.AdapterDashboard1;
import com.dessiapp.models.Body;
import com.dessiapp.models.DashDataModel;
import com.dessiapp.models.DashModel2;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

public class ForYouFragment extends Fragment implements View.OnClickListener, AdapterDashboard1.Onclick {

    View v;
    RecyclerView recyclerView;
    AdapterDashboard adapterDashboard;
    AdapterDashboard1 adapterDashboard1;
    List<DashDataModel> dataModelList;
    LinearLayout whtMind;
    PreferenceManager prefManager;
    String userID;
    SwipeRefreshLayout swipeRefresh;
    CircleImageView cirImg;
    String profImg;

    private InterstitialAd interstitialAd;
    //private AdView adView;
    List<Body> allList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_for_you, container, false);
        prefManager = new PreferenceManager(getActivity());
        userID = prefManager.getString(getActivity(), Const.userid, "");
        profImg = prefManager.getString(getActivity(), Const.profileimg, "");
        recyclerView = v.findViewById(R.id.recyclerView);
        swipeRefresh = v.findViewById(R.id.swipeRefresh);
        whtMind = v.findViewById(R.id.whtMind);
        cirImg = v.findViewById(R.id.cirImg);

        interstitialAd = new InterstitialAd(getActivity(), getActivity().getResources().getString(R.string.fb_interstetial_placement));
        //adView = new AdView(getActivity(), getActivity().getResources().getString(R.string.fb_medium_placement), AdSize.RECTANGLE_HEIGHT_250);

        if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getActivity()).load(profImg).into(cirImg);

        //callApi();
        loadApi();
        whtMind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(getActivity(), CreatePostActivity.class), 111);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApi();
            }
        });

        adapterDashboard1 = new AdapterDashboard1(getActivity(), allList, userID, interstitialAd, /*adView,*/ this);
        recyclerView.setAdapter(adapterDashboard1);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);



      /*  mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        // Load a reward based video ad
        mRewardedVideoAd.loadAd(getString(R.string.rewarded_video), new AdRequest.Builder().build());*/






        return v;
    }

    InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
        @Override
        public void onInterstitialDisplayed(Ad ad) {
            // Interstitial ad displayed callback
            Log.e(TAG, "Interstitial ad displayed.");
        }

        @Override
        public void onInterstitialDismissed(Ad ad) {
            // Interstitial dismissed callback
            Log.e(TAG, "Interstitial ad dismissed.");
        }

        @Override
        public void onError(Ad ad, AdError adError) {
            // Ad error callback
            Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
        }

        @Override
        public void onAdLoaded(Ad ad) {
            // Interstitial ad is loaded and ready to be displayed
            Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
            // Show the ad
            interstitialAd.show();
        }

        @Override
        public void onAdClicked(Ad ad) {
            // Ad clicked callback
            Log.d(TAG, "Interstitial ad clicked!");
        }

        @Override
        public void onLoggingImpression(Ad ad) {
            // Ad impression logged callback
            Log.d(TAG, "Interstitial ad impression logged!");
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            loadApi();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void loadApi() {
        Call<DashModel2> callApi = ApiCaller.getInstance().getAllPost(userID);
        callApi.enqueue(new Callback<DashModel2>() {
            @Override
            public void onResponse(Call<DashModel2> call, retrofit2.Response<DashModel2> response) {
                //uploading.dismiss();
                swipeRefresh.setRefreshing(false);
                DashModel2 dashModel2 = response.body();
                if (dashModel2 != null && dashModel2.getStatus().equals(Const.SUCCESS)) {
                    adapterDashboard1.setNewData(dashModel2.getBody());
                } else {
                    Toast.makeText(getActivity(), (dashModel2.getMessage() != null) ? dashModel2.getMessage() : "Internet Issues", Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(getActivity(), "On destroy", Toast.LENGTH_SHORT).show();
//        if (interstitialAd != null)
//            interstitialAd.destroy();
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whtMind:
                startActivity(new Intent(getActivity(), CreatePostActivity.class));
                break;
            case R.id.whtMind1:
                startActivity(new Intent(getActivity(), CreatePostActivity.class));
                break;
        }
    }

    @Override
    public void listner() {
        //Toast.makeText(getActivity(), "Hi buddy", Toast.LENGTH_SHORT).show();
        /*if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }else{
            Toast.makeText(getActivity(), "Ad not loaded yet", Toast.LENGTH_SHORT).show();
        }*/
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    @Override
    public void onResume() {
        //mRewardedVideoAd.resume(getActivity());
        Toast.makeText(getActivity(), "On Resume", Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    public void onPause() {
        //mRewardedVideoAd.pause(getActivity());
        Toast.makeText(getActivity(), "On Pause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        //mRewardedVideoAd.destroy(getActivity());
        Toast.makeText(getActivity(), "On Destroy  View", Toast.LENGTH_SHORT).show();
        super.onDestroyView();

    }
}