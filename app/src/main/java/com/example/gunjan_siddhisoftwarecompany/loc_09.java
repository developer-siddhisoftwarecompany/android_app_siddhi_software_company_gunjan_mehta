//

package com.example.gunjan_siddhisoftwarecompany;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class loc_09 extends AppCompatActivity {

    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
    private static final int REQ_MANUAL_ENTRY = 102;

    private MapView map;
    private View blackOverlay;
    private double lat, lon;
    private TextView txtCurrent, txtManual, btnCancel, btnSave, txtMapAddress;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load configuration before setContentView
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.location_09_in_figma);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // ===== INIT VIEWS =====
        blackOverlay = findViewById(R.id.blackOverlay);
        txtCurrent = findViewById(R.id.txtCurrent);
        txtManual = findViewById(R.id.txtManual);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        txtMapAddress = findViewById(R.id.txtMapAddress);
        map = findViewById(R.id.mapview);

        setupMapSettings();

        // Get coordinates from intent
        lat = getIntent().getDoubleExtra("lat", 0.0);
        lon = getIntent().getDoubleExtra("lon", 0.0);

        if (lat != 0.0 && lon != 0.0) {
            updateLocationAndAddress(lat, lon);
        } else {
            // If coordinates are 0.0, fetch current location automatically
            fetchCurrentGPSLocation();
        }

        setCurrentSelectedUI();

        // ===== CLICK LISTENERS =====
        txtCurrent.setOnClickListener(v -> {
            setCurrentSelectedUI();
            fetchCurrentGPSLocation();
        });

        txtManual.setOnClickListener(v -> {
            Intent intent = new Intent(this, loc10.class);
            startActivityForResult(intent, REQ_MANUAL_ENTRY);
        });

        btnSave.setOnClickListener(v -> {
            String finalAddress = txtMapAddress.getText().toString();

            // Validation: Don't allow saving if address failed or is still loading
            if (finalAddress.isEmpty() || finalAddress.contains("GPS signal not found") || finalAddress.contains("Loading")) {
                Toast.makeText(this, "Please wait for valid location", Toast.LENGTH_SHORT).show();
                return;
            }

            SettingsStore.save(this, KEY_LOCATION_MODE, "current");

            // PROFESSIONAL FIX: Send the actual address back to the editor
            Intent result = new Intent();
            result.putExtra("location_mode", "current");
            result.putExtra("manual_address", finalAddress);
            setResult(RESULT_OK, result);
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
        blackOverlay.setOnClickListener(v -> finish());
    }

    @SuppressLint("MissingPermission")
    private void fetchCurrentGPSLocation() {
        txtMapAddress.setText("Loading location...");
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                updateLocationAndAddress(lat, lon);
            } else {
                txtMapAddress.setText("GPS signal not found. Please check your location settings.");
            }
        });
    }

    private void updateLocationAndAddress(double latitude, double longitude) {
        showLocationOnMap(latitude, longitude);
        new Thread(() -> {
            String liveAddress = fetchAddressFromOSM(latitude, longitude);
            runOnUiThread(() -> txtMapAddress.setText(liveAddress));
        }).start();
    }

    private void setupMapSettings() {
        Configuration.getInstance().setOsmdroidTileCache(getCacheDir());
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(17.0);
    }

    private void showLocationOnMap(double latitude, double longitude) {
        GeoPoint node = new GeoPoint(latitude, longitude);
        map.getController().setCenter(node);

        Marker startMarker = new Marker(map);
        startMarker.setPosition(node);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

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

            // PROFESSIONAL FIX: Unique User-Agent to prevent getting blocked by OSM
            conn.setRequestProperty("User-Agent", "GunjanSiddhiStampApp/1.1");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);

                JSONObject json = new JSONObject(response.toString());
                return json.optString("display_name", "Address not found");
            }
        } catch (Exception e) {
            Log.e("LOCATION_ERROR", "Fetch failed", e);
        } finally {
            if (conn != null) conn.disconnect();
        }
        return "Network error. Could not load address.";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_MANUAL_ENTRY && resultCode == RESULT_OK && data != null) {
            data.putExtra("location_mode", "manual");
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void setCurrentSelectedUI() {
        txtCurrent.setTextColor(0xFF0A84FF);
        txtManual.setTextColor(0xFF6B6B6B);
        View mapContainer = findViewById(R.id.mapContainer);
        if (mapContainer != null) {
            mapContainer.setVisibility(View.VISIBLE);
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
}






//donr
//    private String fetchAddressFromOSM(double lat, double lon) {
//        HttpURLConnection conn = null;
//        try {
//            String urlStr = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", lat, lon);
//            URL url = new URL(urlStr);
//            conn = (HttpURLConnection) url.openConnection();
////            conn.setRequestProperty("User-Agent", getPackageName());
//            conn.setRequestProperty("User-Agent", "MyCustomCameraApp/1.0 (contact@example.com)");
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) response.append(line);
//
//                JSONObject json = new JSONObject(response.toString());
//                return json.optString("display_name", "Address not found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) conn.disconnect();
//        }
//        return "Network Error";
//    }

   //done
//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.preference.PreferenceManager;
//
//import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.model.Marker;
//
//import org.json.JSONObject;
//import org.osmdroid.config.Configuration;
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
//import org.osmdroid.util.GeoPoint;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Locale;
//
//public class loc_09 extends AppCompatActivity {
//
//    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
//    private static final int REQ_MANUAL_ENTRY = 102;
//    private MapView map;
//    private View blackOverlay;
//    private double lat, lon;
//    private TextView txtCurrent, txtManual, btnCancel, btnSave,txtMapAddress;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.location_09_in_figma);
//        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
//        double lat = getIntent().getDoubleExtra("lat", 0.0);
//        double lon = getIntent().getDoubleExtra("lon", 0.0);
//
//        // ===== INIT VIEWS =====
//        blackOverlay = findViewById(R.id.blackOverlay);
//        txtCurrent = findViewById(R.id.txtCurrent);
//        txtManual = findViewById(R.id.txtManual);
//        btnCancel = findViewById(R.id.btnCancel);
//        btnSave = findViewById(R.id.btnSave);
//        txtMapAddress = findViewById(R.id.txtMapAddress);
//        map = findViewById(R.id.mapview);
//        //loc to come in the textbox
//
//        //loc
//        setupMapSettings();
//        if (lat != 0.0 && lon != 0.0) {
//            showLocationOnMap(lat, lon);
//        }
//        // Fetch address in a background thread to avoid crashing the UI
//        new Thread(() -> {
//            String liveAddress = fetchAddressFromOSM(lat, lon);
//            runOnUiThread(() -> txtMapAddress.setText(liveAddress));
//        }).start();
//
//        // ===== DEFAULT STATE (CURRENT SELECTED) =====
//        setCurrentSelectedUI();
//
//        // ===== CURRENT CLICK =====
//        txtCurrent.setOnClickListener(v -> setCurrentSelectedUI());
//
//        // ===== MANUAL CLICK → OPEN loc10 DIRECTLY =====
//        txtManual.setOnClickListener(v -> {
//            Intent intent = new Intent(this, loc10.class);
//            startActivityForResult(intent, REQ_MANUAL_ENTRY);
//        });
//
//        // ===== OK BUTTON (CURRENT ONLY) =====
//        btnSave.setOnClickListener(v -> {
//            SettingsStore.save(this, KEY_LOCATION_MODE, "current");
//
//            Intent result = new Intent();
//            result.putExtra("location_mode", "current");
//            setResult(RESULT_OK, result);
//            finish();
//        });
//
//        // ===== CANCEL / OVERLAY =====
//        btnCancel.setOnClickListener(v -> finish());
//        blackOverlay.setOnClickListener(v -> finish());
//    }
//
//    private void setupMapSettings() {
//        map.setTileSource(TileSourceFactory.MAPNIK);
//        map.setMultiTouchControls(true);
//        map.getController().setZoom(17.0);
//    }
//    private void showLocationOnMap(double latitude, double longitude) {
//        GeoPoint node = new GeoPoint(latitude, longitude);
//        map.getController().setCenter(node);
//
//        Marker startMarker = new Marker(map);
//        startMarker.setPosition(node);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        map.getOverlays().clear();
//        map.getOverlays().add(startMarker);
//        map.invalidate();
//    }
//    private String fetchAddressFromOSM(double lat, double lon) {
//        HttpURLConnection conn = null;
//        try {
//            String urlStr = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f", lat, lon);
//            URL url = new URL(urlStr);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("User-Agent", getPackageName());
//
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) response.append(line);
//
//                JSONObject json = new JSONObject(response.toString());
//                return json.optString("display_name", "Address not found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) conn.disconnect();
//        }
//        return "Network Error";
//    }
//    // ===== RECEIVE MANUAL ADDRESS FROM loc10 =====
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQ_MANUAL_ENTRY && resultCode == RESULT_OK && data != null) {
//            data.putExtra("location_mode", "manual");
//            setResult(RESULT_OK, data);
//            finish();
//        }
//    }
//
//    // ===== UI HELPERS =====
//    private void setCurrentSelectedUI() {
//        txtCurrent.setTextColor(0xFF0A84FF); // Blue
//        txtManual.setTextColor(0xFF6B6B6B);  // Grey
//
//        View mapContainer = findViewById(R.id.mapContainer);
//        if (mapContainer != null) {
//            mapContainer.setVisibility(View.VISIBLE);
//        }
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // This is vital for osmdroid to start loading tiles
//        if (map != null) map.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // This saves memory when the user leaves the screen
//        if (map != null) map.onPause();
//    }
//}

//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//
//public class loc_09 extends AppCompatActivity {
//
//    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
//    private static final int REQ_MANUAL_ENTRY = 102;
//
//    View blackOverlay, locationPopup;
//    TextView txtCurrent, txtManual, btnCancel, btnSave;
//    String selectedMode = "current";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.location_09_in_figma);
//
//        // 1. Init Views
//        blackOverlay = findViewById(R.id.blackOverlay);
//        locationPopup = findViewById(R.id.locationPopup);
//        txtCurrent = findViewById(R.id.txtCurrent);
//        txtManual = findViewById(R.id.txtManual);
//        btnCancel = findViewById(R.id.btnCancel);
//        btnSave = findViewById(R.id.btnSave);
//
//        // 2. Click Listeners
//        txtCurrent.setOnClickListener(v -> {
//            selectedMode = "current";
//            updateSelectionUI();
//        });
//
//        txtManual.setOnClickListener(v -> {
//            selectedMode = "manual";
//            updateSelectionUI();
//        });
//
//        btnCancel.setOnClickListener(v -> finish());
//
//        // FIX: Only one Save listener. It handles the logic based on selectedMode.
//        btnSave.setOnClickListener(v -> {
//            SettingsStore.save(this, KEY_LOCATION_MODE, selectedMode);
//
//            if (selectedMode.equals("manual")) {
//                // Open the typing screen (loc10)
//                Intent intent = new Intent(this, loc10.class);
//                startActivityForResult(intent, REQ_MANUAL_ENTRY);
//            } else {
//                // Return to main screen for "Automatic" mode
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("location_mode", "automatic");
//                setResult(RESULT_OK, resultIntent);
//                finish();
//            }
//        });
//
//        blackOverlay.setOnClickListener(v -> finish());
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQ_MANUAL_ENTRY && resultCode == RESULT_OK && data != null) {
//            // Forward the address data from loc10 back to stamp_0_up
//            data.putExtra("location_mode", "manual");
//            setResult(RESULT_OK, data);
//            finish();
//        }
//    }
//
//    private void updateSelectionUI() {
//        if (selectedMode.equals("current")) {
//            txtCurrent.setTextColor(0xFF0A84FF);
//            txtManual.setTextColor(0xFF6B6B6B);
//            findViewById(R.id.mapContainer).setVisibility(View.VISIBLE);
//            // Hide the internal manual box if it exists in this layout
//            View manualBox = findViewById(R.id.manualAddressBox);
//            if (manualBox != null) manualBox.setVisibility(View.GONE);
//        } else {
//            txtManual.setTextColor(0xFF0A84FF);
//            txtCurrent.setTextColor(0xFF6B6B6B);
//            findViewById(R.id.mapContainer).setVisibility(View.GONE);
//            View manualBox = findViewById(R.id.manualAddressBox);
//            if (manualBox != null) manualBox.setVisibility(View.VISIBLE);
//        }
//    }
//}
////package com.example.gunjan_siddhisoftwarecompany;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.view.View;
////import android.widget.TextView;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
////
////public class loc_09 extends AppCompatActivity {
////
////    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
////    private static final int REQ_MANUAL_ENTRY = 102;
////
////    View blackOverlay, locationPopup;
////    TextView txtCurrent, txtManual, btnCancel, btnSave;
////    String selectedMode = "current";
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.location_09_in_figma);
////
////        // 1. Initialize Views
////        blackOverlay = findViewById(R.id.blackOverlay);
////        locationPopup = findViewById(R.id.locationPopup);
////        txtCurrent = findViewById(R.id.txtCurrent);
////        txtManual = findViewById(R.id.txtManual);
////        btnCancel = findViewById(R.id.btnCancel);
////        btnSave = findViewById(R.id.btnSave);
////        View closeBtn = findViewById(R.id.btnClose);
////
////        // 2. Set Initial UI state
////        updateSelectionUI();
////
////        // 3. Mode Selection Listeners
////        txtCurrent.setOnClickListener(v -> {
////            selectedMode = "current";
////            updateSelectionUI();
////        });
////
////        txtManual.setOnClickListener(v -> {
////            selectedMode = "manual";
////            updateSelectionUI();
////        });
////
////        // 4. Action Buttons
////        btnCancel.setOnClickListener(v -> finish());
////
////        if (closeBtn != null) {
////            closeBtn.setOnClickListener(v -> finish());
////        }
////
////        btnSave.setOnClickListener(v -> {
////            // Save the mode preference
////            SettingsStore.save(this, KEY_LOCATION_MODE, selectedMode);
////
////            if (selectedMode.equals("manual")) {
////                // If manual is selected, open loc10 to get the address text
////                Intent intent = new Intent(this, loc10.class);
////                startActivityForResult(intent, REQ_MANUAL_ENTRY);
////            } else {
////                // If "current" (Automatic) is selected, return a flag to the main screen
////                Intent resultIntent = new Intent();
////                resultIntent.putExtra("location_mode", "automatic");
////                setResult(RESULT_OK, resultIntent);
////                finish();
////            }
////        });
////
////        // Prevent clicks on the popup from closing it, but click on overlay closes it
////        locationPopup.setOnClickListener(v -> { /* Consume click */ });
////        blackOverlay.setOnClickListener(v -> finish());
////    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == REQ_MANUAL_ENTRY && resultCode == RESULT_OK && data != null) {
////            // Forward the manual address from loc10 back to the main activity (stamp_0_up)
////            // We also add the "manual" flag so the main activity knows what to do
////            data.putExtra("location_mode", "manual");
////            setResult(RESULT_OK, data);
////            finish();
////        }
////    }
////
////    private void updateSelectionUI() {
////        if (selectedMode.equals("current")) {
////            txtCurrent.setTextColor(0xFF0A84FF); // Active Blue
////            txtManual.setTextColor(0xFF6B6B6B);  // Inactive Gray
////
////            // Show the map part, hide the manual box
////            if (findViewById(R.id.mapContainer) != null) {
////                findViewById(R.id.mapContainer).setVisibility(View.VISIBLE);
////            }
////            if (findViewById(R.id.manualAddressBox) != null) {
////                findViewById(R.id.manualAddressBox).setVisibility(View.GONE);
////            }
////        } else {
////            txtManual.setTextColor(0xFF0A84FF); // Active Blue
////            txtCurrent.setTextColor(0xFF6B6B6B);  // Inactive Gray
////
////            // Hide map, show manual box
////            if (findViewById(R.id.mapContainer) != null) {
////                findViewById(R.id.mapContainer).setVisibility(View.GONE);
////            }
////            if (findViewById(R.id.manualAddressBox) != null) {
////                findViewById(R.id.manualAddressBox).setVisibility(View.VISIBLE);
////            }
////        }
////    }
////}
//////package com.example.gunjan_siddhisoftwarecompany;
//////
//////import android.content.Intent;
//////import android.os.Bundle;
//////import android.view.View;
//////import android.widget.TextView;
//////
//////import androidx.appcompat.app.AppCompatActivity;
//////
//////import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//////
//////public class loc_09 extends AppCompatActivity {
//////
//////
//////        private static final String KEY_LOCATION_MODE = "stamp_location_mode";
//////        private static final int REQ_MANUAL_ENTRY = 102;
//////
//////        View blackOverlay, locationPopup;
//////        TextView txtCurrent, txtManual, btnCancel, btnSave;
//////        String selectedMode = "current";
//////
//////        @Override
//////        protected void onCreate(Bundle savedInstanceState) {
//////            super.onCreate(savedInstanceState);
//////            setContentView(R.layout.location_09_in_figma);
//////
//////            // 1. Init Views
//////            blackOverlay = findViewById(R.id.blackOverlay);
//////            locationPopup = findViewById(R.id.locationPopup);
//////            txtCurrent = findViewById(R.id.txtCurrent);
//////            txtManual = findViewById(R.id.txtManual);
//////            btnCancel = findViewById(R.id.btnCancel);
//////            btnSave = findViewById(R.id.btnSave);
//////
//////            // 2. Restore Selection
////////
//////
//////            // 3. Click Listeners
//////            txtCurrent.setOnClickListener(v -> {
//////                selectedMode = "current";
//////                updateSelectionUI();
//////            });
//////
//////            txtManual.setOnClickListener(v -> {
//////                selectedMode = "manual";
//////                updateSelectionUI();
//////            });
//////
//////            btnCancel.setOnClickListener(v -> finish());
//////
////////            btnSave.setOnClickListener(v -> {
////////                SettingsStore.save(this, KEY_LOCATION_MODE, selectedMode);
//////
//////
//////            btnSave.setOnClickListener(v -> {
//////                if (selectedMode.equals("manual")) {
//////                    // Open loc10 to get the custom string
//////                    Intent intent = new Intent(this, loc10.class);
//////                    startActivityForResult(intent, REQ_MANUAL_ENTRY);
//////                } else {
//////                    // Mode is "Current" (Automatic)
//////                    Intent resultIntent = new Intent();
//////                    resultIntent.putExtra("location_mode", "automatic");
//////                    setResult(RESULT_OK, resultIntent);
//////                    finish();
//////                }
//////            });
//////
//////                if (selectedMode.equals("manual")) {
//////                    // Open the typing screen (loc10)
//////                    Intent intent = new Intent(this, loc10.class);
//////                    startActivityForResult(intent, REQ_MANUAL_ENTRY);
//////                } else {
//////                    // Return to main screen for "Automatic" mode
//////                    setResult(RESULT_OK);
//////                    finish();
//////                }
//////            });
//////
//////            locationPopup.setOnClickListener(v -> { /* Just to consume clicks */ });
//////            blackOverlay.setOnClickListener(v -> finish());
//////
//////            // Ensure you have this ID in your XML or remove this line
//////            View closeBtn = findViewById(R.id.btnClose);
//////            if (closeBtn != null) {
//////                closeBtn.setOnClickListener(v -> finish());
//////            }
//////        }
//////
//////        // This method MUST be outside onCreate
//////        @Override
//////        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//////            super.onActivityResult(requestCode, resultCode, data);
//////            if (requestCode == REQ_MANUAL_ENTRY && resultCode == RESULT_OK && data != null) {
//////                // Forward the address data from loc10 back to stamp_0_up
//////                setResult(RESULT_OK, data);
//////                finish();
//////            }
//////        }
//////    private void updateSelectionUI() {
//////        if (selectedMode.equals("current")) {
//////            txtCurrent.setTextColor(0xFF0A84FF); // Blue
//////            txtManual.setTextColor(0xFF6B6B6B);  // Gray
//////
//////            // Show the map part, hide the manual text box
//////
//////                findViewById(R.id.mapContainer).setVisibility(View.VISIBLE);
//////
//////                findViewById(R.id.manualAddressBox).setVisibility(View.GONE);
//////
//////        } else {
//////            txtManual.setTextColor(0xFF0A84FF); // Blue
//////            txtCurrent.setTextColor(0xFF6B6B6B);  // Gray
//////
//////            // Hide map, show manual text box
//////
//////                findViewById(R.id.mapContainer).setVisibility(View.GONE);
//////
//////                findViewById(R.id.manualAddressBox).setVisibility(View.VISIBLE);
//////        }
//////    }
////////    private void updateSelectionUI() {
////////        if (selectedMode.equals("current")) {
////////            txtCurrent.setTextColor(0xFF0A84FF); // Blue
////////            txtManual.setTextColor(0xFF6B6B6B);  // Gray
////////            findViewById(R.id.mapContainer).setVisibility(View.VISIBLE);
////////            findViewById(R.id.manualAddressBox).setVisibility(View.GONE);
////////
////////        } else {
////////            txtManual.setTextColor(0xFF0A84FF); // Blue
////////            txtCurrent.setTextColor(0xFF6B6B6B);  // Gray
////////            findViewById(R.id.mapContainer).setVisibility(View.GONE);
////////            findViewById(R.id.manualAddressBox).setVisibility(View.VISIBLE);
////////        }
////////    }
////////        private void updateSelectionUI() {
////////            if (selectedMode.equals("current")) {
////////                txtCurrent.setTextColor(0xFF0A84FF); // Blue
////////                txtManual.setTextColor(0xFF6B6B6B);  // Gray
////////            } else {
////////                txtManual.setTextColor(0xFF0A84FF); // Blue
////////                txtCurrent.setTextColor(0xFF6B6B6B); // Gray
////////            }
////////        }
////////selectedMode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
//////////            updateSelectionUI();
//////
////////    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
////////    private static final int REQ_MANUAL_ENTRY = 102; // Unique code for loc10
////////
////////    View blackOverlay, locationPopup;
////////    TextView txtCurrent, txtManual, btnCancel, btnSave;
////////    String selectedMode = "current";
////////
////////    @Override
////////    protected void onCreate(Bundle savedInstanceState) {
////////        super.onCreate(savedInstanceState);
////////        setContentView(R.layout.location_09_in_figma);
////////
////////        // ... init views as you did ...
////////        blackOverlay = findViewById(R.id.blackOverlay);
////////        locationPopup = findViewById(R.id.locationPopup);
////////        txtCurrent = findViewById(R.id.txtCurrent);
////////        txtManual = findViewById(R.id.txtManual);
////////        btnCancel = findViewById(R.id.btnCancel);
////////        btnSave = findViewById(R.id.btnSave);
////////
////////        selectedMode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
////////        updateSelectionUI();
////////
////////        txtCurrent.setOnClickListener(v -> {
////////            selectedMode = "current";
////////            updateSelectionUI();
////////        });
////////
////////        txtManual.setOnClickListener(v -> {
////////            selectedMode = "manual";
////////            updateSelectionUI();
////////        });
////////
////////        btnCancel.setOnClickListener(v -> finish());
////////
////////        // FIX: Handle Save logic properly
////////        btnSave.setOnClickListener(v -> {
////////            SettingsStore.save(this, KEY_LOCATION_MODE, selectedMode);
////////
////////            if (selectedMode.equals("manual")) {
////////                // Open the typing screen
////////                Intent intent = new Intent(this, loc10.class);
////////                startActivityForResult(intent, REQ_MANUAL_ENTRY);
////////            } else {
////////                // It's "current" (Automatic) - Tell the first screen to update
////////                setResult(RESULT_OK);
////////                finish();
////////            }
////////        });
////////// 2. Add this method to forward the address from loc10 back to the main screen
////////        @Override
////////        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////////            super.onActivityResult(requestCode, resultCode, data);
////////            if (requestCode == 102 && resultCode == RESULT_OK && data != null) {
////////                // Forward the data (manual_address) back to stamp_0_up
////////                setResult(RESULT_OK, data);
////////                finish();
////////            }
////////        }
////////        // Prevent clicks passing through
////////        locationPopup.setOnClickListener(v -> {
////////        });
////////        blackOverlay.setOnClickListener(v -> finish());
////////
////////        findViewById(R.id.btnClose).setOnClickListener(v -> finish());
////////    }
////////
////////    // FIX: Catch the address from loc10 and pass it back to stamp_0_up
////////    @Override
////////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////////        super.onActivityResult(requestCode, resultCode, data);
////////        if (requestCode == REQ_MANUAL_ENTRY && resultCode == RESULT_OK) {
////////            // Forward the result from loc10 directly back to stamp_0_up
////////            setResult(RESULT_OK, data);
////////            finish();
////////        }
////////    }
////////
////////    private void updateSelectionUI() {
////////        if (selectedMode.equals("current")) {
////////            txtCurrent.setTextColor(0xFF0A84FF);
////////            txtManual.setTextColor(0xFF6B6B6B);
////////        } else {
////////            txtManual.setTextColor(0xFF0A84FF);
////////            txtCurrent.setTextColor(0xFF6B6B6B);
////////        }
////////    }
//////
////////    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
////////
////////    View blackOverlay;
////////    View locationPopup;
////////
////////    TextView txtCurrent, txtManual;
////////    TextView btnCancel, btnSave;
////////
////////    String selectedMode = "current"; // default
////////
////////    @Override
////////    protected void onCreate(Bundle savedInstanceState) {
////////        super.onCreate(savedInstanceState);
////////        setContentView(R.layout.location_09_in_figma);
////////
////////        blackOverlay   = findViewById(R.id.blackOverlay);
////////        locationPopup  = findViewById(R.id.locationPopup);
////////
////////        txtCurrent = findViewById(R.id.txtCurrent);
////////        txtManual  = findViewById(R.id.txtManual);
////////
////////        btnCancel = findViewById(R.id.btnCancel);
////////        btnSave   = findViewById(R.id.btnSave);
////////
////////
////////        selectedMode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
////////        updateSelectionUI();
////////
////////        txtCurrent.setOnClickListener(v -> {
////////            selectedMode = "current";
////////            updateSelectionUI();
////////        });
////////
////////        // ===== MANUAL =====
////////        txtManual.setOnClickListener(v -> {
////////            selectedMode = "manual";
////////            updateSelectionUI();
////////        });
////////
////////        // ===== CANCEL =====
////////        btnCancel.setOnClickListener(v -> finish());
////////
////////        // ===== SAVE =====
////////        btnSave.setOnClickListener(v -> {
////////            SettingsStore.save(this, KEY_LOCATION_MODE, selectedMode);
////////
////////
////////
////////            finish();
////////        });
////////
////////
////////    }
////////if (selectedMode.equals("manual")) {
////////        startActivity(new Intent(this, loc10.class));
////////    }
////////    // Prevent clicks passing through
////////        locationPopup.setOnClickListener(v -> {});
////////        blackOverlay.setOnClickListener(v -> finish());
////////    private void updateSelectionUI() {
////////        if (selectedMode.equals("current")) {
////////            txtCurrent.setTextColor(0xFF0A84FF); // blue
////////            txtManual.setTextColor(0xFF6B6B6B);  // gray
////////        } else {
////////            txtManual.setTextColor(0xFF0A84FF);
////////            txtCurrent.setTextColor(0xFF6B6B6B);
////////        }
////////    }
////////}
//////}
