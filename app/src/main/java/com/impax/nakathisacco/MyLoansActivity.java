package com.impax.nakathisacco;

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

import com.impax.nakathisacco.Model.Artist;
import com.impax.nakathisacco.Model.AssetsModel;
import com.impax.nakathisacco.Model.Genre;
import com.impax.nakathisacco.Model.GuarantorModel;
import com.impax.nakathisacco.Model.Loan;
import com.impax.nakathisacco.Model.LoanTypeModel;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Model.MyLoan;
import com.impax.nakathisacco.Model.SavingsModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.AppUtilits;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.GenreAdapter;
import com.impax.nakathisacco.adapters.LoanTypeAdapter;
import com.impax.nakathisacco.adapters.LoansApplicantsAdapter;
import com.impax.nakathisacco.adapters.MyLoansAdapter;
import com.impax.nakathisacco.adapters.RecentLoansAdapter;
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
    double rate=1,doubleSavings,maximumCanBorrow;
    private String amt;
    private String savings;
    private TextView tvDisplayMaximumLoan,tv_display_no_loans;
    private Button btnLoanRequest;
    private LinearLayout linearLayout;

    private RecyclerView recyclerView,recyclerView2;
    private List<Loan> loanModels = new ArrayList<>();
    private List<MyLoan> myLoans = new ArrayList<>();
    public MyLoansAdapter adapter;
    LinearLayoutManager layoutManager2;


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

        //recyclerView = findViewById(R.id.rvRecentLoans);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       // recyclerView.setLayoutManager(linearLayoutManager);
      //  recyclerView.setHasFixedSize(true);
      //  adapter = new RecentLoansAdapter(this,loanModels);
       // recyclerView.setAdapter(adapter);
       // RecyclerView recyclerView =  findViewById(R.id.recycler_view);
       // LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //instantiate your adapter with the list of
        List<Genre> makeGenres = new ArrayList<>();
       recyclerView2 = findViewById(R.id.rv_my_Loans);
       // List<GuarantorModel> me = new ArrayList<>();
       // List<GuarantorModel> queen = new ArrayList<>();
       // queen.add(new GuarantorModel("1000"));
      // queen.add(new GuarantorModel("1000"));
     //  queen.add(new GuarantorModel("1000"));
//
//
//
       // myLoans.add(new MyLoan("HELLO",queen));
      // myLoans.add(new MyLoan("HELLO",queen));
     //  myLoans.add(new MyLoan("HELLO",queen));
      Log.e(TAG, "onCreate: "+myLoans );
//        adapter = new MyLoansAdapter(myLoans);
        layoutManager2 = new LinearLayoutManager(this);
//        recyclerView2.setLayoutManager(layoutManager2);
//        recyclerView2.setAdapter(adapter);

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
        mService.getRecentLoans(session.getIdNumber()).enqueue(new Callback<List<MyLoan>>() {
            @Override
            public void onResponse(Call<List<MyLoan>> call, Response<List<MyLoan>> response) {

                Log.e(TAG, "onResponse: "+response.body() );

                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                   // Toast.makeText(MyLoansActivity.this, "No loans", Toast.LENGTH_SHORT).show();

                        tv_display_no_loans.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);


                } else {

                   myLoans.clear();
                   myLoans.addAll(response.body());
                   adapter = new MyLoansAdapter(myLoans);

                    recyclerView2.setLayoutManager(layoutManager2);
                    recyclerView2.setAdapter(adapter);



                }
            }

            @Override
            public void onFailure(Call<List<MyLoan>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(MyLoansActivity.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void onBackPressed() {
        //super.onBackPressed();
    }




}
