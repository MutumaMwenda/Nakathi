package com.impax.nakathisacco.UtilitiesPackage;

import com.impax.nakathisacco.Retrofit.INakathiAPI;
import com.impax.nakathisacco.Retrofit.RetrofitClient;
import com.impax.nakathisacco.Retrofit.VehicleAPI;

public class Common {
    private static final String BASE_URL ="http://10.0.2.2/nakathi/";
    //private static final String BASE_URL ="http://10.0.0.74/nakathi/";
   // private static final String BASE_URL ="http://13.69.85.221:5301/nakathi/";
    private static final String BASE_URL2 ="http://localhost:11472/vehicle/regno/";
    public  static INakathiAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(INakathiAPI.class);
    }
    public  static VehicleAPI getVehicleApi()
    {
        return RetrofitClient.getClient(BASE_URL2).create(VehicleAPI.class);
    }

}
