package com.gurug.education.data.repository.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gurug.education.Application;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.view.activity.SelectSubjectClassActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

public class AppPrefs {

    public static final int IS_FRESH_START = 0;
    public static final int IS_FRAME_WORK_SELECTED = 1;

    private static final String STATE = "state";
    private static final String IS_HELP_DONE = "help";


    private static final String SELECTED_FRAMEWORK_ID = "framwork";
    private static final String SELECTED_FRAMEWORK_NAME = "framwork_name";
    private static final String SELECTED_BOARD = "board";
    private static final String SELECTED_MEDIUM = "medium";
    private static final String SELECTED_CLASS = "class";
    private static final String SELECTED_TOPIC = "topic";
    private static final String SELECTED_SUBJECT = "subject";
    private static final String SELECTED_USER_NAME = "username";
    private static final String SELECTED_USER_QUALIFICATION = "userqualificatio";
    private static final String SELECTED_TEACH = "teach";
    private static final String SETTINGS_CUSTODIAN_ORG_ID = "custodianOrgId";

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getBoolean(permission, true);
    }

    public static int getState(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getInt(STATE, 0);
    }

    public static void setState(Context context, int state) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putInt(STATE, state).apply();
    }

    public static boolean isHelpDone(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getBoolean(IS_HELP_DONE, true);
    }

    public static void setHelpDone(Context context, boolean isHelpDone) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(IS_HELP_DONE, isHelpDone).apply();
    }

    public static String getSelectedBoard(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_BOARD, "");
    }

    public static void setSelectedBoard(Context context, String board) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_BOARD, board).apply();
    }

    public static String getSelectedFramework(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_FRAMEWORK_ID, "");
    }

    public static void setSelectedFramework(Context context, String board) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_FRAMEWORK_ID, board).apply();
    }

    public static String getSelectedFrameworkName(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_FRAMEWORK_NAME, "");
    }

    public static void setSelectedFrameworkName(Context context, String board) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_FRAMEWORK_NAME, board).apply();
    }

    public static String getSelectedMedium(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_MEDIUM, "");
    }

    public static void setSelectedMedium(Context context, String medium) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_MEDIUM, medium).apply();
    }

    public static String getSelectedClass(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_CLASS, "");
    }

    public static void setSelectedClass(Context context, String classes) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_CLASS, classes).apply();
    }

    public static String getSelectedTopic(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_TOPIC, "");
    }

    public static void setSelectedTopic(Context context, String classes) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_TOPIC, classes).apply();
    }

    public static String getSelectedSubject(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_SUBJECT, "");
    }

    public static void setSelectedSubject(Context context, String classes) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_SUBJECT, classes).apply();
    }

    public static String getSelectedUserName(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_USER_NAME, "");
    }

    public static void setSelectedUserName(Context context, String userName) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_USER_NAME, userName).apply();
    }

    public static String getSelectedUserQualification(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SELECTED_USER_QUALIFICATION, "");
    }

    public static void setSelectedUserQualification(Context context, String userName) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SELECTED_USER_QUALIFICATION, userName).apply();
    }


    public static void setCustodianOrgId(Context context, String value) {
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        sharedPreference.edit().putString(SETTINGS_CUSTODIAN_ORG_ID, value).apply();

    }

    public static String getCustodianOrgId(Context context) {
        return context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE).getString(SETTINGS_CUSTODIAN_ORG_ID, "");
    }

    public static void clearStateLogoutPref(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("LoginState", Context
                .MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        preferences = context.getSharedPreferences(AppConstants.MY_PREFERENCES, Context
                .MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void addTeach(Context context, String grade, String subject) {
        Gson gson = new Gson();
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        String storedTeach = sharedPreference.getString(SELECTED_TEACH, "");
        if (!TextUtils.isEmpty(storedTeach)) {
            java.lang.reflect.Type type = new TypeToken<LinkedHashMap<String, String>>() {
            }.getType();
            LinkedHashMap<String, String> storedHashMapTeach = gson.fromJson(storedTeach, type);
            storedHashMapTeach.put(grade + AppConstants.COLON + subject, grade + AppConstants.COLON + subject);
            sharedPreference.edit().putString(SELECTED_TEACH, gson.toJson(storedHashMapTeach)).apply();
        } else {
            LinkedHashMap<String, String> teach = new LinkedHashMap<>();
            teach.put(grade + AppConstants.COLON + subject, grade + AppConstants.COLON + subject);
            sharedPreference.edit().putString(SELECTED_TEACH, gson.toJson(teach)).apply();
        }
    }

    public static void removeTeach(Context context, String teach) {
        Gson gson = new Gson();
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        String storedTeach = sharedPreference.getString(SELECTED_TEACH, "");
        if (!TextUtils.isEmpty(storedTeach)) {
            java.lang.reflect.Type type = new TypeToken<LinkedHashMap<String, String>>() {
            }.getType();
            LinkedHashMap<String, String> storedHashMapTeach = gson.fromJson(storedTeach, type);
            storedHashMapTeach.remove(teach);
            sharedPreference.edit().putString(SELECTED_TEACH, gson.toJson(storedHashMapTeach)).apply();
        }
    }

    public static LinkedHashMap<String, String> getAllTeach(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreference = context.getSharedPreferences(AppConstants.MY_PREFERENCES, MODE_PRIVATE);
        String storedTeach = sharedPreference.getString(SELECTED_TEACH, "");
        if (!TextUtils.isEmpty(storedTeach)) {
            java.lang.reflect.Type type = new TypeToken<LinkedHashMap<String, String>>() {
            }.getType();
            return gson.fromJson(storedTeach, type);
        } else {
            return new LinkedHashMap<>();
        }
    }

}
