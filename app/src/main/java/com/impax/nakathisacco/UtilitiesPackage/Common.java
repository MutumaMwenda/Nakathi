package com.impax.nakathisacco.UtilitiesPackage;

import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.Retrofit.RetrofitClient;

public class Common {
    private static final String BASE_URL ="http:/13.69.85.221:5301/Nakathi/";
    public  static INakathiAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(INakathiAPI.class);
    }

}
