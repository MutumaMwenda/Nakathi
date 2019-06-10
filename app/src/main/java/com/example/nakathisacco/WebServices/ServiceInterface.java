package com.example.nakathisacco.WebServices;

import com.example.nakathisacco.Responses.NewUserRegistration;
import com.example.nakathisacco.Responses.UserSignInRes;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ServiceInterface {


    // method,, return type ,, secondary url

    @Multipart
    @POST("new_user_registration.php")
    Call<NewUserRegistration> NewUserRegistrationCall(
            @Part("firstname") RequestBody firstname,
            @Part("id_number") RequestBody idnumber,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password

    );

    @Multipart
    @POST("user_signin.php")
    Call<UserSignInRes> UserSigninCall(
            @Part("id_number") RequestBody id_number,
            @Part("password") RequestBody password
    );

}
