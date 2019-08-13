package com.impax.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanTypeModel {



    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("interestRate")
    @Expose
    public String interestRate;
    @SerializedName("repaymentPeriod")
    @Expose
    public String repaymentPeriod;
    @SerializedName("loan_type_code")
    @Expose
    public String loan_type_code;

    public LoanTypeModel() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
