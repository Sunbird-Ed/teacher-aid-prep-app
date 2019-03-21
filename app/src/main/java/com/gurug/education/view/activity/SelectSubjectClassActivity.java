package com.gurug.education.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gurug.education.R;
import com.gurug.education.data.model.response.facetsearh.Values;
import com.gurug.education.data.model.response.frameworkdetail.Terms;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.Utility;
import com.gurug.education.view.adapter.SelectedTeachAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectSubjectClassActivity extends BaseActivity implements SelectedTeachAdapter.OnClickSelectedTeach {

    @BindView(R.id.ll_select_class)
    LinearLayout mLlSelectClass;

    @BindView(R.id.tv_select_class)
    TextView mTvSelectClass;

    @BindView(R.id.ll_select_subject)
    LinearLayout mLlSelectSubject;

    @BindView(R.id.tv_select_subject)
    TextView mTvSelectSubject;

    @BindView(R.id.rv_selected_teach)
    RecyclerView mRvSelectedTeach;

    @BindView(R.id.ll_selected_teach)
    LinearLayout mLlSelectedTeach;

    @BindView(R.id.ll_select_class_subject_drop)
    LinearLayout mLlSelectedSubjectDrop;

    @BindView(R.id.bt_next)
    Button mBtNext;

    @BindView(R.id.bt_add_more)
    Button mBtAddMore;

    private SelectedTeachAdapter mSelectedTeachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject_class);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        setUpSelectedTeach();
        checkEnableDisable();
    }

    private void setUpSelectedTeach() {
        mSelectedTeachAdapter = new SelectedTeachAdapter(SelectSubjectClassActivity.this);
        mRvSelectedTeach.setLayoutManager(new LinearLayoutManager(SelectSubjectClassActivity.this));
        mRvSelectedTeach.setAdapter(mSelectedTeachAdapter);
    }

    private void checkEnableDisable() {
        if (AppPrefs.getAllTeach(SelectSubjectClassActivity.this).size() > 0) {
            mBtNext.setEnabled(true);
            mLlSelectedTeach.setVisibility(View.VISIBLE);
            //mLlSelectedSubjectDrop.setAlpha(.2f);
            //mBtAddMore.setVisibility(View.VISIBLE);
            //mLlSelectClass.setClickable(false);
            //mLlSelectSubject.setClickable(false);
        } else {
            mBtNext.setEnabled(false);
            //mLlSelectedSubjectDrop.setAlpha(1);
            mLlSelectedTeach.setVisibility(View.GONE);
            //mBtAddMore.setVisibility(View.GONE);
            mLlSelectClass.setClickable(true);
            mLlSelectSubject.setClickable(true);
        }
        mSelectedTeachAdapter.update(new ArrayList<>(AppPrefs.getAllTeach(SelectSubjectClassActivity.this).values()));
    }

    @OnClick(R.id.ll_select_class)
    void onClickSelectBoard() {
        Intent intent = new Intent(SelectSubjectClassActivity.this, SelectFrameWorkDetailsActivity.class);
        intent.putExtra(IntentConstant.FRAMEWORK_TYPE, AppConstants.CLASS);
        intent.putExtra(IntentConstant.TITLE, mTvSelectClass.getText());
        startActivityForResult(intent, IntentConstant.REQUET_CODE);
    }

    @OnClick(R.id.ll_select_subject)
    void onClickSelectMedium() {
        Intent intent = new Intent(SelectSubjectClassActivity.this, SelectFrameWorkDetailsActivity.class);
        intent.putExtra(IntentConstant.FRAMEWORK_TYPE, AppConstants.SUBJECT);
        intent.putExtra(IntentConstant.TITLE, mTvSelectSubject.getText());
        startActivityForResult(intent, IntentConstant.REQUET_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.REQUET_CODE && resultCode == IntentConstant.RESULT_SUCCESS_CODE) {
            assert data != null;
            Values terms = data.getParcelableExtra(IntentConstant.FRAMEWORK);
            if (data.getStringExtra(IntentConstant.FRAMEWORK_TYPE).equalsIgnoreCase(AppConstants.CLASS)) {

                mTvSelectClass.setText(Utility.toTitleCase(terms.getName()));
                AppPrefs.setSelectedClass(SelectSubjectClassActivity.this, Utility.toTitleCase(terms.getName()));
            } else {
                mTvSelectSubject.setText(Utility.toTitleCase(terms.getName()));
                AppPrefs.setSelectedSubject(SelectSubjectClassActivity.this, Utility.toTitleCase(terms.getName()));
            }

            if (!mTvSelectClass.getText().toString().equalsIgnoreCase(getString(R.string.text_select_class))
                    && !mTvSelectSubject.getText().toString().equalsIgnoreCase(getString(R.string.text_select_subject))) {
                AppPrefs.addTeach(SelectSubjectClassActivity.this, mTvSelectClass.getText().toString(), mTvSelectSubject.getText().toString());
                mTvSelectSubject.setText(getString(R.string.text_select_subject));
                mTvSelectClass.setText(getString(R.string.text_select_class));
                checkEnableDisable();
            }
        }
    }

    @OnClick(R.id.ib_back)
    void onClickBack() {
        finish();
    }

    @OnClick(R.id.bt_next)
    void onClickNext() {
        if (!getIntent().getBooleanExtra(IntentConstant.IS_FROM_CLASS_LIST,false)) {
            AppPrefs.setState(SelectSubjectClassActivity.this, AppPrefs.IS_FRAME_WORK_SELECTED);
            Intent intent = new Intent(SelectSubjectClassActivity.this, HomeScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            finish();
        }
    }

    @OnClick(R.id.bt_add_more)
    void onClickAddMore() {
        mLlSelectedSubjectDrop.setAlpha(1);
        mLlSelectClass.setClickable(true);
        mLlSelectSubject.setClickable(true);
    }

    @Override
    public void onClickSelectedTeach(String teach) {
        AppPrefs.removeTeach(SelectSubjectClassActivity.this, teach);
        checkEnableDisable();
    }
}
