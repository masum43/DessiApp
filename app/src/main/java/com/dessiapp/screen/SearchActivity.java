package com.dessiapp.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dessiapp.R;
import com.dessiapp.adapter.AdapterPeoples;
import com.dessiapp.adapter.AdapterSearch;
import com.dessiapp.models.PeoplesModel;
import com.dessiapp.provider.ApiCaller;
import com.dessiapp.provider.CallFor;
import com.dessiapp.provider.Const;
import com.dessiapp.provider.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {

    EditText searchTxt;
    Button serchBtn;
    RecyclerView recyclerView;
    AdapterSearch adapterPeople;
    PreferenceManager prefMana;
    String userid;

    Toolbar toolbar;
    private ImageView imgArrow, imgSearch, imgAdd;
    TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.add_query_toolbar);
        setSupportActionBar(toolbar);
        imgArrow = findViewById(R.id.imgArrow);
        textTitle = findViewById(R.id.txtTitle);
        textTitle.setText("Search");
        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        prefMana = new PreferenceManager(getApplicationContext());
        userid = prefMana.getString(getApplicationContext(), Const.userid, "");
        searchTxt = findViewById(R.id.searchTxt);
        serchBtn = findViewById(R.id.serchBtn);
        recyclerView = findViewById(R.id.recyclerView);
        serchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchTxt.getText().toString()==null || searchTxt.getText().toString().equals("")){
                    Toast.makeText(SearchActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                }else{
                    callApi(searchTxt.getText().toString());
                }
            }
        });

        searchTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(searchTxt.getText().toString()==null || searchTxt.getText().toString().equals("")){
                        Toast.makeText(SearchActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                    }else{
                        callApi(searchTxt.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

    }


    void callApi(String search) {
        ProgressDialog uploading = ProgressDialog.show(SearchActivity.this, "Searching", "Please wait...", false, false);
        Call<PeoplesModel> callApi = ApiCaller.getInstance().searchPerson(userid, search);
        callApi.enqueue(new Callback<PeoplesModel>() {
            @Override
            public void onResponse(Call<PeoplesModel> call, retrofit2.Response<PeoplesModel> response) {
                uploading.dismiss();
                PeoplesModel peoplesModel = response.body();
                if (peoplesModel != null && peoplesModel.getStatus().equals(Const.SUCCESS)) {
                    adapterPeople = new AdapterSearch(getApplicationContext(), peoplesModel.getBody(), CallFor.PEOPLE);
                    recyclerView.setAdapter(adapterPeople);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PeoplesModel> call, Throwable t) {
                uploading.dismiss();
            }
        });
    }

}