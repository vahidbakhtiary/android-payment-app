package ir.sep.android.merchantapp.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import ir.sep.android.merchantapp.BuildConfig;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.data.entities.RahseparRequest;
import ir.sep.android.merchantapp.data.entities.RahseparResponse;
import ir.sep.android.merchantapp.data.remote.SepyarApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@ActivityRetainedScoped
public class TransactionReportRepository {

    private SepyarApiService apiRequest;

    @Inject
    public TransactionReportRepository(SepyarApiService apiRequest) {
        this.apiRequest = apiRequest;
    }

    public LiveData<RahseparResponse> getTransactionReport(RahseparRequest request) {
        Log.d("mytest", "TransactionReport is Called ");

        final MutableLiveData<RahseparResponse> data = new MutableLiveData<>();
        try {
            apiRequest.getReport(request)
                    .enqueue(new Callback<RahseparResponse>() {
                        @Override
                        public void onResponse(Call<RahseparResponse> call, Response<RahseparResponse> response) {
                            data.setValue(response.body());
                            Log.d(Const.TAG, "body: " + response.body());
                        }

                        @Override
                        public void onFailure(Call<RahseparResponse> call, Throwable t) {
                            data.setValue(null);
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            data.setValue(null);
        }

        return data;
    }
    public LiveData<RahseparResponse> getTotallyTransactionReport(RahseparRequest request) {
        Log.d("mytest", "TotallyTransactionReport is Called ");
        final MutableLiveData<RahseparResponse> data = new MutableLiveData<>();
        try {

            Call<RahseparResponse> call = BuildConfig.isTest ? apiRequest.getReportTotally() : apiRequest.getReportTotally(request);

           call.enqueue(new Callback<RahseparResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<RahseparResponse> call, @NotNull Response<RahseparResponse> response) {

                            data.setValue(response.body());
                            Log.d(Const.TAG, "body: " + response.body());
                        }

                        @Override
                        public void onFailure(@NotNull Call<RahseparResponse> call, @NotNull Throwable t) {
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
