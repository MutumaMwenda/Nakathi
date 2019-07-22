package com.impax.nakathisacco;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.Model.AssetsRevenueModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.AssetRevenueAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetRevenueActivity extends AppCompatActivity {


    public String reg_no,name,id_number;


    private TextView tvReg,tvRouteName,tvRevenue,tvLoans,tvSavings,tvTotals;
    private EditText eDate;
    INakathiAPI mService;
    Session session;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_revenue);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mService = Common.getAPI();
        session = new Session(this);
        id_number=session.getIdNumber();
        eDate=findViewById(R.id.EdxDate);
        String newdate="2019-06-27";
        Intent intent = getIntent();

        reg_no=intent.getStringExtra("reg_no");
        name=intent.getStringExtra("route_name");

        tvReg=findViewById(R.id.tv_vehicle);
        tvRouteName=findViewById(R.id.tv_route_name);
        tvRevenue=findViewById(R.id.txtRevenue);
        tvLoans=findViewById(R.id.txtLoans);
        tvSavings=findViewById(R.id.txtSavings);
        tvTotals=findViewById(R.id.txtTotals);
         getAssetRevenue(id_number,newdate);


        mService = Common.getAPI();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

eDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new DatePickerDialog(AssetRevenueActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }
});

        tvReg.setText(reg_no);
        tvRouteName.setText(name);

     /*   eDate.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AssetRevenueActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/
        getAssetRevenue(id_number,date.toString());

    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eDate.setText(sdf.format(myCalendar.getTime()));
    }
/*    private void updateDisplay() {

        eDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear).append(" "));
    }*/

    private void getAssetRevenue(String id_number,String date) {
       mService.getVehicleContribution(id_number,date).enqueue(new Callback<AssetsRevenueModel>() {
           @Override
           public void onResponse(Call<AssetsRevenueModel> call, Response<AssetsRevenueModel> response) {
               if(response.isSuccessful()){
                   tvRevenue.setText("Revenue: \t \t"+response.body().Revenue);
                   tvLoans.setText("Loan: \t \t"+response.body().Loan);
                   tvSavings.setText("Savings: \t \t"+response.body().Savings);
                   double total =Double.parseDouble(response.body().Revenue) + Double.parseDouble(response.body().Loan)+Double.parseDouble(response.body().Savings);
                   tvTotals.setText("Total \t"+total);
               }else{
                   Toast.makeText(AssetRevenueActivity.this, "No results", Toast.LENGTH_SHORT).show();
               }


           }

           @Override
           public void onFailure(Call<AssetsRevenueModel> call, Throwable t) {

           }
       });

    }

/*    private void getAssetRevenue(String reg_no) {
        mService.getAssetRevenue(reg_no).enqueue(new Callback<List<AssetsRevenueModel>>() {
            @Override
            public void onResponse(Call<List<AssetsRevenueModel>> call, Response<List<AssetsRevenueModel>> response) {

                assetsRevenueModels.clear();
                assetsRevenueModels.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AssetsRevenueModel>> call, Throwable t) {

                Toast.makeText(AssetRevenueActivity.this, "Error occurred while processing your request", Toast.LENGTH_SHORT).show();

            }
        });
    }*/
}
