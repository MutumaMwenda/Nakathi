package com.example.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetsRevenueModel{

        @SerializedName("revenue_type")
        @Expose
        public String revenue_type;
        @SerializedName("amount")
        @Expose
        public String amount;
    @SerializedName("date_collected")
    @Expose
    public String date_collected;

    public String getDate_collected() {
        return date_collected;
    }

    public void setDate_collected(String date_collected) {
        this.date_collected = date_collected;
    }

    public String getRevenue_type() {
        return revenue_type;
    }

    public void setRevenue_type(String revenue_type) {
        this.revenue_type = revenue_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public AssetsRevenueModel() {
        }

}
