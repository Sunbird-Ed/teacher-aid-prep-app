
package com.gurug.education.di.components;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.gurug.education.data.repository.local.EkstepDataBase;
import com.gurug.education.data.repository.local.IDatabaseInterface;
import com.gurug.education.data.repository.local.ManagerDatabase;
import com.gurug.education.data.repository.remote.IAPIInterface;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.modules.ModuleApplication;
import com.gurug.education.di.scopes.ApplicationContext;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;
import retrofit2.Retrofit;

/**
 * Created by Sandy on 11/03/18.
 */

@Singleton
@Component(modules = {ModuleApplication.class})
public interface ComponentApplication {

    void inject(com.gurug.education.Application application);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    Cache httpCache();

    Gson gson();

    @Named("normal")
    Retrofit Call();

    IAPIInterface iapiInterface();

    ManagerAPI managerApi();

    SharedPreferences sharedPreferences();

    IDatabaseInterface iDatabaseInterface();

    ManagerDatabase managerDatabase();

    EkstepDataBase ekstepDataBase();

}
