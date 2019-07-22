package com.impax.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavingsLogModel {

    @SerializedName("contributor")
    @Expose
    public String contributor;

    @SerializedName("amount")
    @Expose
    public String amount;

    @SerializedName("contribution_date")
    @Expose
    public String contribution_date;

    public SavingsLogModel() {
    }

    public SavingsLogModel(String contributor, String amount, String contribution_date) {
        this.contributor = contributor;
        this.amount = amount;
        this.contribution_date = contribution_date;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContribution_date() {
        return contribution_date;
    }

    public void setContribution_date(String contribution_date) {
        this.contribution_date = contribution_date;
    }
}
