package com.example.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavingsModel {

    @SerializedName("amount")
    @Expose
    public String amount;

    @SerializedName("total_saving_amount")
    @Expose
    public String total_saving_amount;

    @SerializedName("available_amount")
    @Expose
    public String available_amount;

    @SerializedName("due_date")
    @Expose
    public String due_date;

    @SerializedName("balance")
    @Expose
    public String balance;





}
