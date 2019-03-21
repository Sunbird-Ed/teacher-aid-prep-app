package com.gurug.education.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.response.framwork.Framework;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.utill.ErrorHandler;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.Utility;
import com.gurug.education.view.adapter.FrameworkAdapter;
import com.gurug.education.viewmodel.ViewModelFramework;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectFrameWorkActivity extends BaseActivity implements FrameworkAdapter.IFrameWorkClick {

    @BindView(R.id.rv_select_framework)
    RecyclerView mRvSelectFrameWork;

    @BindView(R.id.tv_title)
    TextView mtvTitle;

    @Inject
    ViewModelFramework mViewModelFramework;

    private FrameworkAdapter mFrameworkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_frame_work2);
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

    private void getFrameWork() {
        Utility.showProgress(SelectFrameWorkActivity.this);
        mViewModelFramework.getFramwork().observe(this, responseFramework -> {
            if (responseFramework != null && ErrorHandler.handleError(SelectFrameWorkActivity.this, responseFramework)) {
                mFrameworkAdapter.update(responseFramework.getResult().getChannel().getFrameworks());
            }
            Utility.hideProgress();
        });
    }

    private void setUpFrameworkAdapter() {
        mFrameworkAdapter = new FrameworkAdapter(SelectFrameWorkActivity.this);
        mRvSelectFrameWork.setLayoutManager(new LinearLayoutManager(SelectFrameWorkActivity.this));
        mRvSelectFrameWork.setAdapter(mFrameworkAdapter);
    }

    @Override
    public void onClickFrameWork(Framework framework) {
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.FRAMEWORK, framework);
        intent.putExtra(IntentConstant.FRAMEWORK_TYPE, getIntent().getStringExtra(IntentConstant.FRAMEWORK_TYPE));
        setResult(IntentConstant.RESULT_SUCCESS_CODE, intent);
        finish();
    }

    @OnClick(R.id.ib_close)
    void onClickClose() {
        finish();
    }
}
