package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;

public class loc_09 extends AppCompatActivity {

    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
    private static final int REQ_MANUAL_ENTRY = 102;

    private View blackOverlay;
    private TextView txtCurrent, txtManual, btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_09_in_figma);

        // ===== INIT VIEWS =====
        blackOverlay = findViewById(R.id.blackOverlay);
        txtCurrent = findViewById(R.id.txtCurrent);
        txtManual = findViewById(R.id.txtManual);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        // ===== DEFAULT STATE (CURRENT SELECTED) =====
        setCurrentSelectedUI();

        // ===== CURRENT CLICK =====
        txtCurrent.setOnClickListener(v -> setCurrentSelectedUI());

        // ===== MANUAL CLICK → OPEN loc10 DIRECTLY =====
        txtManual.setOnClickListener(v -> {
            Intent intent = new Intent(this, loc10.class);
            startActivityForResult(intent, REQ_MANUAL_ENTRY);
        });

        // ===== OK BUTTON (CURRENT ONLY) =====
        btnSave.setOnClickListener(v -> {
            SettingsStore.save(this, KEY_LOCATION_MODE, "current");

            Intent result = new Intent();
            result.putExtra("location_mode", "current");
            setResult(RESULT_OK, result);
            finish();
        });

        // ===== CANCEL / OVERLAY =====
        btnCancel.setOnClickListener(v -> finish());
        blackOverlay.setOnClickListener(v -> finish());
    }

    // ===== RECEIVE MANUAL ADDRESS FROM loc10 =====
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_MANUAL_ENTRY && resultCode == RESULT_OK && data != null) {
            data.putExtra("location_mode", "manual");
            setResult(RESULT_OK, data);
            finish();
        }
    }

    // ===== UI HELPERS =====
    private void setCurrentSelectedUI() {
        txtCurrent.setTextColor(0xFF0A84FF); // Blue
        txtManual.setTextColor(0xFF6B6B6B);  // Grey

        View mapContainer = findViewById(R.id.mapContainer);
        if (mapContainer != null) {
            mapContainer.setVisibility(View.VISIBLE);
        }
    }
}

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
