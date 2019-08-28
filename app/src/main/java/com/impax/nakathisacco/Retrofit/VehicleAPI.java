package com.impax.nakathisacco.Retrofit;

import com.impax.nakathisacco.Model.AssetsModel;
import com.impax.nakathisacco.Model.AssetsRevenueModel;
import com.impax.nakathisacco.Model.CertsModel;
import com.impax.nakathisacco.Model.LoanApplicantModel;
import com.impax.nakathisacco.Model.LoanBalanceModel;
import com.impax.nakathisacco.Model.LoanTypeModel;
import com.impax.nakathisacco.Model.MembersModel;
import com.impax.nakathisacco.Model.MessageModel;
import com.impax.nakathisacco.Model.MyLoan;
import com.impax.nakathisacco.Model.SavingsLogModel;
import com.impax.nakathisacco.Model.SavingsModel;
import com.impax.nakathisacco.Model.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VehicleAPI {

    @GET("Search") //i.e https://api.test.com/Search?
    Call<Vehicle> getVehicles(@Query("prefix") String prefix
                              );

}
