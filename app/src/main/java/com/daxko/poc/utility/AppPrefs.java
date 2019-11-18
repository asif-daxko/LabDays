package com.daxko.poc.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPrefs {

    private static final String KEY_LAST_TIME_STAMP = "sp_last_timestamp";

    public static void storeLong(Context context, long status) {
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        prefsEditor.putLong(KEY_LAST_TIME_STAMP, status).commit();
    }

    public static long getLong(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getLong(KEY_LAST_TIME_STAMP, 0L);
    }
}