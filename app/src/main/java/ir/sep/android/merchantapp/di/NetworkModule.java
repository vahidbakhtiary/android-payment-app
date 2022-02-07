package ir.sep.android.merchantapp.di;


import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import ir.sep.android.merchantapp.BuildConfig;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.data.remote.SepyarApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {

    @Singleton
    @Provides
    public SepyarApiService provideRahseparApiService() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Const.WEBSERVICE_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(Const.WEBSERVICE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Const.WEBSERVICE_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Const.WEBSERVICE_TIME_OUT, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.isTest ? Const.RAHSEPAR_BASEURL_Mock_Server : Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(SepyarApiService.class);
    }

}