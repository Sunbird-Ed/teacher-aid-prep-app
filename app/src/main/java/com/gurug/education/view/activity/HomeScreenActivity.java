package com.gurug.education.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gurug.education.R;
import com.gurug.education.view.fragments.PlansFragment;
import com.gurug.education.view.fragments.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeScreenActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;

    @BindView(R.id.bn_home_screen)
    BottomNavigationView mBnHomeScreen;

    private Fragment mFragment;
    private FragmentManager mFragmentManager;

    private MenuItem mMiLastChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        mBnHomeScreen.setOnNavigationItemSelectedListener(this);
        mBnHomeScreen.setSelectedItemId(R.id.action_plans);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        boolean ret = true;
        switch (menuItem.getItemId()) {
            case R.id.action_plans:
                mMiLastChecked = menuItem;
                if (!(mFragment instanceof PlansFragment)) {
                    mFragment = new PlansFragment();
                }
                break;

            case R.id.action_scan:

                if (mMiLastChecked != null) {
                    mMiLastChecked.setChecked(true);
                }
                ret = false;

                Toast.makeText(HomeScreenActivity.this, "Coming soon!", Toast.LENGTH_LONG).show();

               /*
                Intent intent = new Intent(HomeScreenActivity.this, QrScanActivity.class);
                startActivityForResult(intent, IntentConstant.REQUET_CODE);*/
                break;

            case R.id.action_profile:
                mMiLastChecked = menuItem;
                if (!(mFragment instanceof ProfileFragment)) {
                    mFragment = new ProfileFragment();
                }
                break;
        }

        if (!mFragment.isAdded()) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            /*ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
                    R.animator.fragment_slide_left_exit,
                    R.animator.fragment_slide_right_enter,
                    R.animator.fragment_slide_right_exit);*/
            ft.replace(R.id.fl_container
                    , mFragment, mFragment.getTag());
            ft.commit();
        }
        return ret;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.fb_scan)
    public void onClickScan() {
        Toast.makeText(HomeScreenActivity.this, "Coming soon!", Toast.LENGTH_LONG).show();
/*
        Intent intent = new Intent(HomeScreenActivity.this, QrScanActivity.class);
        startActivityForResult(intent, IntentConstant.REQUET_CODE);*/
    }
}
