package com.example.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loan {
    private  int member_id;
    private  int loan_type;
    @SerializedName("received_by")
    @Expose
    public String received_by;
    @SerializedName("amount")
    @Expose
    public int amount;
    @SerializedName("p_amount")
    @Expose
    public String p_amount;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("date_requested")
    @Expose
    public String date_requested;

    public Loan() {
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(int loan_type) {
        this.loan_type = loan_type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
