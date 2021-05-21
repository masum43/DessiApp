package com.dessiapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.models.Body;
import com.dessiapp.models.PeopleListModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.CallFor;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.dessiapp.screen.ViewOtherProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class AdapterPeoples extends RecyclerView.Adapter<AdapterPeoples.AdapterViewHolder> {

    Context context;
    List<PeopleListModel> listData;
    String userId;
    PreferenceManager prefManager;
    CallFor callFor;

    public AdapterPeoples(Context context, List<PeopleListModel> list, CallFor callFor) {
        this.listData = list;
        this.context = context;
        this.callFor = callFor;
        prefManager = new PreferenceManager(context);
        userId = prefManager.getString(context, Const.userid, "");
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_peoples, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder hol, int pos) {

        PeopleListModel model = listData.get(pos);
        hol.userName.setText(model.getUsername());
        hol.userId.setText("@" + model.getUserid());
        hol.setType(model, userId, pos);
        if (model.getProfileimg() != null && !model.getProfileimg().equals(""))
            Glide.with(context).load(model.getProfileimg()).into(hol.postUserPic);
        hol.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ViewOtherProfileActivity.class).putExtra("userID",model.getUserid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName,
                userId;

        PeopleListModel peopleModel;
        AppCompatButton folloWClick, unfolloWClick;
        String userIdValue;
        int pos;
        CircleImageView postUserPic;


        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userId = itemView.findViewById(R.id.userId);
            folloWClick = itemView.findViewById(R.id.folloWClick);
            unfolloWClick = itemView.findViewById(R.id.unfolloWClick);
            postUserPic = itemView.findViewById(R.id.postUserPic);
            folloWClick.setOnClickListener(this);
            unfolloWClick.setOnClickListener(this);
        }

        void setType(PeopleListModel peopleModel, String userIdValue, int pos) {
            this.peopleModel = peopleModel;
            this.userIdValue = userIdValue;
            this.pos = pos;

            if (callFor == CallFor.PEOPLE) {
                if (peopleModel.getIsFollowing()) {
                    unfolloWClick.setVisibility(View.VISIBLE);
                    folloWClick.setVisibility(View.GONE);
                } else {
                    unfolloWClick.setVisibility(View.GONE);
                    folloWClick.setVisibility(View.VISIBLE);
                }
            } else if (callFor == CallFor.FOLLOWERS) {
                unfolloWClick.setVisibility(View.GONE);
                folloWClick.setVisibility(View.GONE);
            } else {
                unfolloWClick.setVisibility(View.VISIBLE);
                folloWClick.setVisibility(View.GONE);
            }

        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.folloWClick:
                    if (callFor == CallFor.PEOPLE)
                        followApi();
                    break;
                case R.id.unfolloWClick:
                    unfollowApi();
                    break;
            }
        }


        void followApi() {
            Call<Object> callApi = ApiCaller.getInstance().requestFollow(userIdValue, peopleModel.getUserid());
            callApi.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    try {
                        JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                        if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                            folloWClick.setVisibility(View.GONE);
                            unfolloWClick.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(context, jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                       // Toast.makeText(context, jOb.getString(Const.STATUS), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }

        void unfollowApi() {
            Call<Object> callApi = ApiCaller.getInstance().requestUnFollow(userIdValue, peopleModel.getUserid());
            callApi.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    try {
                        JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                        if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                            if (callFor == CallFor.PEOPLE) {
                                unfolloWClick.setVisibility(View.GONE);
                                folloWClick.setVisibility(View.VISIBLE);
                            } else {
                                listData.remove(pos);
                                notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(context, jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(context, jOb.getString(Const.STATUS), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }
    }
}
