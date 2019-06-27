package com.example.nakathisacco.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class AssetsModel  {
        @SerializedName("reg_no")
        @Expose
        public String reg_no;
        @SerializedName("name")
        @Expose
        public String name;



        public AssetsModel() {
        }



    }
