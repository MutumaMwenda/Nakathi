package com.impax.nakathisacco.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuarantorModel implements Parcelable {
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("name")
    @Expose
    public String name;
    public String member_id;
    private String loan_id;


    public GuarantorModel() {
    }public GuarantorModel(String amount) {
        this.amount= amount;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.status);
        dest.writeString(this.name);
        dest.writeString(this.member_id);
        dest.writeString(this.loan_id);
    }

    protected GuarantorModel(Parcel in) {
        this.amount = in.readString();
        this.status = in.readString();
        this.name = in.readString();
        this.member_id = in.readString();
        this.loan_id = in.readString();
    }

    public static final Parcelable.Creator<GuarantorModel> CREATOR = new Parcelable.Creator<GuarantorModel>() {
        @Override
        public GuarantorModel createFromParcel(Parcel source) {
            return new GuarantorModel(source);
        }

        @Override
        public GuarantorModel[] newArray(int size) {
            return new GuarantorModel[size];
        }
    };
}
