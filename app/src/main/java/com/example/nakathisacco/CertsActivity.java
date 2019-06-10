package com.example.nakathisacco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.nakathisacco.Model.CertsModel;
import com.example.nakathisacco.Model.LoanApplicantModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.CertsAdapter;
import com.example.nakathisacco.adapters.LoansApplicantsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertsActivity extends AppCompatActivity {


    private static final String TAG = CertsActivity.class.getSimpleName();

    Session session;
    String id_number;
    private RecyclerView recyclerView;
    private List<CertsModel> certsModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private String loan_id,amount;
    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();


        session = new Session(this);
        loan_id=session.getLoanId();
        id_number = session.getIdNumber();
        amount=session.getAmount();
        getCerts(id_number);

        recyclerView = findViewById(R.id.rvCerts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(linearLayoutManager);
       recyclerView.setHasFixedSize(true);
       adapter = new CertsAdapter(this,certsModels);
        recyclerView.setAdapter(adapter);



    }
    private  void getCerts(String id_number){
        mService.getCerts(id_number).enqueue(new Callback<List<CertsModel>>() {
            @Override
            public void onResponse(Call<List<CertsModel>> call, Response<List<CertsModel>> response) {

                Log.e(TAG, "onResponse: "+response.body() );

                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                    Toast.makeText(CertsActivity.this, "Members not available", Toast.LENGTH_SHORT).show();
                } else {

                    certsModels.clear();
                    certsModels.addAll(response.body());
                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onFailure(Call<List<CertsModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(CertsActivity.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();


            }
        });
    }




}
