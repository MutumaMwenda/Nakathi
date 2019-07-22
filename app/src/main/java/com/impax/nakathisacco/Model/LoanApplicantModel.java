package com.impax.nakathisacco.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanApplicantModel implements Parcelable {
    @SerializedName("loan_id")
    @Expose
    public String loan_id;
    @SerializedName("amount_requested")
    @Expose
    public String amount_requested;
    @SerializedName("applicant_id")
    @Expose
    public String applicant_id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("date_requested")
    @Expose
    public String date_requested;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("noOfGuarantors")
    @Expose
    public String noOfGuarantors;
    @SerializedName("amount_borrowed")
    @Expose
    public String amount_borrowed;


    public LoanApplicantModel() {
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.loan_id);
        dest.writeString(this.amount_borrowed);
        dest.writeString(this.amount_requested);
        dest.writeString(this.applicant_id);
        dest.writeString(this.noOfGuarantors);
        dest.writeString(this.name);
        dest.writeString(this.date_requested);
        dest.writeString(this.status);
    }



    protected LoanApplicantModel(Parcel in) {
        this.loan_id = in.readString();
        this.amount_requested = in.readString();
        this.amount_borrowed = in.readString();
        this.applicant_id = in.readString();
        this.noOfGuarantors = in.readString();
        this.name = in.readString();
        this.date_requested = in.readString();
        this.status = in.readString();
    }
    public static final Parcelable.Creator<LoanApplicantModel> CREATOR = new Parcelable.Creator<LoanApplicantModel>() {
        @Override
        public LoanApplicantModel createFromParcel(Parcel source) {
            return new LoanApplicantModel(source);
        }

        @Override
        public LoanApplicantModel[] newArray(int size) {
            return new LoanApplicantModel[size];
        }
    };
}
