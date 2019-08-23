package com.impax.nakathisacco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.Model.AssetsModel;
import com.impax.nakathisacco.Model.Contribution;
import com.impax.nakathisacco.Model.LoanApplicantModel;
import com.impax.nakathisacco.Model.Vehicle;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.ContributionAdapter;
import com.impax.nakathisacco.adapters.ContributionDetailsAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContributionDetails extends AppCompatActivity {
    private static final String TAG = ContributionDetails.class.getSimpleName();

    Session session;
    String id_number;
    private List<Contribution> contributions= new ArrayList<>();
    private Button btnDone;
    public static final String CONTRIBUTION_DETAILS_ITEM_KEY = "details_item_key";
    private Contribution mContribution;
    private RecyclerView recyclerView;
    RecyclerView.Adapter adapter = null;
    private TextView textView;


    private String reg_no,name;
    INakathiAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContribution= getIntent().getExtras().getParcelable(ContributionDetails.CONTRIBUTION_DETAILS_ITEM_KEY);
        if (contributions== null) {
            throw new AssertionError("No data item received!");
        } else {
            // Toast.makeText(this, "itmem: " + mContribution.c_list, Toast.LENGTH_SHORT).show();

        }
        recyclerView = findViewById(R.id.rvc_details);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ContributionDetailsAdapter(this,mContribution.contribution);
        recyclerView.setAdapter(adapter);
        textView =  findViewById(R.id.c_date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp da = java.sql.Timestamp.valueOf(mContribution.contribution_date ) ;
        // java.sql.Timestamp dt = java.sql.Timestamp.valueOf(driver.assigned_up_to_date ) ;
        Date date_da= new Date(da.getTime());
        String c_date = formatter.format(date_da);
        textView.setText(c_date);


        mService = Common.getAPI();
        session = new Session(this);
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContributionDetails.this,MyLoansActivity.class);
                startActivity(intent);
            }
        });


    }
    public void onBackPressed() {
        //super.onBackPressed();
    }


}
