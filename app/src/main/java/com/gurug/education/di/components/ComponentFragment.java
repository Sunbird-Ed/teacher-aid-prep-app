package com.gurug.education.di.components;


import com.gurug.education.di.modules.ModuleFragment;
import com.gurug.education.di.scopes.PerActivity;
import com.gurug.education.view.fragments.PlansFragment;
import com.gurug.education.view.fragments.ProfileFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ComponentApplication.class, modules = ModuleFragment.class)
public interface ComponentFragment {


    void inject(ProfileFragment profileFragment);

    void inject(PlansFragment plansFragment);
}
