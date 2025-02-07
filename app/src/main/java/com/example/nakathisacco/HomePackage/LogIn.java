package com.example.nakathisacco.HomePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nakathisacco.ApproveLoanActivity;
import com.example.nakathisacco.CheckUserActivity;
import com.example.nakathisacco.LoanActivity;
import com.example.nakathisacco.MainActivity;
import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.MyAccount;
import com.example.nakathisacco.R;
import com.example.nakathisacco.Responses.NewUserRegistration;
import com.example.nakathisacco.Responses.UserSignInRes;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.AppUtilits;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.Constant;
import com.example.nakathisacco.UtilitiesPackage.NetworkUtility;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.UtilitiesPackage.SharePreferenceUtils;
import com.example.nakathisacco.WebServices.ServiceInterface;
import com.example.nakathisacco.WebServices.ServiceWrapper;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {
    private String TAG ="LogIn";
    private Button btnLogin,btnSignup;
    private EditText idText,passwordText;
    private String id_number,password;
    INakathiAPI mService;
    Session session;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);


        btnLogin=findViewById(R.id.button3);
        btnSignup=findViewById(R.id.btnSignup);
        idText= findViewById(R.id.editText);
        passwordText= findViewById(R.id.editText2);
        mService = Common.getAPI();
        session = new Session(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this,signup.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(LogIn.this, CheckUserActivity.class);
                startActivity(intentSignUp);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: clicked" );


                id_number=idText.getText().toString();
                password=passwordText.getText().toString();

                if (id_number.isEmpty()) {
                    idText.setError("Enter your id Number");
                    idText.requestFocus();
                    return;

                }if (password.isEmpty()) {
                    passwordText.setError("Enter your password");
                    passwordText.requestFocus();
                    return;

                }
                AppUtilits.showDialog(LogIn.this);
                mService.checkPassword(id_number,password).enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        AppUtilits.dismissDialog();
                        Log.e(TAG, "onResponse: "+response );

                        if(response.isSuccessful()){
                            String msg= response.body().getMessage();
                            if(msg.equalsIgnoreCase("true")){
//
                                getAccountInfo();


                            } else{
                                Toast.makeText(LogIn.this, "Invalid  Password", Toast.LENGTH_SHORT).show();
                            }




                    }else{
                            Toast.makeText(LogIn.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {
                        AppUtilits.dismissDialog();

                        Log.e(TAG, "onFailure: "+t );
                        Toast.makeText(LogIn.this, "Error occcured while processing your request", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }

    private void getAccountInfo() {

        mService.getMemberInfo(id_number).enqueue(new Callback<MembersModel>() {
            @Override
            public void onResponse(Call<MembersModel> call, Response<MembersModel> response) {

                if(response.isSuccessful()){

                    String idNumber=response.body().id_number;
                    String fullnames = response.body().name;
                    String  email = response.body().email;
                    String phone_number = response.body().phone_number;
                    Log.e(TAG, "onResponse: "+id_number+' ' +fullnames+' '+email+' '+phone_number );
                    session.setLoggedIn(true);
                    session.setIdNumber(idNumber);
                    Intent intent= new Intent(LogIn.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(LogIn.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<MembersModel> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(LogIn.this, "The user does not exist in the system", Toast.LENGTH_SHORT).show();

            }
        });


    }




}
