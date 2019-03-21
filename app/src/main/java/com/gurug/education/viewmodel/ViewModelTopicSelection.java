package com.gurug.education.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.facetsearh.ResponseFacetSearch;
import com.gurug.education.data.model.response.frameworkdetail.Associations;
import com.gurug.education.data.model.response.frameworkdetail.Category;
import com.gurug.education.data.model.response.frameworkdetail.ResponseFramwork;
import com.gurug.education.data.model.response.frameworkdetail.Terms;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.data.repository.remote.ManagerAPI;
import com.gurug.education.di.components.DaggerComponentViewModel;
import com.gurug.education.utill.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ViewModelTopicSelection extends AndroidViewModel {

    @Inject
    ManagerAPI mManagerAPI;

    private com.gurug.education.Application mApplication;

    @Inject
    public ViewModelTopicSelection(@NonNull Application application) {
        super(application);
        mApplication = (com.gurug.education.Application) application;
        DaggerComponentViewModel.builder().componentApplication(((com.gurug.education.Application) application).getComponent())
                .build().inject(this);
    }

    public LiveData<ResponseFramwork> getTopics() {
        return mManagerAPI.getFramwork(AppPrefs.getSelectedFramework(mApplication));
    }


    public LiveData<ResponseFacetSearch> getFacetsSearch(RequestLessonPlan request) {
        return mManagerAPI.getFacetSearch(request);
    }

    public List<Terms> getValidTopics(ResponseFramwork response) {

        List<Associations> associations = new ArrayList<>();

        List<Terms> topicList = new ArrayList<>();
        List<Terms> finalTopicsList = new ArrayList<>();

        for (Category category : response.getResult().getFramework().getCategories()) {

            if (category.getCode() != null && category.getCode().equalsIgnoreCase("topic")) {
                topicList.addAll(category.getTerms());
            }


            if (category.getCode() != null) {
                for (Terms terms : category.getTerms()) {
                    if (terms != null && terms.getAssociations() != null) {
                        for (Associations association : terms.getAssociations()) {
                            if (association.getCategory().equalsIgnoreCase("topic")) {
                                if (category.getCode().equalsIgnoreCase(AppConstants.BOARD) && terms.getName().equalsIgnoreCase(AppPrefs.getSelectedBoard(mApplication))) {
                                    associations.add(association);
                                } else if (category.getCode().equalsIgnoreCase(AppConstants.MEDIUM) && terms.getName().equalsIgnoreCase(AppPrefs.getSelectedMedium(mApplication))) {
                                    associations.add(association);
                                } else if (category.getCode().equalsIgnoreCase(AppConstants.SUBJECT) && terms.getName().equalsIgnoreCase(AppPrefs.getSelectedSubject(mApplication))) {
                                    associations.add(association);
                                } else if (category.getCode().equalsIgnoreCase(AppConstants.CLASS) && terms.getName().equalsIgnoreCase(AppPrefs.getSelectedClass(mApplication))) {
                                    associations.add(association);
                                }
                            }
                        }
                    }
                }
            }


        }

        Map<String, Associations> map = new HashMap<>();
        for (Associations association : associations) {
            map.put(association.getIdentifier(), association);
        }

        for (Map.Entry<String, Associations> association : map.entrySet()) {
            for (Terms terms : topicList) {
                if (association.getValue().getIdentifier().equalsIgnoreCase(terms.getIdentifier())) {
                    finalTopicsList.add(terms);
                }
            }
        }

        return finalTopicsList.size() > 0 ? finalTopicsList : topicList;
    }
}
