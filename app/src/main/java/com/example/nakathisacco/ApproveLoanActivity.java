package com.example.nakathisacco;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nakathisacco.Model.GuarantorModel;
import com.example.nakathisacco.Model.LoanApplicantModel;
import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.GuarantorAdapter;
import com.example.nakathisacco.adapters.LoansApplicantsAdapter;
import com.example.nakathisacco.adapters.TabAdapter;
import com.example.nakathisacco.fragments.NewOnesFragment;
import com.example.nakathisacco.fragments.OldOnesFragment;
import com.example.nakathisacco.fragments.RejectedOnesFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveLoanActivity extends AppCompatActivity {


    private static final String TAG = ApproveLoanActivity.class.getSimpleName();

    Session session;
    String id_number;
    private RecyclerView recyclerView;
    private List<LoanApplicantModel> loanApplicantModels = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    String TabNames[]={"New","Approved","Rejected"};
    Context context;

    private String loan_id,amount;
    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getApplicationContext();
        setContentView(R.layout.activity_approve_loan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();

        viewPager = findViewById(R.id.viewpager);
        createViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        session = new Session(this);
        loan_id=session.getLoanId();
        id_number = session.getIdNumber();
        amount=session.getAmount();




      //  getGuarantorLoans(id_number);

        //recyclerView = findViewById(R.id.rvHome);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.setHasFixedSize(true);
       // adapter = new LoansApplicantsAdapter(this,loanApplicantModels);
        //recyclerView.setAdapter(adapter);
       ;






    }







    private void getGuarantorLoans(String id_number) {
        mService.getGuarantorLoans(id_number).enqueue(new Callback<List<LoanApplicantModel>>() {
            @Override
            public void onResponse(Call<List<LoanApplicantModel>> call, Response<List<LoanApplicantModel>> response) {
                Log.e(TAG, "onResponse: "+response.body() );

                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                    Toast.makeText(ApproveLoanActivity.this, "Members not available", Toast.LENGTH_SHORT).show();
                } else {

                    loanApplicantModels.clear();
                    loanApplicantModels.addAll(response.body());
                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onFailure(Call<List<LoanApplicantModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(ApproveLoanActivity.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewOnesFragment.newInstance();
                case 1:
                    return OldOnesFragment.newInstance();

                case 2:
                    return RejectedOnesFragment.newInstance();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return TabNames[0];
                case 1:
                    return TabNames[1];
                case 2:
                    return TabNames[2];

            }
            return "";
        }
    }


}
