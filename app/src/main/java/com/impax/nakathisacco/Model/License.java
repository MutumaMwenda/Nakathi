package com.impax.nakathisacco.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class License implements Parcelable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("reg_no")
    @Expose
    public String reg_no;
    @SerializedName("expiryDate")
    @Expose
    public String expiryDate;
    @SerializedName("activefrom")
    @Expose
    public String activefrom;
    @SerializedName("status")
    @Expose
    public String status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.reg_no);
        dest.writeString(this.expiryDate);
        dest.writeString(this.activefrom);
        dest.writeString(this.status);
    }

    public License() {
    }

    protected License(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.reg_no = in.readString();
        this.expiryDate = in.readString();
        this.activefrom = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<License> CREATOR = new Parcelable.Creator<License>() {
        @Override
        public License createFromParcel(Parcel source) {
            return new License(source);
        }

        @Override
        public License[] newArray(int size) {
            return new License[size];
        }
    };
}
