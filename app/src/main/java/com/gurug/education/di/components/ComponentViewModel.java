
package com.gurug.education.di.components;

import com.gurug.education.di.modules.ModuleViewModel;
import com.gurug.education.di.scopes.PerActivity;
import com.gurug.education.viewmodel.ViewModelDetailView;
import com.gurug.education.viewmodel.ViewModelFramework;
import com.gurug.education.viewmodel.ViewModelHome;
import com.gurug.education.viewmodel.ViewModelQuickView;
import com.gurug.education.viewmodel.ViewModelSelectBoardMedium;
import com.gurug.education.viewmodel.ViewModelSplash;
import com.gurug.education.viewmodel.ViewModelTopicSelection;

import dagger.Component;

@PerActivity
@Component(dependencies = ComponentApplication.class, modules = ModuleViewModel.class)
public interface ComponentViewModel {

    void inject(ViewModelSelectBoardMedium viewModelSelectBoardMedium);

    void inject(ViewModelHome viewModelHome);

    void inject(ViewModelQuickView viewModelQuickView);

    void inject(ViewModelDetailView viewModelDetailView);

    void inject(ViewModelFramework viewModelFramework);

    void inject(ViewModelTopicSelection viewModelTopicSelection);

    void inject(ViewModelSplash viewModelSplash);
}
