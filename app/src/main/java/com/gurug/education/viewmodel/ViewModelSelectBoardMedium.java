package com.gurug.education.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.facetsearh.ResponseFacetSearch;
import com.gurug.education.data.model.response.frameworkdetail.ResponseFramwork;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.components.DaggerComponentViewModel;

import javax.inject.Inject;

public class ViewModelSelectBoardMedium extends AndroidViewModel {

    @Inject
    ManagerAPI mManagerAPI;

    private com.gurug.education.Application mApplication;

    @Inject
    public ViewModelSelectBoardMedium(@NonNull Application application) {
        super(application);
        mApplication = (com.gurug.education.Application) application;
        DaggerComponentViewModel.builder().componentApplication(((com.gurug.education.Application) application).getComponent())
                .build().inject(this);
    }

    public LiveData<ResponseFramwork> getFramwork() {
        return mManagerAPI.getFramwork(AppPrefs.getSelectedFramework(mApplication));
    }

    public LiveData<ResponseFacetSearch> getFacetsSearch(RequestLessonPlan request) {
        return mManagerAPI.getFacetSearch(request);
    }
}

