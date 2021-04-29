package com.dessiapp.screen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dessiapp.R;

public class NetworkCheckActivity extends AppCompatActivity {
    private Button retry_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_notavail);
        retry_btn=findViewById(R.id.retry_btn);

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNetworkConnection()){
                    Intent intent=new Intent(NetworkCheckActivity.this, SplashScreen.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(NetworkCheckActivity.this,"Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkCheckActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
