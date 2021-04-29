package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dessiapp.R;
import com.dessiapp.adapter.MyAdapter;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;
import com.google.android.material.tabs.TabLayout;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    MyAdapter adapter;
    PreferenceManager prefManager;
    String profImg;
    CircleImageView cirImg;
    ImageView imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        cirImg = findViewById(R.id.cirImg);
        imgAdd = findViewById(R.id.imgAdd);
        updateProfilePic();
        adapter = new MyAdapter(NavigationActivity.this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date=new Date();
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n HI BUDDY YYY: \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"+date.getTime());
            }
        });

    }

  void  updateProfilePic(){
     /* prefManager = new PreferenceManager(getApplicationContext());
      profImg = prefManager.getString(getApplicationContext(), Const.profileimg, "");

      if (!profImg.equals("") && profImg != null && !profImg.equals("null"))
            Glide.with(getApplicationContext()).load(profImg).into(cirImg);
*/
    }
}