/*
 * Copyright (c) 2018. McAfee
 * All Rights Reserved
 *
 */

package com.gurug.education.utill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.RequestOptions;
import com.gurug.education.R;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.lessonplan.TeachingMethod;
import com.gurug.education.data.model.response.lessonplan.Topic;
import com.gurug.education.data.repository.local.AppPrefs;
import com.gurug.education.view.activity.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sandy on 12/03/18.
 */

public class Utility {

    private static long sStartTime = 0;
    public static long sTimeTaken = 0;

    private static ProgressBar mProgressBar;

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showSnackBar(final Context context, String errorMessage) {
        hideProgress();
        View parentLayout = ((Activity) context).findViewById(android.R.id.content);
        Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.text_ok), view -> {

                })
                .setActionTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                .setDuration(5000)
                .show();
    }

    public static void showProgress(Context context) {
        hideProgress();
        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content)
                .getRootView();
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.circle_progress_bar));
        //mProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"),android.graphics.PorterDuff.Mode.MULTIPLY);

        mProgressBar.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(context,R.color.colorBlue), android.graphics.PorterDuff.Mode.SRC_IN);

        RelativeLayout rl = new RelativeLayout(context);
        rl.setGravity(Gravity.CENTER);
        rl.addView(mProgressBar);
        layout.addView(rl, params);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Email validation pattern.
     */
    @SuppressWarnings("Annotator")
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    /**
     * Validates if the given input is a valid email address.
     *
     * @param email The email to validate.
     * @return {@code true} if the input is a valid email. {@code false} otherwise.
     */
    public static boolean isValidUserId(CharSequence email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates if the given input is a valid password.
     *
     * @param passwrod The password to validate.
     * @return {@code true} if the input is a valid password. {@code false} otherwise.
     */
    public static boolean isValidPassword(String passwrod) {
        if (passwrod == null) {
            return false;
        }
        try {
            //String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,40}$";
            String PASSWORD_PATTERN = "^(?=.{8,})((?=.*[^a-zA-Z])(?=.*[a-z])(?=.*[A-Z])|(?=.*[^a-zA-Z0-9])(?=.*[0-9])(?=.*[a-zA-Z])).*$";
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(passwrod);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param context Activity context to start login activity after logout
     */
    public static void logout(Context context) {
       /* try {
            hideDiaLog();
        } catch (Exception ignored) {

        }
        SafeHelperFactory factory = new SafeHelperFactory(Utility.getKey(context).toCharArray());
        SupportDatabase supportDatabase =
                Room.databaseBuilder(context.getApplicationContext(), SupportDatabase.class, AppConstants.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .openHelperFactory(factory)
                        .fallbackToDestructiveMigration()
                        .build();

        supportDatabase.clearAllTables();
        supportDatabase.close();
        RESTManager.getInstance().clearData();
        AppStatePrefs.clearStateLogoutPref(context);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }*/

        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static String getDateTimeddmmyyyyTimeStamp(long timestamp) {
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        Date date;
        if (timestamp > 0) {
            date = new Date(timestamp);
        } else {
            date = new Date(System.currentTimeMillis());
        }
        return format.format(date);
    }

    public static String getFormatedDateTimeFromFromddmmyyy(String input) {
        String dateOutput = input;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterInput = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterOutput = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date dateStart;
        try {
            dateStart = formatterInput.parse(input);
            dateOutput = formatterOutput.format(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateOutput;
    }

    public static String getFormatedDateTimeFromFromddmmTyyy(String input) {
        if (input.contains("T")) {
            input = input.replace('T', ' ');
        }
        String dateOutput = input;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterOutput = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date dateStart;
        try {
            dateStart = formatterInput.parse(input);
            dateOutput = formatterOutput.format(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateOutput;
    }

    public static String getFormatedDateFromFromddmmTyyy(String input) {
        if (input.contains("T")) {
            input = input.replace('T', ' ');
        }
        String dateOutput = input;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterOutput = new SimpleDateFormat("MM/dd/yyyy");
        Date dateStart;
        try {
            dateStart = formatterInput.parse(input);
            dateOutput = formatterOutput.format(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateOutput;
    }

    private static String getReadableTimezoneAbbrevationStr(String timezone) {
        TimeZone tz = TimeZone.getTimeZone(timezone);
        return getReadableTimezoneAbbrevationStr(tz);
    }

    private static String getReadableTimezoneAbbrevationStr(TimeZone timezone) {
        if (timezone == null) {
            timezone = TimeZone.getDefault();
        }
        //logger.info("timeznoe=="+timezone);
        timezone.getOffset(Calendar.ZONE_OFFSET);

        String[] availableIDs = TimeZone.getAvailableIDs(timezone.getOffset(Calendar.ZONE_OFFSET));
        String strTimeZone = null;
        for (String availableID : availableIDs) {
            TimeZone tz = TimeZone.getTimeZone(availableID);
            //            System.out.println(tz.getID() + " " +
            //                               tz.getDisplayName(false, TimeZone.SHORT));
            strTimeZone = tz.getDisplayName(false, TimeZone.SHORT);
            break;
        }
        return strTimeZone;
    }

    public static String getTimeZone(String timeZoneOffset) {
        if (!TextUtils.isEmpty(timeZoneOffset)) {
            Pattern pattern =
                    Pattern.compile("^\\(([^\\)]*)\\)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(timeZoneOffset);
            String timeZoneId = "";
            while (matcher.find()) {
                timeZoneId = matcher.group(1);
            }
            return getReadableTimezoneAbbrevationStr(timeZoneId);
        } else {
            return "MST";
        }
    }

    public static ArrayList<Content> getLessonPlans(Context context, String topic) {
        ArrayList<Content> contentArrayList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(loadJsonFromAssets(context, "data/lessonPlans.json"));
            for (int i = 0; i < jsonArray.length(); i++) {
                Content content = new Content();

                content.setId(jsonArray.getJSONObject(i).getString("id"));
                content.setTopic(jsonArray.getJSONObject(i).getString("topic"));
                content.setSubject(jsonArray.getJSONObject(i).getString("subject"));
                content.setGrade(jsonArray.getJSONObject(i).getString("Class"));
                content.setMedium(jsonArray.getJSONObject(i).getString("medium"));
                content.setPedagogyFlow(jsonArray.getJSONObject(i).getString("pedagogyFlow"));
                content.setBoard(jsonArray.getJSONObject(i).getString("board"));
                content.setDescription(jsonArray.getJSONObject(i).getString("description"));
                content.setTotalDuration(jsonArray.getJSONObject(i).getString("totalDuration"));

                ArrayList<TeachingMethod> teachingMethodArrayList = new ArrayList<>();

                JSONArray teachingMethods = jsonArray.getJSONObject(i).getJSONArray("teachingMethods");
                for (int j = 0; j < teachingMethods.length(); j++) {
                    TeachingMethod teachingMethod = new TeachingMethod();
                    teachingMethod.setId(teachingMethods.getJSONObject(j).getString("id"));
                    teachingMethod.setShortDescription(teachingMethods.getJSONObject(j).getString("shortDescription"));
                    teachingMethod.setLongDescription(teachingMethods.getJSONObject(j).getString("longDescription"));
                    teachingMethod.setDuration(teachingMethods.getJSONObject(j).getString("duration"));
                    teachingMethod.setPedagogyStep(teachingMethods.getJSONObject(j).getString("pedagogyStep"));
                    teachingMethod.setMethodType(teachingMethods.getJSONObject(j).getString("methodType"));
                    teachingMethodArrayList.add(teachingMethod);
                }
                //content.setTeachingMethods(teachingMethodArrayList);
                contentArrayList.add(content);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Content> filteredContentArrayList = new ArrayList<>();

        if (TextUtils.isEmpty(topic)) {
            if (contentArrayList.size() > 0) {
                for (Content content : contentArrayList) {
                    if (content.getGrade().equalsIgnoreCase(AppPrefs.getSelectedClass(context))
                            && content.getMedium().equalsIgnoreCase(AppPrefs.getSelectedMedium(context))
                            && content.getBoard().equalsIgnoreCase(AppPrefs.getSelectedBoard(context))
                            && content.getSubject().equalsIgnoreCase(AppPrefs.getSelectedSubject(context))) {
                        filteredContentArrayList.add(content);
                    }
                }
            }
        } else {
            if (contentArrayList.size() > 0) {
                for (Content content : contentArrayList) {
                    if (content.getGrade().equalsIgnoreCase(AppPrefs.getSelectedClass(context))
                            && content.getMedium().equalsIgnoreCase(AppPrefs.getSelectedMedium(context))
                            && content.getBoard().equalsIgnoreCase(AppPrefs.getSelectedBoard(context))
                            && content.getSubject().equalsIgnoreCase(AppPrefs.getSelectedSubject(context))
                            && content.getTopic().equalsIgnoreCase(topic)
                    ) {
                        filteredContentArrayList.add(content);
                    }
                }
            }
        }

        return filteredContentArrayList;
    }

    public static ArrayList<Topic> getTopics(Context context) {
        ArrayList<Topic> topicArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(loadJsonFromAssets(context, "data/topics.json"));
            for (int i = 0; i < jsonArray.length(); i++) {
                Topic topic = new Topic();
                topic.setId(jsonArray.getJSONObject(i).getString("id"));
                topic.setTopic(jsonArray.getJSONObject(i).getString("topic"));
                topic.setSubject(jsonArray.getJSONObject(i).getString("subject"));
                topic.setGrade(jsonArray.getJSONObject(i).getString("class"));
                topic.setBoard(jsonArray.getJSONObject(i).getString("board"));
                topic.setMedium(jsonArray.getJSONObject(i).getString("medium"));
                topicArrayList.add(topic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Topic> filteredTopicArrayList = new ArrayList<>();

        if (topicArrayList.size() > 0) {
            for (Topic topic : topicArrayList) {
                if (topic.getGrade().equalsIgnoreCase(AppPrefs.getSelectedClass(context))
                        && topic.getMedium().equalsIgnoreCase(AppPrefs.getSelectedMedium(context))
                        && topic.getBoard().equalsIgnoreCase(AppPrefs.getSelectedBoard(context))
                        && topic.getSubject().equalsIgnoreCase(AppPrefs.getSelectedSubject(context))) {
                    filteredTopicArrayList.add(topic);
                }
            }
        }

        return filteredTopicArrayList;
    }

    private static String loadJsonFromAssets(Context context, String fileName) {
        String json = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {

        }

        return json;

    }

    /*public static String getDefaultStoragePath(Context context) {
        String filepath;
        boolean isExternalDefaultStorage = PreferenceUtil.getPreferenceWrapper().getBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);
        if (isExternalDefaultStorage) {
            filepath = getExternalSdcardPath(context);
        } else {
            filepath = getExternalFilesDir(context).toString();
        }
        return filepath;
    }*/

    @NonNull
    public static File getExternalFilesDir(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir == null)
            throw new RuntimeException("External file could not be loaded.");
        return externalFilesDir;
    }

    public static RequestOptions getDefaultOption(int defaultPlaceHolder, int errorImage) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(defaultPlaceHolder);
        requestOptions.error(defaultPlaceHolder);
        return requestOptions;
    }

    @SuppressLint("PackageManagerGetSignatures")
    public static String getKey(Context context) {
        String hash_key = context.getString(R.string.default_key_hash);
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hash_key = new String(Base64.encode(md.digest(), 0));
            }
        } catch (Exception ignored) {
        }
        return hash_key;
    }

    public static String toTitleCase(String str) {
        if (str == null) {
            return null;
        }
        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c) && !charCheckIsSpecialChar(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c) && charCheckIsSpecialChar(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }


    static boolean charCheckIsSpecialChar(char input_char) {
        // CHECKING FOR ALPHABET
        if ((input_char >= 65 && input_char <= 90) || (input_char >= 97 && input_char <= 122))
            return false;
            // CHECKING FOR DIGITS
        else if (input_char >= 48 && input_char <= 57) {
            System.out.println(" Digit ");
            return false;
        }
        // OTHERWISE SPECIAL CHARACTER
        else {
            return true;
        }
    }


}
