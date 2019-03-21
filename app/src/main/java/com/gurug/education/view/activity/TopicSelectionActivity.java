package com.gurug.education.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.request.lessonplans.Filters;
import com.gurug.education.data.model.request.lessonplans.Request;
import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.facetsearh.Values;
import com.gurug.education.data.model.response.frameworkdetail.FrameWorkChild;
import com.gurug.education.data.model.response.frameworkdetail.Terms;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.utill.ErrorHandler;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.Utility;
import com.gurug.education.utill.views.multilevelview.MultiLevelRecyclerView;
import com.gurug.education.utill.views.multilevelview.models.Item;
import com.gurug.education.utill.views.multilevelview.models.RecyclerViewItem;
import com.gurug.education.view.adapter.FrameworkSelectionAdapter;
import com.gurug.education.view.adapter.TopicSelectionAdapter;
import com.gurug.education.viewmodel.ViewModelTopicSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopicSelectionActivity extends BaseActivity implements FrameworkSelectionAdapter.IFrameWorkClick {

    @BindView(R.id.rv_select_topic)
    RecyclerView mRvSelectTopic;

    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;

    @Inject
    ViewModelTopicSelection mViewModelTopicSelection;

    private FrameworkSelectionAdapter mFrameworkSelectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        DaggerComponentActivity.builder()
                .componentApplication(((Application) getApplication()).getComponent())
                .build().inject(this);

        setUpTopics();
        getTopics();
    }

    private void getTopics() {
        Utility.showProgress(TopicSelectionActivity.this);
        /*mViewModelTopicSelection.getTopics().observe(this, response -> {
            if (response != null && ErrorHandler.handleError(TopicSelectionActivity.this, response)) {
                List<Terms> terms = mViewModelTopicSelection.getValidTopics(response);
                //mTopicSelectionAdapter.update(terms);
                Collections.sort(terms, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                updateTopics(terms);
            }
            Utility.hideProgress();
        });*/

        mViewModelTopicSelection.getFacetsSearch(getPlanRequest()).observe(this, responseFacetSearch -> {
            if (responseFacetSearch != null && ErrorHandler.handleError(TopicSelectionActivity.this, responseFacetSearch)) {
                if (responseFacetSearch.getResult().getFacets() != null && responseFacetSearch.getResult().getFacets().size() > 0&&responseFacetSearch.getResult().getFacets().get(0).getValues().size()>0) {
                    mFrameworkSelectionAdapter.update(responseFacetSearch.getResult().getFacets().get(0).getValues(),true);
                    mLlEmpty.setVisibility(View.GONE);
                    mRvSelectTopic.setVisibility(View.VISIBLE);
                } else {
                    mLlEmpty.setVisibility(View.VISIBLE);
                    mRvSelectTopic.setVisibility(View.GONE);
                }
            } else {
                mLlEmpty.setVisibility(View.VISIBLE);
                mRvSelectTopic.setVisibility(View.GONE);
            }
            Utility.hideProgress();
        });
    }

    private RequestLessonPlan getPlanRequest() {

        RequestLessonPlan requestLessonPlan = new RequestLessonPlan();

        Filters filters = new Filters();
        ArrayList<String> contentType = new ArrayList<>();
        contentType.add("TeacherAid");

        filters.setContentType(contentType);

        Request request = new Request();
        request.setFilters(filters);
        ArrayList<String> board = new ArrayList<>();
        board.add(AppPrefs.getSelectedBoard(TopicSelectionActivity.this));

        ArrayList<String> gradeLevel = new ArrayList<>();
        gradeLevel.add(AppPrefs.getSelectedClass(TopicSelectionActivity.this));

        ArrayList<String> subject = new ArrayList<>();
        subject.add(AppPrefs.getSelectedSubject(TopicSelectionActivity.this));

        ArrayList<String> medium = new ArrayList<>();
        medium.add(AppPrefs.getSelectedMedium(TopicSelectionActivity.this));

        filters.setContentType(contentType);
        filters.setBoard(board);
        filters.setGradeLevel(gradeLevel);
        filters.setSubject(subject);
        filters.setMedium(medium);

        request.setLimit(0);
        List<String> facest = new ArrayList<>();
        facest.add("topics");
        request.setFacets(facest);

        requestLessonPlan.setRequest(request);

        return requestLessonPlan;
    }

/*

    private void updateTopics(List<Terms> terms) {
        mRvSelectTopic.setLayoutManager(new LinearLayoutManager(TopicSelectionActivity.this));
        //List<Item> itemList = (List<Item>)recursivePopulateFakeData(0, terms.size());
        List<Item> itemList = (List<Item>) getTopics(terms);
        mTopicSelectionAdapter = new TopicSelectionAdapter(this, itemList, mRvSelectTopic);
        mRvSelectTopic.setAdapter(mTopicSelectionAdapter);
        //If you are handling the click on your own then you can
        // multiLevelRecyclerView.removeItemClickListeners();
        mRvSelectTopic.setToggleItemOnClick(false);
        mRvSelectTopic.setAccordion(false);

        mRvSelectTopic.openTill(0, 1, 2, 3);
    }

    private List<?> getTopics(List<Terms> terms) {
        List<RecyclerViewItem> itemList = new ArrayList<>();
        for (int i = 0; i < terms.size(); i++) {
            Item item = new Item(0);
            item.setText(terms.get(i).getName());
            item.setSecondText(terms.get(i).getName().toLowerCase());
            item.addChildren((List<RecyclerViewItem>) getChildren(1, terms.get(i).getChildren()));
            itemList.add(item);
        }

        return itemList;
    }

    private List<?> getChildren(int levelNumber, ArrayList<FrameWorkChild> children) {
        List<RecyclerViewItem> itemList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            Item item = new Item(levelNumber);
            item.setText(children.get(i).getName());
            item.setSecondText(children.get(i).getName().toLowerCase());
            item.addChildren((List<RecyclerViewItem>) getChildren(levelNumber + 1, children.get(i).getChildren()));
            itemList.add(item);
        }
        return itemList;
    }
*/

    private void setUpTopics() {
        mFrameworkSelectionAdapter = new FrameworkSelectionAdapter(TopicSelectionActivity.this);
        mRvSelectTopic.setLayoutManager(new LinearLayoutManager(TopicSelectionActivity.this));
        mRvSelectTopic.setAdapter(mFrameworkSelectionAdapter);
    }

    @OnClick(R.id.ib_close)
    void onClickClose() {
        finish();
    }


    @Override
    public void onClickFrameWork(Values terms) {
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.TOPIC, terms.getName());
        setResult(IntentConstant.RESULT_SUCCESS_CODE, intent);
        finish();

    }
}
