package com.gurug.education.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.response.settings.ResultResponseSettings;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.data.repository.local.ManagerDatabase;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.components.DaggerComponentViewModel;

import javax.inject.Inject;

public class ViewModelSplash extends AndroidViewModel {

    @Inject
    ManagerAPI mManagerAPI;

    @Inject
    ManagerDatabase mManagerDatabase;

    private com.gurug.education.Application mApplication;

    @Inject
    public ViewModelSplash(@NonNull Application application) {
        super(application);
        mApplication = (com.gurug.education.Application) application;
        DaggerComponentViewModel.builder().componentApplication(((com.gurug.education.Application) application).getComponent())
                .build().inject(this);
    }

    public void getSettings() {
        mManagerAPI.getSettings().observeForever(responseSettings -> {
            if (responseSettings != null && responseSettings.getResult() != null && responseSettings.getResult().getResponse() != null) {
                for (ResultResponseSettings resultResponseSettings:responseSettings.getResult().getResponse()){
                    if (resultResponseSettings.getId()!=null&&resultResponseSettings.getId().equalsIgnoreCase("custodianOrgId")){
                        AppPrefs.setCustodianOrgId(mApplication,resultResponseSettings.getValue());
                    }
                }
            }
        });
    }


}
