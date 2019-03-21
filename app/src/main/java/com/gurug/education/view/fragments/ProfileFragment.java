package com.gurug.education.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.di.components.DaggerComponentFragment;
import com.gurug.education.utill.IntentConstant;
import com.gurug.education.utill.textdrawble.TextDrawable;
import com.gurug.education.utill.views.ImageViewCircle;
import com.gurug.education.view.activity.LessonPlanDetailViewActivity;
import com.gurug.education.view.activity.LessonPlanQuickViewActivity;
import com.gurug.education.view.activity.ProfileEditActivity;
import com.gurug.education.view.activity.SelectClassActivity;
import com.gurug.education.view.adapter.UserPrefLessonPlanAdapter;
import com.gurug.education.viewmodel.ViewModelHome;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements UserPrefLessonPlanAdapter.LessonPlanClickListener {

    @BindView(R.id.tl_profile)
    TabLayout mTlProfile;

    @BindView(R.id.rv_user_prefs)
    RecyclerView mRvUserPrefs;

    @BindView(R.id.tv_subject_name)
    TextView mTvSubjectName;

    @BindView(R.id.tv_class)
    TextView mTvClassName;

    @BindView(R.id.tv_board)
    TextView mTvBoardName;

    @BindView(R.id.tv_user_qualifications)
    TextView mTvUserQualification;

    @BindView(R.id.tv_user_name)
    TextView mTvUserName;

    @BindView(R.id.tv_selected_class)
    TextView mTvSelectedClassName;

    @BindView(R.id.iv_c_user_icon)
    ImageViewCircle mIvCUserIcon;

    private View mRootView;
    @Inject
    ViewModelHome mViewModelHome;

    private String mSelectedSubject, mSelectedGrade;

    private UserPrefLessonPlanAdapter mUserPrefLessonPlanAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSelectedSubject = AppPrefs.getSelectedSubject(getActivity());
        mSelectedGrade = AppPrefs.getSelectedClass(getActivity());
    }

    private void init() {
        ButterKnife.bind(this, mRootView);
        DaggerComponentFragment.builder()
                .componentApplication(((Application) Objects.requireNonNull(getActivity()).getApplication()).getComponent())
                .build().inject(this);

        if (mTlProfile.getTabCount() < 1) {
            mTlProfile.addTab(mTlProfile.newTab());
            mTlProfile.addTab(mTlProfile.newTab());
        }
/*

        updateDone();
*/

        setUpUserPrefs();
        mTlProfile.getTabAt(0).setText("BookMarks");
        mTlProfile.getTabAt(1).setText("Completed");

        setUpTabChangeListener();

        if (mTlProfile.getTabCount() > 0) {
            mTlProfile.getTabAt(0).select();
            updateBookmarked();
        }

        String selectedClass = AppPrefs.getSelectedClass(Objects.requireNonNull(getActivity())) + " " + AppPrefs.getSelectedSubject(Objects.requireNonNull(getActivity()));
        mTvSelectedClassName.setText(selectedClass);
    }

    private void updateDone() {

        mViewModelHome.getMarkAsDonePlans(AppPrefs.getSelectedBoard(Objects.requireNonNull(getActivity())), AppPrefs.getSelectedMedium(getActivity()), mSelectedGrade, mSelectedSubject).observe(this, contents -> {
            if (contents != null) {
                mUserPrefLessonPlanAdapter.update(new ArrayList<>(), true);
                mUserPrefLessonPlanAdapter.update(contents, false);
            }
        });
    }

    private void updateBookmarked() {
        mViewModelHome.getBookMarkedPlans(AppPrefs.getSelectedBoard(Objects.requireNonNull(getActivity())), AppPrefs.getSelectedMedium(getActivity()), mSelectedGrade, mSelectedSubject).observe(this, contents -> {
            if (contents != null) {
                mUserPrefLessonPlanAdapter.update(new ArrayList<>(), false);
                mUserPrefLessonPlanAdapter.update(contents, true);
            }
        });
    }

    private void setUpTabChangeListener() {
        mTlProfile.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    updateBookmarked();
                } else {
                    updateDone();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /*if (tab.getPosition() == 0) {
                    updateBookmarked();
                } else {
                    updateDone();
                }*/
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpUserPrefs() {
        mRvUserPrefs.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserPrefLessonPlanAdapter = new UserPrefLessonPlanAdapter(getActivity(), this);
        mRvUserPrefs.setAdapter(mUserPrefLessonPlanAdapter);
    }

    @OnClick(R.id.ib_edit)
    void onClickEditProfile() {
        startActivity(new Intent(getActivity(), ProfileEditActivity.class));
    }

    @OnClick(R.id.ll_select_class)
    void onClickSelectClass() {
        Intent intent = new Intent(getActivity(), SelectClassActivity.class);
        intent.putExtra(IntentConstant.IS_FROM_PROFILE, true);
        startActivityForResult(intent, IntentConstant.REQUET_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        if (!TextUtils.isEmpty(AppPrefs.getSelectedUserName(Objects.requireNonNull(getActivity())))) {
            mTvUserName.setText(AppPrefs.getSelectedUserName(Objects.requireNonNull(getActivity())));
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect(AppPrefs.getSelectedUserName(Objects.requireNonNull(getActivity())).charAt(0) + "", ContextCompat.getColor(getActivity(), R.color.colorRed));
            mIvCUserIcon.setImageDrawable(drawable);
        } else {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect("N", ContextCompat.getColor(getActivity(), R.color.colorRed));
            mIvCUserIcon.setImageDrawable(drawable);
        }
        if (!TextUtils.isEmpty(AppPrefs.getSelectedUserQualification(Objects.requireNonNull(getActivity())))) {
            mTvUserQualification.setText(AppPrefs.getSelectedUserQualification(Objects.requireNonNull(getActivity())));
        }
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
        updateBookmarked();
        //mLessonPlanAdapter.updatePos(content, pos);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.REQUET_CODE && resultCode == IntentConstant.RESULT_SUCCESS_CODE) {
            mSelectedGrade = data.getStringExtra("grade");
            mSelectedSubject = data.getStringExtra("subject");
            init();
        }
    }
}
