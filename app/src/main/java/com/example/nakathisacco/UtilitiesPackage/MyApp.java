package com.example.nakathisacco.UtilitiesPackage;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApp extends Application {

    private String TAG ="myApp";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.e(TAG, "myapp stater");
    }

    public static Context getContext(){
        return context;
    }
}
