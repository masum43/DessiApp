package com.dessiapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.models.ProfileModel;
import com.dessiapp.screen.CommentActivity;
import com.dessiapp.screen.CommentProfileActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.AdapterImgView> {

    Context context;
    List<ProfileModel.Post> arrayList;

    public AdapterProfile(Context context, List<ProfileModel.Post> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AdapterImgView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_profile, viewGroup, false);
        return new AdapterImgView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImgView holder, final int i) {
        ProfileModel.Post model = arrayList.get(i);
        if (!model.getFilepath().equals("") && model.getFilepath() != null && !model.getFilepath().equals("null"))
            Glide.with(context).load(model.getFilepath()).into(holder.picImg);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json = new Gson().toJson(model);
                context.startActivity(new Intent(context, CommentProfileActivity.class).putExtra("model", json));
            }
        });

    }

    public void removePostion(int position) {

        arrayList.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class AdapterImgView extends RecyclerView.ViewHolder {

        ImageView picImg;
        RelativeLayout layout;

        public AdapterImgView(@NonNull View itemView) {
            super(itemView);
            picImg = itemView.findViewById(R.id.picImg);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}
