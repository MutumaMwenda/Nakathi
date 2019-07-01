package com.example.nakathisacco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nakathisacco.Model.GuarantorModel;
import com.example.nakathisacco.Model.LoanTypeModel;
import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.GuarantorAdapter;
import com.example.nakathisacco.adapters.LoanTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuarantorActivity extends AppCompatActivity {

    private Button mCreate;
    private Spinner guarantor1,guarantor2,guarantor3;
    GuarantorAdapter guarantorAdapter;
    private static final String TAG = LoanActivity.class.getSimpleName();
    MembersModel membersModel;
    Session session;
    private String guarantor1_id,guarantor2_id,guarantor3_id;
    private String loan_id,amount,applicant_id;
    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantor);
        mService = Common.getAPI();

        guarantor1 = findViewById(R.id.spGuarantor1);
        guarantor2 = findViewById(R.id.spGuarantor2);
        guarantor3 = findViewById(R.id.spGuarantor3);
        mCreate= findViewById(R.id.btnLoan);
        getMembers();
        session = new Session(this);
        guarantorAdapter = new GuarantorAdapter(this);
        guarantor1.setAdapter(guarantorAdapter);
        guarantor2.setAdapter(guarantorAdapter);
        guarantor3.setAdapter(guarantorAdapter);
        loan_id=session.getLoanId();
        applicant_id = session.getIdNumber();

        amount=session.getAmount();
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGuarantors(loan_id,guarantor1_id,amount,applicant_id);
                insertGuarantors(loan_id,guarantor2_id,amount,applicant_id);
                insertGuarantors(loan_id,guarantor3_id,amount,applicant_id);
            }
        });
        guarantor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                membersModel = guarantorAdapter.membersModels.get(position);
                guarantor1_id=membersModel.id_number;
               // Log.d("ID",""+member_id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        guarantor2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                membersModel = guarantorAdapter.membersModels.get(position);

                guarantor2_id=membersModel.id_number;
                //Log.d("ID",""+member_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        guarantor3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                membersModel = guarantorAdapter.membersModels.get(position);

                guarantor3_id=membersModel.id_number;
               // Log.d("ID",""+member_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void insertGuarantors(final String loan_id,final String member_id,  final String amount,String applicant_id) {

    }

    private void getMembers() {
        mService.getMembers().enqueue(new Callback<List<MembersModel>>() {
            @Override
            public void onResponse(Call<List<MembersModel>> call, Response<List<MembersModel>> response) {

                Log.d("id",response.body().toString());
                Type type = new TypeToken<ArrayList<MembersModel>>() {
                }.getType();
//                ArrayList<LoanTypeModel> loanTypes = new Gson().fromJson(response, type);
                guarantorAdapter.membersModels.clear();
                guarantorAdapter.membersModels.addAll(response.body());
                guarantorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MembersModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}
