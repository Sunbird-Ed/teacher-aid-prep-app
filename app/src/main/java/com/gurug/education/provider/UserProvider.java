package com.gurug.education.provider;

import com.gurug.education.BuildConfig;

import org.ekstep.genieproviders.user.AbstractUserProvider;

/**
 * Created on 1/6/17.
 * shriharsh
 */

public class UserProvider extends AbstractUserProvider {

    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;

    }

}
