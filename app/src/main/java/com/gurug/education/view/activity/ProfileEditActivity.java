package com.gurug.education.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.gurug.education.R;
import com.gurug.education.data.repository.local.AppPrefs;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileEditActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.et_qualification)
    EditText mEtQualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(AppPrefs.getSelectedUserName(Objects.requireNonNull(ProfileEditActivity.this)))) {
            mEtName.setText(AppPrefs.getSelectedUserName(Objects.requireNonNull(ProfileEditActivity.this)));
        }
        if (!TextUtils.isEmpty(AppPrefs.getSelectedUserQualification(Objects.requireNonNull(ProfileEditActivity.this)))) {
            mEtQualification.setText(AppPrefs.getSelectedUserQualification(Objects.requireNonNull(ProfileEditActivity.this)));
        }
    }

    @OnClick(R.id.bt_save)
    void onClickSave() {
        AppPrefs.setSelectedUserName(ProfileEditActivity.this, mEtName.getText().toString());
        AppPrefs.setSelectedUserQualification(ProfileEditActivity.this, mEtQualification.getText().toString());
        finish();
    }

    @OnClick(R.id.ib_back)
    void onClickBack() {
        finish();
    }


}
