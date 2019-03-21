package com.gurug.education.provider;

import com.gurug.education.BuildConfig;

import org.ekstep.genieproviders.telemetry.AbstractTelemetryProvider;

/**
 * Created on 1/6/17.
 * shriharsh
 */

public class TelemetryProvider extends AbstractTelemetryProvider {


    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
