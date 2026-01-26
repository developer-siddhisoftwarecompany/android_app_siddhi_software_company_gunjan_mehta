//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.database.Cursor;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//
//import org.json.JSONObject;
//import org.osmdroid.config.Configuration;
//import org.osmdroid.util.GeoPoint;
//import org.osmdroid.views.MapView;
//import org.osmdroid.views.overlay.Marker;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class image12file extends AppCompatActivity {
//
//    private MapView map;
//    private FusedLocationProviderClient fusedLocationClient;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        setContentView(R.layout.image_info_12);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        map = findViewById(R.id.mapview);
//        setupHttpsTiles();
//
//
//        map.setMultiTouchControls(true);
//
//        File osmdroidBasePath = new File(getFilesDir(), "osmdroid");
//        File osmdroidTileCache = new File(osmdroidBasePath, "tiles");
//
//        Configuration.getInstance().setOsmdroidBasePath(osmdroidBasePath);
//        Configuration.getInstance().setOsmdroidTileCache(osmdroidTileCache);
//
//        Configuration.getInstance().load(
//                getApplicationContext(),
//                PreferenceManager.getDefaultSharedPreferences(this)
//        );
//        Configuration.getInstance().setUserAgentValue(getPackageName());
//// ---------------------------------------------------------
//
//        ImageView btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(v -> finish());
//
//        String imageUriString = getIntent().getStringExtra("imageUri");
//        if (imageUriString == null) return;
//
//        Uri imageUri = Uri.parse(imageUriString);
//
//        // Fill the labels (File Name, Size, etc.)
//        fetchImageMetadata(imageUri);
//
//        // Load Location and Address
//        getPhotoLocation(imageUriString);
//    }
//    private void setupHttpsTiles() {
//        org.osmdroid.tileprovider.tilesource.ITileSource httpsSource =
//                new org.osmdroid.tileprovider.tilesource.XYTileSource(
//                        "OSM HTTPS",
//                        1, 20, 256, ".png",
//                        new String[]{
//                                "https://tile.openstreetmap.org/"
//                        }
//                );
//
//        map.setTileSource(httpsSource);
//        map.setUseDataConnection(true);
//    }
//
//
//
//    private void fetchImageMetadata(Uri imageUri) {
//        String fileName = "Unknown";
//        long fileSizeBytes = 0;
//        long dateTaken = 0;
//
//        try (Cursor cursor = getContentResolver().query(
//                imageUri,
//                new String[]{
//                        MediaStore.Images.Media.DISPLAY_NAME,
//                        MediaStore.Images.Media.SIZE,
//                        MediaStore.Images.Media.DATE_TAKEN
//                }, null, null, null)) {
//
//            if (cursor != null && cursor.moveToFirst()) {
//                fileName = cursor.getString(0);
//                fileSizeBytes = cursor.getLong(1);
//                dateTaken = cursor.getLong(2);
//            }
//        }
//
//        String fileSize = (fileSizeBytes / 1024) + " KB";
//        String dateText = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(new Date(dateTaken));
//
//        // Get Resolution
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        try (InputStream is = getContentResolver().openInputStream(imageUri)) {
//            BitmapFactory.decodeStream(is, null, options);
//        } catch (Exception ignored) {}
//
//        String resolution = options.outWidth + " x " + options.outHeight;
//
//        // Set the rows in your UI
//        setRow(findViewById(R.id.row1), R.drawable._12_2_image_folder, "File Name", fileName);
//        setRow(findViewById(R.id.row2), R.drawable._12_1_image_folder, "Image Uri", imageUri.toString());
//        setRow(findViewById(R.id.row3), R.drawable._12_3_image_folder, "File Size", fileSize);
//        setRow(findViewById(R.id.row4), R.drawable._12_4_image, "Resolution", resolution);
//        setRow(findViewById(R.id.row5), R.drawable._12_5_image_cal, "Date & Time", dateText);
//    }
//
//    private void setRow(View row, int icon, String label, String value) {
//        if (row == null) return;
//        ImageView img = row.findViewById(R.id.imgIcon);
//        TextView txtLabel = row.findViewById(R.id.txtLabel);
//        TextView txtValue = row.findViewById(R.id.txtValue);
//
//        img.setImageResource(icon);
//        txtLabel.setText(label + " : ");
//        txtValue.setText(value);
//    }
//
//    private void updateAddress(String text) {
//        TextView txtAddress = findViewById(R.id.addressCard).findViewById(R.id.txtAddress);
//        if (txtAddress != null) {
//            txtAddress.setText(text);
//        }
//    }
////    private void getPhotoLocation(String uriString) {
////        new Thread(() -> {
////            try {
////                Uri uri = Uri.parse(uriString);
////
////                // 1. Request the original file (with GPS) from the System
//////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
////////
//////
//////                    uri = MediaStore.setRequireOriginal(uri);
//////                }
//////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && uriString.contains("com.android.providers")) {
//////                    // This is a system gallery photo
//////                    // Read it directly without setRequireOriginal
//////                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//////                    uri = MediaStore.setRequireOriginal(uri);
//////                }
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
////                    try {
////                        // This is the line that triggers the SecurityException
////                        uri = MediaStore.setRequireOriginal(uri);
////                    } catch (SecurityException e) {
////                        // Fallback: If we can't get the 'Original', we try to read what's available
////                        android.util.Log.e("GPS_ERROR", "Original access denied, trying fallback.");
////                    }
////                }
////
////
//////                // 2. Open the stream
////                InputStream is = getContentResolver().openInputStream(uri);
////                if (is == null) return;
////
////                androidx.exifinterface.media.ExifInterface exif = new androidx.exifinterface.media.ExifInterface(is);
////                float[] latLong = new float[2];
////
////                if (exif.getLatLong(latLong)) {
////                    double lat = latLong[0];
////                    double lon = latLong[1];
////
////                    runOnUiThread(() -> {
////                        setupMap(lat, lon);
////                        updateAddress("Fetching address...");
////                    });
////
////                    String address = fetchAddressFromOSM(lat, lon);
////                    runOnUiThread(() -> updateAddress(address));
////                } else {
////                    runOnUiThread(() -> updateAddress("No GPS found in this photo's metadata"));
////                }
////                is.close();
////
////            } catch (SecurityException e) {
////            runOnUiThread(() -> updateAddress("Error: Permission denied for GPS metadata"));
////        } catch (Exception e) {
////            runOnUiThread(() -> updateAddress("Error: " + e.getMessage()));
////        }
////        }).start();
////    }
////private void getPhotoLocation(String uriString) {
////    new Thread(() -> {
////        try {
////            Uri uri = Uri.parse(uriString);
////
////            // Try to get the original file for GPS access (Android 10+)
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
////                try {
////                    uri = MediaStore.setRequireOriginal(uri);
////                } catch (SecurityException e) {
////                    // This is expected for some gallery apps; we log it and continue
////                    android.util.Log.e("GPS_DEBUG", "Original access denied, trying standard stream.");
////                }
////            }
////
////            // Use try-with-resources to ensure the stream closes properly
////            try (InputStream is = getContentResolver().openInputStream(uri)) {
////                if (is == null) {
////                    runOnUiThread(() -> updateAddress("Cannot open image file."));
////                    return;
////                }
////
////                androidx.exifinterface.media.ExifInterface exif = new androidx.exifinterface.media.ExifInterface(is);
////                float[] latLong = new float[2];
////
////                if (exif.getLatLong(latLong)) {
////                    double lat = latLong[0];
////                    double lon = latLong[1];
////
////                    runOnUiThread(() -> {
////                        setupMap(lat, lon);
////                        updateAddress("Fetching address...");
////                    });
////
////                    String address = fetchAddressFromOSM(lat, lon);
////                    runOnUiThread(() -> updateAddress(address));
////                } else {
////                    runOnUiThread(() -> updateAddress("No GPS found in this photo's metadata"));
////                }
////            }
////        } catch (Exception e) {
////            runOnUiThread(() -> updateAddress("Error accessing metadata: " + e.getMessage()));
////        }
////    }).start();
////}
//private void getPhotoLocation(String uriString) {
//    new Thread(() -> {
//        try {
//            Uri uri = Uri.parse(uriString);
//
//            InputStream is = getContentResolver().openInputStream(uri);
//            if (is == null) {
//                runOnUiThread(() -> updateAddress("Unable to access image data"));
//                return;
//            }
//
//            androidx.exifinterface.media.ExifInterface exif =
//                    new androidx.exifinterface.media.ExifInterface(is);
//
//            float[] latLong = new float[2];
//
//            if (exif.getLatLong(latLong)) {
//                double lat = latLong[0];
//                double lon = latLong[1];
//
//                runOnUiThread(() -> {
//                    setupMap(lat, lon);
//                    updateAddress("Fetching address...");
//                });
//
//                String address = fetchAddressFromOSM(lat, lon);
//                runOnUiThread(() -> updateAddress(address));
//
//            } else {
//                runOnUiThread(() ->
//                        updateAddress("No GPS found in this photo"));
//            }
//
//            is.close();
//
//        } catch (SecurityException e) {
//            runOnUiThread(() ->
//                    updateAddress("Permission denied by Gallery app"));
//        } catch (Exception e) {
//            runOnUiThread(() ->
//                    updateAddress("Error reading image metadata"));
//        }
//    }).start();
//}
//
//    private void setupMap(double lat, double lon) {
//        if (map == null) return;
//        GeoPoint startPoint = new GeoPoint(lat, lon);
//        map.getController().setZoom(17.0);
//        map.getController().setCenter(startPoint);
//        map.setMultiTouchControls(true);
//
//        // Add a red pin at the location
//        Marker startMarker = new Marker(map);
//        startMarker.setPosition(startPoint);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        startMarker.setTitle("Photo Location");
//        map.getOverlays().clear();
//        map.getOverlays().add(startMarker);
//        map.invalidate(); // Refresh map
//    }
//
//
//private String fetchAddressFromOSM(double lat, double lon) {
//    HttpURLConnection conn = null;
//    try {
//        // Nominatim API - Worldwide & Free (OpenStreetMap)
//        String urlStr = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", lat, lon);
//        URL url = new URL(urlStr);
//
//        conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//
//        // IMPORTANT: Nominatim requires a User-Agent and prefers a Referer
//        conn.setRequestProperty("User-Agent", getPackageName());
//        conn.setRequestProperty("Referer", "https://openstreetmap.org");
//
//        // Timeouts prevent the app from getting stuck on a bad connection
//        conn.setConnectTimeout(10000); // 10 seconds
//        conn.setReadTimeout(10000);    // 10 seconds
//
//        int responseCode = conn.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//            reader.close();
//
//            JSONObject json = new JSONObject(response.toString());
//            // "display_name" is the full formatted address string
//            return json.optString("display_name", "Address not available");
//        } else {
//            return "Server Error: " + responseCode;
//        }
//    } catch (java.net.SocketTimeoutException e) {
//        return "Network Error: Request timed out";
//    } catch (Exception e) {
//        e.printStackTrace();
//        return "Network Error: Check Internet connection";
//    } finally {
//        if (conn != null) {
//            conn.disconnect();
//        }
//    }
//}
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (map != null) map.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (map != null) map.onPause();
//    }

///////////// mellow
//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.database.Cursor;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//
//import org.json.JSONObject;
//import org.osmdroid.config.Configuration;
//import org.osmdroid.util.GeoPoint;
//import org.osmdroid.views.MapView;
//import org.osmdroid.views.overlay.Marker;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class image12file extends AppCompatActivity {
//
//    private MapView map;
//    private FusedLocationProviderClient fusedLocationClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.image_info_12);
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        // --- OSM (Map) Setup ---
//        map = findViewById(R.id.mapview);
//        setupHttpsTiles();
//        map.setMultiTouchControls(true);
//
//        File osmdroidBasePath = new File(getFilesDir(), "osmdroid");
//        File osmdroidTileCache = new File(osmdroidBasePath, "tiles");
//        Configuration.getInstance().setOsmdroidBasePath(osmdroidBasePath);
//        Configuration.getInstance().setOsmdroidTileCache(osmdroidTileCache);
//        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(this));
//        Configuration.getInstance().setUserAgentValue(getPackageName());
//
//        // --- UI Logic ---
//        ImageView btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(v -> finish());
//
//        String imageUriString = getIntent().getStringExtra("imageUri");
//        if (imageUriString == null) return;
//
//        Uri imageUri = Uri.parse(imageUriString);
//
//        // UPDATED: Query the folder name and other details
//        fetchProjectImageDetails(imageUri);
//
//        // Load Location and Address
//        getPhotoLocation(imageUriString);
//    }
//
//    private void setupHttpsTiles() {
//        org.osmdroid.tileprovider.tilesource.ITileSource httpsSource =
//                new org.osmdroid.tileprovider.tilesource.XYTileSource(
//                        "OSM HTTPS", 1, 20, 256, ".png",
//                        new String[]{"https://tile.openstreetmap.org/"}
//                );
//        map.setTileSource(httpsSource);
//        map.setUseDataConnection(true);
//    }
//
//    /**
//     * Extracts and displays image metadata, specifically focusing on the
//     * custom project folder name for worldwide support.
//     */
//    private void fetchProjectImageDetails(Uri imageUri) {
//        String fileName = "Unknown";
//        String folderDisplayName = "Unknown"; // For the custom company folder
//        long fileSizeBytes = 0;
//        long dateTaken = 0;
//        String[] projection = {
//                MediaStore.Images.Media.DISPLAY_NAME,
//                MediaStore.Images.Media.SIZE,
//                MediaStore.Images.Media.DATE_TAKEN,
//                MediaStore.Images.Media.RELATIVE_PATH,
//                "_data" // Fallback for real file path
//        };
//
//        // Query including RELATIVE_PATH to identify the user's custom folder
////
////        try (Cursor cursor = getContentResolver().query(
////                imageUri,
////                new String[]{
////                        MediaStore.Images.Media.DISPLAY_NAME,
////                        MediaStore.Images.Media.SIZE,
////                        MediaStore.Images.Media.DATE_TAKEN,
////                        MediaStore.Images.Media.RELATIVE_PATH // Contains "Pictures/FolderName"
////                }, null, null, null)) {
//
//        try (Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null)) {
//            if (cursor != null && cursor.moveToFirst()) {
//                fileName = cursor.getString(0);
//                fileSizeBytes = cursor.getLong(1);
//                dateTaken = cursor.getLong(2);
//
//                // Extract folder name from the full system path
//                String fullPath = cursor.getString(3);
//                if (fullPath != null && !fullPath.isEmpty()) {
//                    // Clean path to show only the folder name (e.g., SiddhiSoftware)
//                    folderDisplayName = fullPath.replace("Pictures/", "").replace("/", "");
//                }
//                else {
//                    String dataPath = cursor.getString(4);
//                    if (dataPath != null) {
//                        File file = new File(dataPath);
//                        folderDisplayName = (file.getParentFile() != null) ? file.getParentFile().getName() : "Gallery";
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (folderDisplayName.equals("Unknown")) folderDisplayName = "System Gallery";
//        // Format for readability
//        String fileSize = (fileSizeBytes / 1024) + " KB";
//        String dateText = (dateTaken > 0)? new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(new Date(dateTaken))
//                : "Date Not Available";
//
//        // Get Resolution
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        try (InputStream is = getContentResolver().openInputStream(imageUri)) {
//            BitmapFactory.decodeStream(is, null, options);
//        } catch (Exception ignored) {}
//        String resolution = options.outWidth + " x " + options.outHeight;
//
//        // Update UI Rows
//        setRow(findViewById(R.id.row1), R.drawable._12_2_image_folder, "File Name", fileName);
//        // Show folder name instead of URI
//        setRow(findViewById(R.id.row2), R.drawable._12_1_image_folder, "Folder", folderDisplayName);
//        setRow(findViewById(R.id.row3), R.drawable._12_3_image_folder, "File Size", fileSize);
//        setRow(findViewById(R.id.row4), R.drawable._12_4_image, "Resolution", resolution);
//        setRow(findViewById(R.id.row5), R.drawable._12_5_image_cal, "Date & Time", dateText);
//    }
//
//    private void setRow(View row, int icon, String label, String value) {
//        if (row == null) return;
//        ImageView img = row.findViewById(R.id.imgIcon);
//        TextView txtLabel = row.findViewById(R.id.txtLabel);
//        TextView txtValue = row.findViewById(R.id.txtValue);
//
//        img.setImageResource(icon);
//        txtLabel.setText(label + " : ");
//        txtValue.setText(value);
//    }
//
//    private void updateAddress(String text) {
//        TextView txtAddress = findViewById(R.id.addressCard).findViewById(R.id.txtAddress);
//        if (txtAddress != null) txtAddress.setText(text);
//    }
//
//    private void getPhotoLocation(String uriString) {
//        new Thread(() -> {
//            try {
//                Uri uri = Uri.parse(uriString);
//                try (InputStream is = getContentResolver().openInputStream(uri)) {
//                    if (is == null) {
//                        runOnUiThread(() -> updateAddress("Unable to access image data"));
//                        return;
//                    }
//
//                    androidx.exifinterface.media.ExifInterface exif = new androidx.exifinterface.media.ExifInterface(is);
//                    float[] latLong = new float[2];
//
//                    if (exif.getLatLong(latLong)) {
//                        double lat = latLong[0];
//                        double lon = latLong[1];
//
//                        runOnUiThread(() -> {
//                            setupMap(lat, lon);
//                            updateAddress("Fetching address...");
//                        });
//
//                        String address = fetchAddressFromOSM(lat, lon);
//                        runOnUiThread(() -> updateAddress(address));
//                    } else {
//                        runOnUiThread(() -> updateAddress("No GPS found in this photo"));
//                    }
//                }
//            } catch (Exception e) {
//                runOnUiThread(() -> updateAddress("Error reading image metadata"));
//            }
//        }).start();
//    }
//
//    private void setupMap(double lat, double lon) {
//        if (map == null) return;
//        GeoPoint startPoint = new GeoPoint(lat, lon);
//        map.getController().setZoom(17.0);
//        map.getController().setCenter(startPoint);
//        Marker startMarker = new Marker(map);
//        startMarker.setPosition(startPoint);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        startMarker.setTitle("Photo Location");
//        map.getOverlays().clear();
//        map.getOverlays().add(startMarker);
//        map.invalidate();
//    }
//
//    private String fetchAddressFromOSM(double lat, double lon) {
//        HttpURLConnection conn = null;
//        try {
//            String urlStr = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", lat, lon);
//            URL url = new URL(urlStr);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("User-Agent", getPackageName());
//            conn.setRequestProperty("Referer", "https://openstreetmap.org");
//            conn.setConnectTimeout(10000);
//            conn.setReadTimeout(10000);
//
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) response.append(line);
//                reader.close();
//                JSONObject json = new JSONObject(response.toString());
//                return json.optString("display_name", "Address not available");
//            }
//            return "Server Error: " + conn.getResponseCode();
//        } catch (Exception e) {
//            return "Network Error: Check connection";
//        } finally {
//            if (conn != null) conn.disconnect();
//        }
//    }
//
//    @Override
//    protected void onResume() { super.onResume(); if (map != null) map.onResume(); }
//    @Override
//    protected void onPause() { super.onPause(); if (map != null) map.onPause(); }


//mellowwwwwwwwwww




















package com.example.gunjan_siddhisoftwarecompany;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class image12file extends AppCompatActivity {

    private MapView map;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_info_12);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // --- OSM (Map) Setup ---
        map = findViewById(R.id.mapview);
        setupHttpsTiles();
        map.setMultiTouchControls(true);

        File osmdroidBasePath = new File(getFilesDir(), "osmdroid");
        File osmdroidTileCache = new File(osmdroidBasePath, "tiles");
        Configuration.getInstance().setOsmdroidBasePath(osmdroidBasePath);
        Configuration.getInstance().setOsmdroidTileCache(osmdroidTileCache);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(this));
        Configuration.getInstance().setUserAgentValue(getPackageName());

        // --- UI Logic ---
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        String imageUriString = getIntent().getStringExtra("imageUri");
        String source = getIntent().getStringExtra("source");
        if (imageUriString == null) return;

        Uri imageUri = Uri.parse(imageUriString);

        // ACTUAL DATA RETRIEVAL:
        // We use different logic for Gallery vs App Photos to ensure accuracy.
        if ("gallery".equals(source)) {
            fetchGalleryDetails(imageUri);
        } else {
            fetchProjectImageDetails(imageUri);
        }

        // Load ACTUAL GPS Location and Address
        getPhotoLocation(imageUriString);
    }

    private void fetchGalleryDetails(Uri imageUri) {
        String fileName = "Actual Name Hidden";
        long fileSizeBytes = 0;
        long dateTaken = 0;

        String[] projection = {
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATE_ADDED
        };

        try (Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                fileName = cursor.getString(0);
                fileSizeBytes = cursor.getLong(1);
                long taken = cursor.getLong(2); // DATE_TAKEN (ms)
                long added = cursor.getLong(3) * 1000; // DATE_ADDED (sec → ms)

                dateTaken = (taken > 0) ? taken : added;
//                dateTaken = cursor.getLong(2) * 1000; // Standardize to milliseconds
            }
        } catch (Exception e) { e.printStackTrace(); }

        updateUI(fileName, "System Gallery", fileSizeBytes, dateTaken, imageUri);
    }

    private void fetchProjectImageDetails(Uri imageUri) {
        String fileName = "Actual Name Hidden";
        String folderDisplayName = "My Project";
        long fileSizeBytes = 0;
        long dateTaken = 0;

        String[] projection = {
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.RELATIVE_PATH,
                "_data"
        };

        try (Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                fileName = cursor.getString(0);
                fileSizeBytes = cursor.getLong(1);
                dateTaken = cursor.getLong(2);

                // ACTUAL FOLDER LOGIC:
                String fullPath = cursor.getString(3);
                if (fullPath != null && !fullPath.isEmpty()) {
                    // Strips system path to show the ACTUAL project folder
                    folderDisplayName = fullPath.replace("Pictures/", "").replace("/", "");
                } else {
                    // Fallback for older Android versions to find ACTUAL folder
                    String dataPath = cursor.getString(4);
                    if (dataPath != null) {
                        File file = new File(dataPath);
                        folderDisplayName = (file.getParentFile() != null) ? file.getParentFile().getName() : "Project";
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        updateUI(fileName, folderDisplayName, fileSizeBytes, dateTaken, imageUri);
    }

    // CENTRAL UI UPDATER: Ensures all labels show actual values
    private void updateUI(String name, String folder, long size, long date, Uri uri) {
        String fileSizeText = (size / 1024) + " KB";
        String dateText = (date > 0) ? new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(new Date(date)) : "Date Not Available";

        // ACTUAL RESOLUTION: Reads directly from the image file stream
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try (InputStream is = getContentResolver().openInputStream(uri)) {
            BitmapFactory.decodeStream(is, null, options);
        } catch (Exception ignored) {}
        String resolution = options.outWidth + " x " + options.outHeight;

        setRow(findViewById(R.id.row1), R.drawable._12_2_image_folder, "File Name", name);
        setRow(findViewById(R.id.row2), R.drawable._12_1_image_folder, "Folder", folder);
        setRow(findViewById(R.id.row3), R.drawable._12_3_image_folder, "File Size", fileSizeText);
        setRow(findViewById(R.id.row4), R.drawable._12_4_image, "Resolution", resolution);
        setRow(findViewById(R.id.row5), R.drawable._12_5_image_cal, "Date & Time", dateText);
    }

    private void setRow(View row, int icon, String label, String value) {
        if (row == null) return;
        ImageView img = row.findViewById(R.id.imgIcon);
        TextView txtLabel = row.findViewById(R.id.txtLabel);
        TextView txtValue = row.findViewById(R.id.txtValue);

        img.setImageResource(icon);
        txtLabel.setText(label + " : ");
        txtValue.setText(value);
    }

    // --- REMAINDER OF YOUR OSM AND GPS METHODS ---
    // (Ensure you use Nominatim for ACTUAL worldwide address lookup)

    private void setupHttpsTiles() {
        org.osmdroid.tileprovider.tilesource.ITileSource httpsSource =
                new org.osmdroid.tileprovider.tilesource.XYTileSource(
                        "OSM HTTPS", 1, 20, 256, ".png",
                        new String[]{"https://tile.openstreetmap.org/"}
                );
        map.setTileSource(httpsSource);
        map.setUseDataConnection(true);
    }

    private void updateAddress(String text) {
        TextView txtAddress = findViewById(R.id.addressCard).findViewById(R.id.txtAddress);
        if (txtAddress != null) txtAddress.setText(text);
    }

    private void getPhotoLocation(String uriString) {
        new Thread(() -> {
            try {
                Uri uri = Uri.parse(uriString);
                try (InputStream is = getContentResolver().openInputStream(uri)) {
                    if (is == null) {
                        runOnUiThread(() -> updateAddress("Unable to access image data"));
                        return;
                    }

                    androidx.exifinterface.media.ExifInterface exif = new androidx.exifinterface.media.ExifInterface(is);
                    float[] latLong = new float[2];

                    if (exif.getLatLong(latLong)) {
                        double lat = latLong[0];
                        double lon = latLong[1];

                        runOnUiThread(() -> {
                            setupMap(lat, lon);
                            updateAddress("Fetching address...");
                        });

                        // Nominatim provides ACTUAL worldwide addresses
                        String address = fetchAddressFromOSM(lat, lon);
                        runOnUiThread(() -> updateAddress(address));
                    } else {
                        runOnUiThread(() -> updateAddress("No GPS found in this photo"));
                    }
                }
            } catch (Exception e) {
                runOnUiThread(() -> updateAddress("Error reading image metadata"));
            }
        }).start();
    }

    private void setupMap(double lat, double lon) {
        if (map == null) return;
        GeoPoint startPoint = new GeoPoint(lat, lon);
        map.getController().setZoom(17.0);
        map.getController().setCenter(startPoint);
        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("Photo Location");
        map.getOverlays().clear();
        map.getOverlays().add(startMarker);
        map.invalidate();
    }

    private String fetchAddressFromOSM(double lat, double lon) {
        HttpURLConnection conn = null;
        try {
            String urlStr = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", lat, lon);
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", getPackageName());
            conn.setRequestProperty("Referer", "https://openstreetmap.org");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();
                JSONObject json = new JSONObject(response.toString());
                return json.optString("display_name", "Address not available");
            }
            return "Server Error: " + conn.getResponseCode();
        } catch (Exception e) {
            return "Network Error: Check connection";
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    @Override
    protected void onResume() { super.onResume(); if (map != null) map.onResume(); }
    @Override
    protected void onPause() { super.onPause(); if (map != null) map.onPause(); }
}


  //  try {
//                        uri = MediaStore.setRequireOriginal(uri);
//                    } catch (SecurityException e) {
//                        // This happens if the user hasn't granted ACCESS_MEDIA_LOCATION
//                        runOnUiThread(() -> updateAddress("Permission Denied: Cannot access GPS info"));
//                        return;
//                    }
//    //    private void getPhotoLocation(String uriString) {
////        new Thread(() -> {
////            InputStream inputStream = null;
////            try {
////                Uri uri = Uri.parse(uriString);
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
////                    uri = MediaStore.setRequireOriginal(uri); // Required for GPS access
////                }
////                inputStream = getContentResolver().openInputStream(uri);
////
////                if (inputStream == null) {
////                    runOnUiThread(() -> updateAddress("No GPS metadata found"));
////                    return;
////                }
////
////                androidx.exifinterface.media.ExifInterface exif = new androidx.exifinterface.media.ExifInterface(inputStream);
////                float[] latLong = new float[2];
////
////                if (!exif.getLatLong(latLong)) {
////                    // Check if the file actually has coordinates in the header strings
////                    String lat = exif.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_GPS_LATITUDE);
////                    android.util.Log.d("GPS_DEBUG", "Raw Latitude: " + lat);
////
////                    runOnUiThread(() -> updateAddress("No GPS metadata found"));
////                    return;
////                }
////
////                double lat = latLong[0];
////                double lon = latLong[1];
////
////                // Update Map UI
////                runOnUiThread(() -> setupMap(lat, lon));
////
////                // Manual Network Fetch (Works worldwide without Google Key)
////                String address = fetchAddressFromOSM(lat, lon);
////                runOnUiThread(() -> updateAddress(address));
////
////            } catch (Exception e) {
////                runOnUiThread(() -> updateAddress("Network error: Check Internet"));
////            } finally {
////                try { if (inputStream != null) inputStream.close(); } catch (Exception ignored) {}
////            }
////        }).start();
////    }       // -------- FIX 3: OSMDroid cache path (Android 10+) --------
//        // IMPORTANT: OSMDroid needs this before setContentView
////        Configuration.getInstance().setUserAgentValue(getPackageName());
//
////        Configuration.getInstance().load(
////                getApplicationContext(),
////                PreferenceManager.getDefaultSharedPreferences(this)
////        );
////        Configuration.getInstance().setUserAgentValue(getPackageName());
//        //        map.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
//    private String fetchAddressFromOSM(double lat, double lon) {
//        try {
//            // Nominatim API - Worldwide & Free
//            String urlStr = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", lat, lon);
//            URL url = new URL(urlStr);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("User-Agent", getPackageName());
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) response.append(line);
//            reader.close();
//
//            JSONObject json = new JSONObject(response.toString());
//            return json.optString("display_name", "Address not available");
//        } catch (Exception e) {
//            return "Network Error: Could not fetch address";
//        }
//    }
    //package com.example.gunjan_siddhisoftwarecompany;
//
//import android.database.Cursor;
//import android.graphics.BitmapFactory;
//import android.location.Address;
//import android.location.Geocoder;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import java.io.File;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class image12file extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.image_info_12);
////        findViewById(R.id.addressCard).findViewById(R.id.txtValue);
//
//        ImageView btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(v -> finish());
//
//        String imageUriString = getIntent().getStringExtra("imageUri");
//
//        if (imageUriString == null) return;
//
//        Uri imageUri = Uri.parse(imageUriString);
//
//        // File name (SAFE way)
//        String fileName = "Unknown";
//        long fileSizeBytes = 0;
//        long dateTaken = 0;
//
//        Cursor cursor = getContentResolver().query(
//                imageUri,
//                new String[]{
//                        MediaStore.Images.Media.DISPLAY_NAME,
//                        MediaStore.Images.Media.SIZE,
//                        MediaStore.Images.Media.DATE_TAKEN
//                },
//                null,
//                null,
//                null
//        );
//
//        if (cursor != null && cursor.moveToFirst()) {
//            fileName = cursor.getString(0);
//            fileSizeBytes = cursor.getLong(1);
//            dateTaken = cursor.getLong(2);
//            cursor.close();
//        }
//
//        String fileSize = (fileSizeBytes / 1024) + " KB";
//
//        String dateText = new SimpleDateFormat(
//                "MMM dd, yyyy HH:mm:ss",
//                Locale.getDefault()
//        ).format(new Date(dateTaken));
//
//        // Resolution
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//
//        InputStream is = null;
//        try {
//            is = getContentResolver().openInputStream(imageUri);
//            BitmapFactory.decodeStream(is, null, options);
//        } catch (Exception ignored) {
//        } finally {
//            try {
//                if (is != null) is.close();
//            } catch (Exception ignored) {
//            }
//        }
//
//        String resolution = options.outWidth + " x " + options.outHeight;
//
//        setRow(findViewById(R.id.row1), R.drawable._12_2_image_folder, "File Name", fileName);
//        setRow(findViewById(R.id.row2), R.drawable._12_1_image_folder, "Image Uri", imageUriString);
//        setRow(findViewById(R.id.row3), R.drawable._12_3_image_folder, "File Size", fileSize);
//        setRow(findViewById(R.id.row4), R.drawable._12_4_image, "Resolution", resolution);
//        setRow(findViewById(R.id.row5), R.drawable._12_5_image_cal, "Date & Time", dateText);
//        getPhotoLocation(imageUriString);
//    }
//
//    private void setRow(View row, int icon, String label, String value) {
//        ImageView img = row.findViewById(R.id.imgIcon);
//        TextView txtLabel = row.findViewById(R.id.txtLabel);
//        TextView txtValue = row.findViewById(R.id.txtValue);
//
//        img.setImageResource(icon);
//        txtLabel.setText(label + " : ");
//        txtValue.setText(value);
//    }
//
//    private void updateAddress(String text) {
//        TextView txtAddress =
//                findViewById(R.id.addressCard).findViewById(R.id.txtAddress);
//
//        if (txtAddress != null) {
//            txtAddress.setText(text);
//        }
//    }
//
//    private void getPhotoLocation(String uriString) {
//        new Thread(() -> {
//            InputStream inputStream = null;
//            try {
//                Uri uri = Uri.parse(uriString);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    uri = MediaStore.setRequireOriginal(uri);
//                }
//                inputStream = getContentResolver().openInputStream(uri);
//
//                if (inputStream == null) {
//                    runOnUiThread(() -> updateAddress("No GPS metadata found"));
//                    return;
//                }
//
//                androidx.exifinterface.media.ExifInterface exif =
//                        new androidx.exifinterface.media.ExifInterface(inputStream);
//
//                float[] latLong = new float[2];
//
//                if (!exif.getLatLong(latLong)) {
//                    runOnUiThread(() -> updateAddress("No GPS metadata found"));
//                    return;
//                }
//
//                double latitude = latLong[0];
//                double longitude = latLong[1];
//                if (!Geocoder.isPresent()) {
//                    runOnUiThread(() -> updateAddress("Geocoder service not available"));
//                    return;
//                }
//                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//                List<Address> addresses =
//                        geocoder.getFromLocation(latitude, longitude, 1);
//
//                if (addresses != null && !addresses.isEmpty()) {
//                    String address = addresses.get(0).getAddressLine(0);
//                    runOnUiThread(() -> updateAddress(address));
//                } else {
//                    runOnUiThread(() -> updateAddress("Address not available"));
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace(); // View this in Logcat to see the real error!
//                runOnUiThread(() -> updateAddress("Network error: Unable to fetch address"));
//            }finally {
//                try {
//                    if (inputStream != null) inputStream.close();
//                } catch (Exception ignored) {}
//            }
//        }).start();
//    }
//
//

//    private void getPhotoLocation(String uriString) {
//        try {
//            Uri uri = Uri.parse(uriString);
//            InputStream inputStream = getContentResolver().openInputStream(uri);
//
//            if (inputStream != null) {
//                androidx.exifinterface.media.ExifInterface exif = new androidx.exifinterface.media.ExifInterface(inputStream);
//                float[] latLong = new float[2];
//
//                if (exif.getLatLong(latLong)) {
//                    double latitude = latLong[0];
//                    double longitude = latLong[1];
//
//                    // 1. Get the Address (Geocoder)
//                    android.location.Geocoder geocoder = new android.location.Geocoder(this, Locale.getDefault());
//                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//
//                    if (addresses != null && !addresses.isEmpty()) {
//                        String address = addresses.get(0).getAddressLine(0);
////
////                        TextView txtAddress = findViewById(R.id.addressCard).findViewById(R.id.txtValue);
//                        TextView txtAddress = findViewById(R.id.txtAddress);
//                        if (txtAddress != null) txtAddress.setText(address);
//                    }
//
//                    // 2. Load the Real Map Image
//                    // REPLACE 'YOUR_API_KEY' with your actual Google Maps API Key
////                    String apiKey = "YOUR_API_KEY";
//                    String mapUrl = "https://static-maps.yandex.ru/1.x/?lang=en_US&ll="
//
//                            + longitude + "," + latitude + "&z=15&l=map&pt="
//                            + longitude + "," + latitude + ",pm2rdl";
//                    ImageView imgMap = findViewById(R.id.imgMap);
//                    if (imgMap != null) {
//                    com.bumptech.glide.Glide.with(this).
//                            load(mapUrl).
//                            placeholder(R.drawable.map_location_09)
//                            .into(imgMap);}
////
////                    ImageView imgMap = findViewById(R.id.imgMap);
////
////                    if (imgMap != null) {
////                        com.bumptech.glide.Glide.with(this)
////                                .load(mapUrl)
////                                .placeholder(R.drawable.map_location_09)
//////                                .centerCrop()
////                                .into(imgMap);
////                    }
//
//                } else {
//                    Toast.makeText(this, "No GPS metadata found", Toast.LENGTH_SHORT).show();
//                }
//                inputStream.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

