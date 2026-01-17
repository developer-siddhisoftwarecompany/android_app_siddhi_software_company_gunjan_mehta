package com.example.gunjan_siddhisoftwarecompany.util;

import android.content.Context;

public class SubscriptionUtils {

    public static boolean isPremium(Context c) {
        return c.getSharedPreferences("sub", Context.MODE_PRIVATE)
                .getBoolean("premium", false);
    }

    public static void activate(Context c) {
        c.getSharedPreferences("sub", Context.MODE_PRIVATE)
                .edit().putBoolean("premium", true).apply();
    }
}
