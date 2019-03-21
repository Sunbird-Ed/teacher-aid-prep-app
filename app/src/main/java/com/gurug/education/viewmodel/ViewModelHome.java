package com.gurug.education.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.lessonplan.ResponseLessonPlan;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.data.repository.local.ManagerDatabase;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.components.DaggerComponentViewModel;
import com.gurug.education.utill.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ViewModelHome extends AndroidViewModel {

    @Inject
    ManagerAPI mManagerAPI;

    @Inject
    ManagerDatabase mManagerDatabase;

    private com.gurug.education.Application mApplication;

    @Inject
    public ViewModelHome(@NonNull Application application) {
        super(application);
        mApplication = (com.gurug.education.Application) application;
        DaggerComponentViewModel.builder().componentApplication(((com.gurug.education.Application) application).getComponent())
                .build().inject(this);
    }

    public LiveData<ResponseLessonPlan> getLessonPlans(RequestLessonPlan requestLessonPlan) {
        final MutableLiveData<ResponseLessonPlan> data = new MutableLiveData<>();
        if (Utility.isNetworkAvailable(mApplication)) {
            mManagerAPI.getLessonPlans(requestLessonPlan).observeForever(responseLessonPlan -> {
                if (responseLessonPlan != null && responseLessonPlan.getResult() != null && responseLessonPlan.getResult().getContent() != null) {
                    mManagerDatabase.getLocalLessPlans(AppPrefs.getSelectedBoard(mApplication), AppPrefs.getSelectedMedium(mApplication), AppPrefs.getSelectedClass(mApplication), AppPrefs.getSelectedSubject(mApplication)).observeForever(responseLessonPlan1 -> {
                        if (responseLessonPlan1 != null && responseLessonPlan1.getResult() != null && responseLessonPlan1.getResult().getContent() != null) {
                            for (Content content : responseLessonPlan.getResult().getContent()) {
                                for (Content contentLocal : responseLessonPlan1.getResult().getContent()) {
                                    if (contentLocal.getIdentifier().equalsIgnoreCase(content.getIdentifier())) {
                                        content.setBookMarked(contentLocal.isBookMarked());
                                        content.setDone(contentLocal.isDone());
                                    }
                                }
                            }
                        }
                        mManagerDatabase.updateLessonPlans(responseLessonPlan.getResult().getContent());
                        data.setValue(responseLessonPlan);
                    });
                } else {
                    data.setValue(responseLessonPlan);
                }
            });
        } else {
            mManagerDatabase.getLocalLessPlans(AppPrefs.getSelectedBoard(mApplication), AppPrefs.getSelectedMedium(mApplication), AppPrefs.getSelectedClass(mApplication), AppPrefs.getSelectedSubject(mApplication)).observeForever(data::setValue);
        }
        return data;
    }

    public void updateLessonPlans(ArrayList<Content> contents) {
        mManagerDatabase.getLocalLessPlans(AppPrefs.getSelectedBoard(mApplication), AppPrefs.getSelectedMedium(mApplication), AppPrefs.getSelectedClass(mApplication), AppPrefs.getSelectedSubject(mApplication)).observeForever(responseLessonPlan -> {
            if (responseLessonPlan != null && responseLessonPlan.getResult() != null && responseLessonPlan.getResult().getContent() != null) {
                for (Content content : contents) {
                    for (Content contentLocal : responseLessonPlan.getResult().getContent()) {
                        if (contentLocal.getIdentifier().equalsIgnoreCase(content.getIdentifier())) {
                            content.setBookMarked(contentLocal.isBookMarked());
                        }
                    }
                }
            }
            mManagerDatabase.updateLessonPlans(contents);
        });
    }

    public void updateLessons(ArrayList<Content> contents) {
        mManagerDatabase.updateLessonPlans(contents);
    }

    public ArrayList<Content> getValidPlans(ResponseLessonPlan responseLessonPlan) {
        ArrayList<Content> contents = new ArrayList<>();
        if (responseLessonPlan != null && responseLessonPlan.getResult() != null && responseLessonPlan.getResult().getContent() != null) {
            for (Content content : responseLessonPlan.getResult().getContent()) {
                content.setGrade(content.getGradeLevel().get(0));
                contents.add(content);
            }
        }
        return contents;
    }

    public LiveData<List<Content>> getBookMarkedPlans(String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject) {
        return mManagerDatabase.getBookMarkedLessPlans(selectedBoard,selectedMedium,selectedClass,selectedSubject);
    }

    public LiveData<List<Content>> getMarkAsDonePlans(String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject) {
        return mManagerDatabase.getMarkAsDonePlans(selectedBoard,selectedMedium,selectedClass,selectedSubject);
    }
}
