package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {
    ImageView imgArrow;
    Toolbar toolbar;
    TextView txtTitle;
    Bundle b;
    String image,name;
    PhotoView imageShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        imageShow=findViewById(R.id.imageShow);
        toolbar=findViewById(R.id.add_query_toolbar);
        txtTitle=findViewById(R.id.txtTitle);
        imgArrow=findViewById(R.id.imgArrow);
       // toolbar.setBackgroundColor(Color.parseColor("#000000"));

        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        b=getIntent().getExtras();
        image=b.getString("image");
        name=b.getString("name");

        if(name!=null && !name.equals("")) {

            txtTitle.setText(name);
            Glide.with(getApplicationContext()).load(image).into(imageShow);

        }else {
            Glide.with(getApplicationContext()).load(image).into(imageShow);
            txtTitle.setText("");
        }
    }
}