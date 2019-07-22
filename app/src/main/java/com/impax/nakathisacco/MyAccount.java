package com.impax.nakathisacco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.Model.MembersModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyAccount extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MyAccount.class.getSimpleName();
    INakathiAPI mService;
    Session session;
    String id_number;
    TextView tvFullNames,tvIdNo,tvEmail,tvPhoneNo,tvAccNo;
    String idNumber,fullnames,email,phone_number;



  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      mService = Common.getAPI();
      session = new Session(this);

       id_number = session.getIdNumber();

        getAccountInfo();
        tvFullNames = findViewById(R.id.tv_fnames);
        tvEmail = findViewById(R.id.tv_email);
        tvPhoneNo = findViewById(R.id.tv_phone);
        tvIdNo = findViewById(R.id.tv_idno);
        tvAccNo = findViewById(R.id.tv_accno);


  }

  private void getAccountInfo() {

      mService.getMemberInfo(id_number).enqueue(new Callback<MembersModel>() {
          @Override
          public void onResponse(Call<MembersModel> call, Response<MembersModel> response) {

              if(response.isSuccessful()){

                   idNumber=response.body().id_number;
                   fullnames = response.body().name;
                   email = response.body().email;
                   phone_number = response.body().phone_number;

                  tvFullNames.setText(fullnames);
                  tvEmail.setText(email);
                  tvPhoneNo.setText(phone_number);
                  tvIdNo.setText(idNumber);

                  Log.e(TAG, "onResponse: "+id_number+' ' +fullnames+' '+email+' '+phone_number );

              }else{
                  Toast.makeText(MyAccount.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();

              }

          }

          @Override
          public void onFailure(Call<MembersModel> call, Throwable t) {
              Log.e(TAG, "onFailure: "+t );
              Toast.makeText(MyAccount.this, "The user does not exist in the system", Toast.LENGTH_SHORT).show();

          }
      });


  }

  private void setData() {

  }

  public void showDialog() {

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
    public void onStop() {
        super.onStop();
    }



    @Override
    protected void onPause() {
        super.onPause();

    }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {

    }
  }
}
