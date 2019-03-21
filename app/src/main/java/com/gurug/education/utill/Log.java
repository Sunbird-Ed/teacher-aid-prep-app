package com.gurug.education.utill;

import com.gurug.education.BuildConfig;


public class Log {

    private static final boolean LOG = BuildConfig.LOG;

    public static void i(String tag, String string) {
        if (tag != null && string != null)
            if (LOG) android.util.Log.i(tag, string);
    }

    public static void e(String tag, String string) {
        if (tag != null && string != null)
            if (LOG) android.util.Log.e(tag, string);
    }

    public static void d(String tag, String string) {
        if (tag != null && string != null)
            if (LOG) android.util.Log.d(tag, string);
    }

    public static void v(String tag, String string) {
        if (tag != null && string != null)
            if (LOG) android.util.Log.v(tag, string);
    }

    public static void w(String tag, String string) {
        if (tag != null && string != null)
            if (LOG) android.util.Log.w(tag, string);
    }

}
