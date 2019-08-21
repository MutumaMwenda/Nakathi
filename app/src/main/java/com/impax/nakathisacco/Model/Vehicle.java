package com.impax.nakathisacco.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Parcelable {

        @SerializedName("reg_no")
        @Expose
        public String reg_no;
        @SerializedName("drivers")
        @Expose
        public List<Driver> drivers = new ArrayList<>();
        public List<Contribution> contributions = new ArrayList<>();
        public List<License> licence = new ArrayList<>();


        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.reg_no);
                dest.writeTypedList(this.drivers);
                dest.writeTypedList(this.contributions);
                dest.writeTypedList(this.licence);
        }

        public Vehicle() {
        }

        protected Vehicle(Parcel in) {
                this.reg_no = in.readString();
                this.drivers = in.createTypedArrayList(Driver.CREATOR);
                this.contributions = in.createTypedArrayList(Contribution.CREATOR);
                this.licence = in.createTypedArrayList(License.CREATOR);
        }

        public static final Parcelable.Creator<Vehicle> CREATOR = new Parcelable.Creator<Vehicle>() {
                @Override
                public Vehicle createFromParcel(Parcel source) {
                        return new Vehicle(source);
                }

                @Override
                public Vehicle[] newArray(int size) {
                        return new Vehicle[size];
                }
        };
}
