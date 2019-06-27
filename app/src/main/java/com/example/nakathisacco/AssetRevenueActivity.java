package com.example.nakathisacco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.Model.AssetsRevenueModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.adapters.AssetRevenueAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetRevenueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<AssetsRevenueModel> assetsRevenueModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private String reg_no,name;

    private TextView tvReg,tvRouteName;
    INakathiAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_revenue);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();
        Intent intent = getIntent();
        reg_no=intent.getStringExtra("reg_no");
        name=intent.getStringExtra("route_name");

        tvReg=findViewById(R.id.tv_vehicle);
        tvRouteName=findViewById(R.id.tv_route_name);


        recyclerView = findViewById(R.id.rvAssetRevenue);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new AssetRevenueAdapter(this,assetsRevenueModels);
        recyclerView.setAdapter(adapter);
        mService = Common.getAPI();
        tvReg.setText(reg_no);
        tvRouteName.setText(name);
        getAssetRevenue(reg_no);

    }

    private void getAssetRevenue(String reg_no) {
        mService.getAssetRevenue(reg_no).enqueue(new Callback<List<AssetsRevenueModel>>() {
            @Override
            public void onResponse(Call<List<AssetsRevenueModel>> call, Response<List<AssetsRevenueModel>> response) {

                assetsRevenueModels.clear();
                assetsRevenueModels.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AssetsRevenueModel>> call, Throwable t) {

                Toast.makeText(AssetRevenueActivity.this, "Error occurred while processing your request", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
