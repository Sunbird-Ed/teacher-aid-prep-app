package com.gurug.education.data.repository.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.request.framework.RequestFramework;
import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.facetsearh.ResponseFacetSearch;
import com.gurug.education.data.model.response.frameworkdetail.ResponseFramwork;
import com.gurug.education.data.model.response.framwork.ResponseFramework;
import com.gurug.education.data.model.response.lessonplan.ResponseLessonPlan;
import com.gurug.education.data.model.response.settings.ResponseCustFramework;
import com.gurug.education.data.model.response.settings.ResponseSettings;
import com.gurug.education.data.model.response.teachingmethod.ReponseMethodBody;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodDetail;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodResourcesDetails;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ManagerAPI {
    private final IAPIInterface mIAPIInterface;

    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public ManagerAPI(IAPIInterface apiInterface) {
        this.mIAPIInterface = apiInterface;
    }

    public LiveData<ResponseSettings> getSettings() {
        final MutableLiveData<ResponseSettings> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseSettings -> responseSettings).subscribe((responseSettings, throwable) -> {
                    if (throwable != null) {
                        if (responseSettings != null) {
                            responseSettings.setHttpError(throwable);
                        } else {
                            responseSettings = new ResponseSettings();
                            responseSettings.setHttpError(throwable);
                        }
                        data.setValue(responseSettings);
                    } else {
                        data.setValue(responseSettings);
                    }
                }));
        return data;
    }


    public LiveData<ResponseFramwork> getFramwork(String framworkId) {
        final MutableLiveData<ResponseFramwork> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getFramework(framworkId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseFramwork -> responseFramwork).subscribe((responseFramwork, throwable) -> {
                    if (throwable != null) {
                        if (responseFramwork != null) {
                            responseFramwork.setHttpError(throwable);
                        } else {
                            responseFramwork = new ResponseFramwork();
                            responseFramwork.setHttpError(throwable);
                        }
                        data.setValue(responseFramwork);
                    } else {
                        data.setValue(responseFramwork);
                    }
                }));
        return data;
    }

    public LiveData<ResponseFacetSearch> getFacetSearch(RequestLessonPlan request) {
        final MutableLiveData<ResponseFacetSearch> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getFacetSearch(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseFacetSearch -> responseFacetSearch).subscribe((responseFacetSearch, throwable) -> {
                    if (throwable != null) {
                        if (responseFacetSearch != null) {
                            responseFacetSearch.setHttpError(throwable);
                        } else {
                            responseFacetSearch = new ResponseFacetSearch();
                            responseFacetSearch.setHttpError(throwable);
                        }
                        data.setValue(responseFacetSearch);
                    } else {
                        data.setValue(responseFacetSearch);
                    }
                }));
        return data;
    }

    public LiveData<ResponseFramework> getFramwork(RequestFramework framework) {
        final MutableLiveData<ResponseFramework> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getFrameworks(framework)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseFramwork -> responseFramwork).subscribe((responseFramwork, throwable) -> {
                    if (throwable != null) {
                        if (responseFramwork != null) {
                            responseFramwork.setHttpError(throwable);
                        } else {
                            responseFramwork = new ResponseFramework();
                            responseFramwork.setHttpError(throwable);
                        }
                        data.setValue(responseFramwork);
                    } else {
                        data.setValue(responseFramwork);
                    }
                }));
        return data;
    }

    public LiveData<ResponseCustFramework> getFrameworkByCustID(String  framework) {
        final MutableLiveData<ResponseCustFramework> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getFrameworkByCustId(framework)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseCustFramework -> responseCustFramework).subscribe((responseCustFramework, throwable) -> {
                    if (throwable != null) {
                        if (responseCustFramework != null) {
                            responseCustFramework.setHttpError(throwable);
                        } else {
                            responseCustFramework = new ResponseCustFramework();
                            responseCustFramework.setHttpError(throwable);
                        }
                        data.setValue(responseCustFramework);
                    } else {
                        data.setValue(responseCustFramework);
                    }
                }));
        return data;
    }

    public LiveData<ResponseLessonPlan> getLessonPlans(RequestLessonPlan requestLessonPlan) {
        final MutableLiveData<ResponseLessonPlan> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getLessonPlans(requestLessonPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseLessonPlan -> responseLessonPlan).subscribe((responseLessonPlan, throwable) -> {
                    if (throwable != null) {
                        if (responseLessonPlan != null) {
                            responseLessonPlan.setHttpError(throwable);
                        } else {
                            responseLessonPlan = new ResponseLessonPlan();
                            responseLessonPlan.setHttpError(throwable);
                        }
                        data.setValue(responseLessonPlan);
                    } else {
                        data.setValue(responseLessonPlan);
                    }
                }));
        return data;
    }

    public LiveData<ResponseMethodDetail> getMethodDetails(String content_id, String mode) {
        final MutableLiveData<ResponseMethodDetail> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getMethodDetails(content_id, mode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseMethodDetail -> responseMethodDetail).subscribe((responseMethodDetail, throwable) -> {
                    if (throwable != null) {
                        if (responseMethodDetail != null) {
                            responseMethodDetail.setHttpError(throwable);
                        } else {
                            responseMethodDetail = new ResponseMethodDetail();
                            responseMethodDetail.setHttpError(throwable);
                        }
                        data.setValue(responseMethodDetail);
                    } else {
                        data.setValue(responseMethodDetail);
                    }
                }));
        return data;
    }

    public LiveData<ResponseMethodResourcesDetails> getResources(String content_id, String mode) {
        final MutableLiveData<ResponseMethodResourcesDetails> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getResources(content_id, mode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseMethodDetail -> responseMethodDetail).subscribe((responseMethodDetail, throwable) -> {
                    if (throwable != null) {
                        if (responseMethodDetail != null) {
                            responseMethodDetail.setHttpError(throwable);
                        } else {
                            responseMethodDetail = new ResponseMethodResourcesDetails();
                            responseMethodDetail.setHttpError(throwable);
                        }
                        data.setValue(responseMethodDetail);
                    } else {
                        data.setValue(responseMethodDetail);
                    }
                }));
        return data;
    }

    public LiveData<ReponseMethodBody> getMethodBody(String content_id, String mode, String fields) {
        final MutableLiveData<ReponseMethodBody> data = new MutableLiveData<>();
        mCompositeDisposable.add(mIAPIInterface.getMethodBody(content_id, mode, fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(reponseMethodBody -> reponseMethodBody).subscribe((reponseMethodBody, throwable) -> {
                    if (throwable != null) {
                        if (reponseMethodBody != null) {
                            reponseMethodBody.setHttpError(throwable);
                        } else {
                            reponseMethodBody = new ReponseMethodBody();
                            reponseMethodBody.setHttpError(throwable);
                        }
                        data.setValue(reponseMethodBody);
                    } else {
                        data.setValue(reponseMethodBody);
                    }
                }));
        return data;
    }

}
