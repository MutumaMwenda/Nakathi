package com.example.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavingsLogModel {

    @SerializedName("reg_no")
    @Expose
    public String reg_no;

    @SerializedName("amount")
    @Expose
    public String amount;

    @SerializedName("deposited_date")
    @Expose
    public String deposited_date;

    public SavingsLogModel() {
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeposited_date() {
        return deposited_date;
    }

    public void setDeposited_date(String deposited_date) {
        this.deposited_date = deposited_date;
    }

    public SavingsLogModel(String reg_no, String amount, String deposited_date) {
        this.reg_no = reg_no;
        this.amount = amount;
        this.deposited_date = deposited_date;
    }
}
