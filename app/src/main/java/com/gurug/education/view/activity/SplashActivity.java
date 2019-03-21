package com.gurug.education.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.gurug.education.Application;
import com.gurug.education.R;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.di.components.DaggerComponentActivity;
import com.gurug.education.viewmodel.ViewModelSplash;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {


    @Inject
    ViewModelSplash mViewModelSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        DaggerComponentActivity.builder()
                .componentApplication(((Application) getApplication()).getComponent())
                .build().inject(this);


        Thread timerThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    navigate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timerThread.start();

        //mViewModelSplash.getSettings();
    }

    private void navigate() {
        if (AppPrefs.isHelpDone(SplashActivity.this)) {

            switch (AppPrefs.getState(SplashActivity.this)) {
                case AppPrefs.IS_FRESH_START:
                    startActivity(new Intent(SplashActivity.this, SelectBoardMediumActivity.class));
                    finish();
                    break;
                case AppPrefs.IS_FRAME_WORK_SELECTED:
                    startActivity(new Intent(SplashActivity.this, HomeScreenActivity.class));
                    finish();
                    break;
            }
        } else {
            startActivity(new Intent(SplashActivity.this, HelpActivity.class));
            finish();
        }
    }
}
