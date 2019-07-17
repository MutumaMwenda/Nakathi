package com.example.nakathisacco.HomePackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.LoanActivity;
import com.example.nakathisacco.MainActivity;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.R;
import com.example.nakathisacco.Responses.NewUserRegistration;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.AppUtilits;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Constant;
import com.example.nakathisacco.UtilitiesPackage.NetworkUtility;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.UtilitiesPackage.SharePreferenceUtils;
import com.example.nakathisacco.WebServices.ServiceWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup  extends AppCompatActivity {
    private String TAG = "signup";
    private Session session;
    Context cntx;
    private Button btnLogin,btnSignup;
    EditText id_numberText, passwordText, retype_passwordText;
    TextView create_acc;
    private String id_number,password,confirmpass;
    INakathiAPI mService;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_signup);
        session = new Session(this);
        mService = Common.getAPI();

        id_numberText =  findViewById(R.id.editText);
        passwordText =  findViewById(R.id.editText2);
        retype_passwordText =  findViewById(R.id.editText3);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.button3);


//        id_numberText.setText(session.getIdNumber());
//        id_numberText.setEnabled(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this,LogIn.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id_number=id_numberText.getText().toString();
                password=passwordText.getText().toString().trim();
                confirmpass=retype_passwordText.getText().toString().trim();
                if(password.equals(confirmpass)){
                    AppUtilits.showDialog(signup.this);
                    mService.createUser(password,id_number).enqueue(new Callback<MessageModel>() {
                        @Override
                        public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                            String msg= response.body().getMessage();
                            AppUtilits.dismissDialog();
                            if(msg.equalsIgnoreCase("success")){
                                Toast.makeText(signup.this, "You have successfully created your PIN. Proceed to Log In", Toast.LENGTH_LONG).show();
                                Intent intent= new Intent(signup.this, LogIn.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageModel> call, Throwable t) {
                            AppUtilits.dismissDialog();
                            Log.e(TAG, "onFailure: "+t );
                            Toast.makeText(signup.this, "Error occurred", Toast.LENGTH_SHORT).show();

                        }
                    });

                }else{
                    passwordText.setError("Your passwords dont match!");
                    passwordText.requestFocus();
                    return;

                }

            }
        });

    }


}

