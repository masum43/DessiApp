package com.dessiapp.provider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.Log;

import com.dessiapp.R;
import com.dessiapp.utility.Constant;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;

public class Application extends android.app.Application {
    private PreferenceManager mPreferenceSettings;
    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mPreferenceSettings = getPreferenceSettings();

        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        JSONObject consent = new JSONObject();
        try {
            // Provide correct consent value to sdk which is obtained by User
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
            consent.put("gdpr", "0");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(this, "b31ee3ae8dcf4d54b9d305be9ccf520f", consent, new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (error == null) {
                    Log.d("InMobi", "InMobi SDK Initialization Success");

                } else {
                    Log.e("InMobi", "InMobi SDK Initialization failed: " + error.getMessage());
                }
            }
        });


    }

    public static synchronized Application getInstance() {
        return mInstance;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public PreferenceManager getPreferenceSettings() {
        if (mPreferenceSettings == null) {
            mPreferenceSettings = new PreferenceManager(getApplicationContext());
        }
        return mPreferenceSettings;
    }
    public DialogCreated listener;

    public interface DialogCreated {
        void onDialogCreated(androidx.appcompat.app.AlertDialog alertDialog);
    }
    public static void alertDialog(Activity activity2, int gravity, int i, boolean cancel, DialogCreated dialogCreated) {
        new Application().listener = dialogCreated;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity2);
        builder.setView(activity2.getLayoutInflater().inflate(i, null));
        androidx.appcompat.app.AlertDialog create = builder.create();
        create.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnim;
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.getWindow().setGravity(gravity);
        create.setCancelable(cancel);
        create.show();

        dialogCreated.onDialogCreated(create);
    }
}
