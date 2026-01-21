package com.example.gunjan_siddhisoftwarecompany.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public static boolean hasAll(Context c) {
        return ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(c, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

public static boolean hasLocation(Context c) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        return ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    return ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
}
    public static void requestLocation(android.app.Activity activity, int requestCode) {
        List<String> permissions = new ArrayList<>();
        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);

        //  Android 10+ Gallery GPS
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            permissions.add(android.Manifest.permission.ACCESS_MEDIA_LOCATION);
        }

        androidx.core.app.ActivityCompat.requestPermissions(
                activity,
                permissions.toArray(new String[0]),
                requestCode
        );
    }
//
//    public static void requestLocation(android.app.Activity activity, int requestCode) {
//        androidx.core.app.ActivityCompat.requestPermissions(
//                activity,
//                new String[]{
//                        android.Manifest.permission.ACCESS_FINE_LOCATION,
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION
//                },
//                requestCode
//        );
//    }
//    public static boolean hasLocation(Context c) {
//        return androidx.core.content.ContextCompat.checkSelfPermission(
//                c,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//        ) == android.content.pm.PackageManager.PERMISSION_GRANTED;
//    }

}
