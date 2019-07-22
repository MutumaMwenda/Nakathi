package com.impax.nakathisacco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.impax.nakathisacco.Model.AssetsModel;
import com.impax.nakathisacco.Model.CertsModel;
import com.impax.nakathisacco.Model.MembersModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.AssetAdapter;
import com.impax.nakathisacco.adapters.CertsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetsActivity extends AppCompatActivity {
    private static final String TAG = AssetsActivity.class.getSimpleName();

    Session session;
    String id_number;
    private RecyclerView recyclerView;
    private List<AssetsModel> assetModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private String reg_no,name;
    INakathiAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();
        session = new Session(this);

        recyclerView = findViewById(R.id.rvAssets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new AssetAdapter(this,assetModels);
        recyclerView.setAdapter(adapter);
        id_number= session.getIdNumber();
        getTaggedVehicles(id_number);
    }

    private void getTaggedVehicles(String id_number) {
        mService.getTaggedVehicles(id_number).enqueue(new Callback<List<AssetsModel>>(){
            @Override
            public void onResponse(Call<List<AssetsModel>> call, Response<List<AssetsModel>> response) {
                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                    Toast.makeText(AssetsActivity.this, "Assets not available", Toast.LENGTH_SHORT).show();
                } else {

                    assetModels.clear();
                    assetModels.addAll(response.body());
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<AssetsModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );

                Toast.makeText(AssetsActivity.this, "Error occurred while processing your request", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
