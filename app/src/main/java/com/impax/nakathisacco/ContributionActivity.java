package com.impax.nakathisacco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.Model.ContributionTypes;
import com.impax.nakathisacco.Model.ContributionsModel;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Model.SavingsLogModel;
import com.impax.nakathisacco.Model.SavingsModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.AppUtilits;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.EnterContributionsAdapter;
import com.impax.nakathisacco.adapters.SavingsLogAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContributionActivity extends AppCompatActivity {


    Session session;
   // String id_number,reg_no;
    private RecyclerView recyclerView;
    private List<ContributionsModel> contributionsModels = new ArrayList<>();
    private List<ContributionTypes>  mcContributionTypes = new ArrayList<>();
    RecyclerView.Adapter adapter = null;

    private String  reg_no, member_id, contribution_id, amount, contribution_source,received_by;
    private TextView tvRegno,tvOwner;
    private EditText edxAmount;
    private Button submitBtn;
    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();
        tvRegno=findViewById(R.id.tv_reg_no);
        tvOwner=findViewById(R.id.tv_owner_name);
        edxAmount=findViewById(R.id.tv_amount);
         submitBtn=findViewById(R.id.submitBtn);
         reg_no= getIntent().getExtras().getString("regno");

        session = new Session(this);
        received_by = session.getIdNumber();


        recyclerView = findViewById(R.id.rvContributionsLog);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new EnterContributionsAdapter(this,mcContributionTypes);
        recyclerView.setAdapter(adapter);
        mService = Common.getAPI();
        getContributions(reg_no);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int randomNumber = random.nextInt(9999999- 100000) + 100000;
                String transaction_id="TRN"+randomNumber;
                 AppUtilits.showDialog(ContributionActivity.this);
                for (ContributionTypes contributionType: mcContributionTypes)
                {

                    adapter.notifyDataSetChanged();
                    contribution_id =contributionType.id;
                     amount =contributionType.amount;

                    //String amount =contributionType.getAmount();
                   // received_by=session.getName();
                    reg_no=tvRegno.getText().toString();
                   // member_id=member_id;
                    contribution_source=contributionType.contribution_source;

                    contribution_source="3";
                   // Log.e("","Name:"+received_by);
                  //Log.e(received_by+"Contribution","   amt: "+amount+"src:"+contribution_source+"reg:"+reg_no+"cont:"+contribution_id+"mem:"+member_id+"trans"+transaction_id );
                    saveContributions( reg_no, member_id, contribution_id, amount, contribution_source,received_by,transaction_id);
                }
                AppUtilits.dismissDialog();
                startActivity(new Intent(ContributionActivity.this,GetVehicleActivity.class));

//                for(int i =0;i<mcContributionTypes.size();i++){
//                    reg_no="KFSJHB";
//                    member_id="6783";
//                    contribution_id="6";
//                    amount="700";
//                    contribution_source="3";
//                    mcContributionTypes.
//
//                    Log.e("Values",amount);
//                    saveContributions( reg_no, member_id, contribution_id, amount, contribution_source);
//                }


            }
        });

    }



private void saveContributions(String reg_no,String member_id,String contribution_id,String amount,String contribution_source ,String received_by,String transaction_id){
        mService.saveContributions(reg_no, member_id, contribution_id, amount, contribution_source,received_by,transaction_id).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                AppUtilits.dismissDialog();


            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {

            }
        });

}
    private void getContributions(String reg_no) {
        mService.getContributions(reg_no).enqueue(new Callback<ContributionsModel>() {
            @Override
            public void onResponse(Call<ContributionsModel>call, Response<ContributionsModel> response) {

                Log.e("Data",response.body().contributions.toString());
                if (  response.body().toString().equalsIgnoreCase("[]")) {
                    Toast.makeText(ContributionActivity.this, "Contributions not available", Toast.LENGTH_SHORT).show();
                } else {
                     member_id = response.body().owner_id;
                      tvRegno.setText(response.body().reg_no);
                      tvOwner.setText(response.body().name+" ("+response.body().phone_number+")");

                    mcContributionTypes.clear();
                    mcContributionTypes.addAll(response.body().contributions);
                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onFailure(Call<ContributionsModel> call, Throwable t) {
              Log.d("Msg",t.getMessage());
                Toast.makeText(ContributionActivity.this, "Error occurred while processing your request", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
