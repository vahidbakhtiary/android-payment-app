package ir.sep.android.merchantapp.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ir.sep.android.merchantapp.BuildConfig;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.data.entities.MerchantServiceBaseEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.remote.SepyarApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupervisorRepository {

    private SepyarApiService apiRequest;

    @Inject
    public SupervisorRepository(SepyarApiService apiRequest) {
        this.apiRequest = apiRequest;
    }

    public LiveData<MerchantServiceEntity> posResetPW(String merchant_GUID, String appKey_GUID, long terminalNo) {
        MerchantServiceBaseEntity request=new MerchantServiceEntity();
        request.setMerchantKey(merchant_GUID);
        request.setAppKey(appKey_GUID);
        request.setTerminalNO(terminalNo);

        final MutableLiveData<MerchantServiceEntity> data = new MutableLiveData<>();
        try {
            Call<MerchantServiceEntity> call = BuildConfig.isTest ? apiRequest.PosPaperRoll() : apiRequest.PosResetPW(request);
            call.enqueue(new Callback<MerchantServiceEntity>() {
                @Override
                public void onResponse(Call<MerchantServiceEntity> call, Response<MerchantServiceEntity> response) {
                    data.setValue(response.body());
                    Log.d(Const.TAG, "body: " + response.body());
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
