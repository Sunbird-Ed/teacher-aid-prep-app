package com.gurug.education.provider;

import com.gurug.education.BuildConfig;

import org.ekstep.genieproviders.summarizer.AbstractSummarizerProvider;

/**
 * Created on 9/6/17.
 * shriharsh
 */

public class SummarizerProvider extends AbstractSummarizerProvider {
    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
