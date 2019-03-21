
package com.gurug.education.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gurug.education.BuildConfig;
import com.gurug.education.data.repository.local.EkstepDataBase;
import com.gurug.education.data.repository.local.IDatabaseInterface;
import com.gurug.education.data.repository.local.ManagerDatabase;
import com.gurug.education.data.repository.remote.IAPIInterface;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.scopes.ApplicationContext;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.Utility;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sandy on 11/03/18.
 */

@Module
public class ModuleApplication {

    private final Application mApplication;

    public ModuleApplication(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
    }

    @Provides
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Named("normal")
    Retrofit provideCall(final SharedPreferences sharedPreferences) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //enable THIS FOR dev
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //enable THIS FOR ic_default/uat
        //interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request;
                    request = original.newBuilder()
                            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI3NjdmZGYzZjE0ODQ0NmZlYmEyMzFlZTQxYjU0NTkxYiJ9.0NzSt1sPZC356z1Myx7Ui00L-pvOoAkLtCBAEOcEPMg")
                            .addHeader("x-authenticated-user-token", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ1WXhXdE4tZzRfMld5MG5PS1ZoaE5hU0gtM2lSSjdXU25ibFlwVVU0TFRrIn0.eyJqdGkiOiIxNzU5YWEyMy1jN2YxLTRmMzItYTljYS1kODA2NGMyYjg3OTYiLCJleHAiOjE1NTMyNjQ2NzcsIm5iZiI6MCwiaWF0IjoxNTUzMTc4Mjc3LCJpc3MiOiJodHRwczovL3N0YWdpbmcubnRwLm5ldC5pbi9hdXRoL3JlYWxtcy9zdW5iaXJkIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiNDgyYmI0MTEtYzcyNS00MTMxLWJkOGQtOTkxN2Y1YTc1MzA1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiMGZhYmM0NWYtMDE5Yi00ZWY4LWIwMTAtY2Q0MmNjNzMwN2UxIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6W10sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7fSwibmFtZSI6IkJvb2sgY3JlYXRvciIsInByZWZlcnJlZF91c2VybmFtZSI6Imdib29rY3JlYXRvciIsImdpdmVuX25hbWUiOiJCb29rIiwiZmFtaWx5X25hbWUiOiJjcmVhdG9yIiwiZW1haWwiOiJkbXljcmVhdG9yQGdtYWlsLmNvbSJ9.NAZGcbAGfm8yhv6Jsub8_lm1Vd-x4qADQmC_zF7fbHV7tHEfC_h-nW49rHTq0UrsRAjlroEQ3hpSU85p2Zattc8C2MMCQzr6-u5czngGwhUeAMvRtmCaleLB1CFYjb-cbFkTSHsUCyoJ8LBC5CTB4bbq95oDjY1nRbrb_p6vBzXClThgS1sCzXjOM-rWkG36UNfjwo__J0Vr_h0WmirYnd-SMly8E0qh0nMdYuoDTDlJbirYRqQ2kM9zR_7tPnlTxt8gYd6WTa84U8Fl5c10BdB_0KyKSfPCcByvKVpPSBfR0rJMmJBfSPrCEWWwNA4tMH1-wAIwn20gpKeluE6M-g")
                            .build();
                    return chain.proceed(request);
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public IAPIInterface providesIapiInterface(@Named("normal") Retrofit retrofit) {
        return retrofit.create(IAPIInterface.class);
    }

    @Provides
    @Singleton
    public ManagerAPI providesManagerApi(IAPIInterface iapiInterface) {
        return new ManagerAPI(iapiInterface);
    }

    @Provides
    EkstepDataBase providesekstepDataBase(Application application) {
        SafeHelperFactory factory = new SafeHelperFactory(Utility.getKey(application).toCharArray());
        EkstepDataBase ekstepDataBase = Room.databaseBuilder(application.getApplicationContext(), EkstepDataBase.class, BuildConfig.DATABASE_NAME)
                .allowMainThreadQueries()
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build();

        return ekstepDataBase;

    }

    @Provides
    @Singleton
    public IDatabaseInterface providesIDatabaseInterface(EkstepDataBase ekstepDataBase) {
        return ekstepDataBase.daoAccess();
    }

    @Provides
    @Singleton
    public ManagerDatabase providesManagerDatabase(IDatabaseInterface databaseInterface) {
        return new ManagerDatabase(databaseInterface);
    }

}
