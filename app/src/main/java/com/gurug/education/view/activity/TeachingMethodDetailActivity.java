package com.gurug.education.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeachingMethodDetailActivity extends BaseActivity implements ResourcesAdapter.ResourceItemListeners {

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

    @BindView(R.id.wv_long_dec)
    WebView mWvLongDec;

    @BindView(R.id.rv_resources)
    RecyclerView mRvResources;

    private Content mContent;
    private List<ChildrenMethod> mChildrenMethodList;

    private int mCurrentMethod = 0;

    @Inject
    ViewModelDetailView mViewModelDetailView;

    private ResourcesAdapter mResourcesAdapter;

    private ContentService mContentService;

    private org.ekstep.genieservices.commons.bean.Content mResourceContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_method_detail);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        DaggerComponentActivity.builder()
                .componentApplication(((Application) getApplication()).getComponent())
                .build().inject(this);
        mCurrentMethod = getIntent().getIntExtra(IntentConstant.POS, 0);
        mContent = getIntent().getParcelableExtra(IntentConstant.CONTENT);

        setupWebViewSettings();

        setupHeader();
        getMothodDetails();

        setUpResources();
    }

    private void setUpResources() {
        mRvResources.setLayoutManager(new LinearLayoutManager(TeachingMethodDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        mResourcesAdapter = new ResourcesAdapter(TeachingMethodDetailActivity.this);
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
    }

    private void setupHeader() {
        mTvSubjectName.setText(AppPrefs.getSelectedSubject(TeachingMethodDetailActivity.this));
        mTvClassName.setText(AppPrefs.getSelectedClass(TeachingMethodDetailActivity.this));
        mTvMediumName.setText(AppPrefs.getSelectedMedium(TeachingMethodDetailActivity.this));
    }

    @SuppressLint("SetTextI18n")
    private void getMothodDetails() {
        Utility.showProgress(TeachingMethodDetailActivity.this);
        mViewModelDetailView.getMethodDetails(mContent.getIdentifier()).observe(this, responseMethodDetail -> {
            Utility.hideProgress();
            if (ErrorHandler.handleError(TeachingMethodDetailActivity.this, responseMethodDetail)) {
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
                }
            }
        });
    }

    private void getMethodResources() {
        mViewModelDetailView.getResources(mChildrenMethodList.get(mCurrentMethod).getIdentifier()).observe(this, responseMethodDetail -> {
            if (responseMethodDetail != null && ErrorHandler.handleError(TeachingMethodDetailActivity.this, responseMethodDetail)) {
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

        });
    }

    private void getMethodBody() {
        mViewModelDetailView.getMethodBody(mChildrenMethodList.get(mCurrentMethod).getIdentifier()).observe(this, reponseMethodBody -> {
            if (reponseMethodBody != null && ErrorHandler.handleError(TeachingMethodDetailActivity.this, reponseMethodBody)) {
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
                    ContentPlayer.play(TeachingMethodDetailActivity.this, genieResponse.getResult(), new HashMap<>());
                } else {
                    ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
                    ContentImport contentImport = new ContentImport(contentImportResponse.getIdentifier(), true, Utility.getExternalFilesDir(TeachingMethodDetailActivity.this).toString());
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
        ContentImport contentImport = new ContentImport(childrenMethod.getIdentifier(), true, Utility.getExternalFilesDir(TeachingMethodDetailActivity.this).toString());
        builder.add(contentImport);
        ContentDetailsRequest.Builder requestBuilder = new ContentDetailsRequest.Builder().forContent(childrenMethod.getIdentifier()).withFeedback().withContentAccess();
        mContentService.getContentDetails(requestBuilder.build(), new IResponseHandler<org.ekstep.genieservices.commons.bean.Content>() {
            @Override
            public void onSuccess(GenieResponse<org.ekstep.genieservices.commons.bean.Content> genieResponse) {
                Log.d("genieResponse", genieResponse.getResult().getBasePath() + " ");
                mResourceContent = genieResponse.getResult();
                if (genieResponse.getResult().isAvailableLocally()) {
                    ContentPlayer.play(TeachingMethodDetailActivity.this, genieResponse.getResult(), new HashMap<>());
                } else {
                    ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
                    ContentImport contentImport = new ContentImport(childrenMethod.getIdentifier(), true, Utility.getExternalFilesDir(TeachingMethodDetailActivity.this).toString());
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
