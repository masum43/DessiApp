package com.dessiapp.screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterPeoples;
import com.dessiapp.adapter.AdapterSearch;
import com.dessiapp.models.PeoplesModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.CallFor;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;

public class FollowingActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    AdapterSearch adapterPeople;
    LinearLayout whtMind;
    PreferenceManager prefManager;
    String userID;
    RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefresh;

    Toolbar toolbar;
    ImageView imgArrow;
    TextView textTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Followings");
        imgArrow.setOnClickListener(this);

        prefManager = new PreferenceManager(FollowingActivity.this);
        userID = prefManager.getString(getApplicationContext(), Const.userid, "");
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        //whtMind = findViewById(R.id.whtMind);
        callApi();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            callApi();
        }
    }

    private void registerClick(int[] buttonResId) {
        for (int i = 0; i < buttonResId.length; i++) {
            findViewById(buttonResId[i]).setOnClickListener(this);
        }
    }

    void callApi() {
        Call<PeoplesModel> callApi = ApiCaller.getInstance().getFollowing(userID);
        callApi.enqueue(new Callback<PeoplesModel>() {
            @Override
            public void onResponse(Call<PeoplesModel> call, retrofit2.Response<PeoplesModel> response) {
                if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
                PeoplesModel peoplesModel = response.body();
                if (peoplesModel != null && peoplesModel.getStatus().equals(Const.SUCCESS)) {
                    adapterPeople = new AdapterSearch(getApplicationContext(), peoplesModel.getBody(), CallFor.FOLLOWING);
                    recyclerView.setAdapter(adapterPeople);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Issues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PeoplesModel> call, Throwable t) {

            }
        });
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
            case R.id.imgArrow:
                onBackPressed();
                break;
        }
    }
}