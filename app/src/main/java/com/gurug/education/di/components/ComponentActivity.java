package com.gurug.education.di.components;


import com.gurug.education.di.modules.ModuleActivity;
import com.gurug.education.di.scopes.PerActivity;
import com.gurug.education.view.activity.LessonPlanDetailViewActivity;
import com.gurug.education.view.activity.LessonPlanQuickViewActivity;
import com.gurug.education.view.activity.SelectBoardMediumActivity;
import com.gurug.education.view.activity.SelectFrameWorkActivity;
import com.gurug.education.view.activity.SelectFrameWorkDetailsActivity;
import com.gurug.education.view.activity.SplashActivity;
import com.gurug.education.view.activity.TeachingMethodDetailActivity;
import com.gurug.education.view.activity.TopicSelectionActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ComponentApplication.class, modules = ModuleActivity.class)
public interface ComponentActivity {

    void inject(SelectBoardMediumActivity selectBoardMediumActivity);

    void inject(SelectFrameWorkDetailsActivity selectFrameWorkDetailsActivity);

    void inject(LessonPlanQuickViewActivity lessonPlanQuickViewActivity);

    void inject(LessonPlanDetailViewActivity lessonPlanDetailViewActivity);

    void inject(SelectFrameWorkActivity selectFrameWorkActivity);

    void inject(TeachingMethodDetailActivity teachingMethodDetailActivity);

    void inject(TopicSelectionActivity topicSelectionActivity);

    void inject(SplashActivity splashActivity);

}
