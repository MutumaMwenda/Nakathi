package com.example.nakathisacco.HomePackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nakathisacco.LoanActivity;
import com.example.nakathisacco.MainActivity;
import com.example.nakathisacco.R;
import com.example.nakathisacco.UtilitiesPackage.Session;

public class SplashScreen  extends AppCompatActivity {
    private String TAG ="splashAcctivity";
    private Session session;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_splash_screen);
        init();
        Log.e(TAG, " splash start showing");
        session = new Session(this);

    }
    public void init(){

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                 next();

                }

        }, 3000 );

    }

    private void next() {
        boolean loggedOut = session.loggedOut();
        Log.e(TAG, "next: "+loggedOut );
        if (!loggedOut) {
            Intent intentLogIn = new Intent(SplashScreen.this, LogIn.class);
           startActivity(intentLogIn);
          finish();
         } else {
            Intent intentHome = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intentHome);
            finish();

        }

    }
}
