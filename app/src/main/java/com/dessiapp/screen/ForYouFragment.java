package com.dessiapp.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class ForYouFragment extends Fragment implements View.OnClickListener/*, AdapterDashboard1.Onclick*/ {

    View v;
    RecyclerView recyclerView;
//    AdapterDashboard adapterDashboard;
    AdapterDashboard1 adapterDashboard1;
    List<DashDataModel> dataModelList;
    LinearLayout whtMind;
    PreferenceManager prefManager;
    String userID;
    SwipeRefreshLayout swipeRefresh;
    CircleImageView cirImg;
    String profImg;
    int ScrollPosition = 0;

    List<Body> allPosts;
    List<Body> somePosts = new ArrayList<>();
    int ScrollStartIndex=0,ScrollEndIndex = 0;
    boolean isPostsLoading = false;
    NestedScrollView nestedScrollVIew;
    public static int commentCount = 0;
    public static int itemPosition = 0;




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
        nestedScrollVIew = v.findViewById(R.id.nestedScrollVIew);
        recyclerView.setNestedScrollingEnabled(false);
        whtMind = v.findViewById(R.id.whtMind);
        cirImg = v.findViewById(R.id.cirImg);

        if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getActivity()).load(profImg).into(cirImg);

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


        adapterDashboard1 = new AdapterDashboard1(getActivity(), somePosts, userID );
        recyclerView.setAdapter(adapterDashboard1);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
//        adapterDashboard1.setHasStableIds(true);
        recyclerView.setHasFixedSize(true);


        nestedScrollVIew.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int sp = nestedScrollVIew.getScrollY();
                if(sp>=ScrollPosition){
                    ScrollPosition = sp;
                }
                Log.e("ScrollPosition", "onScrollChange: "+ScrollPosition );
                if(isPostsLoading) return;
                if (!nestedScrollVIew.canScrollVertically(1) && scrollY > 0){
                    loadMorePost();
                }else if (!recyclerView.canScrollVertically(-1) && scrollY < 0){
                   // Log.e("onScrolled", "TOP" );
                    //scrolled to TOP
                }
            }
        });

        //callApi();
        loadApi();
        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            loadApi();
        }
    }
    private void loadMorePost(){
        if(ScrollEndIndex>=allPosts.size()) return;

        isPostsLoading = true;
        ScrollStartIndex=ScrollEndIndex;
        ScrollEndIndex = ScrollEndIndex + 5;
//        Log.e("loadMorePost", ScrollStartIndex + ""+ScrollEndIndex + " "+ allPosts.size());
        boolean flag = false;
        for(int i=ScrollStartIndex;i<ScrollEndIndex;i++){
            if(i<allPosts.size()){
                flag = true;
                somePosts.add(allPosts.get(i));

            }
        }
        nestedScrollVIew.stopNestedScroll();
        recyclerView.stopScroll();
        Log.e("ScrollPosition", "loadMorePost: "+ScrollPosition );
//        Log.e("loadMorePost", "loadMorePost: "+ScrollPosition );

        if(flag){
        adapterDashboard1.notifyDataSetChanged();

//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            adapterDashboard1.notifyDataSetChanged();
//                        }
//                    });
//                }
//            }, 100);
        }


//        if(ScrollPosition>0)
//            nestedScrollVIew.setScrollY(ScrollPosition);

        nestedScrollVIew.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        isPostsLoading = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(somePosts.size()>0 && itemPosition<somePosts.size()){
            somePosts.get(itemPosition).setComments(commentCount);
            allPosts.get(itemPosition).setComments(commentCount);
            adapterDashboard1.notifyItemRemoved(itemPosition);
            adapterDashboard1.notifyItemRangeChanged(itemPosition,somePosts.size());
        }

    }

    void loadApi() {
        somePosts.clear();
        adapterDashboard1.notifyDataSetChanged();
        ScrollStartIndex=0;
        ScrollEndIndex = 0;
        ScrollPosition = 0;
        Call<DashModel2> callApi = ApiCaller.getInstance().getAllPost(userID,"1");
        callApi.enqueue(new Callback<DashModel2>() {
            @Override
            public void onResponse(Call<DashModel2> call, retrofit2.Response<DashModel2> response) {
                //uploading.dismiss();
                swipeRefresh.setRefreshing(false);
                DashModel2 dashModel2 = response.body();
                if (dashModel2 != null && dashModel2.getStatus().equals(Const.SUCCESS)) {
                    allPosts = dashModel2.getBody();
                    loadMorePost();
                } else {
                    Toast.makeText(getActivity(), (dashModel2.getMessage() != null) ? dashModel2.getMessage() : "Internet Issues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashModel2> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
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