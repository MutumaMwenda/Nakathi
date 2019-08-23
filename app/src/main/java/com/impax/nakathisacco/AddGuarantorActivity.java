package com.impax.nakathisacco;

import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.HomePackage.signup;
import com.impax.nakathisacco.Model.GuarantorModel;
import com.impax.nakathisacco.Model.MembersModel;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.AppUtilits;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.GuarantorCursorAdapter;
import com.impax.nakathisacco.adapters.SelectGuarantorAdapter;

import java.text.DecimalFormat;
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
    public String lastInsertedId,amountBorrowed,member,loantype,loanid,repay_period,rate,l_name;
    private String id, gtype;



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
         gtype = getIntent().getExtras().getString("gtype");
         repay_period= getIntent().getExtras().getString("repay_period");
         rate= getIntent().getExtras().getString("rate");
         l_name= getIntent().getExtras().getString("l_name");
        String savings = getIntent().getExtras().getString("savings");

        //Toast.makeText(this, amount+member+loantype+gtype+savings, Toast.LENGTH_SHORT).show();
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
                if(gtype.equalsIgnoreCase("others")){

                    final String sAmount= amountBorrowed;
                    final String member_id = member;
                    final String loantypeId = loantype;
                    final String rp_period = repay_period;
                    final String interestRate = rate;
                    String loan_name = l_name;
                    session.setAmount(sAmount);

                    final Dialog dialog = new Dialog(AddGuarantorActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.review_loan);
                    dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;

                    TextView r_amount =  dialog.findViewById(R.id.tv_review_amount);
                    TextView r_type =  dialog.findViewById(R.id.tv_review_type);
                    TextView r_interest =  dialog.findViewById(R.id.tv_interest);
                    TextView r_interest_rate =  dialog.findViewById(R.id.tv_interest_rate);
                    TextView r_period =  dialog.findViewById(R.id.tv_pay_period);
                    TextView r_total =  dialog.findViewById(R.id.tv_pay_amount);
                    TextView text =  dialog.findViewById(R.id.tv_review_amount);
                    double my_amount = Double.parseDouble(sAmount);
                    DecimalFormat formatter = new DecimalFormat("#,###");
                    text.setText("Amount : "+formatter.format(my_amount));
                    r_type.setText("Type : "+loan_name);
                    r_interest_rate.setText("Rate :"+interestRate+"%");
                    r_period.setText("Repay Period : "+repay_period+" Months");
                    double interest = Double.parseDouble(interestRate)/100* Double.parseDouble(sAmount);
                    r_interest.setText("Interest : "+String.valueOf(formatter.format(interest)));
                    double total_pay = my_amount+interest;
                    r_total.setText("Total : "+String.valueOf(formatter.format(total_pay)));

                    Button cancelButton = dialog.findViewById(R.id.btn_cancel);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Button proceedButton = dialog.findViewById(R.id.btn_ok);
                    proceedButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendOthers();

                        }
                    });

                    dialog.show();

                    //sendOthers();

                } else {
                    final String sAmount= amountBorrowed;
                    final String member_id = member;
                    final String loantypeId = loantype;
                    final String rp_period = repay_period;
                    final String interestRate = rate;
                    String loan_name = l_name;
                    session.setAmount(sAmount);

                    final Dialog dialog = new Dialog(AddGuarantorActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.review_loan);
                    dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;

                    TextView r_amount =  dialog.findViewById(R.id.tv_review_amount);
                    TextView r_type =  dialog.findViewById(R.id.tv_review_type);
                    TextView r_interest =  dialog.findViewById(R.id.tv_interest);
                    TextView r_interest_rate =  dialog.findViewById(R.id.tv_interest_rate);
                    TextView r_period =  dialog.findViewById(R.id.tv_pay_period);
                    TextView r_total =  dialog.findViewById(R.id.tv_pay_amount);
                    TextView text =  dialog.findViewById(R.id.tv_review_amount);
                    double my_amount = Double.parseDouble(sAmount);
                    DecimalFormat formatter = new DecimalFormat("#,###");
                    text.setText("Amount : "+formatter.format(my_amount));
                    r_type.setText("Type : "+loan_name);
                    r_interest_rate.setText("Rate :"+interestRate+"%");
                    r_period.setText("Repay Period : "+repay_period+" Months");
                    double interest = Double.parseDouble(interestRate)/100* Double.parseDouble(sAmount);
                    r_interest.setText("Interest : "+String.valueOf(formatter.format(interest)));
                    double total_pay = my_amount+interest;
                    r_total.setText("Total : "+String.valueOf(formatter.format(total_pay)));

                    Button cancelButton = dialog.findViewById(R.id.btn_cancel);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Button proceedButton = dialog.findViewById(R.id.btn_ok);
                    proceedButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendWithOthers();

                        }
                    });

                    dialog.show();
                   // sendWithOthers();
                }
                break;

            case R.id.btnValidate:
                Log.e(TAG, "button clicked");
                validateID();


                break;
        }


    }
    public void  sendOthers(){
        double totals =  total();

        if(chkTerms.isChecked()){

            if(totals== (int)(Double.parseDouble(editLoanAmount.getText().toString()))) {

                AppUtilits.showDialog(this);

                mService.insertLoan(member,loantype,amountBorrowed,repay_period,rate).enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        AppUtilits.dismissDialog();


                        if (response.isSuccessful())
                        {
                            Log.e(TAG, "Loan id Response: "+response.body().message);
                            Log.e(TAG, "Loan id Response: "+response.body().message);
                            //sToast.makeText(AddGuarantorActivity.this, response.body().message, Toast.LENGTH_SHORT).show();




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


                                if (!guarantorsSubmit.contains(SelectGuarantorAdapter.membersModels.get(i).id_number)) {

                                    guarantorsSubmit.add(SelectGuarantorAdapter.membersModels.get(i));

                                }

                                Log.e(TAG, "submit:  Loan id"+response.body().message + "Name --->" + name + " Id number-----> " + id_number + " Amount " + amount + " Applicant Id " + applicant_id);
                            }

                            List<MembersModel> membersModels= guarantorModels;
                            Log.e(TAG, "no of guarantors to submit"+guarantorsSubmit.size() );
                            Log.e(TAG,"");


                            for (int i = 0; i < guarantorsSubmit.size(); i++) {
                                Log.e(TAG, "submit: " + SelectGuarantorAdapter.membersModels.size());
                                name = guarantorsSubmit.get(i).name;
                                id_number = guarantorsSubmit.get(i).id_number;
                                amount = guarantorsSubmit.get(i).amount;
                                insertGuarantors(response.body().message,id_number,amount,applicant_id);

                            }
                            startActivity(new Intent(AddGuarantorActivity.this,SuccessActivity.class));












                        }
                    }
                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {
                        AppUtilits.dismissDialog();
                        Log.e("ln",""+ t.getMessage());

                    }
                });










            }else {
                Toast.makeText(this, "Your Guarantors amount and Loan applied should match", Toast.LENGTH_SHORT).show();



            }


        }else{
            Toast.makeText(this, "Check your Terms and conditions", Toast.LENGTH_SHORT).show();
        }


    }
    public void sendWithOthers(){
        if (editSelfAmount.getText().toString().isEmpty()) {
            editSelfAmount.setError("Please enter amount ");
            editSelfAmount.requestFocus();
            return;
        }
        final String self_amount =editSelfAmount.getText().toString();
       double totals =  total()+Double.parseDouble(self_amount);

        if(chkTerms.isChecked()){

            if(totals== (int)(Double.parseDouble(editLoanAmount.getText().toString()))) {

                AppUtilits.showDialog(this);

                mService.insertLoan(member,loantype,amountBorrowed,repay_period,rate).enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        AppUtilits.dismissDialog();


                        if (response.isSuccessful())
                        {
                            Log.e(TAG, "Loan id Response: "+response.body().message);
                            Log.e(TAG, "Loan id Response: "+response.body().message);
                            //sToast.makeText(AddGuarantorActivity.this, response.body().message, Toast.LENGTH_SHORT).show();




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


                                if (!guarantorsSubmit.contains(SelectGuarantorAdapter.membersModels.get(i).id_number)) {

                                    guarantorsSubmit.add(SelectGuarantorAdapter.membersModels.get(i));

                                }

                                Log.e(TAG, "submit:  Loan id"+response.body().message + "Name --->" + name + " Id number-----> " + id_number + " Amount " + amount + " Applicant Id " + applicant_id);
                            }

                            List<MembersModel> membersModels= guarantorModels;
                            Log.e(TAG, "no of guarantors to submit"+guarantorsSubmit.size() );

                            for (int i = 0; i < guarantorsSubmit.size(); i++) {
                                Log.e(TAG, "submit: " + SelectGuarantorAdapter.membersModels.size());
                                name = guarantorsSubmit.get(i).name;
                                id_number = guarantorsSubmit.get(i).id_number;
                                amount = guarantorsSubmit.get(i).amount;
                                insertGuarantors(response.body().message,id_number,amount,applicant_id);

                            }
                            insertGuarantors(response.body().message,member,self_amount,member);
                            startActivity(new Intent(AddGuarantorActivity.this,SuccessActivity.class));












                        }
                    }
                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {
                        AppUtilits.dismissDialog();
                        Log.e("ln",""+ t.getMessage());

                    }
                });










            }else {
                Toast.makeText(this, "Your Guarantors amount and Loan applied should match", Toast.LENGTH_SHORT).show();



            }


        }else{
            Toast.makeText(this, "Check your Terms and conditions", Toast.LENGTH_SHORT).show();
        }



    }
    private void getloanId(){

        mService.insertLoan(member,loantype,amountBorrowed,repay_period,rate).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                AppUtilits.dismissDialog();


                if (response.isSuccessful())
                {
                    Log.e(TAG, "Loan id Response: "+response.body().message);
                     lastInsertedId = response.body().message;



                }
            }
            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                AppUtilits.dismissDialog();
                Log.e("ln",""+ t.getMessage());

            }
        });



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
                    if (response.body().amount!=null) {

                        tIidNo.setVisibility(View.GONE);
                        btnValidate.setVisibility(View.GONE);
                        tIfnames.setVisibility(View.VISIBLE);
                        tIamount.setVisibility(View.VISIBLE);
                        btnAddMore.setVisibility(View.VISIBLE);
                        amount=response.body().amount;
                        //fnames= response.body().name;
                        //guarantorIdNo= response.body().id_number;
                        guarantorIdNo= nationalID;
                        //editfnames.setText(fnames);
                        editAmount.setText(amount);

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
        Log.e(TAG, "submit"+loan_id );

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
       //startActivity(new Intent(AddGuarantorActivity.this,SuccessActivity.class));






    }

    private void insertGuarantors(String loan_id, String member_id, String amount, String applicant_id) {
        Log.e(TAG, "loan id"+loan_id );
        Log.e(TAG, "insertGuarantors: ");

        mService.insertGuarantors(loan_id, member_id, amount, applicant_id).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.isSuccessful()) {

                    String msg = response.body().getMessage();
                    if (msg != null) {


                        if (msg.equalsIgnoreCase("true")) {
                           // Toast.makeText(AddGuarantorActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onResponse: " + response.body());
                            Log.e("Values", "" + response.body().toString());

                        } else {
                            //Toast.makeText(AddGuarantorActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(AddGuarantorActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

