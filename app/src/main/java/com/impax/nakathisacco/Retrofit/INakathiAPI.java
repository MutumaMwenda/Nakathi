package com.impax.nakathisacco.Retrofit;

import com.impax.nakathisacco.Model.AssetsModel;
import com.impax.nakathisacco.Model.AssetsRevenueModel;
import com.impax.nakathisacco.Model.CertsModel;
import com.impax.nakathisacco.Model.Contribution;
import com.impax.nakathisacco.Model.ContributionTypes;
import com.impax.nakathisacco.Model.ContributionsModel;
import com.impax.nakathisacco.Model.GuarantorModel;
import com.impax.nakathisacco.Model.Loan;
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

public interface INakathiAPI {
    @FormUrlEncoded
    @POST("checkExistsLoan.php")
    Call<MessageModel>checkExistsLoan(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("countNotifications.php")
    Call<MessageModel>countNotifications(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getRecentLoans.php")
    Call<List<MyLoan>>getRecentLoans(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getLoanInfo.php")
    Call<SavingsModel>getLoanInfo(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("checkId.php")
    Call<MessageModel>checkId(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("approveLoan.php")
    Call<MessageModel>approveLoan(@Field("status")String loan_id,
                                  @Field("loan_id")String status,
                                  @Field("member_id")String member_id);
    @FormUrlEncoded
    @POST("getGuarantorLoans.php")
    Call<List<LoanApplicantModel>>getGuarantorLoans(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getApproved.php")
    Call<List<LoanApplicantModel>>getGuarantorLoansA(@Field("id_number")String id_number
    );

    @FormUrlEncoded
    @POST("getRejected.php")
    Call<List<LoanApplicantModel>>getGuarantorLoansR(@Field("id_number")String id_number
    );


    @FormUrlEncoded
    @POST("createPassword.php")
    Call<MessageModel>createPassword(@Field("password")String password,
                                     @Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("createUser.php")
    Call<MessageModel>createUser(@Field("password")String password,
                                     @Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("checkPassword.php")
    Call<MessageModel>checkPassword(@Field("id_number")String id_number,
                                     @Field("password")String password
    );
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<MessageModel>checkuser(@Field("id_number")String id_number,
                                    @Field("password")String password
    );
    @FormUrlEncoded
    @POST("getAssetRevenue.php")
    Call<List<AssetsRevenueModel>>getAssetRevenue(@Field("reg_no")String reg_no
    );
    @FormUrlEncoded
    @POST("getTaggedVehicles.php")
    Call<List<AssetsModel>>getTaggedVehicles(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("saveContributions.php")
    Call<MessageModel>saveContributions(@Field("reg_no")String reg_no,
                                                   @Field("member_id") String member_id,
                                                   @Field("contribution_id")String contribution_id,
                                                   @Field("amount")String amount,
                                                   @Field("contribution_source")String contribution_source,
                                                   @Field("received_by")String received_by,
                                                   @Field("transaction_id")String transaction_id
    );
    @FormUrlEncoded
    @POST("asset.php")
    Call<AssetsModel>getAssets(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("contributions.php")
    Call<ContributionsModel>getContributions(@Field("reg_no")String reg_no
    );
    @FormUrlEncoded
    @POST("myTrans.php")
    Call<List<Contribution>>getMyTrans(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getSavingsLog.php")
    Call<List<SavingsLogModel>>getSavingsLog(@Field("id_number")String id_number
    );

    @FormUrlEncoded
    @POST("getLoanBalance.php")
    Call<List<LoanBalanceModel>>getLoanBalance(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getMemberSavings.php")
    Call<SavingsModel>getMemberSavings(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getGuarantor.php")
    Call<MembersModel>getGuarantor(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getCerts.php")
    Call<List<CertsModel>>getCerts(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("getVehicleContribution.php")
    Call<AssetsRevenueModel>getVehicleContribution(@Field("id_number")String id_number,
                                                          @Field("date")String date
    );

    @FormUrlEncoded
    @POST("getVehicleCerts.php")
    Call<List<CertsModel>>getVehicleCerts(@Field("reg_no")String reg_no
    );
    @FormUrlEncoded
    @POST("getMemberInfo.php")
    Call<MembersModel>getMemberInfo(@Field("id_number")String id_number
    );
    @FormUrlEncoded
    @POST("loan.php")
    Call<MessageModel>insertLoan(@Field("member_id" )String member_id,
                         @Field("loan_type_id" )String loan_type_id,
                         @Field("amount" )String amount,
                         @Field("repay_period" )String repay_period,
                         @Field("rate" )String rate

    );
    @FormUrlEncoded
    @POST("insertGuarantors.php")
    Call<MessageModel>insertGuarantors(@Field("loan_id")String loan_id,
                                        @Field("member_id")String member_id,
                                        @Field("amount")String amount,
                                        @Field("applicant_id")String applicant_id

    );
    @GET("getLoanType.php")
    Call<List<LoanTypeModel>>getLoanType();

    @GET("getMembers.php")
    Call<List<MembersModel>>getMembers();

    @GET("vehicle.php?") Call<List<Vehicle>> getVehicles(@Query("prefix") String prefix
    );

}
