package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dessiapp.R;
import com.dessiapp.adapter.AdapterComment;
import com.dessiapp.adapter.AdapterEarning;
import com.dessiapp.models.CommentModel;
import com.dessiapp.models.RewardModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;

public class EarningActivity extends AppCompatActivity {

    ImageView imgArrow;
    PreferenceManager prefMan;
    String userid;
    RewardModel rewardModel;
    TextView rewardMoney,
            withdrwn,
            minAmount,
            likeCount;
    RecyclerView recyclerView;
    AdapterEarning adapterEarning;
    Button redeem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        prefMan = new PreferenceManager(getApplicationContext());
        userid = prefMan.getString(getApplicationContext(), Const.userid, "");
        imgArrow = findViewById(R.id.imgArrow);
        rewardMoney = findViewById(R.id.rewardMoney);
        withdrwn = findViewById(R.id.withdrwn);
        minAmount = findViewById(R.id.minAmount);
        likeCount = findViewById(R.id.likeCountValue);
        recyclerView = findViewById(R.id.recyclerView);
        redeem = findViewById(R.id.redeem);
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }); redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onBackPressed();
            }
        });
        loadEarning();
    }

    void loadEarning() {
        Call<RewardModel> callApi = ApiCaller.getInstance().getRewards(userid);
        callApi.enqueue(new Callback<RewardModel>() {
            @Override
            public void onResponse(Call<RewardModel> call, retrofit2.Response<RewardModel> response) {
                RewardModel rewardModel = response.body();
                if (rewardModel.getStatus().equals(Const.SUCCESS)) {
                    withdrwn.setText("₹ " + rewardModel.getRewardBody().getRewardDetails().getAvailablereward() + "/-");
                    rewardMoney.setText("₹ " + rewardModel.getRewardBody().getRewardDetails().getRewardclaimed() + "/-");
                    likeCount.setText(rewardModel.getRewardBody().getRewardDetails().getTotallikes().toString());
                    minAmount.setText("Min. Amount redeem: ₹ " + rewardModel.getRewardBody().getRewardDetails().getMinclaimamount().toString() + "/-");
                    adapterEarning = new AdapterEarning(getApplicationContext(), rewardModel.getRewardBody().getRewardHistory());
                    recyclerView.setAdapter(adapterEarning);
                } else {
                    Toast.makeText(EarningActivity.this, rewardModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RewardModel> call, Throwable t) {

            }
        });
    }

    void redeem() {
        Call<RewardModel> callApi = ApiCaller.getInstance().getRewards(userid);
        callApi.enqueue(new Callback<RewardModel>() {
            @Override
            public void onResponse(Call<RewardModel> call, retrofit2.Response<RewardModel> response) {
                RewardModel rewardModel = response.body();
                if (rewardModel.getStatus().equals(Const.SUCCESS)) {
                    withdrwn.setText("₹ " + rewardModel.getRewardBody().getRewardDetails().getAvailablereward() + "/-");
                    rewardMoney.setText("₹ " + rewardModel.getRewardBody().getRewardDetails().getRewardclaimed() + "/-");
                    likeCount.setText(rewardModel.getRewardBody().getRewardDetails().getTotallikes().toString());
                    minAmount.setText("Min. Amount redeem: ₹ " + rewardModel.getRewardBody().getRewardDetails().getMinclaimamount().toString() + "/-");
                    adapterEarning = new AdapterEarning(getApplicationContext(), rewardModel.getRewardBody().getRewardHistory());
                    recyclerView.setAdapter(adapterEarning);
                } else {
                    Toast.makeText(EarningActivity.this, rewardModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RewardModel> call, Throwable t) {

            }
        });
    }
}