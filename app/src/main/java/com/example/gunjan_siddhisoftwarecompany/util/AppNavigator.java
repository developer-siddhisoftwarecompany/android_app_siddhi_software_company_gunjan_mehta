package com.example.gunjan_siddhisoftwarecompany.util;

import android.app.Activity;
import android.content.Intent;

import com.example.gunjan_siddhisoftwarecompany.MainActivity;
import com.example.gunjan_siddhisoftwarecompany.MyPhotosActivity;
import com.example.gunjan_siddhisoftwarecompany.Settings_04;
import com.example.gunjan_siddhisoftwarecompany.activity_preview;
import com.example.gunjan_siddhisoftwarecompany.cat_1;
import com.example.gunjan_siddhisoftwarecompany.focus_06;
import com.example.gunjan_siddhisoftwarecompany.grid_05;
import com.example.gunjan_siddhisoftwarecompany.per_req_20;
import com.example.gunjan_siddhisoftwarecompany.stamp_0_up;

public class AppNavigator {

    public static void openCamera(Activity a) {
        a.startActivity(new Intent(a, MainActivity.class));
    }

    public static void openMyPhotos(Activity a) {
        a.startActivity(new Intent(a, MyPhotosActivity.class));
    }

    public static void openSettingsOverlay(Activity a) {
        a.startActivity(new Intent(a, Settings_04.class));
    }

    public static void openGrid(Activity a) {
        a.startActivity(new Intent(a, grid_05.class));
    }

    public static void openFocus(Activity a) {
        a.startActivity(new Intent(a, focus_06.class));
    }

    public static void openStamp(Activity a) {
        a.startActivity(new Intent(a, stamp_0_up.class));
    }

    public static void openCategory(Activity a) {
        a.startActivity(new Intent(a, cat_1.class));
    }

    public static void openPreview(Activity a) {
        a.startActivity(new Intent(a, activity_preview.class));
    }

    public static void openPermissionRequired(Activity a) {
        a.startActivity(new Intent(a, per_req_20.class));
    }
}
