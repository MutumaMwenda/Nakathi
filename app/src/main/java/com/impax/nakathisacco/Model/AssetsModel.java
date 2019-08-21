package com.impax.nakathisacco.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AssetsModel implements Parcelable {

        @SerializedName("message")
        @Expose
        public List<Vehicle>  message = new ArrayList<>();


        @SerializedName("name")
        @Expose
        public String name;



        public AssetsModel() {
        }




        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeList(this.message);
                dest.writeString(this.name);
        }

        protected AssetsModel(Parcel in) {
                this.message = new ArrayList<Vehicle>();
                in.readList(this.message, Vehicle.class.getClassLoader());
                this.name = in.readString();
        }

        public static final Creator<AssetsModel> CREATOR = new Creator<AssetsModel>() {
                @Override
                public AssetsModel createFromParcel(Parcel source) {
                        return new AssetsModel(source);
                }

                @Override
                public AssetsModel[] newArray(int size) {
                        return new AssetsModel[size];
                }
        };
}
