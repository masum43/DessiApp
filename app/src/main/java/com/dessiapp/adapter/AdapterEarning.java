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

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.models.PeopleListModel;
import com.dessiapp.models.RewardModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.CallFor;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class AdapterEarning extends RecyclerView.Adapter<AdapterEarning.AdapterViewHolder> {

    Context context;
    List<RewardModel.RewardHistory> listData;
    String userId;
    PreferenceManager prefManager;

    public AdapterEarning(Context context, List<RewardModel.RewardHistory> list) {
        this.listData = list;
        this.context = context;
        prefManager = new PreferenceManager(context);
        userId = prefManager.getString(context, Const.userid, "");
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_reward, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder hol, int pos) {

        RewardModel.RewardHistory model = listData.get(pos);

        hol.paymentStatus.setText((model.getStatus() == 0) ? "Pending" : "Completed");
        hol.amountPayfor.setText("Amount :₹ " + model.getAmountclaimed() + "/-");
        hol.payThrough.setText("Pay through :" + model.getTransactionmethod());
        try {
            hol.dateRequest.setText("Date : " + Const.getDateFormat(model.getRequestedon()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView paymentStatus,
                amountPayfor,
                payThrough,
                dateRequest;


        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentStatus = itemView.findViewById(R.id.paymentStatus);
            amountPayfor = itemView.findViewById(R.id.amountPayfor);
            payThrough = itemView.findViewById(R.id.payThrough);
            dateRequest = itemView.findViewById(R.id.dateRequest);
        }

    }
}
