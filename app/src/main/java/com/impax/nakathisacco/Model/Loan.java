package com.impax.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Loan {

    private  int member_id;
    @SerializedName("type")
    @Expose
    public  String type;

    @SerializedName("id")
    @Expose
    public  String id;
    @SerializedName("received_by")
    @Expose
    public String received_by;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("loanbalance")
    @Expose
    public String loanbalance;
    @SerializedName("p_amount")
    @Expose
    public String p_amount;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("date_requested")
    @Expose
    public String date_requested;
    @SerializedName("guarantors")
    @Expose
    public List<GuarantorModel> guarantorModels= new ArrayList<>();


    public Loan() {
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getLoan_type() {
        return type;
    }

    public void setLoan_type(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
