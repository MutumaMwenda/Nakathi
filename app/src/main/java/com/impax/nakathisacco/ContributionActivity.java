package com.impax.nakathisacco;

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
import com.impax.nakathisacco.Model.SavingsLogModel;
import com.impax.nakathisacco.Model.SavingsModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.EnterContributionsAdapter;
import com.impax.nakathisacco.adapters.SavingsLogAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

    private String  reg_no, member_id, contribution_id, amount, contribution_source;
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

        session = new Session(this);
       // id_number = session.getIdNumber();


        recyclerView = findViewById(R.id.rvContributionsLog);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new EnterContributionsAdapter(this,mcContributionTypes);
        recyclerView.setAdapter(adapter);
        mService = Common.getAPI();
        getContributions("KAC230K");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ContributionTypes contributionType: mcContributionTypes)
                {
                    adapter.notifyDataSetChanged();
                    String id =contributionType.id;
                    amount =contributionType.amount;

                    reg_no="KFSJHB";
                    member_id="6783";
                    contribution_id="6";
                    //amount="700";
                    contribution_source="3";
                    Log.e("Contribution",id+"    "+amount );
                }

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



private void saveContributions(String reg_no,String member_id,String contribution_id,String amount,String contribution_source ){
        mService.saveContributions( reg_no, member_id, contribution_id, amount, contribution_source).enqueue(new Callback<List<ContributionTypes>>() {
            @Override
            public void onResponse(Call<List<ContributionTypes>> call, Response<List<ContributionTypes>> response) {

            }

            @Override
            public void onFailure(Call<List<ContributionTypes>> call, Throwable t) {

            }
        });

}
    private void getContributions(String reg_no) {
        mService.getContributions(reg_no).enqueue(new Callback<ContributionsModel>() {
            @Override
            public void onResponse(Call<ContributionsModel>call, Response<ContributionsModel> response) {

                Log.e("Data",response.body().toString());
                if (  response.body().toString().equalsIgnoreCase("[]")) {
                    Toast.makeText(ContributionActivity.this, "Contributions not available", Toast.LENGTH_SHORT).show();
                } else {
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
