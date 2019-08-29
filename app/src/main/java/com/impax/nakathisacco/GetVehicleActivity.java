package com.impax.nakathisacco;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.HomePackage.signup;
import com.impax.nakathisacco.Model.AssetsModel;
import com.impax.nakathisacco.Model.Contribution;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Model.Vehicle;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.AppUtilits;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.AutoSuggestAdapter;
import com.impax.nakathisacco.adapters.ContributionAdapter;
import com.impax.nakathisacco.adapters.GenreAdapter;
import com.impax.nakathisacco.adapters.MyLoansAdapter;
import com.impax.nakathisacco.adapters.SuggestAdapter;
import com.impax.nakathisacco.adapters.TransAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVehicleActivity extends AppCompatActivity {
    public TransAdapter adapter;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;
    private SuggestAdapter suggestAdapter;
    INakathiAPI mService;
    private Button btnNext;
    String regno;
    private RecyclerView recyclerView;
    private List<Contribution> contributions = new ArrayList<>();
    private static final String TAG = GetVehicleActivity.class.getSimpleName();
    private  TextView tv_no_trans;
    LinearLayout linearLayout;
    LinearLayoutManager linearLayoutManager;
    Session session;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_vehicle);
        mService = Common.getAPI();
        session = new Session(this);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setTitle(getClass().getSimpleName());

        //Setting up the adapter for AutoSuggest
        btnNext = findViewById(R.id.btnNext);
        tv_no_trans = findViewById(R.id.tv_display_no_trans);
        linearLayout= findViewById(R.id.ly_trans);
        recyclerView= findViewById(R.id.rv_trans);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new TransAdapter(this,contributions);
        recyclerView.setAdapter(adapter);
        getMyTrans(session.getIdNumber());
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GetVehicleActivity.this,ContributionActivity.class);
                intent.putExtra("regno",regno);
                startActivity(intent);
            }
        });

        final AppCompatAutoCompleteTextView autoCompleteTextView =
                findViewById(R.id.auto_complete_edit_text);
        final TextView selectedText = findViewById(R.id.selected_item);
        suggestAdapter= new SuggestAdapter(this);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(suggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        regno = suggestAdapter.getItem(position).reg_no;
                        selectedText.setText(regno);
                        autoCompleteTextView.setText(regno);
                    }
                });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });


    }
    public void makeApiCall(String prefix){
        mService.getVehicles(prefix).enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                // Log.e("ExpandActvity", "onResponse: "+response.body() );

                suggestAdapter.mVehicles.clear();
                suggestAdapter.mVehicles.addAll(response.body());
                suggestAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Log.e("ExpandActivity", "onFailure: "+t );

            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       // adapter.onRestoreInstanceState(savedInstanceState);
    }
    private void getMyTrans(String id_number) {
        Log.e(TAG, "getAssets: "+id_number );
        mService.getMyTrans(id_number).enqueue(new Callback<List<Contribution>>() {
            @Override
            public void onResponse(Call<List<Contribution>> call, Response<List<Contribution>> response) {
                Log.e(TAG, "onResponse: "+response.body() );

                if (response.body().isEmpty() || response.body().toString().equalsIgnoreCase("[]")) {
                    // Toast.makeText(MyLoansActivity.this, "No loans", Toast.LENGTH_SHORT).show();

                   // Toast.makeText(GetVehicleActivity.this, "Nothing available to display", Toast.LENGTH_SHORT).show();
                    tv_no_trans.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);

                } else {
                    contributions.clear();
                    contributions.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);



                }



            }

            @Override
            public void onFailure(Call<List<Contribution>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t);

            }
        });
    }
}
