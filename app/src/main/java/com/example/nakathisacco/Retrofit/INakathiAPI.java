package com.example.nakathisacco.Retrofit;

import com.example.nakathisacco.Model.CertsModel;
import com.example.nakathisacco.Model.GuarantorModel;
import com.example.nakathisacco.Model.Loan;
import com.example.nakathisacco.Model.LoanApplicantModel;
import com.example.nakathisacco.Model.LoanTypeModel;
import com.example.nakathisacco.Model.MembersModel;
import com.example.nakathisacco.Model.MessageModel;
import com.example.nakathisacco.Model.SavingsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface INakathiAPI {
    @FormUrlEncoded
    @POST("checkExistsLoan.php")
    Call<MessageModel>checkExistsLoan(@Field("id_number")String id_number
    );

    @FormUrlEncoded
    @POST("checkId.php")
    Call<MessageModel>checkId(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("approveLoan.php")
    Call<MessageModel>approveLoan(@Field("loan_id")String loan_id,
                                  @Field("status")String status,
                                  @Field("member_id")String member_id);
    @FormUrlEncoded
    @POST("getGuarantorLoans.php")
    Call<List<LoanApplicantModel>>getGuarantorLoans(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("createPassword.php")
    Call<MessageModel>createPassword(@Field("password")String password,
                                     @Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("checkPassword.php")
    Call<MessageModel>checkPassword(@Field("id_number")String id_number,
                                     @Field("password")String password
    );
    @FormUrlEncoded
    @POST("getMemberSavings.php")
    Call<SavingsModel>getMemberSavings(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getGuarantor.php.")
    Call<MembersModel>getGuarantor(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getCerts.php.")
    Call<List<CertsModel>>getCerts(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getMemberInfo.php.")
    Call<MembersModel>getMemberInfo(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("loan.php")
    Call<MessageModel>insertLoan(@Field("member_id" )String member_id,
                         @Field("loan_type_id" )String loan_type_id,
                         @Field("amount" )String amount

    );
    @FormUrlEncoded
    @POST("insertGuarantors.php")
    Call<GuarantorModel>insertGuarantors(@Field("loan_id")String loan_id,
                                        @Field("member_id")String member_id,
                                        @Field("amount")String amount,
                                        @Field("applicant_id")String applicant_id

    );
    @GET("getLoanType.php")
    Call<List<LoanTypeModel>>getLoanType();

    @GET("getMembers.php")
    Call<List<MembersModel>>getMembers();

}
