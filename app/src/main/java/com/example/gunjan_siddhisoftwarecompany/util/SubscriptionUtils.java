package com.example.gunjan_siddhisoftwarecompany.util;

import android.content.Context;

public class SubscriptionUtils {
    public static boolean isAdmin(Context c) {
        // For now, return true so you and your sir can bypass payment
        // Later, you can check for a specific email or device ID
        return false;
    }
    public static boolean isPremium(Context c) {

        return c.getSharedPreferences("sub", Context.MODE_PRIVATE)
                .getBoolean("premium", false);
    }

    public static void activate(Context c) {
        c.getSharedPreferences("sub", Context.MODE_PRIVATE)
                .edit().putBoolean("premium", true).apply();
    }
}
