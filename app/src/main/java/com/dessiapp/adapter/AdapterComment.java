package com.dessiapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.dessiapp.R;
import com.dessiapp.models.CommentModel;
import com.dessiapp.models.PeopleListModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.AdapterViewHolder> {

    Context context;
    List<CommentModel.CommentBody> listData;
    String userId;
    PreferenceManager prefManager;

    public AdapterComment(Context context, List<CommentModel.CommentBody> list) {
        this.listData = list;
        this.context = context;
        prefManager = new PreferenceManager(context);
        userId = prefManager.getString(context, Const.userid, "");
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder hol, int pos) {

        CommentModel.CommentBody model=listData.get(pos);

        hol.comment.setText(model.getComment());
        hol.name.setText(model.getCommentbyname());
        try {
            hol.neigh.setText(Const.getDateFormat(model.getCommentedon()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {

        CircleImageView dpImg;
        TextView name,
                neigh,
                comment;


        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            dpImg=itemView.findViewById(R.id.dpImg);
            name=itemView.findViewById(R.id.name);
            neigh=itemView.findViewById(R.id.neigh);
            comment=itemView.findViewById(R.id.comment);

        }


    }
}
