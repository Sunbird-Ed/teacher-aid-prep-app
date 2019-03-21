
/*
 * Copyright (c) 2018. McAfee
 * All Rights Reserved
 *
 */

package com.gurug.education.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}
