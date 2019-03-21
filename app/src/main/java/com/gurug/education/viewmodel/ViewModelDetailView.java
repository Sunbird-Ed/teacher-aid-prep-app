package com.gurug.education.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethodResouces;
import com.gurug.education.data.model.response.teachingmethod.ContentMethodBody;
import com.gurug.education.data.model.response.teachingmethod.ReponseMethodBody;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodDetail;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodResourcesDetails;
import com.gurug.education.data.repository.local.ManagerDatabase;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.components.DaggerComponentViewModel;
import com.gurug.education.utill.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ViewModelDetailView extends AndroidViewModel {

    @Inject
    ManagerAPI mManagerAPI;

    @Inject
    ManagerDatabase mManagerDatabase;

    private com.gurug.education.Application mApplication;

    @Inject
    public ViewModelDetailView(@NonNull Application application) {
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

    public LiveData<ResponseMethodResourcesDetails> getResources(String contentId) {
        if (Utility.isNetworkAvailable(mApplication)) {
            return mManagerAPI.getResources(contentId, "edit");
        } else {
            return mManagerDatabase.getResources(contentId);
        }
    }


    public LiveData<ReponseMethodBody> getMethodBody(String contentId) {
        if (Utility.isNetworkAvailable(mApplication)) {
            return mManagerAPI.getMethodBody(contentId, "edit", "duration,methodtype,body,name,versionKey,description,board,gradeLevel,subject,medium");
        } else {
            return mManagerDatabase.getMethodBody(contentId);
        }
    }

    public void updateChildren(String contentId, List<ChildrenMethod> children) {
        mManagerDatabase.getMethodDetails(contentId).observeForever(responseMethodDetail -> {
            for (ChildrenMethod childrenMethod : responseMethodDetail.getResult().getContent().getChildren()) {
                for (ChildrenMethod childrenMethod1 : children) {
                    if (childrenMethod1.getPedagogyId().equalsIgnoreCase(childrenMethod.getPedagogyId())) {
                        childrenMethod1.setMethodtype(childrenMethod.getMethodtype());
                        childrenMethod1.setPedagogyStep(childrenMethod.getPedagogyStep());
                        childrenMethod1.setDescription(childrenMethod.getDescription());
                        childrenMethod1.setDuration(childrenMethod.getDuration());
                    }
                }
            }
            mManagerDatabase.updateTeachingMethods(children);
        });
    }

    public void updateChildren(List<ChildrenMethod> children) {
        mManagerDatabase.updateTeachingMethods(children);
    }

    public void updateMethodBody(ContentMethodBody content) {
        mManagerDatabase.updateMethodBody(content);
    }

    public void updateResourcesLocally(List<ChildrenMethodResouces> children) {
        mManagerDatabase.updateResourcesLocally(children);
    }

    public void updateLessonPlan(Content content) {
        ArrayList<Content> contents = new ArrayList<>();
        contents.add(content);
        mManagerDatabase.updateLessonPlans(contents);
    }
}
