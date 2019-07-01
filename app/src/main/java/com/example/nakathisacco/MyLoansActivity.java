package com.example.nakathisacco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.Model.AssetsModel;
import com.example.nakathisacco.Model.Loan;
import com.example.nakathisacco.Model.LoanTypeModel;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.Model.SavingsModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.AppUtilits;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.LoanTypeAdapter;
import com.example.nakathisacco.adapters.LoansApplicantsAdapter;
import com.example.nakathisacco.adapters.RecentLoansAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLoansActivity extends AppCompatActivity {

    private static final String TAG = MyLoansActivity.class.getSimpleName();
    private Session session;
    double rate=3.50,doubleSavings,maximumCanBorrow;
    private String amt;
    private String savings;
    private TextView tvDisplayMaximumLoan,tv_display_no_loans;
    private Button btnLoanRequest;
    private LinearLayout linearLayout;

    private RecyclerView recyclerView;
    private List<Loan> loanModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;


    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myloans);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();
        tvDisplayMaximumLoan = findViewById(R.id.tv_maximumLoan);
        tv_display_no_loans= findViewById(R.id.tv_display_no_loans);
        linearLayout = findViewById(R.id.ly_header);
        btnLoanRequest = findViewById(R.id.btnRequestLoan);

        session = new Session(this);
      //  AppUtilits.showDialog(this);



        getMembersavings(session.getIdNumber());
        getRecentLoans();
        Log.e(TAG, "maximumcanborrow"+maximumCanBorrow );

        btnLoanRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLoan();
            }
        });

        recyclerView = findViewById(R.id.rvRecentLoans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecentLoansAdapter(this,loanModels);
        recyclerView.setAdapter(adapter);

    }




    private void getMembersavings(String id_number){
        mService.getMemberSavings(id_number).enqueue(new Callback<SavingsModel>() {
            @Override
            public void onResponse(Call<SavingsModel> call, Response<SavingsModel> response) {
                if (response.body() != null) {


                    savings = response.body().amount;

                    doubleSavings = Double.parseDouble(savings);
                    maximumCanBorrow = doubleSavings * rate;
                    Log.e("Sav:", savings + maximumCanBorrow);
                    Integer maximumLoan = (int) maximumCanBorrow;
                    String displayMaximumLoan = maximumLoan.toString();
                    String number = displayMaximumLoan;
                    double amount = Double.parseDouble(number);
                    DecimalFormat formatter = new DecimalFormat("#,###");
                    tvDisplayMaximumLoan.setText("Kshs " + formatter.format(amount));
                }

            }

            @Override
            public void onFailure(Call<SavingsModel> call, Throwable t) {

            }
        });

    }
    private void requestLoan(){
        mService.checkExistsLoan(session.getIdNumber()).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                // AppUtilits.dismissDialog();
                String msg = response.body().getMessage();
                if(msg.equalsIgnoreCase("true")){
                    Toast.makeText(MyLoansActivity.this, "You already have an existing loan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MyLoansActivity.this,LoanActivity.class));

                    // finish();
                }else{
                    startActivity(new Intent(MyLoansActivity.this,LoanActivity.class));


                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Toast.makeText(MyLoansActivity.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();
                AppUtilits.dismissDialog();

            }
        });
    }


    private void getRecentLoans(){
        mService.getRecentLoans(session.getIdNumber()).enqueue(new Callback<List<Loan>>() {
            @Override
            public void onResponse(Call<List<Loan>> call, Response<List<Loan>> response) {

                Log.e(TAG, "onResponse: "+response.body() );

                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                   // Toast.makeText(MyLoansActivity.this, "No loans", Toast.LENGTH_SHORT).show();

                        tv_display_no_loans.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);


                } else {

                    loanModels.clear();
                    loanModels.addAll(response.body());
                    adapter.notifyDataSetChanged();



                }
            }

            @Override
            public void onFailure(Call<List<Loan>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(MyLoansActivity.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();


            }
        });
    }




}
