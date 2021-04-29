package com.dessiapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dessiapp.R;

import java.util.ArrayList;

public class AdapterImgShow extends RecyclerView.Adapter<AdapterImgShow.AdapterImgView> {

    Context context;
    ArrayList<Uri> arrayList;

    public AdapterImgShow(Context context, ArrayList<Uri> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AdapterImgView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.custom_img_show,viewGroup,false);
        return new AdapterImgView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImgView holder, final int i) {


        holder.imageView.setImageURI(arrayList.get(i));

        holder.deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removePostion(i);

            }
        });



    }

    public void removePostion(int position){

        arrayList.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class AdapterImgView extends RecyclerView.ViewHolder{

        ImageView imageView,deletePost;

        public AdapterImgView(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.picEvent);
            deletePost=itemView.findViewById(R.id.deletePost);
        }
    }
}
