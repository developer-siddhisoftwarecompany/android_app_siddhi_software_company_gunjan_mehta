package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;

public class loc_09 extends AppCompatActivity {

    private static final String KEY_LOCATION_MODE = "stamp_location_mode";

    View blackOverlay;
    View locationPopup;

    TextView txtCurrent, txtManual;
    TextView btnCancel, btnSave;

    String selectedMode = "current"; // default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_09_in_figma);

        blackOverlay   = findViewById(R.id.blackOverlay);
        locationPopup  = findViewById(R.id.locationPopup);

        txtCurrent = findViewById(R.id.txtCurrent);
        txtManual  = findViewById(R.id.txtManual);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave   = findViewById(R.id.btnSave);


        selectedMode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
        updateSelectionUI();

        txtCurrent.setOnClickListener(v -> {
            selectedMode = "current";
            updateSelectionUI();
        });

        // ===== MANUAL =====
        txtManual.setOnClickListener(v -> {
            selectedMode = "manual";
            updateSelectionUI();
        });

        // ===== CANCEL =====
        btnCancel.setOnClickListener(v -> finish());

        // ===== SAVE =====
        btnSave.setOnClickListener(v -> {
            SettingsStore.save(this, KEY_LOCATION_MODE, selectedMode);

            if (selectedMode.equals("manual")) {
                startActivity(new Intent(this, loc10.class));
            }

            finish();
        });


        // Prevent clicks passing through
        locationPopup.setOnClickListener(v -> {});
        blackOverlay.setOnClickListener(v -> finish());
    }

    private void updateSelectionUI() {
        if (selectedMode.equals("current")) {
            txtCurrent.setTextColor(0xFF0A84FF); // blue
            txtManual.setTextColor(0xFF6B6B6B);  // gray
        } else {
            txtManual.setTextColor(0xFF0A84FF);
            txtCurrent.setTextColor(0xFF6B6B6B);
        }
    }
}
