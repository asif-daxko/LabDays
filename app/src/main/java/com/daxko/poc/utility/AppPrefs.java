package com.daxko.poc.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {

    private static final String KEY_LAST_TIME_STAMP = "sp_last_timestamp";
    private static final String Steps = "Steps";
    private static AppPrefs preferenceManager;
    private SharedPreferences preference;

    public AppPrefs(Context context) {
        preference = context.getSharedPreferences("IBAPP", Context.MODE_PRIVATE);
    }

    public static AppPrefs getInstance(Context context) {
        if (preferenceManager == null) {
            preferenceManager = new AppPrefs(context);
        }
        return preferenceManager;
    }

    public long getSteps() {
        return preference.getLong(Steps, 0L);
    }

    public void setSteps(long stepslength) {
        preference.edit().putLong(Steps, stepslength).apply();
    }
}