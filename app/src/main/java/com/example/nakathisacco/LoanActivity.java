package com.example.nakathisacco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import com.example.nakathisacco.HomePackage.signup;
import com.example.nakathisacco.Model.LoanTypeModel;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.Model.SavingsModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.AppUtilits;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.LoanTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanActivity extends AppCompatActivity {

    private EditText loantypeid,memberid,amount,selfguarantAmount;
    private Button mCreate,submit;
    private Spinner loanTypeSpinner;
    LoanTypeAdapter loanTypeAdapter;
    private static final String TAG = LoanActivity.class.getSimpleName();
    LoanTypeModel loanTypeModel;
    private Session session;
    double rate=3.50;
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


                if (amount.getText().toString().isEmpty()) {
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


                String sAmount= String.valueOf(doubleAmt);
                String member_id = session.getIdNumber();
                String loantypeId = loanTypeModel.id;
                session.setAmount(sAmount);
                applyLoanSelf(member_id,loantypeId,sAmount);

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
                    savings=response.body().available_amount;
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

    private void applyLoanSelf(String member_id, final String loan_type_id, final String amount) {
        AppUtilits.showDialog(this);

        mService.insertLoan(member_id,loan_type_id,amount).enqueue(new Callback<MessageModel>() {
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
                    Toast.makeText(LoanActivity.this, "Loan Successfully applied", Toast.LENGTH_SHORT).show();
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
    private void applyLoan(String member_id, final String loan_type_id, final String amount) {
        AppUtilits.showDialog(this);

        mService.insertLoan(member_id,loan_type_id,amount).enqueue(new Callback<MessageModel>() {
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
                    //Toast.makeText(LoanActivity.this, "Loan Successfully applied", Toast.LENGTH_SHORT).show();
                    Log.e("res",""+ response);
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

}
