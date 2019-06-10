package com.example.nakathisacco;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.UtilitiesPackage.Common;
import com.example.nakathisacco.UtilitiesPackage.ItemsDecoration;
import com.example.nakathisacco.UtilitiesPackage.Session;
import com.example.nakathisacco.adapters.HomeAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MyAccount.class.getSimpleName();
    INakathiAPI mService;
    Session session;
    String fullnames;

    String[] web = {
            "Apply Loan".toUpperCase(),
            "Guarantees".toUpperCase(),
            "Documents Status".toUpperCase(),
            "Savings".toUpperCase(),
            "Balance".toUpperCase(),
            "Statements".toUpperCase(),

    };

    int[] imageId = {
            R.drawable.ic_money,
            R.drawable.ic_group,
            R.drawable.ic_comment,
            R.drawable.ic_money,
            R.drawable.ic_money,
            R.drawable.ic_money,


    };

    private RecyclerView rvGrid;
    AlertDialog alertDialog;
    TextView tvFullNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        //getSupportActionBar().hide();
        setSupportActionBar(toolbar);
        session = new Session(this);
        mService = Common.getAPI();


        HomeAdapter adapter = new HomeAdapter(web, imageId, MainActivity.this);
        rvGrid = findViewById(R.id.grid);
        rvGrid.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, RecyclerView.VERTICAL, false));
        rvGrid.addItemDecoration(new ItemsDecoration(2, 0, true, true, true));
        rvGrid.setAdapter(adapter);

        tvFullNames= findViewById(R.id.tv_fullnames);
        getAccountInfo();


         mService = Common.getAPI();
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getAccountInfo() {

        mService.getMemberInfo(session.getIdNumber()).enqueue(new Callback<MembersModel>() {
            @Override
            public void onResponse(Call<MembersModel> call, Response<MembersModel> response) {

                if(response.isSuccessful()){


                    fullnames = response.body().name;
                    tvFullNames.setText("Hi, "+fullnames);



                    Log.e(TAG, "onResponse: " +fullnames );

                }else{
                    Toast.makeText(MainActivity.this, "Error occured while processing your request", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<MembersModel> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t );
                Toast.makeText(MainActivity.this, "The user does not exist in the system", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_account) {
            Intent accIntent = new Intent(MainActivity.this, MyAccount.class);
            startActivity(accIntent);

        } else if (id == R.id.nav_logout) {
            alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(getString(R.string.app_name));
            alertDialog.setMessage("Are you sure you want to logout?");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                    new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            session.setLoggedIn(false);
                            Toast.makeText(MainActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

            alertDialog.show();


        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void itemClicked(int adapterPosition, View v) {

        switch(adapterPosition)
        {
            case 0:


                Intent applyLoanIntent = new Intent(this, LoanActivity.class);
                startActivity(applyLoanIntent);
                break;
            case 1:
                Intent guaranteesIntent = new Intent(this, ApproveLoanActivity.class);
                startActivity(guaranteesIntent);
                break;

            case 2:
                Intent certsIntent = new Intent(this, CertsActivity.class);
                startActivity(certsIntent);
                break;
            case 3:
                Intent savingsIntent = new Intent(this, SavingsActivity.class);
                startActivity(savingsIntent);
                break;
        }





    }
}
