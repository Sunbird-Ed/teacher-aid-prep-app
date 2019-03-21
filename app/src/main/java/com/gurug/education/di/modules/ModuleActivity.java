/*
 * Copyright (c) 2018. McAfee
 * All Rights Reserved
 *
 */

package com.gurug.education.di.modules;

import android.app.Activity;
import android.content.Context;


import com.gurug.education.di.scopes.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleActivity {

    private final Activity mActivity;

    public ModuleActivity(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

}
