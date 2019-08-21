package com.impax.nakathisacco.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver implements Parcelable {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("s_name")
    @Expose
    public String s_name;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("reg_no")
    @Expose
    public String reg_no;
    @SerializedName("date_assigned")
    @Expose
    public String date_assigned;
    @SerializedName("assigned_up_to_date")
    @Expose
    public String assigned_up_to_date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.s_name);
        dest.writeString(this.name);
        dest.writeString(this.reg_no);
        dest.writeString(this.date_assigned);
        dest.writeString(this.assigned_up_to_date);
    }

    public Driver() {
    }

    protected Driver(Parcel in) {
        this.id = in.readString();
        this.s_name = in.readString();
        this.name = in.readString();
        this.reg_no = in.readString();
        this.date_assigned = in.readString();
        this.assigned_up_to_date = in.readString();
    }

    public static final Parcelable.Creator<Driver> CREATOR = new Parcelable.Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel source) {
            return new Driver(source);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };
}
