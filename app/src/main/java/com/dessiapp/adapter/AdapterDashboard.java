package com.dessiapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.models.Body;
import com.dessiapp.models.DashDataModel;
import com.dessiapp.models.DashModel2;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.screen.CommentActivity;
import com.dessiapp.screen.PhotoViewActivity;
import com.dessiapp.screen.ViewOtherProfileActivity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.AdapterViewHolder> {

    Context context;
    List<Body> listData;
    public String userid;
    /*private InterstitialAd interstitialAd;
    private AdView adView;*/

    public AdapterDashboard(Context context, List<Body> list, String userid /*,InterstitialAd interstitialAd, AdView adView*/) {
        this.listData = list;
        this.context = context;
        this.userid = userid;
       /* this.interstitialAd = interstitialAd;
        this.adView = adView;*/
    }

   /* InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
        @Override
        public void onInterstitialDisplayed(Ad ad) {
            // Interstitial ad displayed callback
            Log.e(TAG, "Interstitial ad displayed.");
        }

        @Override
        public void onInterstitialDismissed(Ad ad) {
            // Interstitial dismissed callback
            Log.e(TAG, "Interstitial ad dismissed.");
        }

        @Override
        public void onError(Ad ad, AdError adError) {
            // Ad error callback
            Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
        }

        @Override
        public void onAdLoaded(Ad ad) {
            // Interstitial ad is loaded and ready to be displayed
            Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
            // Show the ad
            interstitialAd.show();
        }

        @Override
        public void onAdClicked(Ad ad) {
            // Ad clicked callback
            Log.d(TAG, "Interstitial ad clicked!");
        }

        @Override
        public void onLoggingImpression(Ad ad) {
            // Ad impression logged callback
            Log.d(TAG, "Interstitial ad impression logged!");
        }
    };*/


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder hol, int pos) {

        Body model = listData.get(pos);

        hol.userName.setText(model.getUsername());
        if (model.getFilepath() != null && !model.getFilepath().equals("null") && !model.getFilepath().equals(""))
            Glide.with(context).load(model.getFilepath()).into(hol.picPost);
        else
            hol.picPost.setVisibility(View.GONE);
        if (model.getProfileimg() != null && !model.getProfileimg().equals("null") && !model.getProfileimg().equals(""))
            Glide.with(context).load(model.getProfileimg()).into(hol.postUserPic);
        hol.userId.setText("@" + model.getPostedby());
        try {
            hol.dateTime.setText(Const.getDateFormat(model.getPostedon()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (model.getPostdesc() != null && !model.getPostdesc().equals("null") && !model.getPostdesc().equals(""))
            hol.actiTag.setText(model.getPostdesc());

        if (model.getActivity() != null && !model.getActivity().equals("null") && !model.getActivity().equals(Const.DEFAULT) && !model.getActivity().equals("")) {
            hol.actTyp.setVisibility(View.VISIBLE);
            hol.actTyp.setText(model.getActivity());
        }

        hol.setTrip(model, pos, userid);

        hol.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ViewOtherProfileActivity.class).putExtra("userID", model.getUserid()));
            }
        });

       /* if ((pos + 1) % 4 == 0) {
            hol.adContainer.removeView(adView);
            hol.adContainer.addView(adView);
            adView.loadAd();
            *//*int drawableId = R.drawable.ic_add_black;
            if (drawableId == 0) return;
            ImageView badge = new ImageView(context);
            badge.setImageResource(drawableId);
            int size = (int) convertDpToPx(55.0f);
            badge.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            hol.adContainer.addView(badge);*//*

            hol.viewAbove.setVisibility(View.VISIBLE);
        } else {
            //adContainer.removeAllViews();
            hol.viewAbove.setVisibility(View.GONE);
            hol.adContainer.setVisibility(View.GONE);
        }*/

    }

    public void setNewData(List<Body> listData) {
      //  adView.buildLoadAdConfig();
        this.listData.clear();
        this.listData = listData;
        notifyDataSetChanged();
    }
    private float convertDpToPx(float dp) {
        return dp * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView postUserPic;
        TextView userName, userId, actTyp, actiTag, dateTime;
        ImageView picPost;
        LinearLayout likelayout, unlikelayout;
        LinearLayout likelayout1, unlikelayout1;

        Body body;
        int index;
        String userid;
        TextView likeCount, unlikeCount, cmntCount;

        //        AdView adView;
        LinearLayout commentlayout, shareLay;
        /*LinearLayout adContainer;
        View viewAbove;*/

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserPic = itemView.findViewById(R.id.postUserPic);
            userName = itemView.findViewById(R.id.userName);
            userId = itemView.findViewById(R.id.userId);
            actTyp = itemView.findViewById(R.id.actTyp);
            actiTag = itemView.findViewById(R.id.actiTag);
            picPost = itemView.findViewById(R.id.picPost);
            dateTime = itemView.findViewById(R.id.dateTime);
            likelayout = itemView.findViewById(R.id.likelayout);
            unlikelayout = itemView.findViewById(R.id.unlikelayout);
            likelayout1 = itemView.findViewById(R.id.likelayout1);
            unlikelayout1 = itemView.findViewById(R.id.unlikelayout1);
            likeCount = itemView.findViewById(R.id.likeCount);
            unlikeCount = itemView.findViewById(R.id.unlikeCount);
            commentlayout = itemView.findViewById(R.id.commentlayout);
            cmntCount = itemView.findViewById(R.id.cmntCount);
            shareLay = itemView.findViewById(R.id.shareLay);
            /*adContainer = itemView.findViewById(R.id.bannerContainer);
            viewAbove = itemView.findViewById(R.id.viewAbove);*/
            //adView = itemView.findViewById(R.id.adView);

            unlikelayout1.setOnClickListener(this);
            likelayout1.setOnClickListener(this);
            likelayout.setOnClickListener(this);
            unlikelayout.setOnClickListener(this);
            commentlayout.setOnClickListener(this);
            shareLay.setOnClickListener(this);
            postUserPic.setOnClickListener(this);
        }

        void setTrip(Body body, int i, String userid) {
            this.body = body;
            this.userid = userid;
            index = i;

            likeCount.setText(body.getLikes().toString() + " Like");
            unlikeCount.setText(body.getDislikes().toString() + " Disliked");
            cmntCount.setText(body.getComments().toString() + " Comments");

            if (body.isLikedbyMe()) {
                likelayout1.setVisibility(View.VISIBLE);
                likelayout.setVisibility(View.GONE);
            } else {
                likelayout1.setVisibility(View.GONE);
                likelayout.setVisibility(View.VISIBLE);
            }
            if (body.isIsdisLikedbyMe()) {
                unlikelayout1.setVisibility(View.VISIBLE);
                unlikelayout.setVisibility(View.GONE);
            } else {
                unlikelayout1.setVisibility(View.GONE);
                unlikelayout.setVisibility(View.VISIBLE);
            }
            //adContainer.removeAllViews();


        }

        void sendingShare() {
            String appLink = "https://drive.google.com/file/d/10kcjmLNpkIN9MHzAevlAD0yfQEL7Ez5Y/view?usp=sharing";
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Bitmap bd = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_name);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bd.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                Uri uri = Uri.fromFile(fileExist(bd));
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpeg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                //shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                String shareMessage = "Sharing a post from DessiApp";
                shareMessage = shareMessage + "\n--------" + "\nDessiApp is a private social network for connecting neighbours easily.\n\n\n Here is the Apk Link " + appLink;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Interesting Inputs from DessiApp");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(shareIntent, "Select the App"));
            } catch (Exception e) {
                //e.toString();
            }
        }

        File fileExist(Bitmap bm) {

            File file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/dessiapp/logo.PNG");
            if (!file.exists()) {
                File name = SaveImage(bm);
                return name;
            }
            return file;
        }

        private File SaveImage(Bitmap finalBitmap) {

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/dessiapp");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }

            String fname = "logo.PNG";
            File file = new File(myDir, fname);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return file;
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.likelayout:
                   /* interstitialAd.loadAd(
                            interstitialAd.buildLoadAdConfig()
                                    .withAdListener(interstitialAdListener)
                                    .build());*/
                    likeApi();
                    break;
                case R.id.likelayout1:
                    likeRemoveApi();
                    break;
                case R.id.unlikelayout:
                    dislikeApi();
                    break;
                case R.id.unlikelayout1:
                    dislikeRemoveApi();
                    break;
                case R.id.commentlayout:
                    String json = new Gson().toJson(body);
                    context.startActivity(new Intent(context, CommentActivity.class).putExtra("model", json));
                    break;
                case R.id.shareLay:
                    sendingShare();
                    break;
                case R.id.postUserPic:
                    context.startActivity(new Intent(context, PhotoViewActivity.class).putExtra("image",body.getFilepath()).putExtra("name",body.getUsername()));
                    break;
            }
        }


        void likeApi() {
            Call<Object> callApi = ApiCaller.getInstance().likePost(body.getPostid(), userid);
            callApi.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    try {
                        JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                        if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                            JSONObject body = jOb.getJSONObject("body");
                            likelayout.setVisibility(View.GONE);
                            likelayout1.setVisibility(View.VISIBLE);
                            likeCount.setText(String.valueOf(body.getInt("likeCount")) + " Liked");
                            unlikeCount.setText(String.valueOf((int) body.getDouble("dislikeCount")) + " Disliked");
                            if (unlikelayout1.isShown()) {
                                unlikelayout1.setVisibility(View.GONE);
                                unlikelayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(context, jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
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

        void likeRemoveApi() {
            Call<Object> callApi = ApiCaller.getInstance().likeRemovePost(body.getPostid(), userid);
            callApi.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    try {
                        JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                        if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                            JSONObject body = jOb.getJSONObject("body");
                            likelayout.setVisibility(View.VISIBLE);
                            likelayout1.setVisibility(View.GONE);
                            likeCount.setText(String.valueOf(body.getInt("likeCount")) + " Liked");
                            unlikeCount.setText(String.valueOf((int) body.getDouble("dislikeCount")) + " Disliked");
                        } else {
                            Toast.makeText(context, jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
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

        void dislikeApi() {
            Call<Object> callApi = ApiCaller.getInstance().dislikePost(body.getPostid(), userid);
            callApi.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    try {
                        JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                        if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                            JSONObject body = jOb.getJSONObject("body");
                            unlikelayout.setVisibility(View.GONE);
                            unlikelayout1.setVisibility(View.VISIBLE);
                            likeCount.setText(String.valueOf(body.getInt("likeCount")) + " Liked");
                            unlikeCount.setText(String.valueOf((int) body.getDouble("dislikeCount")) + " Disliked");
                            if (likelayout1.isShown()) {
                                likelayout1.setVisibility(View.GONE);
                                likelayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(context, jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }

        void dislikeRemoveApi() {
            Call<Object> callApi = ApiCaller.getInstance().dislikeRemovePost(body.getPostid(), userid);
            callApi.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    try {
                        JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                        if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                            JSONObject body = jOb.getJSONObject("body");
                            unlikelayout.setVisibility(View.VISIBLE);
                            unlikelayout1.setVisibility(View.GONE);
                            likeCount.setText(String.valueOf(body.getInt("likeCount")) + " Liked");
                            unlikeCount.setText(String.valueOf(body.getInt("dislikeCount")) + " Disliked");
                        } else {
                            Toast.makeText(context, jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, jOb.getString(Const.STATUS), Toast.LENGTH_SHORT).show();
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
