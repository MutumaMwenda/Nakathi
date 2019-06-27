package com.example.nakathisacco.UtilitiesPackage;

import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.Retrofit.RetrofitClient;

public class Common {
    private static final String BASE_URL ="http://10.0.2.2:81/nakathi/";
    public  static INakathiAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(INakathiAPI.class);
    }

}
