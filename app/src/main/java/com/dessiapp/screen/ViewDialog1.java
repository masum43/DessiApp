package com.dessiapp.screen;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.dessiapp.R;

public class ViewDialog1 {

    public TextView dialogButton;
    ClickEvent clickEvent;

    public void showDialog(Activity activity, String msg, ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
        final Dialog dialog = new Dialog(activity, R.style.Theme_Dialog1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customized_dialog_fragment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

        dialogButton = (TextView) dialog.findViewById(R.id.OK_btn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                clickEvent.getClick();
            }
        });

        dialog.show();

    }

    public interface ClickEvent {
        void getClick();
    }

}
