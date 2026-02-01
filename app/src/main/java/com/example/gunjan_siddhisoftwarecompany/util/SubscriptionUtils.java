package com.example.gunjan_siddhisoftwarecompany.util;

import android.content.Context;

import com.example.gunjan_siddhisoftwarecompany.data.room.entity.SubscriptionEntity;

public class SubscriptionUtils {
    private static final long TRIAL_DURATION = 7L * 24 * 60 * 60 * 1000;
    public static boolean hasAccess(Context context, SubscriptionEntity sub) {
        // 1. Admin always has access
        if (isAdmin(context)) return true;

        // 2. Paid users always have access
        if (sub != null && sub.isPremium) return true;

        // 3. Trial users check time remaining
        if (sub != null && sub.trialStartDate > 0) {
            long timePassed = System.currentTimeMillis() - sub.trialStartDate;
            return timePassed <= TRIAL_DURATION;
        }

        // 4. Default: No access (New User)
        return false;
    }
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
