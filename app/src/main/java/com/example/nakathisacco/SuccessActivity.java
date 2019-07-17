package com.example.nakathisacco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nakathisacco.Model.AssetsModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.AssetAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity {
    private static final String TAG = SuccessActivity.class.getSimpleName();

    Session session;
    String id_number;
    private List<AssetsModel> assetModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;
    private Button btnDone;

    private String reg_no,name;
    INakathiAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mService = Common.getAPI();
        session = new Session(this);
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this,MyLoansActivity.class);
                startActivity(intent);
            }
        });


    }
    public void onBackPressed() {
        //super.onBackPressed();
    }


}
