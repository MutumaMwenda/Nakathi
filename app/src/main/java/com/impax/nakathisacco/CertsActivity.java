package com.impax.nakathisacco;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.impax.nakathisacco.Model.CertsModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.CertsAdapter;
import com.impax.nakathisacco.adapters.TabAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertsActivity extends AppCompatActivity {


    private static final String TAG = CertsActivity.class.getSimpleName();

    Session session;
    String id_number,reg_no;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
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

        context=this.getApplicationContext();
        viewPager =  findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Own ");
        adapter.addFragment(new Tab2Fragment(), "Vehicle");
        adapter.addFragment(new Tab3Fragment(), "Staff");

        session = new Session(this);
        loan_id=session.getLoanId();
        id_number = session.getIdNumber();
        amount=session.getAmount();

        //getCerts(id_number);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        highLightCurrentTab(0); // for initial selected tab view
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position); // for tab change
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });




    }
    private void highLightCurrentTab(int position) {

        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getTabView(position,context));
    }

/*
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
*/




}
