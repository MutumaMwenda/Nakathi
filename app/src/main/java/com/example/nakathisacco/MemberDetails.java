package com.example.nakathisacco;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.Model.LoanApplicantModel;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.AppUtilits;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Session;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class MemberDetails extends AppCompatActivity {
    private Button btnApprove,btnReject;
    private String TAG = MemberDetails.class.getSimpleName();
    public static final String MEMBER_ITEM_KEY = "member_item_key";
    TextView tvFullNames,tvRequestedAmount,tvBorrowedOn,tvBorrowedAmount,tvNoOfGuarantors;
    INakathiAPI mService;
    LoanApplicantModel loanApplicantModel;
    private Session session;
    String status,member_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mService = Common.getAPI();
        session = new Session(this);
        member_id = session.getIdNumber();





        loanApplicantModel= getIntent().getExtras().getParcelable(MemberDetails.MEMBER_ITEM_KEY);
        if (loanApplicantModel == null) {
            throw new AssertionError("No data item received!");
        } else {
            // Toast.makeText(this, "itmem: " + loanApplicantModel, Toast.LENGTH_SHORT).show();

        }

        btnApprove = findViewById(R.id.btn_approve);
        btnReject = findViewById(R.id.btn_reject);

        tvFullNames = findViewById(R.id.tv_fnames);
        tvRequestedAmount = findViewById(R.id.tv_requestedAmount);
        tvBorrowedOn = findViewById(R.id.tv_borrowedOn);
        tvBorrowedAmount = findViewById(R.id.tv_borrowed_amount);
        tvNoOfGuarantors = findViewById(R.id.tv_no_of_guarantors);

        tvFullNames.setText(loanApplicantModel.name);
        double amount = Double.parseDouble(loanApplicantModel.amount_requested);
        DecimalFormat formatter2 = new DecimalFormat("#,###");
        tvRequestedAmount.setText("Kshs : "+formatter2.format(amount));
        double amount2 = Double.parseDouble(loanApplicantModel.amount_borrowed);
        DecimalFormat formatter3 = new DecimalFormat("#,###");
        tvBorrowedAmount.setText("Kshs : "+formatter3.format(amount2));

        tvNoOfGuarantors.setText(loanApplicantModel.noOfGuarantors);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(loanApplicantModel.date_requested ) ;
        Date date = new Date(ts.getTime());
        String strDate = formatter.format(date);
        tvBorrowedOn.setText(strDate);


        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AppUtilits.showDialog(MemberDetails.this);
              //  AppUtilits.dismissDialog();

                Log.e(TAG, "clicked "+loanApplicantModel.loan_id );
               AlertDialog alertDialog = new AlertDialog.Builder(MemberDetails.this).create();
                alertDialog.setTitle("Accept  Guarantor");
                alertDialog.setMessage("Are you sure you want to be a Guarantor of this Member Loan?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(MemberDetails.this, "success", Toast.LENGTH_SHORT).show();
//                                Intent guaranteesIntent = new Intent(MemberDetails.this, ApproveLoanActivity.class);
//                                startActivity(guaranteesIntent);
                                status = "1";
                                submit();



                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();


                            }
                        });

                alertDialog.show();



            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AppUtilits.showDialog(MemberDetails.this);
                //AppUtilits.dismissDialog();

                Log.e(TAG, "clicked "+loanApplicantModel.loan_id );
                AlertDialog alertDialog = new AlertDialog.Builder(MemberDetails.this).create();
                alertDialog.setTitle("Decline  Guarantor");
                alertDialog.setMessage("Are you sure you want to Decline Guarantor of this Member Loan?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(MemberDetails.this, "success", Toast.LENGTH_SHORT).show();
//                                Intent guaranteesIntent = new Intent(MemberDetails.this, ApproveLoanActivity.class);
//                                startActivity(guaranteesIntent);
                                status = "2";
                                 submit();



                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();


                            }
                        });

                alertDialog.show();

            }
        });




    }
    private void submit(){
        AppUtilits.showDialog(this);
        Log.e(TAG, "submit: "+loanApplicantModel.loan_id+" "+status+"  "+member_id );
        mService.approveLoan("1",loanApplicantModel.loan_id,member_id).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                Log.e(TAG, "onResponse: "+response.body().toString() );
                AppUtilits.dismissDialog();

                if(response.isSuccessful()){
                    String msg= response.body().getMessage();
                    if (msg.equalsIgnoreCase("true")) {
                        AppUtilits.successDialog(MemberDetails.this);
                        Intent guaranteesIntent = new Intent(MemberDetails.this, ApproveLoanActivity.class);
                        startActivity(guaranteesIntent);

                    }else {
                        Toast.makeText(MemberDetails.this, "You are not able to approve the Member", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(MemberDetails.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(MemberDetails.this, "Error  occurred", Toast.LENGTH_SHORT).show();
                AppUtilits.dismissDialog();

            }
        });



    }
}
