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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // IMPORTANT: OSMDroid needs this before setContentView
//        Configuration.getInstance().setUserAgentValue(getPackageName());

//        Configuration.getInstance().load(
//                getApplicationContext(),
//                PreferenceManager.getDefaultSharedPreferences(this)
//        );
//        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.image_info_12);

        map = findViewById(R.id.mapview);
        setupHttpsTiles();
//        map.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
// -------- FIX 3: OSMDroid cache path (Android 10+) --------
        File osmdroidBasePath = new File(getFilesDir(), "osmdroid");
        File osmdroidTileCache = new File(osmdroidBasePath, "tiles");

        Configuration.getInstance().setOsmdroidBasePath(osmdroidBasePath);
        Configuration.getInstance().setOsmdroidTileCache(osmdroidTileCache);

        Configuration.getInstance().load(
                getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(this)
        );
        Configuration.getInstance().setUserAgentValue(getPackageName());
// ---------------------------------------------------------

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        String imageUriString = getIntent().getStringExtra("imageUri");
        if (imageUriString == null) return;

        Uri imageUri = Uri.parse(imageUriString);

        // Fill the labels (File Name, Size, etc.)
        fetchImageMetadata(imageUri);

        // Load Location and Address
        getPhotoLocation(imageUriString);
    }
    private void setupHttpsTiles() {
        org.osmdroid.tileprovider.tilesource.ITileSource httpsSource =
                new org.osmdroid.tileprovider.tilesource.XYTileSource(
                        "OSM HTTPS",
                        1, 20, 256, ".png",
                        new String[]{
                                "https://tile.openstreetmap.org/"
                        }
                );

        map.setTileSource(httpsSource);
        map.setUseDataConnection(true);
    }

    private void fetchImageMetadata(Uri imageUri) {
        String fileName = "Unknown";
        long fileSizeBytes = 0;
        long dateTaken = 0;

        try (Cursor cursor = getContentResolver().query(
                imageUri,
                new String[]{
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.DATE_TAKEN
                }, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                fileName = cursor.getString(0);
                fileSizeBytes = cursor.getLong(1);
                dateTaken = cursor.getLong(2);
            }
        }

        String fileSize = (fileSizeBytes / 1024) + " KB";
        String dateText = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(new Date(dateTaken));

        // Get Resolution
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try (InputStream is = getContentResolver().openInputStream(imageUri)) {
            BitmapFactory.decodeStream(is, null, options);
        } catch (Exception ignored) {}

        String resolution = options.outWidth + " x " + options.outHeight;

        // Set the rows in your UI
        setRow(findViewById(R.id.row1), R.drawable._12_2_image_folder, "File Name", fileName);
        setRow(findViewById(R.id.row2), R.drawable._12_1_image_folder, "Image Uri", imageUri.toString());
        setRow(findViewById(R.id.row3), R.drawable._12_3_image_folder, "File Size", fileSize);
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

    private void updateAddress(String text) {
        TextView txtAddress = findViewById(R.id.addressCard).findViewById(R.id.txtAddress);
        if (txtAddress != null) {
            txtAddress.setText(text);
        }
    }

    private void getPhotoLocation(String uriString) {
        new Thread(() -> {
            InputStream inputStream = null;
            try {
                Uri uri = Uri.parse(uriString);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    uri = MediaStore.setRequireOriginal(uri); // Required for GPS access
                }
                inputStream = getContentResolver().openInputStream(uri);

                if (inputStream == null) {
                    runOnUiThread(() -> updateAddress("No GPS metadata found"));
                    return;
                }

                androidx.exifinterface.media.ExifInterface exif = new androidx.exifinterface.media.ExifInterface(inputStream);
                float[] latLong = new float[2];

                if (!exif.getLatLong(latLong)) {
                    // Check if the file actually has coordinates in the header strings
                    String lat = exif.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_GPS_LATITUDE);
                    android.util.Log.d("GPS_DEBUG", "Raw Latitude: " + lat);

                    runOnUiThread(() -> updateAddress("No GPS metadata found"));
                    return;
                }

                double lat = latLong[0];
                double lon = latLong[1];

                // Update Map UI
                runOnUiThread(() -> setupMap(lat, lon));

                // Manual Network Fetch (Works worldwide without Google Key)
                String address = fetchAddressFromOSM(lat, lon);
                runOnUiThread(() -> updateAddress(address));

            } catch (Exception e) {
                runOnUiThread(() -> updateAddress("Network error: Check Internet"));
            } finally {
                try { if (inputStream != null) inputStream.close(); } catch (Exception ignored) {}
            }
        }).start();
    }

    private void setupMap(double lat, double lon) {
        if (map == null) return;
        GeoPoint startPoint = new GeoPoint(lat, lon);
        map.getController().setZoom(17.0);
        map.getController().setCenter(startPoint);
        map.setMultiTouchControls(true);

        // Add a red pin at the location
        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("Photo Location");
        map.getOverlays().clear();
        map.getOverlays().add(startMarker);
        map.invalidate(); // Refresh map
    }

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
private String fetchAddressFromOSM(double lat, double lon) {
    HttpURLConnection conn = null;
    try {
        // Nominatim API - Worldwide & Free (OpenStreetMap)
        String urlStr = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", lat, lon);
        URL url = new URL(urlStr);

        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // IMPORTANT: Nominatim requires a User-Agent and prefers a Referer
        conn.setRequestProperty("User-Agent", getPackageName());
        conn.setRequestProperty("Referer", "https://openstreetmap.org");

        // Timeouts prevent the app from getting stuck on a bad connection
        conn.setConnectTimeout(10000); // 10 seconds
        conn.setReadTimeout(10000);    // 10 seconds

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            // "display_name" is the full formatted address string
            return json.optString("display_name", "Address not available");
        } else {
            return "Server Error: " + responseCode;
        }
    } catch (java.net.SocketTimeoutException e) {
        return "Network Error: Request timed out";
    } catch (Exception e) {
        e.printStackTrace();
        return "Network Error: Check Internet connection";
    } finally {
        if (conn != null) {
            conn.disconnect();
        }
    }
}
    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) map.onPause();
    }

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
}
