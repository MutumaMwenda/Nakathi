package com.impax.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class MyLoan  extends ExpandableGroup<GuarantorModel> {
//    private  int member_id;
//    private  int loan_type;
//    @SerializedName("received_by")
//    @Expose
//    public String received_by;
//
    @SerializedName("date")
    @Expose
    public String title;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("p_amount")
    @Expose
    public String p_amount;
    @SerializedName("status")
    @Expose
    public String status;
//    @SerializedName("date_requested")
//    @Expose
//    public String date_requested;

    @SerializedName("guarantors")
    @Expose
    public List<GuarantorModel> items= new ArrayList<>();
    private String iconResId;


    public MyLoan(String title,String amount, List<GuarantorModel>items) {
        super(title,items);


    }}






