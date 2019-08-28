package com.impax.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContributionsModel {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("reg_no")
    @Expose
    public String reg_no;

    @SerializedName("owner_id")
    @Expose
    public String owner_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate_requested() {
        return date_requested;
    }

    public void setDate_requested(String date_requested) {
        this.date_requested = date_requested;
    }

    public List<ContributionTypes> getContributions() {
        return contributions;
    }

    public void setContributions(List<ContributionTypes> contributions) {
        this.contributions = contributions;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public ContributionsModel(String name, String reg_no, String owner_id, String phone_number, String date_requested, List<ContributionTypes> contributions, List<Loan> loans) {
        this.name = name;
        this.reg_no = reg_no;
        this.owner_id = owner_id;
        this.phone_number = phone_number;
        this.date_requested = date_requested;
        this.contributions = contributions;
        this.loans = loans;
    }

    @SerializedName("phone_number")
    @Expose
    public String phone_number;

    @SerializedName("date_requested")
    @Expose
    public String date_requested;

   // public List<Vehicle> vehicle = new ArrayList<>();
   @SerializedName("contributions")
   @Expose
    public List<ContributionTypes> contributions = new ArrayList<>();
    @SerializedName("loans")
    @Expose
    public List<Loan> loans = new ArrayList<>();
}
