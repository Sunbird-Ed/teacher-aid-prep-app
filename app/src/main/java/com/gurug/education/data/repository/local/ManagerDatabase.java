package com.gurug.education.data.repository.local;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.lessonplan.ResponseLessonPlan;
import com.gurug.education.data.model.response.lessonplan.ResultLessonPlan;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethodResouces;
import com.gurug.education.data.model.response.teachingmethod.ContentMethod;
import com.gurug.education.data.model.response.teachingmethod.ContentMethodBody;
import com.gurug.education.data.model.response.teachingmethod.ContentMethodResouces;
import com.gurug.education.data.model.response.teachingmethod.ReponseMethodBody;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodDetail;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodResourcesDetails;
import com.gurug.education.data.model.response.teachingmethod.ResultMethod;
import com.gurug.education.data.model.response.teachingmethod.ResultMethodBody;
import com.gurug.education.data.model.response.teachingmethod.ResultMethodResources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ManagerDatabase {


    private final IDatabaseInterface mIDatabaseInterface;

    private final Executor mExecutor = Executors.newFixedThreadPool(100);

    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public ManagerDatabase(IDatabaseInterface databaseInterface) {
        this.mIDatabaseInterface = databaseInterface;
    }

    public void updateLessonPlans(ArrayList<Content> lessonPlans) {
        try {
            mExecutor.execute(() -> mIDatabaseInterface.insertLessonPlans(lessonPlans));
        } catch (Exception ignored) {

        }
    }

    public void updateTeachingMethods(List<ChildrenMethod> lessonPlans) {
        try {
            mExecutor.execute(() -> mIDatabaseInterface.insertTeachingMethod(lessonPlans));
        } catch (Exception ignored) {

        }
    }

    public void updateMethodBody(ContentMethodBody contentMethodBody) {
        try {
            mExecutor.execute(() -> mIDatabaseInterface.insertMethodBody(contentMethodBody));
        } catch (Exception ignored) {

        }
    }

    public void updateResourcesLocally(List<ChildrenMethodResouces> children) {
        try {
            mExecutor.execute(() -> mIDatabaseInterface.insertMethodResources(children));
        } catch (Exception ignored) {

        }
    }

    @SuppressLint("CheckResult")
    public LiveData<ResponseLessonPlan> getLocalLessPlans(String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject) {
        final MutableLiveData<ResponseLessonPlan> data = new MutableLiveData<>();
        Single.fromCallable(() -> mIDatabaseInterface.getAllLessonPlans(selectedBoard, selectedMedium, selectedClass, selectedSubject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> {

                    ResponseLessonPlan responseLessonPlan = new ResponseLessonPlan();
                    ResultLessonPlan resultLessonPlan = new ResultLessonPlan();
                    resultLessonPlan.setContent((ArrayList<Content>) contents);
                    responseLessonPlan.setResult(resultLessonPlan);
                    data.postValue(responseLessonPlan);
                });
        return data;
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Content>> getBookMarkedLessPlans(String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject) {
        final MutableLiveData<List<Content>> data = new MutableLiveData<>();
        Single.fromCallable(() -> mIDatabaseInterface.getBookMarkedLessonPlans(true,selectedBoard,selectedMedium,selectedClass,selectedSubject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> {
                    data.postValue(contents);
                });
        return data;
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Content>> getMarkAsDonePlans(String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject) {
        final MutableLiveData<List<Content>> data = new MutableLiveData<>();
        Single.fromCallable(() -> mIDatabaseInterface.getMarkAsDonePlans(true,selectedBoard,selectedMedium,selectedClass,selectedSubject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> {
                    data.postValue(contents);
                });
        return data;
    }

    @SuppressLint("CheckResult")
    public LiveData<ResponseMethodDetail> getMethodDetails(String contentId) {
        final MutableLiveData<ResponseMethodDetail> data = new MutableLiveData<>();
        Single.fromCallable(() -> mIDatabaseInterface.getMethodDetails(contentId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> {
                    ResponseMethodDetail responseMethodDetail = new ResponseMethodDetail();
                    ResultMethod resultMethod = new ResultMethod();
                    ContentMethod contentMethod = new ContentMethod();
                    contentMethod.setChildren(contents);
                    resultMethod.setContent(contentMethod);
                    responseMethodDetail.setResult(resultMethod);
                    data.postValue(responseMethodDetail);
                });
        return data;
    }


    @SuppressLint("CheckResult")
    public LiveData<ResponseMethodResourcesDetails> getResources(String contentId) {
        final MutableLiveData<ResponseMethodResourcesDetails> data = new MutableLiveData<>();
        Single.fromCallable(() -> mIDatabaseInterface.getResources(contentId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> {
                    ResponseMethodResourcesDetails responseMethodDetail = new ResponseMethodResourcesDetails();
                    ResultMethodResources resultMethod = new ResultMethodResources();
                    ContentMethodResouces contentMethod = new ContentMethodResouces();
                    contentMethod.setChildren(contents);
                    resultMethod.setContent(contentMethod);
                    responseMethodDetail.setResult(resultMethod);
                    data.postValue(responseMethodDetail);
                });
        return data;
    }


    @SuppressLint("CheckResult")
    public LiveData<ReponseMethodBody> getMethodBody(String contentId) {
        final MutableLiveData<ReponseMethodBody> data = new MutableLiveData<>();
        Single.fromCallable(() -> mIDatabaseInterface.getMethodBody(contentId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> {
                    ReponseMethodBody reponseMethodBody = new ReponseMethodBody();
                    ResultMethodBody resultMethodBody = new ResultMethodBody();
                    resultMethodBody.setContent(contents);
                    reponseMethodBody.setResult(resultMethodBody);
                    data.postValue(reponseMethodBody);
                });
        return data;
    }


    /*
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
     */
/*

    public void updateSrListLocally(ArrayList<ServiceRequestLite> serviceRequestLites) {
        try {
            mExecutor.execute(() -> mDatabaseInterface.insertServiceRequestLite(serviceRequestLites));
        } catch (Exception ignored) {

        }
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<SrListResponse> getSrListLocally(String status) {
        final MutableLiveData<SrListResponse> data = new MutableLiveData<>();
        Single.fromCallable(() -> mDatabaseInterface.getAllSR(status))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(serviceRequestLitesLiteList -> {
                    ArrayList<ServiceRequestLite> serviceRequestLites = (ArrayList<ServiceRequestLite>) serviceRequestLitesLiteList;
                    SrListResponse srListResponse = new SrListResponse();
                    ListofServiceRequestLite listofServiceRequestLite = new ListofServiceRequestLite();
                    listofServiceRequestLite.setServiceRequestLite(serviceRequestLites);
                    srListResponse.setListofServiceRequestLite(listofServiceRequestLite);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterInput = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    Collections.sort(serviceRequestLites, (o1, o2) -> {
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = formatterInput.parse(o1.getMFEPreviousInternalUpdateLocalTZ());
                            date2 = formatterInput.parse(o2.getMFEPreviousInternalUpdateLocalTZ());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date2 != null && date1 != null) {
                            return date2.compareTo(date1);
                        } else {
                            return 1;
                        }

                    });
                    if (serviceRequestLites.size() > 0) {
                        srListResponse.setTotalRecordCount(serviceRequestLites.get(0).getTotalRecordCount());
                    }

                    data.setValue(srListResponse);
                });
        return data;
    }

    public void updateDetailsLocally(SrDetailsResponse srDetailsResponse) {
        try {
            if (srDetailsResponse != null && srDetailsResponse.getServiceRequest() != null) {
                mExecutor.execute(() -> mDatabaseInterface.insertServiceRequest(srDetailsResponse.getServiceRequest()));
            }
        } catch (Exception ignored) {

        }
    }

    @SuppressLint("CheckResult")
    public LiveData<SrDetailsResponse> getSrDetailsLocally(String srNum) {
        final MutableLiveData<SrDetailsResponse> data = new MutableLiveData<>();
        try {
            Single.fromCallable(() -> mDatabaseInterface.getSrDetails(srNum))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((serviceRequest, throwable) -> {
                        if (throwable == null) {
                            SrDetailsResponse srDetailsResponse = new SrDetailsResponse();
                            if (serviceRequest != null && serviceRequest.getListofServiceRequestActivity() != null && serviceRequest.getListofServiceRequestActivity().getServiceRequestActivity() != null) {

                                Collections.sort(serviceRequest.getListofServiceRequestActivity().getServiceRequestActivity(), (o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedBy()));

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Collections.sort(serviceRequest.getListofServiceRequestActivity().getServiceRequestActivity(), (o1, o2) -> {
                                    Date date1 = null;
                                    Date date2 = null;


                                    try {
                                        if (o1.getCreatedDate().contains("T")) {
                                            date1 = formatterInput.parse(o1.getCreatedDate().replace('T', ' '));
                                        } else {
                                            date1 = formatterInput.parse(o1.getCreatedDate());
                                        }


                                        if (o2.getCreatedDate().contains("T")) {
                                            date2 = formatterInput.parse(o2.getCreatedDate().replace('T', ' '));
                                        } else {
                                            date2 = formatterInput.parse(o2.getCreatedDate());
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (date2 != null && date1 != null) {
                                        return date2.compareTo(date1);
                                    } else {
                                        return 1;
                                    }

                                });

                            }
                            srDetailsResponse.setServiceRequest(serviceRequest);
                            data.setValue(srDetailsResponse);
                        } else {
                            SrDetailsResponse srDetailsResponse = new SrDetailsResponse();
                            data.setValue(srDetailsResponse);
                        }
                    });
        } catch (Exception ignored) {

        }
        return data;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<FCMNotification> updateNotification(FCMNotification fcmNotification) {
        final MutableLiveData<FCMNotification> data = new MutableLiveData<>();
        try {
            //if (fcmNotification.getStatus().equalsIgnoreCase(AppConstants.KEY_NOTIFICATION_TYPE_ACTIVITY))
            {
                Single.fromCallable(() -> mDatabaseInterface.getFCMNotification(fcmNotification.getSRNum(), fcmNotification.getStatus()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((fcmNotification1, throwable) -> {
                            if (fcmNotification1 == null) {
                                mExecutor.execute(() -> mDatabaseInterface.insertNotification(fcmNotification));
                                data.setValue(fcmNotification);
                            } else {
                                if (fcmNotification1.getDate() < fcmNotification.getDate()) {
                                    fcmNotification.setDate(fcmNotification1.getDate());
                                }
                                fcmNotification.setMessageId(fcmNotification1.getMessageId() + AppConstants.COMMOA + fcmNotification.getMessageId());
                                fcmNotification.setTotal(fcmNotification1.getTotal() + 1);
                                mDatabaseInterface.updateFCMNotification(fcmNotification);
                                data.setValue(fcmNotification);
                            }
                        });
            }
        } catch (Exception ignored) {

        }
        return data;
    }
*/
}
