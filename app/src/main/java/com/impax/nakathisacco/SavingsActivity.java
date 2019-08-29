package com.impax.nakathisacco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.Model.CertsModel;
import com.impax.nakathisacco.Model.SavingsLogModel;
import com.impax.nakathisacco.Model.SavingsModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.CertsAdapter;
import com.impax.nakathisacco.adapters.SavingsLogAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingsActivity extends AppCompatActivity {


    private static final String TAG = SavingsActivity.class.getSimpleName();

    Session session;
    String id_number,reg_no;
    private RecyclerView recyclerView;
    private List<SavingsLogModel> savingsLogModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private String total_savings,available_amount;
    private TextView tvTotalSavings,tvAvailableBalance;
    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();
        tvAvailableBalance=findViewById(R.id.tv_available_balance);
        tvTotalSavings=findViewById(R.id.tv_total_savings);


        session = new Session(this);
        id_number = session.getIdNumber();


        recyclerView = findViewById(R.id.rvSavingsLog);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SavingsLogAdapter(this,savingsLogModels);
        recyclerView.setAdapter(adapter);
        mService = Common.getAPI();
        getSavingsLog(id_number);
        getMemberSavings(id_number);

    }

    private void getMemberSavings(String id_number) {
        mService.getMemberSavings(id_number).enqueue(new Callback<SavingsModel>() {
            @Override
            public void onResponse(Call<SavingsModel> call, Response<SavingsModel> response) {
                tvAvailableBalance.setText("Available balance "+response.body().available_amount);
                tvTotalSavings.setText("Total Savings "+response.body().total_saving_amount);
            }

            @Override
            public void onFailure(Call<SavingsModel> call, Throwable t) {

            }
        });
    }

    private void getSavingsLog(String id_number) {
       mService.getSavingsLog(id_number).enqueue(new Callback<List<SavingsLogModel>>() {
           @Override
           public void onResponse(Call<List<SavingsLogModel>> call, Response<List<SavingsLogModel>> response) {

               Log.e(TAG, "onResponse: "+response.body() );

               if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                   Toast.makeText(SavingsActivity.this, "Savings Log not available", Toast.LENGTH_SHORT).show();
               } else {

                   savingsLogModels.clear();
                   savingsLogModels.addAll(response.body());
                   adapter.notifyDataSetChanged();

               }


           }

           @Override
           public void onFailure(Call<List<SavingsLogModel>> call, Throwable t) {
               Log.e(TAG, "onFailure: "+t );
               Toast.makeText(SavingsActivity.this, "Error occurred while processing your request", Toast.LENGTH_SHORT).show();


           }
       });

    }

}
