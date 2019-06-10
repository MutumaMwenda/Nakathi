package com.example.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembersModel {
    @SerializedName("id_number")
    @Expose
    public String id_number;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("phone_number")
    @Expose
    public String phone_number;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("amount")
    @Expose
    public String amount;

    public MembersModel() {
    }


    public MembersModel(String id_number, String name, String phone_number, String email, String amount) {
        this.id_number = id_number;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.amount = amount;
    }

    public String getId() {
        return id_number;
    }

    public void setId(String id_number) {
        this.id_number = id_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
