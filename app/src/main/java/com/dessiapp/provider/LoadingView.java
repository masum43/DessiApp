package com.dessiapp.provider;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.dessiapp.R;

public class LoadingView {

    public TextView dialogButton;

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

}
