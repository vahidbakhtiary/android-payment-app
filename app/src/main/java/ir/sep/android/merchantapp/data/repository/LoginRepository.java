package ir.sep.android.merchantapp.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import javax.inject.Inject;

import ir.sep.android.merchantapp.BuildConfig;
import ir.sep.android.merchantapp.data.entities.MerchantServiceBaseEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.remote.SepyarApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.sep.android.merchantapp.Const.TAG;

public class LoginRepository {

    private SepyarApiService apiRequest;

    @Inject
    public LoginRepository(SepyarApiService apiRequest) {
        this.apiRequest = apiRequest;
    }

    public LiveData<MerchantServiceEntity> getRegistrationPin(long terminal, String mobileNO, String appKey) {
        MerchantServiceBaseEntity request=new MerchantServiceEntity();
        request.setTerminalNO(terminal);
        request.setMobileNO(mobileNO);
        request.setAppKey(appKey);

        final MutableLiveData<MerchantServiceEntity> data = new MutableLiveData<>();
        try {

            Call<MerchantServiceEntity> call = BuildConfig.isTest ? apiRequest.RegistrationPin() : apiRequest.RegistrationPin(request);
            call.enqueue(new Callback<MerchantServiceEntity>() {
                @Override
                public void onResponse(Call<MerchantServiceEntity> call, Response<MerchantServiceEntity> response) {
                    Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                    data.setValue(response.body());
                    Log.d(TAG, "body: " + response.body());
                }

                @Override
                public void onFailure(Call<MerchantServiceEntity> call, Throwable t) {
                    data.setValue(null);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            data.setValue(null);
        }


        return data;

    }

    public LiveData<MerchantServiceEntity> validateRegistrationPin(long terminal, String mobileNO, String appKey, String pin,String regKey) {
        MerchantServiceBaseEntity request=new MerchantServiceEntity();
        request.setTerminalNO(terminal);
        request.setMobileNO(mobileNO);
        request.setAppKey(appKey);
        request.setPin(pin);
        request.setRegKey(regKey);

        final MutableLiveData<MerchantServiceEntity> data = new MutableLiveData<>();
        try {


            Call<MerchantServiceEntity> call =BuildConfig.isTest ? apiRequest.ValidateRegistrationPin() : apiRequest.ValidateRegistrationPin(request);

                    call.enqueue(new Callback<MerchantServiceEntity>() {
                        @Override
                        public void onResponse(Call<MerchantServiceEntity> call, Response<MerchantServiceEntity> response) {
                            data.setValue(response.body());
                            Log.d(TAG, "body: " + response.body());

                        }

                        @Override
                        public void onFailure(Call<MerchantServiceEntity> call, Throwable t) {
                            data.setValue(null);
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            data.setValue(null);
        }

        return data;
    }



}
