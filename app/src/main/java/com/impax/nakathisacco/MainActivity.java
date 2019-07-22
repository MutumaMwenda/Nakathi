package com.impax.nakathisacco;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.impax.nakathisacco.HomePackage.LogIn;
import com.impax.nakathisacco.HomePackage.SplashScreen;
import com.impax.nakathisacco.Model.MembersModel;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.UtilitiesPackage.AppUtilits;
import com.impax.nakathisacco.UtilitiesPackage.Common;
import com.impax.nakathisacco.UtilitiesPackage.ItemsDecoration;
import com.impax.nakathisacco.UtilitiesPackage.Session;
import com.impax.nakathisacco.adapters.HomeAdapter;

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
            "My Loans".toUpperCase(),
            "Guarantees".toUpperCase(),
            "Certifications".toUpperCase(),
            "Savings".toUpperCase(),
            "Balance".toUpperCase(),
            "assets".toUpperCase(),

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
    TextView tvFullNames,tvnoOfNotifications;
    CoordinatorLayout mCoordinatorLayout;
    FloatingActionButton noFloatingActionButton;
    ImageView  notsImageView;



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
        tvnoOfNotifications= findViewById(R.id.tvNonotifications);
        mCoordinatorLayout= findViewById(R.id.codNotification);
        noFloatingActionButton= findViewById(R.id.fbnotification);
        notsImageView = findViewById(R.id.imageNots);


        notsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NotificationActivity.class));
            }
        });
        getAccountInfo();
        getNotifications();


         mService = Common.getAPI();
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getNotifications() {
        mService.countNotifications(session.getIdNumber()).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {

                if (response.body().getMessage().equalsIgnoreCase("0")) {

                }else{
                    String noOfNotification = response.body().getMessage();
                    Log.e(TAG, "Nos"+noOfNotification );
                    mCoordinatorLayout.setVisibility(View.VISIBLE);

                    tvnoOfNotifications.setText(noOfNotification);


                }

            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {


            }
        });
    }

    private void getAccountInfo() {
        //AppUtilits.showDialog(this);
        mService.getMemberInfo(session.getIdNumber()).enqueue(new Callback<MembersModel>() {
            @Override
            public void onResponse(Call<MembersModel> call, Response<MembersModel> response) {
                AppUtilits.dismissDialog();

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
                //AppUtilits.dismissDialog();
                startActivity(new Intent(MainActivity.this, LogIn.class));
                //Toast.makeText(MainActivity.this, "The user does not exist in the system", Toast.LENGTH_SHORT).show();

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
        //menu.findItem(R.id.action_settings).setTitle(Html.fromHtml("<font color='#ff3824'>Settings</font>"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }

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
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",
                    new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            session.setLoggedIn(false);
                            Toast.makeText(MainActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                            finish();

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
                Intent myLoanIntent = new Intent(this, MyLoansActivity.class);
                startActivity(myLoanIntent);
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

            case 4:
                Intent loanBalanceIntent = new Intent(this, LoanBalanceActivity.class);
                startActivity(loanBalanceIntent);
                break;
            case 5:
                Intent assetsIntent = new Intent(this, AssetsActivity.class);
                startActivity(assetsIntent);
                break;
        }





    }
}
