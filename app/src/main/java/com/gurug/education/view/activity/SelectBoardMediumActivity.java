package com.gurug.education.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.response.facetsearh.Values;
import com.gurug.education.data.model.response.frameworkdetail.Terms;
import com.gurug.education.data.model.response.framwork.Framework;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.Utility;
import com.gurug.education.viewmodel.ViewModelSelectBoardMedium;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectBoardMediumActivity extends BaseActivity {


    @BindView(R.id.ll_select_board)
    LinearLayout mLlSelectBoard;

    @BindView(R.id.tv_select_board)
    TextView mTvSelectBoard;

    @BindView(R.id.ll_select_medium)
    LinearLayout mLlSelectMedium;

    @BindView(R.id.tv_select_medium)
    TextView mTvSelectMedium;

    @BindView(R.id.ll_select_framework)
    LinearLayout mLlSelectFramework;

    @BindView(R.id.tv_select_framework)
    TextView mTvSelectFramework;

    @BindView(R.id.bt_next)
    Button mBtNext;

    @Inject
    ViewModelSelectBoardMedium mViewModelSelectBoardMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_board_medium);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        DaggerComponentActivity.builder()
                .componentApplication(((Application) getApplication()).getComponent())
                .build().inject(this);

        checkEnableDisable();
    }

    private void checkEnableDisable() {
        if (!TextUtils.isEmpty(AppPrefs.getSelectedBoard(SelectBoardMediumActivity.this))) {
            mTvSelectBoard.setText(Utility.toTitleCase(AppPrefs.getSelectedBoard(SelectBoardMediumActivity.this)));
        }
        if (!TextUtils.isEmpty(AppPrefs.getSelectedMedium(SelectBoardMediumActivity.this))) {
            mTvSelectMedium.setText(Utility.toTitleCase(AppPrefs.getSelectedMedium(SelectBoardMediumActivity.this)));
        }
        if (!TextUtils.isEmpty(AppPrefs.getSelectedFrameworkName(SelectBoardMediumActivity.this))) {
            mTvSelectFramework.setText(Utility.toTitleCase(AppPrefs.getSelectedFrameworkName(SelectBoardMediumActivity.this)));
        }
        if (!mTvSelectBoard.getText().toString().equalsIgnoreCase(getString(R.string.text_select_board))
                && !mTvSelectMedium.getText().toString().equalsIgnoreCase(getString(R.string.text_select_medium))
                && !mTvSelectFramework.getText().toString().equalsIgnoreCase(getString(R.string.text_select_framework))) {
            mBtNext.setEnabled(true);
        } else {
            mBtNext.setEnabled(false);
        }
    }

    @OnClick(R.id.ll_select_framework)
    void onClickSelectFrameWork() {
        Intent intent = new Intent(SelectBoardMediumActivity.this, SelectFrameWorkDetailsActivity.class);
        intent.putExtra(IntentConstant.FRAMEWORK_TYPE, AppConstants.BOARD);
        intent.putExtra(IntentConstant.TITLE, mTvSelectFramework.getText());
        startActivityForResult(intent, IntentConstant.REQUET_CODE);
    }

    @OnClick(R.id.ll_select_board)
    void onClickSelectBoard() {
        if (!mTvSelectFramework.getText().toString().equalsIgnoreCase(getString(R.string.text_select_framework))) {
            Intent intent = new Intent(SelectBoardMediumActivity.this, SelectFrameWorkDetailsActivity.class);
            intent.putExtra(IntentConstant.FRAMEWORK_TYPE, AppConstants.BOARD);
            intent.putExtra(IntentConstant.TITLE, mTvSelectBoard.getText());
            startActivityForResult(intent, IntentConstant.REQUET_CODE);
        } else {
            Toast.makeText(SelectBoardMediumActivity.this, getString(R.string.text_select_framework_first), Toast.LENGTH_LONG).show();

        }
    }

    @OnClick(R.id.ll_select_medium)
    void onClickSelectMedium() {
        if (!mTvSelectFramework.getText().toString().equalsIgnoreCase(getString(R.string.text_select_framework))) {
            Intent intent = new Intent(SelectBoardMediumActivity.this, SelectFrameWorkDetailsActivity.class);
            intent.putExtra(IntentConstant.FRAMEWORK_TYPE, AppConstants.MEDIUM);
            intent.putExtra(IntentConstant.TITLE, mTvSelectBoard.getText());
            startActivityForResult(intent, IntentConstant.REQUET_CODE);
        } else {
            Toast.makeText(SelectBoardMediumActivity.this, getString(R.string.text_select_framework_first), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.REQUET_CODE && resultCode == IntentConstant.RESULT_SUCCESS_CODE) {
            assert data != null;
            if (data.getStringExtra(IntentConstant.FRAMEWORK_TYPE).equalsIgnoreCase(AppConstants.BOARD)) {
                Values terms = data.getParcelableExtra(IntentConstant.FRAMEWORK);
                mTvSelectBoard.setText(terms.getName());
                mTvSelectFramework.setText(terms.getName());
                AppPrefs.setSelectedBoard(SelectBoardMediumActivity.this, Utility.toTitleCase(terms.getName()));
                AppPrefs.setSelectedFrameworkName(SelectBoardMediumActivity.this, Utility.toTitleCase(terms.getName()));
            } else if (data.getStringExtra(IntentConstant.FRAMEWORK_TYPE).equalsIgnoreCase(AppConstants.MEDIUM)) {
                Values terms = data.getParcelableExtra(IntentConstant.FRAMEWORK);
                mTvSelectMedium.setText(terms.getName());
                AppPrefs.setSelectedMedium(SelectBoardMediumActivity.this, Utility.toTitleCase(terms.getName()));
            } else {
                Values framework = data.getParcelableExtra(IntentConstant.FRAMEWORK);
                AppPrefs.setSelectedFramework(SelectBoardMediumActivity.this, Utility.toTitleCase(framework.getName()));
                AppPrefs.setSelectedFrameworkName(SelectBoardMediumActivity.this, Utility.toTitleCase(framework.getName()));
                mTvSelectBoard.setText(framework.getName());
                mTvSelectFramework.setText(framework.getName());
                AppPrefs.setSelectedBoard(SelectBoardMediumActivity.this, Utility.toTitleCase(framework.getName()));
                AppPrefs.setSelectedFrameworkName(SelectBoardMediumActivity.this, Utility.toTitleCase(framework.getName()));
            }
            checkEnableDisable();
        }
    }

    @OnClick(R.id.bt_next)
    void onClickNext() {
        Intent intent = new Intent(SelectBoardMediumActivity.this, SelectSubjectClassActivity.class);
        startActivity(intent);
    }

}
