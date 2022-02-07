package ir.sep.android.merchantapp.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ir.sep.android.merchantapp.BuildConfig;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.data.entities.MerchantServiceBaseEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;
import ir.sep.android.merchantapp.data.remote.SepyarApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RollRepository {
    private SepyarApiService apiRequest;

    @Inject
    public RollRepository(SepyarApiService apiRequest) {
        this.apiRequest = apiRequest;
    }

    public LiveData<MerchantServiceEntity> sendRollRequest(String merchant_GUID, String appKey_GUID, long terminalNo) {
        final MutableLiveData<MerchantServiceEntity> data = new MutableLiveData<>();
        MerchantServiceBaseEntity request=new MerchantServiceEntity();
        request.setMerchantKey(merchant_GUID);
        request.setAppKey(appKey_GUID);
        request.setTerminalNO(terminalNo);

        try {

            Call<MerchantServiceEntity> call =BuildConfig.isTest ? apiRequest.PosPaperRoll() : apiRequest.PosPaperRoll(request);
                   call.enqueue(new Callback<MerchantServiceEntity>() {
                        @Override
                        public void onResponse(Call<MerchantServiceEntity> call, Response<MerchantServiceEntity> response) {
                            //todo fix bug

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

    public LiveData<MerchantServiceRollEntity> getRollHistory(String merchant_GUID, String appKey_GUID) {
        MerchantServiceBaseEntity request=new MerchantServiceEntity();
        request.setMerchantKey(merchant_GUID);
        request.setAppKey(appKey_GUID);

        final MutableLiveData<MerchantServiceRollEntity> data = new MutableLiveData<>();
        try {
            Call<MerchantServiceRollEntity> call = BuildConfig.isTest ?  apiRequest.PosPaperRollHis() : apiRequest.PosPaperRollHis(request);

            call.enqueue(new Callback<MerchantServiceRollEntity>() {
                @Override
                public void onResponse(Call<MerchantServiceRollEntity> call, Response<MerchantServiceRollEntity> response) {
//                            SystemClock.sleep(3000);
                    data.setValue(response.body());
                    Log.d(Const.TAG, "body: " + response.body());
                }

                @Override
                public void onFailure(Call<MerchantServiceRollEntity> call, Throwable t) {
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
