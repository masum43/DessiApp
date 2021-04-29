package com.dessiapp.screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dessiapp.R;
import com.dessiapp.adapter.AdapterDashboard;
import com.dessiapp.models.DashDataModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DashActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    AdapterDashboard adapterDashboard;
    List<DashDataModel> dataModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        recyclerView = findViewById(R.id.recyclerView);

        int[] value = new int[]{R.id.imgAdd, R.id.whtMind};
        registerClick(value);

    }

    private void registerClick(int[] buttonResId) {
        for (int i = 0; i < buttonResId.length; i++) {
            findViewById(buttonResId[i]).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whtMind:
                startActivity(new Intent(DashActivity.this, CreatePostActivity.class));
                break;
            case R.id.whtMind1:
                startActivity(new Intent(DashActivity.this, CreatePostActivity.class));
                break;
        }
    }
}