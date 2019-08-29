package com.impax.nakathisacco.UtilitiesPackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setIdNumber(String id_number) {
        prefs.edit().putString("id_number", id_number).commit();
    }

    public String getIdNumber() {
        String id_number = prefs.getString("id_number","");
        return id_number;
    }
    public void setLoanId(String loan_id) {
        prefs.edit().putString("loan_id", loan_id).commit();
    }

    public String getLoanId() {
        String loan_id = prefs.getString("loan_id","");
        return loan_id;
    }
    public void setAmount(String amount) {

        prefs.edit().putString("amount", amount).commit();
    }

    public String getAmount() {
        String amount = prefs.getString("amount","");
        return amount;
    }
    public void setLoggedIn(Boolean loggedIn) {

        prefs.edit().putBoolean("loggedin", loggedIn).apply();
    }

    public  boolean loggedOut() {

        boolean loggedIn = prefs.getBoolean("loggedin",false);
        return loggedIn;

    }

    public void setName(String name) {
        prefs.edit().putString("name", name).commit();
    }

    public String getName() {
        String name = prefs.getString("name","");
        return name;
    }

}