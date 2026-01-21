package com.example.gunjan_siddhisoftwarecompany;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class stamp_0_up extends AppCompatActivity {

    private static final int REQ_LOCATION = 101;
    private static final int REQ_SAVE_DIALOG = 105;

    private static final String KEY_DATE = "stamp_date";
    private static final String KEY_LOCATION = "stamp_location";
    private static final String KEY_ALPHA = "stamp_alpha";
    private static final String KEY_COLOR = "stamp_color";
    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
    private static final String KEY_STAMP_DRAWABLE = "stamp_drawable";

    private ImageView btnBack, btnDone, stampPreview;
    private TextView txtDateValue, addressText, txtTransparencyValue;
    private ImageView c1, c2, c3, c4, c5, c6;

    private SeekBar seekTransparency;
    private TextView txtSeekValue;

    private int currentAlpha = 60;
    private int currentColor = 0xFFFF9800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamp_0_up_layer_figma);

        // ===== INIT VIEWS =====
        btnBack = findViewById(R.id.btnBack);
        btnDone = findViewById(R.id.btnDone);
        stampPreview = findViewById(R.id.stamp);
        txtDateValue = findViewById(R.id.txtDateValue);
        addressText = findViewById(R.id.addressText);
        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);
        txtSeekValue = findViewById(R.id.txtSeekValue);
        seekTransparency = findViewById(R.id.seekTransparency);

        c1 = findViewById(R.id.colorCircle1);
        c2 = findViewById(R.id.colorCircle2);
        c3 = findViewById(R.id.colorCircle3);
        c4 = findViewById(R.id.colorCircle4);
        c5 = findViewById(R.id.colorCircle5);
        c6 = findViewById(R.id.colorCircle6);

        restoreSavedData();

        // ===== CLICK LISTENERS =====
//        btnBack.setOnClickListener(v -> onBackPressed());

        btnDone.setOnClickListener(v -> {
            ChangeTracker.mark();
            finish();
        });

        findViewById(R.id.dateRow).setOnClickListener(v ->
                startActivity(new Intent(this, Date_time_07.class))
        );

        findViewById(R.id.addressRow).setOnClickListener(v -> {
            if (!PermissionUtils.hasLocation(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            startActivityForResult(new Intent(this, loc_09.class), REQ_LOCATION);
        });

        stampPreview.setOnClickListener(v -> {
            if (!SubscriptionUtils.isPremium(this)) {
                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
                return;
            }
            int drawable = R.drawable.christma_text_setting_pg;
            stampPreview.setImageResource(drawable);
            SettingsStore.save(this, KEY_STAMP_DRAWABLE, drawable);
            ChangeTracker.mark();
        });

        // ===== SEEK BAR LOGIC =====
        seekTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 10) progress = 10;
                currentAlpha = progress;
                txtTransparencyValue.setText(progress + " %");
                stampPreview.setAlpha(progress / 100f);
                txtSeekValue.setText(progress + "%");
                txtSeekValue.setVisibility(View.VISIBLE);
                moveSeekLabel(progress);
                SettingsStore.save(stamp_0_up.this, KEY_ALPHA, progress);
                ChangeTracker.mark();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { txtSeekValue.setVisibility(View.VISIBLE); }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        findViewById(R.id.iconTransArrow).setOnClickListener(v -> {
            final String[] values = {"10","20","30","40","50","60","70","80","90","100"};
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Transparency")
                    .setItems(values, (dialog, which) -> {
                        int selected = Integer.parseInt(values[which]);
                        seekTransparency.setProgress(selected);
                    }).show();
        });

        setFreeColor(c1, 0xFFFF9800);
        setFreeColor(c2, 0xFF000000);
        setFreeColor(c3, 0xFFFFEB3B);
        setFreeColor(c4, 0xFF4CAF50);
        setFreeColor(c5, 0xFF009688);
        setFreeColor(c6, 0xFF2196F3);
    }

    // ===== HELPER METHODS (OUTSIDE onCreate) =====

    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, withoutsav_14.class);
//        startActivityForResult(intent, REQ_SAVE_DIALOG);
//    }
//    @Override
//    public void onBackPressed() {
//        if (ChangeTracker.changed) {
//            Intent intent = new Intent(this, withoutsav_14.class);
//            startActivityForResult(intent, REQ_SAVE_DIALOG);
//        } else {
//            finish();
//        }
//    }
    @Override
    public void onBackPressed() {

        // If permission or any child screen is open, just go back
        if (isTaskRoot()) {
            if (ChangeTracker.changed) {
                startActivityForResult(
                        new Intent(this, withoutsav_14.class),
                        REQ_SAVE_DIALOG
                );
            } else {
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }


    private void fetchAutomaticLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    updateAddressFromLatLng(location.getLatitude(), location.getLongitude());
                } else {
                    Toast.makeText(this, "Could not detect GPS location.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SecurityException e) { e.printStackTrace(); }
    }

    private void updateAddressFromLatLng(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address addr = addresses.get(0);
                String fullAddress = addr.getLocality() + ", " + addr.getAdminArea() + ", " + addr.getCountryName();
                addressText.setText(fullAddress);
                SettingsStore.save(this, KEY_LOCATION, fullAddress);
                SettingsStore.save(this, KEY_LOCATION_MODE, "reset");
                ChangeTracker.mark();
            }
        } catch (Exception e) {
            addressText.setText("Address service failed.");
        }
    }

    private void moveSeekLabel(int progress) {
        seekTransparency.post(() -> {
            if (seekTransparency.getThumb() == null) return;
            int thumbX = seekTransparency.getThumb().getBounds().centerX();
            float x = seekTransparency.getX() + thumbX - (txtSeekValue.getWidth() / 2f);
            float barCenterY = seekTransparency.getY() + (seekTransparency.getHeight() / 2f);
            float y = barCenterY - (txtSeekValue.getHeight() / 2f);
            txtSeekValue.setX(x);
            txtSeekValue.setY(y);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String mode = data.getStringExtra("location_mode");

            if ("manual".equals(mode)) {
                String address = data.getStringExtra("manual_address");
                addressText.setText(address);
                SettingsStore.save(this, KEY_LOCATION, address);
                SettingsStore.save(this, KEY_LOCATION_MODE, "manual");
            } else if ("current".equals(mode)) {
                fetchAutomaticLocation();
                SettingsStore.save(this, KEY_LOCATION_MODE, "current");
            }

            ChangeTracker.mark();
            ChangeTracker.reset();

            if (requestCode == REQ_SAVE_DIALOG) {
                if (resultCode == RESULT_OK) {
                    ChangeTracker.mark();
                    finish();
                } else if (resultCode == RESULT_CANCELED) {
                    finish();
                }
            }
        }}

    private void setFreeColor(ImageView view, int color) {
        view.setOnClickListener(v -> {
            currentColor = color;
            stampPreview.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
            SettingsStore.save(this, KEY_COLOR, color);
            ChangeTracker.mark();
        });
    }

    private void restoreSavedData() {
        txtDateValue.setText(SettingsStore.get(this, KEY_DATE, "Select Date"));
        String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
        String address = SettingsStore.get(this, KEY_LOCATION, "Tap to set location");

        if (mode.equals("manual")) {
            addressText.setText(address);
            ((TextView)findViewById(R.id.txtAddressValue)).setText("Manual");
        } else {
            ((TextView)findViewById(R.id.txtAddressValue)).setText("reset");
        }

        currentAlpha = SettingsStore.get(this, KEY_ALPHA, 60);
        seekTransparency.setProgress(currentAlpha);
        txtTransparencyValue.setText(currentAlpha + " %");
        txtSeekValue.setText(currentAlpha + "%");
        txtSeekValue.setVisibility(View.VISIBLE);
        stampPreview.setAlpha(currentAlpha / 100f);
        seekTransparency.post(() -> moveSeekLabel(currentAlpha));
        currentColor = SettingsStore.get(this, KEY_COLOR, 0xFFFF9800);
        stampPreview.setColorFilter(currentColor, android.graphics.PorterDuff.Mode.MULTIPLY);

        int savedStamp = SettingsStore.get(this, KEY_STAMP_DRAWABLE, R.drawable.cal_grp_7_th_screen);
        stampPreview.setImageResource(savedStamp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreSavedData();
        ChangeTracker.reset();
    }
}
//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
//import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
//import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//
//import java.util.List;
//import java.util.Locale;
//
//public class stamp_0_up extends AppCompatActivity {
//
//    private static final int REQ_LOCATION = 101; // loc
//    private static final int REQ_SAVE_DIALOG = 105;//save dialog
//
//    private static final String KEY_DATE = "stamp_date";
//    private static final String KEY_LOCATION = "stamp_location";
//    private static final String KEY_ALPHA = "stamp_alpha";
//    private static final String KEY_COLOR = "stamp_color";
//    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
//    private static final String KEY_STAMP_DRAWABLE = "stamp_drawable";
//
//    private ImageView btnBack, btnDone, stampPreview;
//    private TextView txtDateValue, addressText, txtTransparencyValue;
//    private ImageView c1, c2, c3, c4, c5, c6;
//
//    private SeekBar seekTransparency;
//    private TextView txtSeekValue;
//
//    private int currentAlpha = 60;
//    private int currentColor = 0xFFFF9800;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.stamp_0_up_layer_figma);
//
//        // ===== INIT VIEWS =====
//        btnBack = findViewById(R.id.btnBack);
//        btnDone = findViewById(R.id.btnDone);
//        stampPreview = findViewById(R.id.stamp);
//
//        txtDateValue = findViewById(R.id.txtDateValue);
//        addressText = findViewById(R.id.addressText);
//        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);
//
//        txtSeekValue = findViewById(R.id.txtSeekValue);
//        seekTransparency = findViewById(R.id.seekTransparency);
//
//        c1 = findViewById(R.id.colorCircle1);
//        c2 = findViewById(R.id.colorCircle2);
//        c3 = findViewById(R.id.colorCircle3);
//        c4 = findViewById(R.id.colorCircle4);
//        c5 = findViewById(R.id.colorCircle5);
//        c6 = findViewById(R.id.colorCircle6);
//
//        restoreSavedData();
//
//        // ===== BACK =====
//        btnBack.setOnClickListener(v -> onBackPressed());
//
//        btnDone.setOnClickListener(v -> {
//            ChangeTracker.mark();
//            finish();
//        });
//
//
//        // ===== DATE =====
//        findViewById(R.id.dateRow).setOnClickListener(v ->
//                startActivity(new Intent(this, Date_time_07.class))
//        );
//
//        // ===== LOCATION =====
//        findViewById(R.id.addressRow).setOnClickListener(v -> {
//            if (!PermissionUtils.hasLocation(this)) {
//                startActivity(new Intent(this, per_req_20.class));
//                return;
//            }
//            startActivityForResult(new Intent(this, loc_09.class), REQ_LOCATION);
//        });
//
//        // ===== STAMP (PREMIUM) =====
//        stampPreview.setOnClickListener(v -> {
//            if (!SubscriptionUtils.isPremium(this)) {
//                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            int drawable = R.drawable.christma_text_setting_pg;
//            stampPreview.setImageResource(drawable);
//            SettingsStore.save(this, KEY_STAMP_DRAWABLE, drawable);
//            ChangeTracker.mark();
//        });
//
//        // ===== SEEK BAR =====
//        seekTransparency.setProgress(currentAlpha);
//        txtSeekValue.setText(String.valueOf(currentAlpha));
//        moveSeekLabel(currentAlpha);
//
//        seekTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (progress < 10) progress = 10;
//
//                currentAlpha = progress;
//                txtTransparencyValue.setText(progress + " %");
//                stampPreview.setAlpha(progress / 100f);
//                txtSeekValue.setText((String.valueOf(progress)+ "%"));
//                txtSeekValue.setVisibility(View.VISIBLE);
//                moveSeekLabel(progress);
//
//
//
////                txtSeekValue.setText(String.valueOf(progress));
//
//
//
//                SettingsStore.save(stamp_0_up.this, KEY_ALPHA, progress);
//                ChangeTracker.mark();
//            }
//
//            @Override public void onStartTrackingTouch(SeekBar seekBar) {
//                txtSeekValue.setVisibility(View.VISIBLE);
//            }
//
//            @Override public void onStopTrackingTouch(SeekBar seekBar) {
//                txtSeekValue.setVisibility(View.VISIBLE);
//            }
//        });
//        @Override
//        public void onBackPressed() {
//            // Instead of finishing, open the "Save changes?" activity
//            Intent intent = new Intent(this, withoutsav_14.class);
//            startActivityForResult(intent, REQ_SAVE_DIALOG);
//        }
//        private void fetchAutomaticLocation() {
//            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//            try {
//                fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
//                    if (location != null) {
//                        updateAddressFromLatLng(location.getLatitude(), location.getLongitude());
//                    } else {
//                        Toast.makeText(this, "Could not detect GPS location.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (SecurityException e) {
//                e.printStackTrace();
//            }
//        }
//        private void updateAddressFromLatLng(double lat, double lng) {
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            try {
//                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
//                if (addresses != null && !addresses.isEmpty()) {
//                    Address addr = addresses.get(0);
//
//                    // Construct address: City, State, Country
//                    String fullAddress = addr.getLocality() + ", " + addr.getAdminArea() + ", " + addr.getCountryName();
//
//                    addressText.setText(fullAddress);
//                    SettingsStore.save(this, KEY_LOCATION, fullAddress);
//                    ChangeTracker.mark();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                addressText.setText("Location service error.");
//            }
//        }
//        // ===== ARROW DROPDOWN =====
//        findViewById(R.id.iconTransArrow).setOnClickListener(v -> {
//
//            final String[] values = {"10","20","30","40","50","60","70","80","90","100"};
//
//            new androidx.appcompat.app.AlertDialog.Builder(this)
//                    .setTitle("Transparency")
//                    .setItems(values, (dialog, which) -> {
//
//                        int selected = Integer.parseInt(values[which]);
//
//                        currentAlpha = selected;
//                        seekTransparency.setProgress(selected);
//
//                        txtTransparencyValue.setText(selected + " %");
//                        stampPreview.setAlpha(selected / 100f);
//
//                        txtSeekValue.setText(String.valueOf(selected));
//                        moveSeekLabel(selected);
//
//                        SettingsStore.save(this, KEY_ALPHA, selected);
//                        ChangeTracker.mark();
//                    })
//                    .show();
//        });
//
//        // ===== COLORS =====
//        setFreeColor(c1, 0xFFFF9800);
//        setFreeColor(c2, 0xFF000000);
//        setFreeColor(c3, 0xFFFFEB3B);
//        setFreeColor(c4, 0xFF4CAF50);
//        setFreeColor(c5, 0xFF009688);
//        setFreeColor(c6, 0xFF2196F3);
//    }
//
//    private void fetchAutomaticLocation() {
//        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try {
//            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
//                if (location != null) {
//                    updateAddressFromLatLng(location.getLatitude(), location.getLongitude());
//                } else {
//                    Toast.makeText(this, "Could not detect location. Try manual entry.", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateAddressFromLatLng(double lat, double lng) {
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
//            if (addresses != null && !addresses.isEmpty()) {
//                Address addr = addresses.get(0);
//
//                // Construct a readable string
//                String fullAddress = addr.getLocality() + ", " + addr.getAdminArea() + ", " + addr.getCountryName();
//
//                // Update UI and Save
//                addressText.setText(fullAddress);
//                SettingsStore.save(this, KEY_LOCATION, fullAddress);
//                ChangeTracker.mark();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            addressText.setText("Location found, but address service failed.");
//        }}
////    private void moveSeekLabel(int progress) {
////        seekTransparency.post(() -> {
////            if (seekTransparency.getThumb() == null) return;
////
////            // 1. Get the center position of the thumb
////            int thumbX = seekTransparency.getThumb().getBounds().centerX();
//////            int thumbY = seekTransparency.getHeight()/2;
////            int thumbCenterY = seekTransparency.getThumb().getBounds().centerY();
////            // 2. Get locations to calculate offset
////            int[] seekBarLocation = new int[2];
////            int[] parentLocation = new int[2];
////            seekTransparency.getLocationOnScreen(seekBarLocation);
////            ((View) seekTransparency.getParent()).getLocationOnScreen(parentLocation);
////
////            // 3. Calculate X to center text on thumb
////            float x = seekTransparency.getX()
////                    + thumbX
////                    - (txtSeekValue.getWidth() / 2f);
////
////            // 4. Calculate Y to center text on thumb
////            float y = seekTransparency.getY()
////                    + thumbCenterY
////                    - (txtSeekValue.getHeight() / 2f);
////
////            txtSeekValue.setX(x);
////            txtSeekValue.setY(y);
////        });
////    }
////private void moveSeekLabel(int progress) {
////    seekTransparency.post(() -> {
////        if (seekTransparency.getThumb() == null) return;
////
////        // 1. Get the horizontal center of the thumb within the SeekBar
////        int thumbX = seekTransparency.getThumb().getBounds().centerX();
////
////        // 2. Instead of drawable centerY, use the middle of the SeekBar's height
////        // This ensures it is vertically centered on the bar itself
////        int barCenterY = seekTransparency.getHeight() / 2;
////
////        // 3. Calculate X relative to the parent FrameLayout
////        // SeekBar.getX() is its position inside the transparencyGraph FrameLayout
////        float x = seekTransparency.getX()
////                + thumbX
////                - (txtSeekValue.getWidth() / 2f);
////
////        // 4. Calculate Y relative to the parent FrameLayout
////        float y = seekTransparency.getY()
////                + barCenterY
////                - (txtSeekValue.getHeight() / 2f);
////
////        // 5. Apply coordinates
////        txtSeekValue.setX(x);
////        txtSeekValue.setY(y);
////    });
////}
//
//    private void moveSeekLabel(int progress) {
//        seekTransparency.post(() -> {
//            if (seekTransparency.getThumb() == null) return;
//
//            // 1. Get Thumb horizontal center
//            int thumbX = seekTransparency.getThumb().getBounds().centerX();
//
//            // 2. Calculate X: Align centers of SeekBar and TextView
//            float x = seekTransparency.getX()
//                    + thumbX
//                    - (txtSeekValue.getWidth() / 2f);
//
//            // 3. Calculate Y: Find the true center of the SeekBar within the FrameLayout
//            // We find the bar's top-left (getY) and add half its height
//            float barCenterY = seekTransparency.getY() + (seekTransparency.getHeight() / 2f);
//
//            // Align TextView center with the bar's center
//            float y = barCenterY - (txtSeekValue.getHeight() / 2f);
//
//            txtSeekValue.setX(x);
//            txtSeekValue.setY(y);
//        });
//    }@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // HANDLE LOCATION SELECTION
//        if (requestCode == REQ_LOCATION && resultCode == RESULT_OK) {
//            String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
//            if (mode.equals("manual") && data != null) {
//                String address = data.getStringExtra("manual_address");
//                addressText.setText(address);
//                SettingsStore.save(this, KEY_LOCATION, address);
//            } else {
//                // User reset to Automatic
//                fetchAutomaticLocation();
//            }
//            ChangeTracker.mark();
//        }
//
//        // HANDLE SAVE DIALOG (REQ_SAVE_DIALOG)
//        if (requestCode == REQ_SAVE_DIALOG) {
//            if (resultCode == RESULT_OK) {
//                // User clicked "Save" on the popup
//                ChangeTracker.mark();
//                finish();
//            } else if (resultCode == RESULT_CANCELED) {
//                // User clicked "No" on the popup - exit without saving new marks
//                finish();
//            }
//        }
//    }
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == REQ_LOCATION && resultCode == RESULT_OK) {
////          String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
////        if (mode.equals("manual") && data != null) {
////            // Case 1: Manual Address from loc10
////            String address = data.getStringExtra("manual_address");
////            addressText.setText(address);
////            SettingsStore.save(this, KEY_LOCATION, address);
////        } else {
////            // Case 2: User reset to Automatic
////            fetchAutomaticLocation();
////        }
////        ChangeTracker.mark();
////    }
//////        if (requestCode == REQ_LOCATION && resultCode == RESULT_OK) {
//////            String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
//////            if (address != null && !address.isEmpty()) {
//////                addressText.setText(address);
//////                SettingsStore.save(this, KEY_LOCATION, address);
//////                SettingsStore.save(this, KEY_LOCATION_MODE, "manual");
//////                ChangeTracker.mark();
//////            }
//////        }
////    }
//
//    private void setFreeColor(ImageView view, int color) {
//        view.setOnClickListener(v -> {
//            currentColor = color;
//            stampPreview.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
////            stampPreview.setColorFilter(color);
//            SettingsStore.save(this, KEY_COLOR, color);
//            ChangeTracker.mark();
//        });
//    }
//
//    private void restoreSavedData() {
//
//        txtDateValue.setText(
//                SettingsStore.get(this, KEY_DATE, txtDateValue.getText().toString())
//        );
//
////
//        String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
//        String address = SettingsStore.get(this, KEY_LOCATION, "Tap to set location");
////        addressText.setText(mode.equals("manual") ? address : "Automatic");
//        if (mode.equals("manual")) {
//            addressText.setText(address);
//            // Changed "Reset" to "Manual" so user knows they customized it
//            ((TextView)findViewById(R.id.txtAddressValue)).setText("Manual");
//        } else {
//            ((TextView)findViewById(R.id.txtAddressValue)).setText("Automatic");
////            addressText.setText("Automatic Location Active");
////            ((TextView)findViewById(R.id.txtAddressValue)).setText("Reset");
//        }
//        currentAlpha = SettingsStore.get(this, KEY_ALPHA, 60);
//        if (currentAlpha < 10) currentAlpha = 10;
//
//        txtTransparencyValue.setText(currentAlpha + " %");
//        stampPreview.setAlpha(currentAlpha / 100f);
//        seekTransparency.setProgress(currentAlpha);
//        seekTransparency.post(() -> {
//            txtSeekValue.setText(String.valueOf(currentAlpha + "%"));
//            moveSeekLabel(currentAlpha);
//        });
//
//        currentColor = SettingsStore.get(this, KEY_COLOR, 0xFFFF9800);
////        stampPreview.setColorFilter(currentColor);
//        stampPreview.clearColorFilter();
//
//        int savedStamp = SettingsStore.get(
//                this,
//                KEY_STAMP_DRAWABLE,
//                R.drawable.cal_grp_7_th_screen
//        );
//        stampPreview.setImageResource(savedStamp);
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Use 'this' here is fine because we are directly in the Activity method
////        String savedDate = SettingsStore.get(this, KEY_DATE, "Select Date");
////        txtDateValue.setText(savedDate);
//        restoreSavedData();
//    }
//
//    //    private int dpToPx(int dp) {
////        return Math.round(dp * getResources().getDisplayMetrics().density);
////    }
//
////    private void moveSeekLabel(int progress) {
////        seekTransparency.post(() -> {
////
////            if (seekTransparency.getThumb() == null) return;
////
////            // Get thumb bounds (THIS is the key)
////            int thumbX = seekTransparency.getThumb().getBounds().centerX();
////
////            // Convert SeekBar-local X to FrameLayout X
////            int[] seekBarLocation = new int[2];
////            int[] parentLocation = new int[2];
////
////            seekTransparency.getLocationOnScreen(seekBarLocation);
////            ((View) seekTransparency.getParent()).getLocationOnScreen(parentLocation);
////
////            float x =
////                    seekBarLocation[0]
////                            - parentLocation[0]
////                            + thumbX
////                            - (txtSeekValue.getWidth() / 2f);
////
////            txtSeekValue.setX(x);
////
////            // Optional: lift text above thumb
////            txtSeekValue.setY(
////                    seekTransparency.getY()
////                            - txtSeekValue.getHeight()
////                            - dpToPx(6)
////            );
////        });
////    } // ===== MOVE FLOATING VALUE =====
////    private void moveSeekLabel(int progress) {
////        seekTransparency.post(() -> {
////        int max = seekTransparency.getMax();
////            int min = 10;
////        int width = seekTransparency.getWidth()
////                - seekTransparency.getPaddingStart()
////                - seekTransparency.getPaddingEnd();
////
//////        float percent = progress / (float) max;
////            float percent = (float) (progress - min) / (max - min);
////            float x = seekTransparency.getX()
////                + seekTransparency.getPaddingStart()
////                + (width * percent)
////                - (txtSeekValue.getWidth() / 2f);
////
////        txtSeekValue.setX(x);
////
////    });}
////    private void moveSeekLabel(int progress) {
////        seekTransparency.post(() -> {
////
////            int min = 10; // 10
////            int max = seekTransparency.getMax(); // 100
////
////            float percent = (progress - min) / (float) (max - min);
////
////            int seekBarWidth =
////                    seekTransparency.getWidth()
////                            - seekTransparency.getPaddingStart()
////                            - seekTransparency.getPaddingEnd();
////
////            // THUMB WIDTH (from your drawable: 42dp)
////            float thumbHalfWidth = txtSeekValue.getWidth() / 2f;
////
////            float thumbX =
////                    seekTransparency.getX()
////                            + seekTransparency.getPaddingStart()
////                            + (seekBarWidth * percent);
////
////            txtSeekValue.setX(thumbX - thumbHalfWidth);
////        });
////    }
////package com.example.gunjan_siddhisoftwarecompany;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.view.View;
////import android.widget.ImageView;
////import android.widget.SeekBar;
////import android.widget.TextView;
////import android.widget.Toast;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
////import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
////import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
////import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;
////
////public class stamp_0_up extends AppCompatActivity {
////
////    private static final int REQ_LOCATION = 101;
////
////    private static final String KEY_DATE = "stamp_date";
////    private static final String KEY_LOCATION = "stamp_location";
////    private static final String KEY_ALPHA = "stamp_alpha";
////    private static final String KEY_COLOR = "stamp_color";
////    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
////
////    private ImageView btnBack, btnDone, stampPreview,iconTransArrow;
//////    private ImageView imgPartyText1;
////    private TextView txtDateValue, addressText, txtTransparencyValue;
////    private ImageView c1, c2, c3, c4, c5, c6;
////
////    private int currentAlpha = 60;
////    private int currentColor = 0xFFFF9800;
////    private static final String KEY_STAMP_DRAWABLE = "stamp_drawable";
////    private TextView txtSeekValue;
////    private SeekBar seekTransparency;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.stamp_0_up_layer_figma);
////        seekTransparency = findViewById(R.id.seekTransparency);
////        txtSeekValue = findViewById(R.id.txtSeekValue);
////
////// initial state
////        seekTransparency.setProgress(currentAlpha);
////        txtSeekValue.setText(currentAlpha + "");
////        moveSeekLabel(currentAlpha);
////
////        btnBack = findViewById(R.id.btnBack);
////        btnDone = findViewById(R.id.btnDone);
////        stampPreview = findViewById(R.id.stamp);
////
////
////        txtDateValue = findViewById(R.id.txtDateValue);
////        addressText = findViewById(R.id.addressText);
////        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);
////
////        c1 = findViewById(R.id.colorCircle1);
////        c2 = findViewById(R.id.colorCircle2);
////        c3 = findViewById(R.id.colorCircle3);
////        c4 = findViewById(R.id.colorCircle4);
////        c5 = findViewById(R.id.colorCircle5);
////        c6 = findViewById(R.id.colorCircle6);
////
////
////        restoreSavedData();
////
////        btnBack.setOnClickListener(v ->
////                startActivity(new Intent(this, withoutsav_14.class))
////        );
////
////        btnDone.setOnClickListener(v -> {
////            ChangeTracker.mark();
////            finish();
////        });
////
////        // DATE (FREE)
////        findViewById(R.id.dateRow).setOnClickListener(v ->
////                startActivity(new Intent(this, Date_time_07.class))
////        );
////
////        // LOCATION (FREE)
////        findViewById(R.id.addressRow).setOnClickListener(v -> {
////            if (!PermissionUtils.hasLocation(this)) {
////                startActivity(new Intent(this, per_req_20.class));
////                return;
////            }
////            startActivityForResult(
////                    new Intent(this, loc_09.class),
////                    REQ_LOCATION
////            );
////        });
////        stampPreview.setOnClickListener(v -> {
////
////            if (!SubscriptionUtils.isPremium(this)) {
////                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            int drawable = R.drawable.christma_text_setting_pg;
////
////            stampPreview.setImageResource(drawable);
////            SettingsStore.save(this, KEY_STAMP_DRAWABLE, drawable);
////
////            ChangeTracker.mark();
////        });
////
//////        stampPreview.setOnClickListener(v -> {
//////            if (!SubscriptionUtils.isPremium(this)) {
//////                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
//////                return;
//////            }
//////            stampPreview.setImageResource(R.drawable.christma_text_setting_pg);
//////            ChangeTracker.mark();
//////        });
////        // TRANSPARENCY (FREE)
//////        SeekBar seekTransparency = findViewById(R.id.seekTransparency);
//////
//////        seekTransparency.setProgress(currentAlpha);
////
//////        seekTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//////            @Override
//////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//////                currentAlpha = progress;
//////                txtTransparencyValue.setText(progress + " %");
//////                stampPreview.setAlpha(progress / 100f);
//////
//////                SettingsStore.save(stamp_0_up.this, KEY_ALPHA, progress);
//////                ChangeTracker.mark();
//////            }
//////
//////            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
//////            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
//////
//////        });
////        SeekBar seekTransparency = findViewById(R.id.seekTransparency);
////
////        seekTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////
////                if (progress < 10) progress = 10;
////
////                currentAlpha = progress;
////
////                txtTransparencyValue.setText(progress + " %");
////                stampPreview.setAlpha(progress / 100f);
////
////                txtSeekValue.setText(String.valueOf(progress));
////                moveSeekLabel(progress);
////
////                SettingsStore.save(stamp_0_up.this, KEY_ALPHA, progress);
////                ChangeTracker.mark();
////            }
////
////            @Override public void onStartTrackingTouch(SeekBar seekBar) {
////                txtSeekValue.setVisibility(View.VISIBLE);
////            }
////
////            @Override public void onStopTrackingTouch(SeekBar seekBar) {
////                txtSeekValue.setVisibility(View.VISIBLE);
////            }
////        });
////
////
////        findViewById(R.id.iconTransArrow).setOnClickListener(v -> {
////
////            final String[] values = {"10","20","30","40","50","60","70","80","90","100"};
////
////            new androidx.appcompat.app.AlertDialog.Builder(this)
////                    .setTitle("Transparency")
////                    .setItems(values, (dialog, which) -> {
////
////                        int selected = Integer.parseInt(values[which]);
////
////                        currentAlpha = selected;
////
////                        seekTransparency.setProgress(selected);
////
////                        txtTransparencyValue.setText(selected + " %");
////                        stampPreview.setAlpha(selected / 100f);
////
////                        txtSeekValue.setText(String.valueOf(selected));
////                        moveSeekLabel(selected);
////
////                        SettingsStore.save(this, KEY_ALPHA, selected);
////                        ChangeTracker.mark();
////                    })
////                    .show();
////        });
////
////
////        private void moveSeekLabel(int progress) {
////
////            int max = seekTransparency.getMax();
////            int width = seekTransparency.getWidth()
////                    - seekTransparency.getPaddingStart()
////                    - seekTransparency.getPaddingEnd();
////
////            float percent = progress / (float) max;
////
////            float x = seekTransparency.getX()
////                    + seekTransparency.getPaddingStart()
////                    + width * percent
////                    - txtSeekValue.getWidth() / 2f;
////
////            txtSeekValue.setX(x);
////        }
////
////
////
////        // COLORS (FREE)
////        setFreeColor(c1, 0xFFFF9800);
////        setFreeColor(c2, 0xFF000000);
////        setFreeColor(c3, 0xFFFFEB3B);
////        setFreeColor(c4, 0xFF4CAF50);
////        setFreeColor(c5, 0xFF009688);
////        setFreeColor(c6, 0xFF2196F3);
////
////
////
////    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        if (requestCode == REQ_LOCATION && resultCode == RESULT_OK && data != null) {
////            String address = data.getStringExtra("manual_address");
////            if (address != null && !address.isEmpty()) {
////                addressText.setText(address);
////                SettingsStore.save(this, KEY_LOCATION, address);
////                SettingsStore.save(this, KEY_LOCATION_MODE, "manual");
////                ChangeTracker.mark();
////            }
////        }
////    }
////
////    private void setFreeColor(ImageView view, int color) {
////        view.setOnClickListener(v -> {
////            currentColor = color;
////            stampPreview.setColorFilter(color);
////            SettingsStore.save(this, KEY_COLOR, color);
////            ChangeTracker.mark();
////        });
////    }
////
////    private void restoreSavedData() {
////        txtDateValue.setText(
////                SettingsStore.get(this, KEY_DATE, txtDateValue.getText().toString())
////        );
////
////        String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
////        addressText.setText(
////                mode.equals("manual")
////                        ? SettingsStore.get(this, KEY_LOCATION, "Tap to set location")
////                        : "Automatic"
////        );
////
////        currentAlpha = SettingsStore.get(this, KEY_ALPHA, 60);
////        if (currentAlpha < 10) currentAlpha = 10;
////        txtTransparencyValue.setText(currentAlpha + " %");
////        stampPreview.setAlpha(currentAlpha / 100f);
////        SeekBar seekTransparency = findViewById(R.id.seekTransparency);
////        seekTransparency.setProgress(currentAlpha);
////        currentColor = SettingsStore.get(this, KEY_COLOR, 0xFFFF9800);
////        stampPreview.setColorFilter(currentColor);
////        int savedStamp = SettingsStore.get(
////                this,
////                KEY_STAMP_DRAWABLE,
////                R.drawable.cal_grp_7_th_screen   // default Christmas Party
////        );
////        stampPreview.setImageResource(savedStamp);
////    }
////}
//}