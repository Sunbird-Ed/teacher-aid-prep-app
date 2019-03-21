package com.gurug.education.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethodResouces;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.ErrorHandler;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.Log;
import com.gurug.education.utill.Utility;
import com.gurug.education.view.adapter.ResourcesAdapter;
import com.gurug.education.viewmodel.ViewModelDetailView;

import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ContentDetailsRequest;
import org.ekstep.genieservices.commons.bean.ContentImport;
import org.ekstep.genieservices.commons.bean.ContentImportRequest;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.eventbus.EventBus;
import org.ekstep.genieservices.utils.ContentPlayer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LessonPlanDetailViewActivity extends BaseActivity implements ResourcesAdapter.ResourceItemListeners {

    @BindView(R.id.tv_subject_name)
    TextView mTvSubjectName;

    @BindView(R.id.tv_class_name)
    TextView mTvClassName;

    @BindView(R.id.tv_medium_name)
    TextView mTvMediumName;

    @BindView(R.id.tv_step_name)
    TextView mTvStepName;

    @BindView(R.id.tv_method_name)
    TextView mTvMethodName;

    @BindView(R.id.tv_prev)
    TextView mTvPrev;

    @BindView(R.id.tv_next)
    TextView mTvNext;

    @BindView(R.id.wv_long_dec)
    WebView mWvLongDec;

    @BindView(R.id.rv_resources)
    RecyclerView mRvResources;

    private Content mContent;
    private List<ChildrenMethod> mChildrenMethodList;

    private int mCurrentMethod = 0;

    private boolean mIsLookingForResources = false;


    @Inject
    ViewModelDetailView mViewModelDetailView;

    private ResourcesAdapter mResourcesAdapter;

    private ContentService mContentService;

    private org.ekstep.genieservices.commons.bean.Content mResourceContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_plan_detail_view);
        init();
    }

    @Override
    void init() {
        mChildrenMethodList = new ArrayList<>();
        mCurrentMethod = 0;
        ButterKnife.bind(this);
        DaggerComponentActivity.builder()
                .componentApplication(((Application) getApplication()).getComponent())
                .build().inject(this);

        mContent = getIntent().getParcelableExtra(IntentConstant.CONTENT);

        setupWebViewSettings();

        setupHeader();
        getMothodDetails();

        setUpResources();
    }

    private void setUpResources() {
        mRvResources.setLayoutManager(new LinearLayoutManager(LessonPlanDetailViewActivity.this, LinearLayoutManager.HORIZONTAL, false));
        mResourcesAdapter = new ResourcesAdapter(LessonPlanDetailViewActivity.this);
        mRvResources.setAdapter(mResourcesAdapter);
    }

    private void setupWebViewSettings() {
        final WebSettings webSettings = mWvLongDec.getSettings();

        mWvLongDec.getSettings().setBuiltInZoomControls(true);
        mWvLongDec.getSettings().setDefaultFontSize((int) getResources().getDimension(R.dimen._14sp));
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mWvLongDec.getSettings().setDisplayZoomControls(false);
    }

    private void setupHeader() {
        mTvSubjectName.setText(AppPrefs.getSelectedSubject(LessonPlanDetailViewActivity.this));
        mTvClassName.setText(AppPrefs.getSelectedClass(LessonPlanDetailViewActivity.this));
        mTvMediumName.setText(AppPrefs.getSelectedMedium(LessonPlanDetailViewActivity.this));
    }

    @SuppressLint("SetTextI18n")
    private void getMothodDetails() {
        Utility.showProgress(LessonPlanDetailViewActivity.this);
        mViewModelDetailView.getMethodDetails(mContent.getIdentifier()).observe(this, responseMethodDetail -> {
            Utility.hideProgress();
            if (ErrorHandler.handleError(LessonPlanDetailViewActivity.this, responseMethodDetail)) {
                Log.i("responseMethodDetail", responseMethodDetail.toString());
                if (responseMethodDetail.getResult() != null && responseMethodDetail.getResult().getContent() != null && responseMethodDetail.getResult().getContent().getChildren() != null) {
                    mChildrenMethodList = responseMethodDetail.getResult().getContent().getChildren();
                    if (mChildrenMethodList != null && mChildrenMethodList.size() > mCurrentMethod) {
                        mTvMethodName.setText(mChildrenMethodList.get(mCurrentMethod).getMethodtype());
                        mTvStepName.setText(mChildrenMethodList.get(mCurrentMethod).getPedagogyStep() + AppConstants.SPACE + AppConstants.HIPHEN + AppConstants.SPACE);
                        mViewModelDetailView.updateChildren(responseMethodDetail.getResult().getContent().getChildren());
                        getMethodBody();
                        getMethodResources();
                    }

                    if (mChildrenMethodList != null && mCurrentMethod == mChildrenMethodList.size() - 1) {
                        mTvNext.setText("MARK AS DONE >");
                        //mTvNext.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorGrey2));
                        mTvMethodName.setText(mChildrenMethodList.get(mCurrentMethod).getMethodtype());
                        mTvStepName.setText(mChildrenMethodList.get(mCurrentMethod).getPedagogyStep() + AppConstants.SPACE + AppConstants.HIPHEN + AppConstants.SPACE);
                    } else if (mChildrenMethodList != null && mChildrenMethodList.size() > mCurrentMethod) {
                        mTvNext.setText("NEXT >");
                        mTvMethodName.setText(mChildrenMethodList.get(mCurrentMethod).getMethodtype());
                        mTvStepName.setText(mChildrenMethodList.get(mCurrentMethod).getPedagogyStep() + AppConstants.SPACE + AppConstants.HIPHEN + AppConstants.SPACE);
                        mTvNext.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                        mTvPrev.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                    }
                }
            }
        });
    }


    private void getMethodResources() {
        mRvResources.setVisibility(View.GONE);
        mIsLookingForResources = true;
        //
        Utility.showProgress(LessonPlanDetailViewActivity.this);
        mViewModelDetailView.getResources(mChildrenMethodList.get(mCurrentMethod).getIdentifier()).observe(this, responseMethodDetail -> {
            if (responseMethodDetail != null && ErrorHandler.handleError(LessonPlanDetailViewActivity.this, responseMethodDetail)) {
                if (responseMethodDetail.getResult() != null && responseMethodDetail.getResult().getContent() != null && responseMethodDetail.getResult().getContent().getChildren() != null && responseMethodDetail.getResult().getContent().getChildren().size() > 0) {
                    mRvResources.setVisibility(View.VISIBLE);
                    mResourcesAdapter.update(responseMethodDetail.getResult().getContent().getChildren());
                    mViewModelDetailView.updateResourcesLocally(responseMethodDetail.getResult().getContent().getChildren());
                } else {
                    mRvResources.setVisibility(View.GONE);
                }
            } else {
                mRvResources.setVisibility(View.GONE);
            }
            mIsLookingForResources = false;
            Utility.hideProgress();
        });
    }

    private void getMethodBody() {
        mViewModelDetailView.getMethodBody(mChildrenMethodList.get(mCurrentMethod).getIdentifier()).observe(this, reponseMethodBody -> {
            if (reponseMethodBody != null && ErrorHandler.handleError(LessonPlanDetailViewActivity.this, reponseMethodBody)) {
                if (reponseMethodBody.getResult() != null && reponseMethodBody.getResult().getContent() != null) {
                    mViewModelDetailView.updateMethodBody(reponseMethodBody.getResult().getContent());
                    mWvLongDec.loadData(reponseMethodBody.getResult().getContent().getBody(), "text/html", "UTF-8");

                }
            }
        });
    }

    @OnClick(R.id.ib_back)
    void onClickBack() {
        finish();
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tv_next)
    void onClickNext() {
        if (!mIsLookingForResources) {
            if (mTvNext.getText().toString().contains("NEXT") && mTvNext.getCurrentTextColor() != ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorGrey2)) {
                mCurrentMethod++;
                if (mChildrenMethodList != null && mCurrentMethod == mChildrenMethodList.size() - 1) {
                    mTvNext.setText("MARK AS DONE >");
                    //mTvNext.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorGrey2));
                    mTvPrev.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                    mTvMethodName.setText(mChildrenMethodList.get(mCurrentMethod).getMethodtype());
                    mTvStepName.setText(mChildrenMethodList.get(mCurrentMethod).getPedagogyStep() + AppConstants.SPACE + AppConstants.HIPHEN + AppConstants.SPACE);
                    getMethodBody();
                    getMethodResources();
                } else if (mChildrenMethodList != null && mChildrenMethodList.size() > mCurrentMethod) {
                    mTvNext.setText("NEXT >");
                    mTvMethodName.setText(mChildrenMethodList.get(mCurrentMethod).getMethodtype());
                    mTvStepName.setText(mChildrenMethodList.get(mCurrentMethod).getPedagogyStep() + AppConstants.SPACE + AppConstants.HIPHEN + AppConstants.SPACE);
                    getMethodBody();
                    getMethodResources();
                    mTvNext.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                    mTvPrev.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                }
            } else {
                mContent.setDone(true);
                mViewModelDetailView.updateLessonPlan(mContent);
                finish();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tv_prev)
    void onClickPrev() {
        if (!mIsLookingForResources) {
            if (mTvPrev.getCurrentTextColor() != ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorGrey2)) {
                mCurrentMethod--;
                if (mChildrenMethodList != null && mCurrentMethod == 0) {
                    mTvPrev.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorGrey2));
                    mTvNext.setText("NEXT >");
                    mTvNext.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                    mTvMethodName.setText(mChildrenMethodList.get(mCurrentMethod).getMethodtype());
                    mTvStepName.setText(mChildrenMethodList.get(mCurrentMethod).getPedagogyStep() + AppConstants.SPACE + AppConstants.HIPHEN + AppConstants.SPACE);
                    getMethodBody();
                    getMethodResources();
                } else if (mChildrenMethodList != null && mChildrenMethodList.size() > mCurrentMethod) {
                    mTvMethodName.setText(mChildrenMethodList.get(mCurrentMethod).getMethodtype());
                    mTvStepName.setText(mChildrenMethodList.get(mCurrentMethod).getPedagogyStep() + AppConstants.SPACE + AppConstants.HIPHEN + AppConstants.SPACE);
                    getMethodBody();
                    getMethodResources();
                    mTvNext.setText("NEXT >");
                    mTvPrev.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                    mTvNext.setTextColor(ContextCompat.getColor(LessonPlanDetailViewActivity.this, R.color.colorBlack2));
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadProgress(DownloadProgress downloadProgress) throws InterruptedException {
        //mPresenter.updateProgressBar(downloadProgress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentImportResponse(ContentImportResponse contentImportResponse) throws InterruptedException {

        ContentDetailsRequest.Builder requestBuilder = new ContentDetailsRequest.Builder().forContent(contentImportResponse.getIdentifier()).withFeedback().withContentAccess();
        mContentService.getContentDetails(requestBuilder.build(), new IResponseHandler<org.ekstep.genieservices.commons.bean.Content>() {
            @Override
            public void onSuccess(GenieResponse<org.ekstep.genieservices.commons.bean.Content> genieResponse) {
                Log.d("genieResponse", genieResponse.getResult().getBasePath() + " ");
                mResourceContent = genieResponse.getResult();
                if (genieResponse.getResult().isAvailableLocally()) {
                    ContentPlayer.play(LessonPlanDetailViewActivity.this, genieResponse.getResult(), new HashMap<>());
                } else {
                    ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
                    ContentImport contentImport = new ContentImport(contentImportResponse.getIdentifier(), true, Utility.getExternalFilesDir(LessonPlanDetailViewActivity.this).toString());
                    builder.add(contentImport);
                    mContentService.importContent(builder.build(), new IResponseHandler<List<ContentImportResponse>>() {
                        @Override
                        public void onSuccess(GenieResponse<List<ContentImportResponse>> genieResponse) {
                            Log.d("genieResponse", genieResponse.getMessage() + " ");
                        }

                        @Override
                        public void onError(GenieResponse<List<ContentImportResponse>> genieResponse) {
                            Log.d("genieResponse", genieResponse.getErrorMessages() + " ");
                        }
                    });
                }
            }

            @Override
            public void onError(GenieResponse<org.ekstep.genieservices.commons.bean.Content> genieResponse) {
                Log.d("genieResponse", genieResponse.getMessage() + " ");
            }
        });
    }

    @Override
    public void onClickResourceItem(ChildrenMethodResouces childrenMethod) {
        mResourceContent = null;
        mContentService = GenieService.getAsyncService().getContentService();
        ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
        ContentImport contentImport = new ContentImport(childrenMethod.getIdentifier(), true, Utility.getExternalFilesDir(LessonPlanDetailViewActivity.this).toString());
        builder.add(contentImport);
        ContentDetailsRequest.Builder requestBuilder = new ContentDetailsRequest.Builder().forContent(childrenMethod.getIdentifier()).withFeedback().withContentAccess();
        mContentService.getContentDetails(requestBuilder.build(), new IResponseHandler<org.ekstep.genieservices.commons.bean.Content>() {
            @Override
            public void onSuccess(GenieResponse<org.ekstep.genieservices.commons.bean.Content> genieResponse) {
                Log.d("genieResponse", genieResponse.getResult().getBasePath() + " ");
                mResourceContent = genieResponse.getResult();
                if (genieResponse.getResult().isAvailableLocally()) {
                    ContentPlayer.play(LessonPlanDetailViewActivity.this, genieResponse.getResult(), new HashMap<>());
                } else {
                    ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
                    ContentImport contentImport = new ContentImport(childrenMethod.getIdentifier(), true, Utility.getExternalFilesDir(LessonPlanDetailViewActivity.this).toString());
                    builder.add(contentImport);
                    mContentService.importContent(builder.build(), new IResponseHandler<List<ContentImportResponse>>() {
                        @Override
                        public void onSuccess(GenieResponse<List<ContentImportResponse>> genieResponse) {
                            Log.d("genieResponse", genieResponse.getMessage() + " ");
                            //ContentPlayer.play(LessonPlanDetailViewActivity.this, (org.ekstep.genieservices.commons.bean.Content) genieResponse.getResult(), new HashMap<>());
                        }

                        @Override
                        public void onError(GenieResponse<List<ContentImportResponse>> genieResponse) {
                            Log.d("genieResponse", genieResponse.getErrorMessages() + " ");
                        }
                    });
                }

            }

            @Override
            public void onError(GenieResponse<org.ekstep.genieservices.commons.bean.Content> genieResponse) {
                Log.d("genieResponse", genieResponse.getMessage() + " ");
            }
        });

        /*

        //2. To download the content

       */


        //1. Get The details of the content

        //3. Play the content
       /* mContentService.getContentDetails(requestBuilder.build(), new IResponseHandler<org.ekstep.genieservices.commons.bean.Content>() {
            @Override
            public void onSuccess(GenieResponse<org.ekstep.genieservices.commons.bean.Content> genieResponse) {
                Log.d("genieResponse", genieResponse.getResult().getBasePath() + " ");
                ContentPlayer.play(getActivity(), genieResponse.getResult(), new HashMap<>());
            }

            @Override
            public void onError(GenieResponse<org.ekstep.genieservices.commons.bean.Content> genieResponse) {
                Log.d("genieResponse", genieResponse.getMessage() + " ");
            }
        });*/


        /*
        ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
        ContentImport contentImport = new ContentImport(mContentData.getIdentifier(), (mIsFromTextBookOrCollection || mIsCanvasDeeplink), FileHandler.getDefaultStoragePath(mContext));
        if (!mIsSpineAvailable) {
            contentImport.setCorrelationData(PreferenceUtil.getCoRelationList());
        }
        builder.add(contentImport);
        mIsDownloading = true;
        ContentUtil.addDownloadQueueItem(mContentData.getIdentifier(), mContentData.getName(),
                mContentSize, null, null, 0);



         */
        //

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.unregisterSubscriber(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

}

