package com.example.nakathisacco.UtilitiesPackage;

import com.example.nakathisacco.Retrofit.INakathiAPI;
import com.example.nakathisacco.Retrofit.RetrofitClient;

public class Common {
    private static final String BASE_URL ="http://10.0.0.54/Nakathi/";
    public  static INakathiAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(INakathiAPI.class);
    }

}
