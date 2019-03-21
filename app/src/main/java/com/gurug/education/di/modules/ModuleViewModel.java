
package com.gurug.education.di.modules;


import android.arch.lifecycle.AndroidViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleViewModel {
    private final AndroidViewModel mBasePresenter;

    public ModuleViewModel(AndroidViewModel presenter) {
        mBasePresenter = presenter;
    }

    @Provides
    AndroidViewModel providePresenter() {
        return mBasePresenter;
    }
}
