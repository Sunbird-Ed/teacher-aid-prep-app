

package com.gurug.education.di.modules;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleFragment {
    private Fragment mFragment;

    public ModuleFragment(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

}
