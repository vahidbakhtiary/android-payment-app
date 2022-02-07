package ir.sep.android.merchantapp.data.remote;

import ir.sep.android.merchantapp.data.entities.MerchantServiceBaseEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;
import ir.sep.android.merchantapp.data.entities.RahseparRequest;
import ir.sep.android.merchantapp.data.entities.RahseparResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SepyarApiService {
    @POST("Service/ajaxCustomersSRV")
    Call<RahseparResponse> getReport(@Body RahseparRequest request);


    @POST("v3/16752e7b-9176-4943-ac7b-30ee1ec3c030")
    Call<RahseparResponse> getReportTotally();
    @POST("Service/ajaxCustomersSRV")
    Call<RahseparResponse> getReportTotally(@Body RahseparRequest request);


    //----------------------------------------------------------------------------------------

    @POST("Service/GetRegistrationPin")
    Call<MerchantServiceEntity> RegistrationPin(@Body MerchantServiceBaseEntity request);
    @GET("v3/abe756b4-f434-4188-83d9-1ae4a84a1902")
    Call<MerchantServiceEntity> RegistrationPin();


    @POST("Service/ValidateRegistrationPin")
    Call<MerchantServiceEntity> ValidateRegistrationPin(@Body MerchantServiceBaseEntity request);
    @GET("v3/d91b72cf-8115-4adf-9277-ef30274b4fdf")
    Call<MerchantServiceEntity> ValidateRegistrationPin();


    @POST("Service/PosPaperRoll")
    Call<MerchantServiceEntity> PosPaperRoll(@Body MerchantServiceBaseEntity request);
    @GET("v3/4515cc74-9d5f-45f3-904d-d897586ddb39")
    Call<MerchantServiceEntity> PosPaperRoll();


    @POST("Service/PosPaperRollHis")
    Call<MerchantServiceRollEntity> PosPaperRollHis(@Body MerchantServiceBaseEntity request);
    @GET("v3/f17b5b78-47e2-4ced-af02-1e415a8c20b1")
    Call<MerchantServiceRollEntity> PosPaperRollHis();


    @POST("Service/PosResetPW")
    Call<MerchantServiceEntity> PosResetPW(@Body MerchantServiceBaseEntity request);

}
