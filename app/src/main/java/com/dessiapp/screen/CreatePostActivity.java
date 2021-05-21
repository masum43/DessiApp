package com.dessiapp.screen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterComment;
import com.dessiapp.adapter.AdapterImgShow;
import com.dessiapp.models.ActivityModel;
import com.dessiapp.models.CommentModel;
import com.dessiapp.models.PostMultiModel;
import com.dessiapp.models.SpinnerDto;
import com.dessiapp.provider.AdapterSpinner;
import com.dessiapp.provider.AdapterSpinner1;
import com.dessiapp.provider.Api;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.MultipartRequest;
import com.dessiapp.provider.PreferenceManager;
import com.dessiapp.provider.RecyclerItemClickListener;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CreatePostActivity extends AppCompatActivity {
    private static final String TAG = "CreatePostActivity";
    Toolbar toolbar;
    ImageView imgArrow;
    TextView textTitle;
    private Button postbutton;
    ImageView postphoto;
    private ProgressDialog dialog;
    private EditText yourmindedittext, videouploadedittext;
    PreferenceManager prefManager;
    String userid, postmsg,
            imgString,
            image, neighbrhood;
    private JSONObject object;
    VideoView simpleVideoView;
    LinearLayout focus;
    byte[] mByte1;
    CircleImageView grpImg, added;
    String selectedPath1 = "";
    int PICK_IMAGE_MULTIPLE = 107;
    ArrayList<Uri> imagesUriList;
    ArrayList<String> encodedImageList;
    static TextView textSpinner;
    static TextView txtId;
    RequestQueue requestQueue;
    ArrayList<ActivityModel.ActivityList> spinnerDtos;
    AdapterSpinner1 adapterSpinner;
    private static final int CAMERA_REQUEST = 1888;

    RecyclerView recyclerView;
    AdapterImgShow adapterImgShow;
    private String txtSpinner, spinnerID;
    LinearLayout clickPhoto;
    LinearLayout posttypeSpin;
    ViewDialog alert = new ViewDialog();
    File selectFile;

     //Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        requestQueue = Volley.newRequestQueue(this);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        txtId = findViewById(R.id.txtId);
        focus = findViewById(R.id.focus);
        added = findViewById(R.id.added);
        recyclerView = findViewById(R.id.recyclerView);
        posttypeSpin = findViewById(R.id.posttypeSpin);
        imagesUriList = new ArrayList<>();
        encodedImageList = new ArrayList<>();
        textTitle.setText("Create Post");
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        spinnerDtos = new ArrayList<>();
        videouploadedittext = findViewById(R.id.videouploadedittext);
        yourmindedittext = findViewById(R.id.yourmindedittext);
        postbutton = findViewById(R.id.postbutton);
        postphoto = findViewById(R.id.postphoto);
        grpImg = findViewById(R.id.grpImg);
        textSpinner = findViewById(R.id.textSpinner);
        simpleVideoView = findViewById(R.id.simpleVideoView);
        clickPhoto = findViewById(R.id.clickPhoto);
        prefManager = new PreferenceManager(this);
        userid = prefManager.getString(CreatePostActivity.this, Const.userid, "null");
        //getHashKey();



        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourmindedittext.setFocusable(true);
                yourmindedittext.setCursorVisible(true);
                //yourmindedittext.setKeyboardNavigationCluster(true);
            }
        });




        clickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialog();
                Intent intent = CropImage.activity().setAspectRatio(3,1).setFixAspectRatio(false)
                        .getIntent(getApplicationContext());
                startActivityForResult(intent, 0);
            }
        });
        videouploadedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CropImage.activity().setAspectRatio(3,1).setFixAspectRatio(false)
                        .getIntent(getApplicationContext());
                startActivityForResult(intent, 0);
            }
        });


        ////////////////////////Spinner View/////////////////

        posttypeSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(CreatePostActivity.this);
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.setContentView(R.layout.spinner_view);
                spinnerDtos.clear();
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                ImageView button = dialog1.findViewById(R.id.iv_mark);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();

                    }
                });

                final RecyclerView spinnerRecy = dialog1.findViewById(R.id.spinnerRecy);
                final TextView txtHead = dialog1.findViewById(R.id.txtHead);
                txtHead.setText(" Select Activity Type ");

                Call<ActivityModel> callApi = ApiCaller.getInstance().getActivity();
                callApi.enqueue(new Callback<ActivityModel>() {
                    @Override
                    public void onResponse(Call<ActivityModel> call, retrofit2.Response<ActivityModel> response) {
                        ActivityModel activityModel = response.body();
                        if (activityModel.getStatus().equals("success")) {
                            adapterSpinner = new AdapterSpinner1(CreatePostActivity.this, activityModel.getBody(), dialog1, "Post");
                            spinnerRecy.setAdapter(adapterSpinner);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(CreatePostActivity.this);
                            spinnerRecy.setLayoutManager(mLayoutManager);
                            spinnerRecy.addOnItemTouchListener(new RecyclerItemClickListener(CreatePostActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    ActivityModel.ActivityList spinnerDto = activityModel.getBody().get(position);
                                    String txttId = spinnerDto.getSerialno().toString();
                                    String txtSpinner = spinnerDto.getActivity();

                                    txtId.setText(txttId);
                                    textSpinner.setText(txtSpinner);
                                    dialog1.dismiss();

                                }
                            }));
                            spinnerRecy.setHasFixedSize(true);
                        } else {
                            Toast.makeText(CreatePostActivity.this, activityModel.getMessage(), Toast.LENGTH_SHORT).show();
                            //.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ActivityModel> call, Throwable t) {

                    }
                });
                dialog1.show();
            }
        });


        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validPosting();
            }
        });

    }



    void validPosting() {
        postmsg = yourmindedittext.getText().toString();
        txtSpinner = textSpinner.getText().toString();
        spinnerID = txtId.getText().toString();
        /*if (!InputValidation.isEditTextHasvalue(yourmindedittext)) {
            alert.showDialog(CreatePostActivity.this, "Please write a message");
        } else {*/
        if (checkNetworkConnection()) {
            if (selectedPath1 != null && !selectedPath1.equals("")) {
                //new UploadVideo().execute();
                String msg = (postmsg != null) ? postmsg : "";
                calApi2(msg);
            } else if (InputValidation.isEditTextHasvalue(yourmindedittext)) {
                //new userpostWebService().execute();
                String msg = (postmsg != null) ? postmsg : "";
                sendTxtOnly(msg);
            } else {
                alert.showDialog(CreatePostActivity.this, "Do some input for publish");
            }

        } else {
            Intent intent = new Intent(CreatePostActivity.this, NetworkCheckActivity.class);
            startActivity(intent);
            finish();
        }
        //}
    }

    void calApi2(String postMsg) {
        //ProgressDialog uploading = ProgressDialog.show(CreatePostActivity.this, "Uploading File", "Please wait...", false, false);
        Dialog dialog = new Dialog(CreatePostActivity.this, R.style.Theme_Dialog1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", selectFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), selectFile));
        String status = (txtId.getText().toString() != null && !txtId.getText().toString().equals("")) ? txtId.getText().toString() : "";
        RequestBody useridBody = RequestBody.create(MediaType.parse("text/plain"),
                userid);
        RequestBody postMsgBody = RequestBody.create(MediaType.parse("text/plain"),
                postMsg);
        RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"),
                status);


        Call<PostMultiModel> callApi = ApiCaller.getInstance().postMultiPart(filePart, useridBody, postMsgBody, statusBody);
        callApi.enqueue(new Callback<PostMultiModel>() {
            @Override
            public void onResponse(Call<PostMultiModel> call, retrofit2.Response<PostMultiModel> response) {
                dialog.dismiss();
                PostMultiModel value = response.body();
                if (value.getStatus().equals(Const.SUCCESS)) {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    Toast.makeText(CreatePostActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreatePostActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostMultiModel> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    void sendTxtOnly(String value) {
        //ProgressDialog uploading = ProgressDialog.show(CreatePostActivity.this, "Loading", "Please wait...", false, false);
        Dialog dialog = new Dialog(CreatePostActivity.this, R.style.Theme_Dialog1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        String status = (txtId.getText().toString() != null && !txtId.getText().toString().equals("")) ? txtId.getText().toString() : "";
        Call<PostMultiModel> callApi = ApiCaller.getInstance().postTxt(userid, value, status);
        callApi.enqueue(new Callback<PostMultiModel>() {
            @Override
            public void onResponse(Call<PostMultiModel> call, retrofit2.Response<PostMultiModel> response) {
                dialog.dismiss();
                PostMultiModel value = response.body();
                if (value.getStatus().equals(Const.SUCCESS)) {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    Toast.makeText(CreatePostActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreatePostActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                }
             }

            @Override
            public void onFailure(Call<PostMultiModel> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity, R.style.Theme_Dialog1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        dialog.show();

    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) CreatePostActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            encodedImageList.clear();
            imagesUriList = new ArrayList<Uri>();

            selectedPath1 = "";
            //videoView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);


            final CropImage.ActivityResult r = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri u = r.getUri();
                imagesUriList.add(u);
                File f = new File(u.getPath());
                Bitmap b = null;
                try {
                    b = new Compressor(getApplicationContext())
                            .compressToBitmap(f);
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                selectFile = f;


                ByteArrayOutputStream ba = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 20, ba);  //quality 80 but i put 10
                mByte1 = ba.toByteArray();
                image = Base64.encodeToString(mByte1, Base64.DEFAULT);

                selectedPath1 = f.getPath();

                videouploadedittext.setText("Image added successfully");
                added.setVisibility(View.VISIBLE);
                grpImg.setVisibility(View.GONE);
                //grpImg.setImageBitmap(b);
                encodedImageList.add(image);

                adapterImgShow = new AdapterImgShow(CreatePostActivity.this, imagesUriList);
                recyclerView.setAdapter(adapterImgShow);
                recyclerView.setLayoutManager(new GridLayoutManager(CreatePostActivity.this, 3));
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(CreatePostActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {

                                ImageView deletepost = view.findViewById(R.id.deletePost);
                                deletepost.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        encodedImageList.remove(position);
                                        adapterImgShow.removePostion(position);

                                        ////When last image delted it shows No image uploaad in edittext
                                        if (encodedImageList.size() == 0) {
                                            videouploadedittext.setText("");
                                            added.setVisibility(View.GONE);
                                            grpImg.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });

                            }
                        })
                );


                //recyclerView.onIt
            }

        }


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }




}
