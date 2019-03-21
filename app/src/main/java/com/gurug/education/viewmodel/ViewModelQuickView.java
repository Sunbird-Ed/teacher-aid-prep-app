package com.gurug.education.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodDetail;
import com.gurug.education.data.repository.local.ManagerDatabase;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.components.DaggerComponentViewModel;
import com.gurug.education.utill.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ViewModelQuickView extends AndroidViewModel {

    @Inject
    ManagerAPI mManagerAPI;

    @Inject
    ManagerDatabase mManagerDatabase;

    private com.gurug.education.Application mApplication;

    @Inject
    public ViewModelQuickView(@NonNull Application application) {
        super(application);
        mApplication = (com.gurug.education.Application) application;
        DaggerComponentViewModel.builder().componentApplication(((com.gurug.education.Application) application).getComponent())
                .build().inject(this);
    }

    public LiveData<ResponseMethodDetail> getMethodDetails(String contentId) {
        if (Utility.isNetworkAvailable(mApplication)) {
            return mManagerAPI.getMethodDetails(contentId, "edit");
        } else {
            return mManagerDatabase.getMethodDetails(contentId);
        }
    }

    public void updateChildren(List<ChildrenMethod> children) {
        mManagerDatabase.updateTeachingMethods(children);
    }

    public void updateLessonPlan(Content content) {
        ArrayList<Content>contents = new ArrayList<>();
        contents.add(content);
        mManagerDatabase.updateLessonPlans(contents);
    }
}
