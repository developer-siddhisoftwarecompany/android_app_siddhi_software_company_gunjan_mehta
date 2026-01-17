package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;

public class stamp_0_up extends AppCompatActivity {

    // ===== REQUEST CODE =====
    private static final int REQ_LOCATION = 101;

    // ===== KEYS =====
    private static final String KEY_DATE = "stamp_date";
    private static final String KEY_LOCATION = "stamp_location";
    private static final String KEY_ALPHA = "stamp_alpha";
    private static final String KEY_COLOR = "stamp_color";
    private static final String KEY_LOCATION_MODE = "stamp_location_mode";

    // ===== UI =====
    private ImageView btnBack, btnDone, stampPreview;
    private TextView txtDateValue, addressText, txtTransparencyValue;
    private ImageView c1, c2, c3, c4, c5, c6;

    // ===== STATE =====
    private int currentAlpha = 60;
    private int currentColor = 0xFFFF9800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamp_0_up_layer_figma);

        // INIT UI
        btnBack = findViewById(R.id.btnBack);
        btnDone = findViewById(R.id.btnDone);
        stampPreview = findViewById(R.id.stamp);

        txtDateValue = findViewById(R.id.txtDateValue);
        addressText = findViewById(R.id.addressText);
        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);

        c1 = findViewById(R.id.colorCircle1);
        c2 = findViewById(R.id.colorCircle2);
        c3 = findViewById(R.id.colorCircle3);
        c4 = findViewById(R.id.colorCircle4);
        c5 = findViewById(R.id.colorCircle5);
        c6 = findViewById(R.id.colorCircle6);

        restoreSavedData();

        // BACK
        btnBack.setOnClickListener(v -> finish());

        // DONE
        btnDone.setOnClickListener(v -> {
            ChangeTracker.mark();
            finish();
        });

        // DATE
        findViewById(R.id.dateRow).setOnClickListener(v ->
                startActivity(new Intent(this, Date_time_07.class))
        );

        // ADDRESS → loc10 (manual)
        findViewById(R.id.addressRow).setOnClickListener(v -> {
            if (!PermissionUtils.hasLocation(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            startActivity(new Intent(this, loc_09.class));
        });




        // TRANSPARENCY
        findViewById(R.id.imgTransValue).setOnClickListener(v -> {
            currentAlpha += 10;
            if (currentAlpha > 100) currentAlpha = 10;

            txtTransparencyValue.setText(currentAlpha + " %");
            stampPreview.setAlpha(currentAlpha / 100f);

            SettingsStore.save(this, KEY_ALPHA, currentAlpha);
            ChangeTracker.mark();
        });

        // COLORS
        setColor(c1, 0xFFFF9800);
        setColor(c2, 0xFF000000);
        setColor(c3, 0xFFFFEB3B);
        setColor(c4, 0xFF4CAF50);
        setColor(c5, 0xFF009688);
        setColor(c6, 0xFF2196F3);
    }

    // ===== RECEIVE MANUAL LOCATION =====
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_LOCATION && resultCode == RESULT_OK && data != null) {
            String address = data.getStringExtra("manual_address");
            if (address != null && !address.isEmpty()) {
                addressText.setText(address);
                SettingsStore.save(this, KEY_LOCATION, address);
                SettingsStore.save(this, KEY_LOCATION_MODE, "manual");
                ChangeTracker.mark();
            }
        }
    }

    // ===== COLOR =====
    private void setColor(ImageView view, int color) {
        view.setOnClickListener(v -> {
            if (!SubscriptionUtils.isPremium(this)) {
                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
                return;
            }
            currentColor = color;
            stampPreview.setColorFilter(color);
            SettingsStore.save(this, KEY_COLOR, color);
            ChangeTracker.mark();
        });
    }

    // ===== RESTORE =====
    private void restoreSavedData() {

        txtDateValue.setText(
                SettingsStore.get(this, KEY_DATE, txtDateValue.getText().toString())
        );

        String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");

        if (mode.equals("manual")) {
            addressText.setText(
                    SettingsStore.get(this, KEY_LOCATION, "Tap to set location")
            );
        } else {
            addressText.setText("Automatic");
        }

        currentAlpha = SettingsStore.get(this, KEY_ALPHA, 60);
        txtTransparencyValue.setText(currentAlpha + " %");
        stampPreview.setAlpha(currentAlpha / 100f);

        currentColor = SettingsStore.get(this, KEY_COLOR, 0xFFFF9800);
        stampPreview.setColorFilter(currentColor);
    }

    @Override
    protected void onResume() {
        super.onResume();

        txtDateValue.setText(
                SettingsStore.get(this, KEY_DATE, txtDateValue.getText().toString())
        );
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, withoutsav_14.class));
    }

}
