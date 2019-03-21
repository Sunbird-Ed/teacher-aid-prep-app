/*
 * Copyright (c) 2018. McAfee
 * All Rights Reserved
 *
 */

package com.gurug.education.utill;


import android.content.Context;

import com.gurug.education.R;
import com.gurug.education.data.model.response.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorHandler {

    public static String getErrorMessage(Throwable throwable, Context context) {
        if (throwable instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
            try {
                if (responseBody == null)
                    return context.getString(R.string.error_generic);
                JSONObject jsonObject = new JSONObject(responseBody.string());
                return jsonObject.getString("message");
            } catch (Exception e) {
                return context.getString(R.string.error_generic);
            }

        } else if (throwable instanceof SocketTimeoutException) {
            return context.getString(R.string.error_time_out);
        } else if (throwable instanceof IOException) {
            return context.getString(R.string.error_no_internet);
        } else {
            return null;
        }
    }

    public static boolean handleError(Context context, Response response) {
        if (response != null && response.getHttpError() != null) {
            Utility.showSnackBar(context, getErrorMessage(response.getHttpError(), context));
            return false;
        } else {
            if (response == null) {
                Utility.showSnackBar(context, context.getString(R.string.error_generic));
                return false;
            } else {
                return true;
            }
        }
    }

}
