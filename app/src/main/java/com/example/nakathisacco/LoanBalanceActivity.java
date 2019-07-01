package com.example.nakathisacco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.Model.LoanBalanceModel;
import com.example.nakathisacco.Model.SavingsLogModel;
import com.example.nakathisacco.Model.SavingsModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.LoanBalanceAdapter;
import com.example.nakathisacco.adapters.SavingsLogAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private TextView tvLoan,tvLoanBalance,tvDueDate;
    String due_date,total_loan,loan_balance;

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
        tvDueDate=findViewById(R.id.tv_loan_due_date);
        tvLoan=findViewById(R.id.tv_total_loan);
        tvLoanBalance= findViewById(R.id.tv_loan_balance);
        session = new Session(this);
        id_number = session.getIdNumber();
        recyclerView = findViewById(R.id.rvLoanBalance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new LoanBalanceAdapter(this,loanBalanceModels);
        recyclerView.setAdapter(adapter);

        getLoanInfo(id_number);
        getLoanBalance(id_number);
    }
    private void getLoanInfo(String id_number){
        mService.getLoanInfo(id_number).enqueue(new Callback<SavingsModel>() {
            @Override
            public void onResponse(Call<SavingsModel>call, Response<SavingsModel> response) {
                SavingsModel savingsModel= response.body();

                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                java.sql.Timestamp ts = java.sql.Timestamp.valueOf(savingsModel.due_date) ;
                Date date = new Date(ts.getTime());
                String strDate = formatter.format(date);
                tvDueDate.setText("Due Date:  "+strDate);
                tvLoan.setText("Total: "+savingsModel.amount);
                tvLoanBalance.setText("Balance: "+savingsModel.balance);

            }

            @Override
            public void onFailure(Call<SavingsModel> call, Throwable t) {

            }
        });
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
