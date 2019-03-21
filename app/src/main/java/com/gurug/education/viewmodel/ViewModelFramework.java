package com.gurug.education.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.request.framework.Request;
import com.gurug.education.data.model.request.framework.RequestFramework;
import com.gurug.education.data.model.request.framework.Search;
import com.gurug.education.data.model.response.frameworkdetail.ResponseFramwork;
import com.gurug.education.data.model.response.framwork.ResponseFramework;
import com.gurug.education.data.model.response.settings.ResponseCustFramework;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.components.DaggerComponentViewModel;

import javax.inject.Inject;

public class ViewModelFramework extends AndroidViewModel {
    @Inject
    ManagerAPI mManagerAPI;

    private com.gurug.education.Application mApplication;

    @Inject
    public ViewModelFramework(@NonNull Application application) {
        super(application);
        mApplication = (com.gurug.education.Application) application;
        DaggerComponentViewModel.builder().componentApplication(((com.gurug.education.Application) application).getComponent())
                .build().inject(this);
    }

    public LiveData<ResponseCustFramework> getFramwork() {
        return mManagerAPI.getFrameworkByCustID(AppPrefs.getCustodianOrgId(mApplication));
    }


}
