package com.gurug.education.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.request.lessonplans.Filters;
import com.gurug.education.data.model.request.lessonplans.Request;
import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentFragment;
import com.gurug.education.telemeter.TelemetryBuilder;
import com.gurug.education.telemeter.TelemetryHandler;
import com.gurug.education.utill.ErrorHandler;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.TelemetryConstants;
import com.gurug.education.utill.Utility;
import com.gurug.education.utill.textdrawble.TextDrawable;
import com.gurug.education.utill.views.ImageViewCircle;
import com.gurug.education.view.activity.LessonPlanDetailViewActivity;
import com.gurug.education.view.activity.LessonPlanQuickViewActivity;
import com.gurug.education.view.activity.SelectClassActivity;
import com.gurug.education.view.activity.TopicSelectionActivity;
import com.gurug.education.view.adapter.LessonPlanAdapter;
import com.gurug.education.viewmodel.ViewModelHome;

import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlansFragment extends Fragment implements LessonPlanAdapter.LessonPlanClickListener {

    private View mRootView;

    private LessonPlanAdapter mLessonPlanAdapter;

    @BindView(R.id.rv_lesson_plans)
    RecyclerView mRvLessonPlans;

    @BindView(R.id.iv_c_class_icon)
    ImageViewCircle mIvCClassIcon;

    @BindView(R.id.tv_subject_name)
    TextView mTvSubjectName;

    @BindView(R.id.tv_class_name)
    TextView mTvClassName;

    @BindView(R.id.tv_selected_topic)
    TextView mTvTopicName;

    @BindView(R.id.tv_medium_name)
    TextView mTvMediumName;

    @BindView(R.id.tv_select_topic)
    TextView mTopicSelect;

    @BindView(R.id.rl_empty)
    RelativeLayout mRlEmty;

    @Inject
    ViewModelHome mViewModelHome;

    private String mSelectedTopicName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_plans, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(TelemetryConstants.MODULE_PLAN, TelemetryConstants.SCREEN_PLAN_LIST, "view"));

        DaggerComponentFragment.builder()
                .componentApplication(((Application) Objects.requireNonNull(getActivity()).getApplication()).getComponent())
                .build().inject(this);

        setupHeader();
        setUpLessonPlans();
        if (!mTvTopicName.getText().toString().equalsIgnoreCase(getString(R.string.text_select_topic))) {
            getPlans();
        }
        getPlans();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadProgress(DownloadProgress downloadProgress) throws InterruptedException {
        //mPresenter.updateProgressBar(downloadProgress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentImportResponse(ContentImportResponse contentImportResponse) throws InterruptedException {
        //mPresenter.manageImportSuccess(contentImportResponse);
    }

    private void getPlans() {
        //mContentService = GenieService.getAsyncService().getContentService();
        /*

        //2. To download the content

        ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
        ContentImport contentImport = new ContentImport("do_2126385307857141761950", true, Utility.getExternalFilesDir(getActivity()).toString());
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
        });*/


        //1. Get The details of the content
        //ContentDetailsRequest.Builder requestBuilder = new ContentDetailsRequest.Builder().forContent("do_21228031946955980819").withFeedback().withContentAccess();

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


        if (TextUtils.isEmpty(AppPrefs.getSelectedTopic(getActivity()))) {
            mRlEmty.setVisibility(View.VISIBLE);
            mRvLessonPlans.setVisibility(View.GONE);
            mTopicSelect.setText(getString(R.string.text_please_select_the_topic_you_want_to_see_the_lesson_plan_for));
        } else {
            mRlEmty.setVisibility(View.GONE);
            mRvLessonPlans.setVisibility(View.VISIBLE);
            Utility.showProgress(getActivity());
            mViewModelHome.getLessonPlans(getPlanRequest()).observe(this, responseLessonPlan -> {
                Utility.hideProgress();
                if (ErrorHandler.handleError(getActivity(), responseLessonPlan)) {
                    if (responseLessonPlan != null && responseLessonPlan.getResult() != null && responseLessonPlan.getResult().getContent() != null && responseLessonPlan.getResult().getContent().size() > 0) {
                        mLessonPlanAdapter.update(mViewModelHome.getValidPlans(responseLessonPlan));

                        mRlEmty.setVisibility(View.GONE);
                        mRvLessonPlans.setVisibility(View.VISIBLE);
                    } else {
                        mLessonPlanAdapter.update(new ArrayList<>());
                        mRlEmty.setVisibility(View.VISIBLE);
                        mRvLessonPlans.setVisibility(View.GONE);
                        mTopicSelect.setText(getString(R.string.text_no_lessonplan_for_topic_selected));
                    }
                }
            });
        }
        //.update(Utility.getLessonPlans(getActivity(), null));
    }

    private RequestLessonPlan getPlanRequest() {
        if (TextUtils.isEmpty(AppPrefs.getSelectedTopic(getActivity()))) {
            mRlEmty.setVisibility(View.VISIBLE);
            mRvLessonPlans.setVisibility(View.GONE);
            mTopicSelect.setText(getString(R.string.text_please_select_the_topic_you_want_to_see_the_lesson_plan_for));
        } else {
            mRlEmty.setVisibility(View.GONE);
            mRvLessonPlans.setVisibility(View.VISIBLE);
        }
        RequestLessonPlan requestLessonPlan = new RequestLessonPlan();

        Filters filters = new Filters();
        ArrayList<String> contentType = new ArrayList<>();
        contentType.add("TeacherAid");

        ArrayList<String> status = new ArrayList<>();
        //status.add("Live");
        status.add("Live");

        ArrayList<String> objectType = new ArrayList<>();
        objectType.add("Content");

        ArrayList<String> board = new ArrayList<>();
        board.add(AppPrefs.getSelectedBoard(getActivity()));

        ArrayList<String> gradeLevel = new ArrayList<>();
        gradeLevel.add(AppPrefs.getSelectedClass(getActivity()));

        ArrayList<String> subject = new ArrayList<>();
        subject.add(AppPrefs.getSelectedSubject(getActivity()));

        ArrayList<String> medium = new ArrayList<>();
        medium.add(AppPrefs.getSelectedMedium(getActivity()));

        filters.setContentType(contentType);
        filters.setStatus(status);
        filters.setObjectType(objectType);
        filters.setBoard(board);
        filters.setGradeLevel(gradeLevel);
        filters.setSubject(subject);
        filters.setMedium(medium);
        if (!TextUtils.isEmpty(AppPrefs.getSelectedTopic(getActivity()))) {
            filters.setTopics(AppPrefs.getSelectedTopic(getActivity()));
        }

        Request request = new Request();
        request.setFilters(filters);

        ArrayList<String> fields = new ArrayList<>();
        fields.add("board");
        fields.add("subject");
        fields.add("channel");
        fields.add("description");
        fields.add("medium");
        fields.add("gradeLevel");
        fields.add("pedagogySteps");
        fields.add("identifier");
        fields.add("topics");
        fields.add("totalDuration");
        request.setFields(fields);
        requestLessonPlan.setRequest(request);

        return requestLessonPlan;
    }

    private void setupHeader() {
        if (!TextUtils.isEmpty(AppPrefs.getSelectedTopic(getActivity()))) {
            mTvTopicName.setText(AppPrefs.getSelectedTopic(getActivity()));
        } else {
            mTvTopicName.setText(getString(R.string.text_select_topic));
        }
        mTvSubjectName.setText(AppPrefs.getSelectedSubject(Objects.requireNonNull(getActivity())));
        mTvClassName.setText(AppPrefs.getSelectedClass(Objects.requireNonNull(getActivity())));
        mTvMediumName.setText(AppPrefs.getSelectedMedium(Objects.requireNonNull(getActivity())));
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(50)
                .height(50)
                .endConfig()
                .buildRect(AppPrefs.getSelectedSubject(Objects.requireNonNull(getActivity())).charAt(0) + "", ContextCompat.getColor(getActivity(), R.color.colorRed));
        mIvCClassIcon.setImageDrawable(drawable);
    }

    private void setUpLessonPlans() {
        mLessonPlanAdapter = new LessonPlanAdapter(getActivity(), this);
        mRvLessonPlans.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvLessonPlans.setAdapter(mLessonPlanAdapter);
    }

    @Override
    public void onClickQuickView(Content content) {
        Intent intent = new Intent(getActivity(), LessonPlanQuickViewActivity.class);
        intent.putExtra(IntentConstant.CONTENT, content);
        startActivity(intent);
    }

    @Override
    public void onClickDetailView(Content content) {
        Intent intent = new Intent(getActivity(), LessonPlanDetailViewActivity.class);
        intent.putExtra(IntentConstant.CONTENT, content);
        startActivity(intent);
    }

    @Override
    public void onClickBookMark(Content content, int pos) {
        ArrayList<Content> contents = new ArrayList<>();
        if (content.isBookMarked()) {
            content.setBookMarked(false);
        } else {
            content.setBookMarked(true);
        }
        contents.add(content);
        mViewModelHome.updateLessons(contents);
        mLessonPlanAdapter.updatePos(content, pos);
    }

    @OnClick(R.id.ib_edit)
    void onClickEditCurriculum() {
        startActivityForResult(new Intent(getActivity(), SelectClassActivity.class), 2);
    }

    @OnClick(R.id.ll_select_topic)
    void onClickSelectTopic() {
        Intent intent = new Intent(getActivity(), TopicSelectionActivity.class);
        startActivityForResult(intent, IntentConstant.REQUET_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.REQUET_CODE && resultCode == IntentConstant.RESULT_SUCCESS_CODE) {
            mSelectedTopicName = data.getStringExtra(IntentConstant.TOPIC);
            mTvTopicName.setText(Utility.toTitleCase(data.getStringExtra(IntentConstant.TOPIC)));
            AppPrefs.setSelectedTopic(getActivity(), Utility.toTitleCase(mSelectedTopicName));
            getPlans();
            //mLessonPlanAdapter.update(Utility.getLessonPlans(getActivity(), mTvTopicName.getText().toString()));
        } else if (requestCode == 2 && resultCode == IntentConstant.RESULT_SUCCESS_CODE) {
            AppPrefs.setSelectedTopic(getActivity(), "");
            setupHeader();
            getPlans();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupHeader();
    }
}
