package com.impax.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CertsModel  {
    @SerializedName("reg_no")
    @Expose
    public String reg_no;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("expiryDate")
    @Expose
    public String expiryDate;
    @SerializedName("status")
    @Expose
    public String status;


    public CertsModel() {
    }



}
