package com.example.nakathisacco;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.HomePackage.signup;
import com.example.nakathisacco.Model.GuarantorModel;
import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.AppUtilits;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.GuarantorCursorAdapter;
import com.example.nakathisacco.adapters.SelectGuarantorAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddGuarantorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoanActivity.class.getSimpleName();
    private CheckBox chkTerms;
    RecyclerView.Adapter adapter = null;
    INakathiAPI mService;
    Session session;
    private RecyclerView recyclerView;
    private List<MembersModel> membersModels = new ArrayList<>();
    private List<MembersModel> guarantorModels = new ArrayList<>();
    private Button btnAddMore,btnValidate,btnSubmit;
    TextInputLayout tIidNo,tIfnames,tIamount,selfAmount;
    private EditText editTextId,editAmount,editfnames,editLoanAmount,editSelfAmount;
    private SearchView searchView;
    private SimpleCursorAdapter myAdapter;
    private GuarantorCursorAdapter guarantorCursorAdapter;
    private String[] strArrData = {"No Suggestions"};
    private Cursor cursor;
    String nameOfGuarantor;
    String idnumberOfGuarantor;
    boolean flag=true;
    private String fnames,guarantorIdNo,amount;
    TextView selftv;
    SelectGuarantorAdapter selectGuarantorAdapter;
    private String lastInsertedId,amountBorrowed,member,loantype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guarantor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mService = Common.getAPI();
        session = new Session(this);

        btnValidate = findViewById(R.id.btnValidate);
        btnSubmit = findViewById(R.id.btnsend);
        editTextId = findViewById(R.id.edtSearchId);
        editAmount = findViewById(R.id.editAmount);
        editfnames = findViewById(R.id.editfnames);
        tIidNo = findViewById(R.id.nationalId);
        tIfnames = findViewById(R.id.fname);
        tIfnames.setEnabled(false);
        tIamount = findViewById(R.id.tIamount);
        chkTerms = findViewById(R.id.chk_terms);
        editLoanAmount = findViewById(R.id.loan_amount);
        selfAmount = findViewById(R.id.s_amount);
        selftv = findViewById(R.id.tvSelf);
        editSelfAmount = findViewById(R.id.self_amount);




        amountBorrowed = getIntent().getExtras().getString("amount");
         member = getIntent().getExtras().getString("member_id");
        loantype = getIntent().getExtras().getString("loantype");
        String gtype = getIntent().getExtras().getString("gtype");
        String savings = getIntent().getExtras().getString("savings");

        Toast.makeText(this, amount+member+loantype+gtype+savings, Toast.LENGTH_SHORT).show();
        if (amountBorrowed!=null){
            editLoanAmount.setText(amountBorrowed);
        }
        if(gtype.equalsIgnoreCase("others")){
            selfAmount.setVisibility(View.GONE);
            selftv.setVisibility(View.GONE);

        }




        String checkTermsConditionText = "I agree to all the <a href='https://www.impaxafrica.com/' > Terms and Conditions</a>";

        chkTerms.setText(Html.fromHtml(checkTermsConditionText));
        chkTerms.setMovementMethod(LinkMovementMethod.getInstance());



        final String[] from = new String[]{"name"};

        final int[] to = new int[]{android.R.id.text1};


        // setup SimpleCursorAdapter
        myAdapter = new SimpleCursorAdapter(AddGuarantorActivity.this,
                android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);




        recyclerView = findViewById(R.id.rvGuarantors);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SelectGuarantorAdapter(this, guarantorModels);
        recyclerView.setAdapter(adapter);


        btnSubmit = findViewById(R.id.btnsend);
        btnAddMore = findViewById(R.id.btnadd);
        btnAddMore.setVisibility(View.GONE);


        btnSubmit.setOnClickListener(this);
        btnAddMore.setOnClickListener(this);
        btnValidate.setOnClickListener(this);

//        loan_id=session.getLoanId();
//        applicant_id = session.getIdNumber();
//        amount= session.getAmount();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnadd:
                addGuarantorToAdapter();

                break;


            case R.id.btnsend:
                Log.e(TAG, "button send");
                //submit();
                send();
                break;

            case R.id.btnValidate:
                Log.e(TAG, "button clicked");
                validateID();


                break;
        }


    }
    public void send(){
        if (editSelfAmount.getText().toString().isEmpty()) {
            editSelfAmount.setError("Please enter amont ");
            editSelfAmount.requestFocus();
            return;
        }
       double totals =  total()+Double.parseDouble(editSelfAmount.getText().toString());

        if(chkTerms.isChecked()){

            if(totals== (int)(Double.parseDouble(editLoanAmount.getText().toString()))) {

                String loanid = getloanId();
                submit(loanid);



            }else {
                Toast.makeText(this, "Your Guarantors amount and Loan applied shoud match", Toast.LENGTH_SHORT).show();



            }


        }else{
            Toast.makeText(this, "Check your Terms and conditions", Toast.LENGTH_SHORT).show();
        }



    }
    private String getloanId(){

        mService.insertLoan(member,loantype,amountBorrowed).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                AppUtilits.dismissDialog();


                if (response.isSuccessful())
                {
                    Log.e(TAG, "onResponse: "+response.body().getMessage() );
                     lastInsertedId = response.body().getMessage();



                }
            }
            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                AppUtilits.dismissDialog();
                Log.e("ln",""+ t.getMessage());

            }
        });

        return lastInsertedId;

    }
    public  double total(){
        double total =0;
        for (int i = 0; i < SelectGuarantorAdapter.membersModels.size(); i++) {
            Log.e(TAG, "submit: " + SelectGuarantorAdapter.membersModels.size());
            total+=Double.parseDouble( SelectGuarantorAdapter.membersModels.get(i).amount);

        }
        return total;

    }

    private void addGuarantorToAdapter() {

        double maximumGuarantorAmount = 0;
        double amountRequested = 0;

        if (editAmount.getText().toString().isEmpty()) {
            editAmount.setError("Please enter a valid Amount ");
            editAmount.requestFocus();
            return;
        }
        if (amount != null && amount!=" ") {
             maximumGuarantorAmount = Double.parseDouble(amount);
             amountRequested = Double.parseDouble(editAmount.getText().toString());

        }
        if (amountRequested>  maximumGuarantorAmount) {
            editAmount.setError("Please enter a valid Amount ");
            editAmount.requestFocus();
            return;
        }

        tIidNo.setVisibility(View.VISIBLE);
        btnValidate.setVisibility(View.VISIBLE);
        tIfnames.setVisibility(View.GONE);
        tIamount.setVisibility(View.GONE);

        guarantorModels.add(new MembersModel(guarantorIdNo, fnames, "", "",editAmount.getText().toString()));
        adapter.notifyDataSetChanged();
        editTextId.setText("");
        btnAddMore.setVisibility(View.GONE);
        amount=null;
       // guarantorIdNo=null;



    }

    private void validateID() {
       if (editTextId.getText().toString().isEmpty()) {
           editTextId.setError("Please Enter Your National ID");
            editTextId.requestFocus();
            return;
       }

        final String nationalID = editTextId.getText().toString().trim();
        Log.e(TAG, "Search if guarantor exist: "+nationalID);
        AppUtilits.showDialog(this);

        mService.getGuarantor(nationalID).enqueue(new Callback<MembersModel>() {
            @Override
            public void onResponse(Call<MembersModel> call, Response<MembersModel> response) {
                AppUtilits.dismissDialog();

                Log.e(TAG, "onResponse: "+response.toString() );

                if(response.isSuccessful()){
                    if (response.body().id_number!=null) {

                        tIidNo.setVisibility(View.GONE);
                        btnValidate.setVisibility(View.GONE);
                        tIfnames.setVisibility(View.VISIBLE);
                        tIamount.setVisibility(View.VISIBLE);
                        btnAddMore.setVisibility(View.VISIBLE);
                        amount=response.body().amount;
                        fnames= response.body().name;
                        guarantorIdNo= response.body().id_number;
                        editfnames.setText(fnames);

                    }else {
                        Toast.makeText(AddGuarantorActivity.this, " The member does not exist", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(AddGuarantorActivity.this, "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MembersModel> call, Throwable t) {
                AppUtilits.dismissDialog();

                Log.e(TAG, "onFailure: "+t);
                Toast.makeText(AddGuarantorActivity.this, "Error", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void submit(String loan_id) {

        String applicant_id = member;
        String name=null;
        String id_number=null;
        String amount=null;
        List<MembersModel> guarantorsSubmit = new ArrayList<>();


        for (int i = 0; i < SelectGuarantorAdapter.membersModels.size(); i++) {
            Log.e(TAG, "submit: " + SelectGuarantorAdapter.membersModels.size());
            name = SelectGuarantorAdapter.membersModels.get(i).name;
            id_number = SelectGuarantorAdapter.membersModels.get(i).id_number;
            amount = SelectGuarantorAdapter.membersModels.get(i).amount;
            if (amount.isEmpty()) {
                adapter.notifyItemChanged(i);
                Toast.makeText(this, "Check amount for each guarantor", Toast.LENGTH_SHORT).show();
                break;

            }

                if (!guarantorsSubmit.contains(SelectGuarantorAdapter.membersModels.get(i).id_number)) {

                    guarantorsSubmit.add(SelectGuarantorAdapter.membersModels.get(i));

                }

            Log.e(TAG, "submit: " + "Name --->" + name + " Id number " + id_number + " Amount " + amount + " Applicant Id " + applicant_id);
        }

        List<MembersModel> membersModels= guarantorModels;
        Log.e(TAG, "no of guarantors to submit"+guarantorsSubmit.size() );

        for (int i = 0; i < guarantorsSubmit.size(); i++) {
            Log.e(TAG, "submit: " + SelectGuarantorAdapter.membersModels.size());
            name = guarantorsSubmit.get(i).name;
            id_number = guarantorsSubmit.get(i).id_number;
            amount = guarantorsSubmit.get(i).amount;
            insertGuarantors(loan_id,id_number,amount,applicant_id);

            }






    }

    private void insertGuarantors(String loan_id, String member_id, String amount, String applicant_id) {
        Log.e(TAG, "insertGuarantors: ");

        mService.insertGuarantors(loan_id, member_id, amount, applicant_id).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.isSuccessful()) {

                    String msg = response.body().getMessage();
                    if (msg != null) {


                        if (msg.equalsIgnoreCase("true")) {
                            Toast.makeText(AddGuarantorActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onResponse: " + response.body());
                            Log.e("Values", "" + response.body().toString());
                            Intent mainIntent = new Intent(AddGuarantorActivity.this, MyLoansActivity.class);
                            startActivity(mainIntent);
                        } else {
                            Toast.makeText(AddGuarantorActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddGuarantorActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
         super.onBackPressed();
    }


}

