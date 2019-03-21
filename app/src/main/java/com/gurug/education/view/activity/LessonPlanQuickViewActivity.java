package com.gurug.education.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.ErrorHandler;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.Log;
import com.gurug.education.utill.Utility;
import com.gurug.education.view.adapter.QuickViewAdapter;
import com.gurug.education.viewmodel.ViewModelQuickView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LessonPlanQuickViewActivity extends BaseActivity implements QuickViewAdapter.IOnClickQuickView {

    @BindView(R.id.tv_subject_name)
    TextView mTvSubjectName;

    @BindView(R.id.tv_class_name)
    TextView mTvClassName;

    @BindView(R.id.tv_medium_name)
    TextView mTvMediumName;

    @BindView(R.id.tv_plan_title)
    TextView mTvPlanTitle;

    @BindView(R.id.tv_plan_duration)
    TextView mTvPlanDuration;

    @BindView(R.id.rv_quick_view_plan_detail)
    RecyclerView mRvQuickView;

    private QuickViewAdapter mQuickViewAdapter;

    private Content mContent;

    @Inject
    ViewModelQuickView mViewModelQuickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_plan_quick_view);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        DaggerComponentActivity.builder()
                .componentApplication(((Application) getApplication()).getComponent())
                .build().inject(this);

        mContent = getIntent().getParcelableExtra(IntentConstant.CONTENT);
        setupHeader();
        setUpQuickViewMethods();

        getMothodDetails();

    }

    private void getMothodDetails() {
        Utility.showProgress(LessonPlanQuickViewActivity.this);
        mViewModelQuickView.getMethodDetails(mContent.getIdentifier()).observe(this, responseMethodDetail -> {
            Utility.hideProgress();
            if (ErrorHandler.handleError(LessonPlanQuickViewActivity.this, responseMethodDetail)) {
                Log.i("responseMethodDetail", responseMethodDetail.toString());
                if (responseMethodDetail.getResult() != null && responseMethodDetail.getResult().getContent() != null && responseMethodDetail.getResult().getContent().getChildren() != null) {
                    for (ChildrenMethod childrenMethod : responseMethodDetail.getResult().getContent().getChildren()) {
                        childrenMethod.setPedagogyId(mContent.getIdentifier());
                    }
                    mViewModelQuickView.updateChildren(responseMethodDetail.getResult().getContent().getChildren());
                    mQuickViewAdapter.update(responseMethodDetail);
                }
            }
        });
    }

    private void setUpQuickViewMethods() {
        mQuickViewAdapter = new QuickViewAdapter(LessonPlanQuickViewActivity.this);
        mRvQuickView.setLayoutManager(new LinearLayoutManager(LessonPlanQuickViewActivity.this));
        mRvQuickView.setAdapter(mQuickViewAdapter);
        //mQuickViewAdapter.update(mContent.getTeachingMethods());
    }

    @SuppressLint("SetTextI18n")
    private void setupHeader() {
        mTvPlanTitle.setText(mContent.getTopic());
        mTvPlanDuration.setText(mContent.getTotalDuration() + AppConstants.SPACE + getString(R.string.text_mins));
        mTvSubjectName.setText(AppPrefs.getSelectedSubject(LessonPlanQuickViewActivity.this));
        mTvClassName.setText(AppPrefs.getSelectedClass(LessonPlanQuickViewActivity.this));
        mTvMediumName.setText(AppPrefs.getSelectedMedium(LessonPlanQuickViewActivity.this));
    }

    @OnClick(R.id.ib_back)
    void onClickBack() {
        finish();
    }

    @Override
    public void onClickMarkAsDone() {
        mContent.setDone(true);
        mViewModelQuickView.updateLessonPlan(mContent);
        finish();
    }

    @Override
    public void onClickMethod(ChildrenMethod teachingMethod, int pos) {
        Intent intent = new Intent(LessonPlanQuickViewActivity.this, TeachingMethodDetailActivity.class);
        intent.putExtra(IntentConstant.CONTENT, mContent);
        intent.putExtra(IntentConstant.POS, pos);
        startActivity(intent);
    }
}
