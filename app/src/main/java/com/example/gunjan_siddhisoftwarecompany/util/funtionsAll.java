package com.example.gunjan_siddhisoftwarecompany.util;

import android.app.Activity;
import android.content.Intent;

import com.example.gunjan_siddhisoftwarecompany.LanguagesActivity16;
import com.example.gunjan_siddhisoftwarecompany.SettingsActivity_15;

public class funtionsAll {
    public static void openSettings(Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity_15.class);
        activity.startActivity(intent);
    }
    public static void openLanguage(Activity activity) {
        Intent intent = new Intent(activity, LanguagesActivity16.class);
        activity.startActivity(intent);
    }

}
