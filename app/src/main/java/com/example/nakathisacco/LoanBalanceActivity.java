package com.example.nakathisacco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.nakathisacco.Model.LoanBalanceModel;
import com.example.nakathisacco.Model.SavingsLogModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.LoanBalanceAdapter;
import com.example.nakathisacco.adapters.SavingsLogAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanBalanceActivity extends AppCompatActivity {
    private static final String TAG = LoanBalanceActivity.class.getSimpleName();

    Session session;
    String id_number;
    private RecyclerView recyclerView;
    private List<LoanBalanceModel> loanBalanceModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    INakathiAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_balance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();

        session = new Session(this);
        id_number = session.getIdNumber();
        recyclerView = findViewById(R.id.rvLoanBalance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new LoanBalanceAdapter(this,loanBalanceModels);
        recyclerView.setAdapter(adapter);

        getLoanBalance(id_number);
    }

    private void getLoanBalance(String id_number) {
        mService.getLoanBalance(id_number).enqueue(new Callback<List<LoanBalanceModel>>() {
            @Override
            public void onResponse(Call<List<LoanBalanceModel>> call, Response<List<LoanBalanceModel>> response) {

                loanBalanceModels.clear();
                loanBalanceModels.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LoanBalanceModel>> call, Throwable t) {

                Toast.makeText(LoanBalanceActivity.this, "Error occurred while processing your request", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
