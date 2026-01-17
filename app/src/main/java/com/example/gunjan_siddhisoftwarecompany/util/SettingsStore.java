package com.example.gunjan_siddhisoftwarecompany.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsStore {

    private static final String PREF_NAME = "app_settings";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ---------------- SAVE ----------------

    public static void save(Context context, String key, String value) {
        getPrefs(context)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static void save(Context context, String key, boolean value) {
        getPrefs(context)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static void save(Context context, String key, int value) {
        getPrefs(context)
                .edit()
                .putInt(key, value)
                .apply();
    }

    // ---------------- GET ----------------

    public static String get(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    public static boolean get(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    public static int get(Context context, String key, int defaultValue) {
        return getPrefs(context).getInt(key, defaultValue);
    }
}
