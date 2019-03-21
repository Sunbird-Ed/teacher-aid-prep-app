package com.gurug.education;

import com.facebook.stetho.Stetho;
import com.gurug.education.di.components.ComponentApplication;
import com.gurug.education.di.components.DaggerComponentApplication;
import com.gurug.education.di.modules.ModuleApplication;

import org.ekstep.genieservices.GenieService;

public class Application extends android.app.Application {
    public static Application sApplication;
    private ComponentApplication mComponentApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        GenieService.init(this, "com.gurug.education");

        //GenieService.init(this, "org.sunbird.teacheraid");

        //GenieSDK.init(this, "org.ekstep.genieservices");
        sApplication = this;

        mComponentApplication = DaggerComponentApplication.builder()
                .moduleApplication(new ModuleApplication(this)).build();
        mComponentApplication.inject(this);
    }

    public ComponentApplication getComponent() {
        return mComponentApplication;
    }

}
