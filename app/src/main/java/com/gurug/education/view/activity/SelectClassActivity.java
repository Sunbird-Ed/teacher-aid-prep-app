package com.gurug.education.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gurug.education.R;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.view.adapter.SelectClassAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectClassActivity extends BaseActivity implements SelectClassAdapter.OnClickClassItem {

    @BindView(R.id.rv_select_class)
    RecyclerView mRvSelectClasses;

    private SelectClassAdapter mSelectClassAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpClasses();
    }

    private void setUpClasses() {
        mSelectClassAdapter = new SelectClassAdapter(SelectClassActivity.this);
        mRvSelectClasses.setLayoutManager(new LinearLayoutManager(SelectClassActivity.this));
        mRvSelectClasses.setAdapter(mSelectClassAdapter);
        mSelectClassAdapter.update(new ArrayList<>(AppPrefs.getAllTeach(SelectClassActivity.this).values()));
    }

    @OnClick(R.id.ib_back)
    void onClickBack() {
        finish();
    }

    @OnClick(R.id.ib_close)
    void onClickAddClasses() {
        Intent intent = new Intent(SelectClassActivity.this, SelectSubjectClassActivity.class);
        intent.putExtra(IntentConstant.IS_FROM_CLASS_LIST, true);
        startActivity(intent);
    }

    @Override
    public void onClickClass(String classes) {
        if (!getIntent().getBooleanExtra(IntentConstant.IS_FROM_PROFILE, false)) {
            AppPrefs.setSelectedClass(SelectClassActivity.this, classes.split(AppConstants.COLON)[0]);
            AppPrefs.setSelectedSubject(SelectClassActivity.this, classes.split(AppConstants.COLON)[1]);
        }
        Intent intent = new Intent();
        intent.putExtra("grade", classes.split(AppConstants.COLON)[0]);
        intent.putExtra("subject", classes.split(AppConstants.COLON)[1]);
        setResult(IntentConstant.RESULT_SUCCESS_CODE, intent);
        finish();
    }
}
