package com.dessiapp.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterDashboard;
import com.dessiapp.adapter.AdapterPeoples;
import com.dessiapp.models.DashDataModel;
import com.dessiapp.models.DashModel2;
import com.dessiapp.models.PeoplesModel;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.CallFor;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.dessiapp.provider.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

//import android.util.JsonReader;

public class PeoplesFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    AdapterPeoples adapterPeople;
    //LinearLayout whtMind;
    PreferenceManager prefManager;
    String userID;
    RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_peoples, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        prefManager = new PreferenceManager(getActivity());
        userID = prefManager.getString(getActivity(), Const.userid, "");
        recyclerView = v.findViewById(R.id.recyclerView);
        swipeRefresh = v.findViewById(R.id.swipeRefresh);
        //whtMind = v.findViewById(R.id.whtMind);
        callApi();
        /*whtMind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), CreatePostActivity.class), 111);
            }
        });*/
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            callApi();
        }
    }

    void callApi() {
        Call<PeoplesModel> callApi = ApiCaller.getInstance().getAppPeopleName(userID);
        callApi.enqueue(new Callback<PeoplesModel>() {
            @Override
            public void onResponse(Call<PeoplesModel> call, retrofit2.Response<PeoplesModel> response) {
                if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
                PeoplesModel peoplesModel = response.body();
                if (peoplesModel != null && peoplesModel.getStatus().equals(Const.SUCCESS)) {
                    adapterPeople = new AdapterPeoples(getContext(), peoplesModel.getBody(), CallFor.PEOPLE);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(adapterPeople);

                    //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Internet Issues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PeoplesModel> call, Throwable t) {

            }
        });
    }


   /* @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whtMind:
                startActivity(new Intent(getActivity(), CreatePostActivity.class));
                break;
            case R.id.whtMind1:
                startActivity(new Intent(getActivity(), CreatePostActivity.class));
                break;
        }
    }*/

}