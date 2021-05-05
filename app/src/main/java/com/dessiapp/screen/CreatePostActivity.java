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

import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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
import com.dessiapp.models.CommentModel;
import com.dessiapp.models.SpinnerDto;
import com.dessiapp.provider.AdapterSpinner;
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
    ArrayList<SpinnerDto> spinnerDtos;
    AdapterSpinner adapterSpinner;
    private static final int CAMERA_REQUEST = 1888;

    RecyclerView recyclerView;
    AdapterImgShow adapterImgShow;
    private String txtSpinner, spinnerID;
    LinearLayout clickPhoto;
    LinearLayout posttypeSpin;
    ViewDialog alert = new ViewDialog();
    File selectFile;

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

        /*dialog = new ProgressDialog(CreatePostActivity.this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();*/
        //checkVideoSize();
        spinnerDtos = new ArrayList<>();
        /*NavigationActivity.imgSearch.setVisibility(View.GONE);
        NavigationActivity.imgAdd.setVisibility(View.GONE);
        NavigationActivity.txtsubTitle.setVisibility(View.GONE);*/
        /*postedittext=findViewById(R.id.postedittext);*/
        videouploadedittext = findViewById(R.id.videouploadedittext);
        yourmindedittext = findViewById(R.id.yourmindedittext);
        postbutton = findViewById(R.id.postbutton);
        //postSpinner = findViewById(R.id.postSpinner);
        postphoto = findViewById(R.id.postphoto);
        grpImg = findViewById(R.id.grpImg);
        textSpinner = findViewById(R.id.textSpinner);
        simpleVideoView = findViewById(R.id.simpleVideoView);
        clickPhoto = findViewById(R.id.clickPhoto);
        prefManager = new PreferenceManager(this);
        //yourmindedittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        /*uploadVideoIntoServer();*/
        userid = prefManager.getString(CreatePostActivity.this, Const.userid, "null");

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
                Intent intent = CropImage.activity()
                        .getIntent(getApplicationContext());
                startActivityForResult(intent, 0);
            }
        });
        videouploadedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showDialog();
                Intent intent = CropImage.activity()
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
//                fetchSpinnerList();
                //final String URLTEXT = "http://neighbrsnook.com/admin/api/master?flag=4";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Api.ACTIVITY, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //JSONArray jsonArray = response.getJSONArray("nbdata");
                            String status = response.getString("status");
                            String message = response.getString("message");


                            if (status.equals("success")) {
                                JSONArray jsonArray = response.getJSONArray("body");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String id = jsonObject1.getString("serialno").toString();
                                    String post_title = jsonObject1.getString("activity");

                                    SpinnerDto dto = new SpinnerDto(post_title, id);
                                    spinnerDtos.add(dto);
                                }

                                adapterSpinner = new AdapterSpinner(CreatePostActivity.this, spinnerDtos, dialog1, "Post");
                                spinnerRecy.setAdapter(adapterSpinner);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(CreatePostActivity.this);
                                spinnerRecy.setLayoutManager(mLayoutManager);
                                spinnerRecy.addOnItemTouchListener(new RecyclerItemClickListener(CreatePostActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        SpinnerDto spinnerDto = spinnerDtos.get(position);
                                        String txttId = spinnerDto.getId();
                                        String txtSpinner = spinnerDto.getName();

                                        txtId.setText(txttId);
                                        textSpinner.setText(txtSpinner);
                                        dialog1.dismiss();

                                    }
                                }));
                                spinnerRecy.setHasFixedSize(true);


                                //
                            } else {
                                Toast.makeText(CreatePostActivity.this, message, Toast.LENGTH_SHORT).show();
                                //.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Error is :" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(Const.HEAD_TOKEN, Const.TOKEN_KEY);
                        return params;
                    }
                };
                requestQueue.add(request);


                dialog1.show();

            }
        });


        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int postlimit = Integer.parseInt(post_img_limit);
                //              if (encodedImageList.size() == 0) {
                validPosting();
    /*            } else {
                    if (encodedImageList.size() > postlimit) {
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(CreatePostActivity.this, "You can upload upto " + postlimit + " images");
                    } else {
                        validPosting();
                    }
                }*/
            }
        });

    }

    void showDialog() {
        //////////////////Dialog///////////////
        final Dialog dialog1 = new Dialog(CreatePostActivity.this);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setContentView(R.layout.custom_vdo_img);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setGravity(Gravity.BOTTOM);

        Button cancelD = dialog1.findViewById(R.id.cancelD);
        //Button videoCam = dialog1.findViewById(R.id.videoCam);
        Button imgCam = dialog1.findViewById(R.id.imgCam);
        cancelD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

            }
        });

        Button imgUpload = dialog1.findViewById(R.id.imgUpload);
        //Button videoUpload = dialog1.findViewById(R.id.videoUpload);
        //final EditText dayGet=dialog1.findViewById(R.id.editDay);
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

            }
        });


        imgCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                Intent intent = CropImage.activity()
                        .getIntent(getApplicationContext());
                startActivityForResult(intent, 0);
            }
        });


        dialog1.show();
    }

    void validPosting() {
        postmsg = yourmindedittext.getText().toString();
        txtSpinner = textSpinner.getText().toString();
        spinnerID = txtId.getText().toString();
       /* if (txtSpinner.isEmpty()) {
            alert.showDialog(CreatePostActivity.this, "Please select activity");
        } else*/
        if (!InputValidation.isEditTextHasvalue(yourmindedittext)) {
            alert.showDialog(CreatePostActivity.this, "Please write a message");
        } else {
            if (checkNetworkConnection()) {
                if (selectedPath1 != null && !selectedPath1.equals("")) {
                    //new UploadVideo().execute();
                    calApi2(postmsg);
                } else {
                    //new userpostWebService().execute();
                    sendTxtOnly();
                }

            } else {
                Intent intent = new Intent(CreatePostActivity.this, NetworkCheckActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    void calApi(String postMsg) {
        ProgressDialog uploading = ProgressDialog.show(CreatePostActivity.this, "Uploading File", "Please wait...", false, false);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("postedBy", userid);
        params.put("postDesc", postMsg);
        //params.put("activity", postMsg);
        if (txtId.getText().toString() != null && !txtId.getText().toString().equals(""))
            params.put("activity", txtId.getText().toString());
        else
            params.put("activity", "");

        MultipartRequest mr = new MultipartRequest(Api.CREATE_POST1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                uploading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    Toast.makeText(CreatePostActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreatePostActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }, selectFile/*new File(selectedPath1)*/, params);
        requestQueue.add(mr).setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0; //retry turn off
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        //requestQueue.
    }

    void calApi2(String postMsg) {
        ProgressDialog uploading = ProgressDialog.show(CreatePostActivity.this, "Uploading File", "Please wait...", false, false);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", selectFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), selectFile));
        String status = (txtId.getText().toString() != null && !txtId.getText().toString().equals("")) ? txtId.getText().toString() : "";
        RequestBody useridBody = RequestBody.create(MediaType.parse("text/plain"),
                userid);
        RequestBody postMsgBody = RequestBody.create(MediaType.parse("text/plain"),
                postMsg);
        RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"),
                status);



        Call<Object> callApi = ApiCaller.getInstance().postMultiPart(filePart, useridBody, postMsgBody, statusBody);
        callApi.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                uploading.dismiss();
                try {
                    String value=String.valueOf(response.body());
                    JSONObject jObj = new JSONObject(String.valueOf(response.body()));
                    if (jObj.getString(Const.STATUS).equals(Const.SUCCESS)) {

                    //if (jObj.getString(Const.STATUS).equals(Const.SUCCESS)) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        Toast.makeText(CreatePostActivity.this, jObj.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreatePostActivity.this, jObj.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CreatePostActivity.this, "Something went wrong code:"+String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                uploading.dismiss();
            }
        });
    }

    void sendTxtOnly(){
        String status = (txtId.getText().toString() != null && !txtId.getText().toString().equals("")) ? txtId.getText().toString() : "";
        Call<Object> callApi = ApiCaller.getInstance().postTxt(userid,neighbrhood,status);
        callApi.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                try {
                    JSONObject jOb = new JSONObject(String.valueOf(response.body()));
                    if (jOb.getString(Const.STATUS).equals(Const.SUCCESS)) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        Toast.makeText(CreatePostActivity.this, jOb.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
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

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getPath(Uri uri) {

        Cursor cursor = CreatePostActivity.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = CreatePostActivity.this.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }


    private String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return imgString;
    }


    @SuppressLint("StaticFieldLeak")
    private class userpostWebService extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = null;
            dialog = new ProgressDialog(CreatePostActivity.this);
            dialog.setMessage("Please Wait");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpClient httpClient = new DefaultHttpClient();
            //HttpPost httpPost = new HttpPost("http://neighbrsnook.com/admin/api/posting?flag=2");
            HttpPost httpPost = new HttpPost(Api.CREATE_POST_TXT);
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("userid", userid));
                nameValuePairs.add(new BasicNameValuePair("textToPost", URLEncoder.encode(neighbrhood)));
                nameValuePairs.add(new BasicNameValuePair("activity", (txtId.getText().toString() != null && !txtId.getText().toString().equals("")) ? txtId.getText().toString() : ""));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpPost.setHeader(Const.HEAD_TOKEN, Const.TOKEN_KEY);
                HttpResponse response = httpClient.execute(httpPost);
                result = EntityUtils.toString(response.getEntity());
                Log.d(TAG, "doInBackground: in loop" + result);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(String s) {

            if (!s.equals("")) {
                String status = null;
                String message = null;
                try {
                    JSONObject object = new JSONObject(s);
                    status = object.getString("status");
                    Log.d(TAG, "onPostExecute: id" + status);
                    message = object.getString("message");
                    Log.d(TAG, "onPostExecute: id" + message);
                    if (status.equals("success")) {


                        Intent intent = new Intent(CreatePostActivity.this, NavigationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    } else {
                        new ViewDialog().showDialog(CreatePostActivity.this, message);
                        //Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
                        /* Toast.makeText(RegisterActivity.this, "Internet issue", Toast.LENGTH_LONG).show();*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (dialog != null)
                dialog.dismiss();
            super.onPostExecute(s);
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
