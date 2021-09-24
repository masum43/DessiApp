package com.dessiapp.screen;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.AdapterImgShow;
import com.dessiapp.models.ActivityModel;
import com.dessiapp.models.PostMultiModel;
import com.dessiapp.provider.AdapterSpinner1;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.Application;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.InputValidation;
import com.dessiapp.provider.PreferenceManager;
import com.dessiapp.provider.RecyclerItemClickListener;
import com.dessiapp.utility.AlbumStorageDirFactory;
import com.dessiapp.utility.BitmapUtils;
import com.dessiapp.utility.Constant;
import com.dessiapp.utility.FroyoAlbumDirFactory;
import com.fenchtose.nocropper.BitmapResult;
import com.fenchtose.nocropper.CropMatrix;
import com.fenchtose.nocropper.CropState;
import com.fenchtose.nocropper.CropperView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
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
    ImageView postphoto;
    private EditText yourmindedittext;
    PreferenceManager prefManager;
    String userid, postmsg;
    LinearLayout focus;
    RequestQueue requestQueue;
    ArrayList<ActivityModel.ActivityList> spinnerDtos;
    ViewDialog alert = new ViewDialog();
    File selectFile;
    public static final int PICK_IMAGE = 1;
    CropperView cropperView;
    //Dialog dialog;
    private HashMap<String, CropMatrix> matrixMap = new HashMap<>();
    private Bitmap mBitmap;
    private boolean isSnappedToCenter = false;
    private int rotationCount = 0;
    private String mCurrentPhotoPath = null;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int ACTION_SELECT_PHOTO = 2;
    File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        mAlbumStorageDirFactory = new FroyoAlbumDirFactory();


        requestQueue = Volley.newRequestQueue(this);
        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        focus = findViewById(R.id.focus);
        textTitle.setText("Create Post");
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
        cropperView = findViewById(R.id.imageview);
        findViewById(R.id.tvSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageOptionDialog();
            }
        });
        spinnerDtos = new ArrayList<>();
        yourmindedittext = findViewById(R.id.yourmindedittext);
        Button postbutton = findViewById(R.id.postbutton);
        postphoto = findViewById(R.id.postphoto);
        prefManager = new PreferenceManager(this);
        userid = prefManager.getString(CreatePostActivity.this, Const.userid, "null");
        findViewById(R.id.snap_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snapImage();
            }
        });
        findViewById(R.id.rotate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateImage();
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourmindedittext.setFocusable(true);
                yourmindedittext.setCursorVisible(true);
            }
        });


        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validPosting();
            }
        });
        updateStatusBarColor("#ffffff");
    }


    public void showImageOptionDialog() {

        Application.alertDialog(CreatePostActivity.this, Gravity.BOTTOM, R.layout.dailog_choose_image, true, new Application.DialogCreated() {
            public void onDialogCreated(final androidx.appcompat.app.AlertDialog alertDialog) {
                (alertDialog.findViewById(R.id.tvGallery)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dispatchGalleryIntent(ACTION_SELECT_PHOTO);
                        alertDialog.dismiss();
                    }
                });
                (alertDialog.findViewById(R.id.tvCamera)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isIntentAvailable(getApplicationContext(), MediaStore.ACTION_IMAGE_CAPTURE)) {
                            dispatchTakePictureIntent(ACTION_TAKE_PHOTO);
                        } else {
                            Toast.makeText(getApplicationContext(), "Device cannot take picture", Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    public void updateStatusBarColor(String color) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    void validPosting() {

        if (checkNetworkConnection()) {
            postmsg = yourmindedittext.getText().toString();
            if (mCurrentPhotoPath != null) {
                //new UploadVideo().execute();
                BitmapResult result = cropperView.getCroppedBitmap();

                if (result.getState() == CropState.FAILURE_GESTURE_IN_PROCESS) {
                    Toast.makeText(this, "unable to crop. Gesture in progress", Toast.LENGTH_SHORT).show();
                    return;
                }

//                FilterActivity.selectFile = result.getBitmap();
                String msg = (postmsg != null) ? postmsg : "";

                String path = store(getApplicationContext(), result.getBitmap(), "images");
                selectFile = new File(path);


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
        Dialog dialog = new Dialog(CreatePostActivity.this, R.style.Theme_Dialog1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", selectFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), selectFile));
        RequestBody useridBody = RequestBody.create(MediaType.parse("text/plain"),
                userid);
        RequestBody postMsgBody = RequestBody.create(MediaType.parse("text/plain"),
                postMsg);
        RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"),
                "");


        Call<PostMultiModel> callApi = ApiCaller.getInstance().postMultiPart(filePart, useridBody, postMsgBody, statusBody);
        callApi.enqueue(new Callback<PostMultiModel>() {
            @Override
            public void onResponse(Call<PostMultiModel> call, retrofit2.Response<PostMultiModel> response) {
                dialog.dismiss();
                Log.e("onResponse", "onResponse: " + response.body().toString());
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
        Call<PostMultiModel> callApi = ApiCaller.getInstance().postTxt(userid, value, "1");
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

    public void showDialog(Activity activity, String msg) {
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


    private void loadNewImage(String filePath) {
        rotationCount = 0;
        Log.i(TAG, "load image: " + filePath);
        mBitmap = BitmapFactory.decodeFile(filePath);
        Log.i(TAG, "bitmap: " + mBitmap.getWidth() + " " + mBitmap.getHeight());

        int maxP = Math.max(mBitmap.getWidth(), mBitmap.getHeight());
        float scale1280 = (float) maxP / 1280;
        Log.i(TAG, "scaled: " + scale1280 + " - " + (1 / scale1280));

        if (cropperView.getWidth() != 0) {
            cropperView.setMaxZoom(cropperView.getWidth() * 2 / 500f);
        } else {

            ViewTreeObserver vto = cropperView.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    cropperView.getViewTreeObserver().removeOnPreDrawListener(this);
                    cropperView.setMaxZoom(cropperView.getWidth() * 2 / 500f);
                    return true;
                }
            });

        }

        mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) (mBitmap.getWidth() / scale1280),
                (int) (mBitmap.getHeight() / scale1280), true);

        cropperView.setImageBitmap(mBitmap);
        final CropMatrix matrix = matrixMap.get(filePath);
        if (matrix != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    cropperView.setCropMatrix(matrix, true);
                }
            }, 30);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }


    private void rotateImage() {
        if (mBitmap == null) {
            Log.e(TAG, "bitmap is not loaded yet");
            return;
        }

        mBitmap = BitmapUtils.rotateBitmap(mBitmap, 90);
        cropperView.setImageBitmap(mBitmap);
        rotationCount++;
    }

    private void snapImage() {
        if (isSnappedToCenter) {
            isSnappedToCenter = false;
            cropperView.cropToCenter();
        } else {
            isSnappedToCenter = true;
            cropperView.fitToCenter();
        }

    }

    public static String store(Context context, Bitmap bitmap, String storePath) {
        String path = null;
        File file = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/DesiApp/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(directory.getAbsolutePath() + "/" + storePath + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png");

            path = Uri.fromFile(file).getPath();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
        return path;
    }


    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    public static int count = 0;

    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch (actionCode) {
            case ACTION_TAKE_PHOTO:
                File f = null;

                try {
                    f = setUpCameraPhotoFile();
                    Uri imageUri = FileProvider.getUriForFile(
                            this,
                            "com.dessiapp.fileprovider",
                            f);
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }

    private File setUpCameraPhotoFile() throws IOException {

        f = Constant.createImageFile(mAlbumStorageDirFactory);
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private void dispatchGalleryIntent(int actionCode) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select image"),
                ACTION_SELECT_PHOTO);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTION_TAKE_PHOTO: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO

            case ACTION_SELECT_PHOTO: {
                if (resultCode == RESULT_OK) {
                    try {

                        Uri pickedImage = data.getData();
                        // Let's read picked image path using content resolver
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(pickedImage,
                                filePath, null, null, null);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(cursor
                                .getColumnIndex(filePath[0]));
                        mCurrentPhotoPath = imagePath;
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handleGalleryPhoto();
                }
                break;
            }
            case 111: {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            }
        }
    }

    private void handleGalleryPhoto() {
        if (mCurrentPhotoPath != null) {
            try {
                File file = new File(mCurrentPhotoPath);
                long length = file.length() / 1024;
                if (length > destWidth) {
                    String bitmap = Constant.compressImage(mCurrentPhotoPath, getApplicationContext());
                    Constant.galleryAddPic(mCurrentPhotoPath, this);
                    mCurrentPhotoPath = bitmap;

                } else {
                    Constant.galleryAddPic(mCurrentPhotoPath, this);
                }
                loadNewImage(mCurrentPhotoPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    int destWidth = 2000;

    private void handleBigCameraPhoto() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        File file = new File(mCurrentPhotoPath);
        long length = file.length() / 1024;
        if (length != 0)
            if (length > destWidth) {
                String bitmap = Constant.compressImage(mCurrentPhotoPath, getApplicationContext());
                Constant.galleryAddPic(mCurrentPhotoPath, this);
                mCurrentPhotoPath = bitmap;

            } else {
                Constant.galleryAddPic(mCurrentPhotoPath, this);
            }
        loadNewImage(mCurrentPhotoPath);

    }


}
