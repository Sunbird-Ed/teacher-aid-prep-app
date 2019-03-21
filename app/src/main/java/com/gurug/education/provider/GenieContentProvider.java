package com.gurug.education.provider;

import com.gurug.education.BuildConfig;

import org.ekstep.genieproviders.content.AbstractContentProvider;

/**
 * Created on 8/6/17.
 * shriharsh
 */

public class GenieContentProvider extends AbstractContentProvider {
    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
