package com.gurug.education.provider;

import com.gurug.education.BuildConfig;

import org.ekstep.genieproviders.partner.AbstractPartnerProvider;

/**
 * Created on 8/6/17.
 * shriharsh
 */

public class PartnerProvider extends AbstractPartnerProvider {
    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
