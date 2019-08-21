package com.impax.nakathisacco.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contribution implements Parcelable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("transaction_id")
    @Expose
    public String transaction_id;

    @SerializedName("reg_no")
    @Expose
    public String reg_no;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("contribution_date")
    @Expose
    public String contribution_date;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.transaction_id);
        dest.writeString(this.reg_no);
        dest.writeString(this.amount);
        dest.writeString(this.contribution_date);
    }

    public Contribution() {
    }

    protected Contribution(Parcel in) {
        this.id = in.readString();
        this.transaction_id = in.readString();
        this.reg_no = in.readString();
        this.amount = in.readString();
        this.contribution_date = in.readString();
    }

    public static final Parcelable.Creator<Contribution> CREATOR = new Parcelable.Creator<Contribution>() {
        @Override
        public Contribution createFromParcel(Parcel source) {
            return new Contribution(source);
        }

        @Override
        public Contribution[] newArray(int size) {
            return new Contribution[size];
        }
    };
}
