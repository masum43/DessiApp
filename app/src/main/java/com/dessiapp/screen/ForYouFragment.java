package com.dessiapp.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterDashboard;
import com.dessiapp.models.DashDataModel;
import com.dessiapp.models.DashModel2;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class ForYouFragment extends Fragment implements View.OnClickListener {

    View v;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_for_you, container, false);
        //requestQueue = Volley.newRequestQueue(getActivity());
        prefManager = new PreferenceManager(getActivity());
        userID = prefManager.getString(getActivity(), Const.userid, "");
        profImg = prefManager.getString(getActivity(), Const.profileimg, "");
        recyclerView = v.findViewById(R.id.recyclerView);
        swipeRefresh = v.findViewById(R.id.swipeRefresh);
        whtMind = v.findViewById(R.id.whtMind);
        cirImg = v.findViewById(R.id.cirImg);

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

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            loadApi();
        }
    }


    void loadApi() {
        Call<DashModel2> callApi = ApiCaller.getInstance().getAllPost(userID);
        callApi.enqueue(new Callback<DashModel2>() {
            @Override
            public void onResponse(Call<DashModel2> call, retrofit2.Response<DashModel2> response) {
                swipeRefresh.setRefreshing(false);
                //System.out.println(response.body());
                DashModel2 dashModel2 = response.body();
                if (dashModel2 != null && dashModel2.getStatus().equals(Const.SUCCESS)) {
                    adapterDashboard = new AdapterDashboard(getContext(), dashModel2.getBody(), userID);
                    recyclerView.setAdapter(adapterDashboard);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), (dashModel2.getMessage() != null) ? dashModel2.getMessage() : "Internet Issues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashModel2> call, Throwable t) {

            }
        });
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
}