
package com.gurug.education.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.request.lessonplans.Filters;
import com.gurug.education.data.model.request.lessonplans.Request;
import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.facetsearh.Values;
import com.gurug.education.data.model.response.frameworkdetail.Category;
import com.gurug.education.data.model.response.frameworkdetail.ResponseFramwork;
import com.gurug.education.data.model.response.frameworkdetail.Terms;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.utill.ErrorHandler;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.Utility;
import com.gurug.education.view.adapter.FrameworkSelectionAdapter;
import com.gurug.education.viewmodel.ViewModelSelectBoardMedium;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectFrameWorkDetailsActivity extends BaseActivity implements FrameworkSelectionAdapter.IFrameWorkClick {

    @BindView(R.id.rv_select_framework)
    RecyclerView mRvSelectFrameWork;

    @BindView(R.id.tv_title)
    TextView mtvTitle;

    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    @Inject
    ViewModelSelectBoardMedium mViewModelSelectBoardMedium;

    private FrameworkSelectionAdapter mFrameworkSelectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_frame_work);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        DaggerComponentActivity.builder()
                .componentApplication(((Application) getApplication()).getComponent())
                .build().inject(this);

        mtvTitle.setText(getIntent().getStringExtra(IntentConstant.TITLE));
        setUpFrameworkAdapter();
        getFrameWork();
    }

    private void setUpFrameworkAdapter() {
        mFrameworkSelectionAdapter = new FrameworkSelectionAdapter(SelectFrameWorkDetailsActivity.this);
        mRvSelectFrameWork.setLayoutManager(new LinearLayoutManager(SelectFrameWorkDetailsActivity.this));
        mRvSelectFrameWork.setAdapter(mFrameworkSelectionAdapter);
    }

    private void getFrameWork() {
        Utility.showProgress(SelectFrameWorkDetailsActivity.this);

        mViewModelSelectBoardMedium.getFacetsSearch(getPlanRequest()).observe(this, responseFacetSearch -> {
            if (responseFacetSearch != null && ErrorHandler.handleError(SelectFrameWorkDetailsActivity.this, responseFacetSearch)) {
                if (responseFacetSearch.getResult().getFacets() != null && responseFacetSearch.getResult().getFacets().size() > 0 && responseFacetSearch.getResult().getFacets().get(0).getValues().size() > 0) {
                    mFrameworkSelectionAdapter.update(responseFacetSearch.getResult().getFacets().get(0).getValues());
                    mLlEmpty.setVisibility(View.GONE);
                    mRvSelectFrameWork.setVisibility(View.VISIBLE);
                } else {
                    mLlEmpty.setVisibility(View.VISIBLE);
                    mRvSelectFrameWork.setVisibility(View.GONE);
                }
            } else {
                mLlEmpty.setVisibility(View.VISIBLE);
                mRvSelectFrameWork.setVisibility(View.GONE);
            }
            Utility.hideProgress();
        });

       /* mViewModelSelectBoardMedium.getFramwork().observe(this, responseFramwork -> {
            if (ErrorHandler.handleError(SelectFrameWorkDetailsActivity.this, responseFramwork)) {
                mFrameworkSelectionAdapter.update(getTerms(Objects.requireNonNull(responseFramwork)));
            }
            Utility.hideProgress();
        });*/
    }

    private RequestLessonPlan getPlanRequest() {

        RequestLessonPlan requestLessonPlan = new RequestLessonPlan();

        Filters filters = new Filters();
        ArrayList<String> contentType = new ArrayList<>();
        contentType.add("TeacherAid");

        filters.setContentType(contentType);

        Request request = new Request();
        request.setFilters(filters);

        request.setLimit(0);
        List<String> facest = new ArrayList<>();
        facest.add(getIntent().getStringExtra(IntentConstant.FRAMEWORK_TYPE));
        request.setFacets(facest);

        requestLessonPlan.setRequest(request);

        return requestLessonPlan;
    }


    private ArrayList<Terms> getTerms(ResponseFramwork responseFramwork) {
        ArrayList<Terms> terms = new ArrayList<>();
        for (Category category : responseFramwork.getResult().getFramework().getCategories()) {
            if (category.getCode() != null && category.getCode().equalsIgnoreCase(getIntent().getStringExtra(IntentConstant.FRAMEWORK_TYPE))) {
                terms = category.getTerms();
                break;
            }
        }
        return terms;
    }

    @OnClick(R.id.ib_close)
    public void onClickClose() {
        finish();
    }

    @Override
    public void onClickFrameWork(Values terms) {
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.FRAMEWORK, terms);
        intent.putExtra(IntentConstant.FRAMEWORK_TYPE, getIntent().getStringExtra(IntentConstant.FRAMEWORK_TYPE));
        setResult(IntentConstant.RESULT_SUCCESS_CODE, intent);
        finish();
    }
}
