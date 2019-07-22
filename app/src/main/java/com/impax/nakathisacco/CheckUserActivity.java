package com.impax.nakathisacco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.impax.nakathisacco.HomePackage.signup;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.AppUtilits;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckUserActivity extends AppCompatActivity {

    private Button mCheckUser;
    private EditText editTextNationalId;
    private static final String TAG = LoanActivity.class.getSimpleName();
    private Session session;

    INakathiAPI mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkuser);
        mService = Common.getAPI();
        editTextNationalId = findViewById(R.id.nationaIdEDX);
        session = new Session(this);



        mCheckUser = findViewById(R.id.btnCheckUser);
        mCheckUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfIdExist();
            }
        });


    }

    private void checkIfIdExist() {
        if (editTextNationalId.getText().toString().isEmpty()) {
            editTextNationalId.setError("Please Enter Your National ID");
            editTextNationalId.requestFocus();
            return;
        }

       final String nationalID = editTextNationalId.getText().toString().trim();
        Log.e(TAG, "checkIfUserExist: "+nationalID);
        AppUtilits.showDialog(this);
        mService.checkId(nationalID).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                AppUtilits.dismissDialog();
                String msg = response.body().getMessage();
                if(msg.equalsIgnoreCase("true")){
                    session.setIdNumber(nationalID);
                    Intent intent= new Intent(CheckUserActivity.this, signup.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CheckUserActivity.this,"The user does not exist",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                AppUtilits.dismissDialog();
                Toast.makeText(CheckUserActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
