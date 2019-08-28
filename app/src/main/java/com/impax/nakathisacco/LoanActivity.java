package com.impax.nakathisacco;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.HomePackage.signup;
import com.impax.nakathisacco.Model.LoanTypeModel;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Model.SavingsModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.AppUtilits;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.LoanTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanActivity extends AppCompatActivity {

    private EditText loantypeid,memberid,amount,selfguarantAmount,edt_repay_period;
    private Button mCreate,submit;
    private Spinner loanTypeSpinner;
    LoanTypeAdapter loanTypeAdapter;
    private static final String TAG = LoanActivity.class.getSimpleName();
    LoanTypeModel loanTypeModel;
    private Session session;
    double rate=1;
    double loanlimit;
    private String amt;
    private String savings="";
    private TextView tvLoanLimit;
    private CheckBox tvcCheckBox;
    private String guarantorType;


    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();
        loanTypeSpinner = findViewById(R.id.spLoanType);
        //loantypeid= findViewById(R.id.loantypeidEDX);
        memberid = findViewById(R.id.memberidEDX);
        amount= findViewById(R.id.amountEDX);
        selfguarantAmount= findViewById(R.id.selfGuarantEd);
        mCreate= findViewById(R.id.btnLoan);
        tvLoanLimit = findViewById(R.id.tv_loanlimit);
        edt_repay_period = findViewById(R.id.repay_period);
        submit = findViewById(R.id.btnSubmit);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        final int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                   if(checkedRadioButton.getId()== R.id.radio2){
                       submit.setVisibility(View.GONE);
                       mCreate.setVisibility(View.VISIBLE);
                      // Toast.makeText(LoanActivity.this, "2", Toast.LENGTH_SHORT).show();
                       guarantorType ="selfwithothers";

                   } else if(checkedRadioButton.getId()== R.id.radio3){
                       //Toast.makeText(LoanActivity.this, "3", Toast.LENGTH_SHORT).show();
                       submit.setVisibility(View.GONE);
                       mCreate.setVisibility(View.VISIBLE);
                       guarantorType ="others";

                   }else {
                       submit.setVisibility(View.VISIBLE);
                       mCreate.setVisibility(View.GONE);
                   }

                }
            }
        });



        tvcCheckBox=findViewById(R.id.chk_for_guarantor);
        tvcCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked)
                {
                    mCreate.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);


                }
                else
                {
                    mCreate.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);

                }
            }
        });

        session = new Session(this);
      //  AppUtilits.showDialog(this);
        mService.checkExistsLoan(session.getIdNumber()).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
               // AppUtilits.dismissDialog();
                String msg = response.body().getMessage();
                if(msg.equalsIgnoreCase("true")){
                    Toast.makeText(LoanActivity.this, "You already have an existing loan", Toast.LENGTH_SHORT).show();

                  // finish();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                AppUtilits.dismissDialog();

            }
        });


        loanTypeAdapter = new LoanTypeAdapter(this);
        loanTypeSpinner.setAdapter(loanTypeAdapter);
        getLoanTypes();
        getMembersavings(session.getIdNumber());
        loanTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loanTypeModel = LoanTypeAdapter.loanTypeModels.get(position);
                if (loanTypeModel.repaymentPeriod != null) {
                    edt_repay_period.setText(loanTypeModel.repaymentPeriod);

                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edt_repay_period.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().isEmpty()) {
                    String newValue = editable.toString();
                    if (newValue.length() > 1 && newValue.startsWith("0")) {
                        newValue = newValue.substring(1);
                        edt_repay_period.setText(newValue);
                        edt_repay_period.setSelection(newValue.length());
                    }
                    Double requested = Double.valueOf(newValue);
                    Double period =Double.valueOf(loanTypeModel.repaymentPeriod);
                    if (requested > period) {
                        edt_repay_period.removeTextChangedListener(this);
                        edt_repay_period.setText(Double.toString(period));
                        edt_repay_period.setSelection(edt_repay_period.getText().length());
                        Toast.makeText(LoanActivity.this, "Maximum repayment period has been reached", Toast.LENGTH_LONG).show();
                        edt_repay_period.addTextChangedListener(this);
                        submit.setEnabled(false);
                    } else if
                    (checkedRadioButtonId == R.id.selfradio1) {
                        submit.setEnabled(true);




                    }
                } else {
                    edt_repay_period.setText("0");
                    edt_repay_period.setSelection(1+amount.getText().length());
                }

            }
        });
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    String newValue = s.toString();
                    if (newValue.length() > 1 && newValue.startsWith("0")) {
                        newValue = newValue.substring(1);
                        amount.setText(newValue);
                        amount.setSelection(newValue.length());
                    }
                    Double requested = Double.valueOf(newValue);
                    if (requested > loanlimit) {
                        amount.removeTextChangedListener(this);
                        amount.setText(Double.toString(loanlimit));
                        amount.setSelection(amount.getText().length());
                        Toast.makeText(LoanActivity.this, "Requested amount cannot exceed  loan limit amount,Request a lower amount or proceed to add guarantor", Toast.LENGTH_LONG).show();
                        amount.addTextChangedListener(this);
                        submit.setEnabled(false);
                    } else if
                         (checkedRadioButtonId == R.id.selfradio1) {
                            submit.setEnabled(true);




                    }
                } else {
                    amount.setText("0");
                    amount.setSelection(amount.getText().length());
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (amount.getText().toString().isEmpty()|| Double.parseDouble(amount.getText().toString())<100) {
                    amount.setError("Enter amount");
                    amount.requestFocus();
                    return;
                }

//                if (selfguarantAmount.getText().toString().isEmpty()) {
//                    selfguarantAmount.setError("Enter Amount for Self Guarantee");
//                    selfguarantAmount.requestFocus();
//                    return;
//                }

                Log.e(TAG, "onClick: "+savings );
                amt=amount.getText().toString();
                double doubleAmt = Double.parseDouble(amt);
                double doubleSavings = Double.parseDouble(savings);
                if(doubleAmt>(doubleSavings*rate)){
                    Toast.makeText(LoanActivity.this, "Add amount equal or lower than your limit", Toast.LENGTH_SHORT).show();
                    return;

                }


               final String sAmount= String.valueOf(doubleAmt);
               final String member_id = session.getIdNumber();
                final String loantypeId = loanTypeModel.id;
                 final String repay_period = loanTypeModel.repaymentPeriod;
                final String interestRate = loanTypeModel.interestRate;
                String loan_name = loanTypeModel.name;
                session.setAmount(sAmount);

                final Dialog dialog = new Dialog(LoanActivity.this);
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
                double interest = Double.parseDouble(interestRate)/100*doubleAmt;
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
                        applyLoanSelf(member_id,loantypeId,sAmount,repay_period,interestRate);
                    }
                });

                dialog.show();


            }
        });




        mCreate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


                if (amount.getText().toString().isEmpty()) {
                   amount.setError("Enter amount");
                   amount.requestFocus();
                   return;
               }

               Log.e(TAG, "onClick: "+savings );
               amt=amount.getText().toString();
               double doubleAmt = Double.parseDouble(amt);
               double doubleSavings = Double.parseDouble(savings);
               if(doubleAmt>(doubleSavings*rate)){
                   Toast.makeText(LoanActivity.this, "Add amount equal or lower than your limit", Toast.LENGTH_SHORT).show();
                   return;

               }


               String sAmount= String.valueOf(doubleAmt);
               String member_id = session.getIdNumber();
               String loantypeId = loanTypeModel.id;
               session.setAmount(sAmount);
              // applyLoan(member_id,loantypeId,sAmount);
               Intent intent= new Intent(LoanActivity.this,AddGuarantorActivity.class);
               intent.putExtra("amount",sAmount);
               intent.putExtra("member_id",member_id);
               intent.putExtra("loantype",loantypeId);
               intent.putExtra("gtype",guarantorType);
               intent.putExtra("savings",tvLoanLimit.getText());
               intent.putExtra("repay_period",loanTypeModel.repaymentPeriod);
               intent.putExtra("rate",loanTypeModel.interestRate);
               intent.putExtra("l_name",loanTypeModel.name);
               startActivity(intent);


           }
       });

    }

    private void getLoanTypes() {
        mService.getLoanType().enqueue(new Callback<List<LoanTypeModel>>() {
            @Override
            public void onResponse(Call<List<LoanTypeModel>> call, Response<List<LoanTypeModel>> response) {
                Log.e(TAG, "onResponse: "+response.body() );

                if (response.body() == null) {

                }else {

                    Type type = new TypeToken<ArrayList<LoanTypeModel>>() {
                    }.getType();
//                ArrayList<LoanTypeModel> loanTypes = new Gson().fromJson(response, type);
                    loanTypeAdapter.loanTypeModels.clear();
                    loanTypeAdapter.loanTypeModels.addAll(response.body());
                    loanTypeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<LoanTypeModel>> call, Throwable t) {

                Toast.makeText(LoanActivity.this," " + t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("ln",""+ t.getMessage());

            }
        });
    }


    private void getMembersavings(String id_number){
        mService.getMemberSavings(id_number).enqueue(new Callback<SavingsModel>() {
            @Override
            public void onResponse(Call<SavingsModel> call, Response<SavingsModel> response) {
                if (response.body() != null) {
                    savings=response.body().amount;
                    double doubleSavings = Double.parseDouble(savings);
                    loanlimit  = doubleSavings*rate;
                    Integer limit =(int) loanlimit;
                    String displayLoanLimit = limit.toString();
                    String number = displayLoanLimit;
                    double amount = Double.parseDouble(number);
                    DecimalFormat formatter = new DecimalFormat("#,###");
                    tvLoanLimit.setText("Kshs " + formatter.format(amount));


                    Log.e("Sav:",savings);

                }
                else {
                    savings="10000";
                }


            }

            @Override
            public void onFailure(Call<SavingsModel> call, Throwable t) {

            }
        });

    }

    private void applyLoanSelf(final String member_id, final String loan_type_id, final String amount, final String repay_period, final String rate) {
        AppUtilits.showDialog(this);

        mService.insertLoan(member_id,loan_type_id,amount,repay_period,rate).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                AppUtilits.dismissDialog();

                if (response.isSuccessful())
                {
                    String lastInsertedId = response.body().getMessage();
                    Log.e(TAG, "onResponse: "+lastInsertedId );
                    if (!lastInsertedId.equals("-1"))
                        session.setLoanId(lastInsertedId);
                    Log.e(TAG, "session loan id "+session.getLoanId() );
                    insertGuarantors(response.body().getMessage(),member_id,amount,member_id);
                    Log.e("res",""+ response);
                    Intent intent= new Intent(LoanActivity.this,SuccessActivity.class);
                    startActivity(intent);

                }
            }
            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                AppUtilits.dismissDialog();
                Toast.makeText(LoanActivity.this," " + t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("ln",""+ t.getMessage());

            }
        });
    }
    private void applyLoan(final String member_id, final String loan_type_id, final String amount, final String repay_period,final String rate) {
        AppUtilits.showDialog(this);

        mService.insertLoan(member_id,loan_type_id,amount,repay_period,rate).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                AppUtilits.dismissDialog();


                if (response.isSuccessful())
                {
                    String lastInsertedId = response.body().getMessage();
                    Log.e(TAG, "onResponse: "+lastInsertedId );
                    if (!lastInsertedId.equals("-1"))
                        session.setLoanId(lastInsertedId);
                    Log.e(TAG, "session loan id "+session.getLoanId() );
                    Intent intent= new Intent(LoanActivity.this,AddGuarantorActivity.class);
                    startActivity(intent);

                }
            }
            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                AppUtilits.dismissDialog();
                Toast.makeText(LoanActivity.this," " + t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("ln",""+ t.getMessage());

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                        }
                    } else {
                    }
                }

            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {

            }
        });
    }


}
