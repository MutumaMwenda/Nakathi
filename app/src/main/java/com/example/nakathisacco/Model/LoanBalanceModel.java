package com.example.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanBalanceModel {

    @SerializedName("received_by")
    @Expose
    public String received_by;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("date_paid")
    @Expose
    public String date_paid;

    public LoanBalanceModel() {
    }

    public LoanBalanceModel(String received_by, String amount, String date_paid) {
        this.received_by = received_by;
        this.amount = amount;
        this.date_paid = date_paid;
    }
}
