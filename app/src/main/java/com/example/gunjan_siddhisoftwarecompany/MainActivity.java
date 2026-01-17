package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.CameraController;
import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;

public class MainActivity extends AppCompatActivity {

    // Bottom
    private ImageButton btnCapture;
    private ImageView iconStamp, iconPhotos;
    private TextView txtStamp, txtPhotos;

    // Top
    private ImageView iconGallery, iconFlash, iconRotate, iconTune, iconSetting;

    private int currentRotation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ================= INIT VIEWS =================
        btnCapture   = findViewById(R.id.btnCapture);

        iconStamp    = findViewById(R.id.iconStamp);
        txtStamp     = findViewById(R.id.txtStamp);

        iconPhotos   = findViewById(R.id.iconPhotos);
        txtPhotos    = findViewById(R.id.txtPhotos);

        iconGallery  = findViewById(R.id.iconGallery);
        iconFlash    = findViewById(R.id.iconFlash);
        iconRotate   = findViewById(R.id.iconRotate);
        iconTune     = findViewById(R.id.iconTune);
        iconSetting  = findViewById(R.id.iconSetting);

        // ================= RESTORE STATE =================
        currentRotation = SettingsStore.get(this, "camera_rotation", 0);

        // ================= CAPTURE =================
        btnCapture.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Camera capture will be added with CameraX",
                        Toast.LENGTH_SHORT
                ).show()
        );

        // ================= STAMP =================
        iconStamp.setOnClickListener(v ->
                startActivity(new Intent(this, stamp_0_up.class))
        );
        txtStamp.setOnClickListener(v ->
                startActivity(new Intent(this, stamp_0_up.class))
        );

        // ================= MY PHOTOS =================
        openWithPermission(
                () -> startActivity(new Intent(this, MyPhotosActivity.class)),
                iconPhotos, txtPhotos
        );

        // ================= GALLERY =================
        iconGallery.setOnClickListener(v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            startActivity(new Intent(this, open_img_11.class));
        });

        // ================= FLASH =================
        iconFlash.setOnClickListener(v ->
                CameraController.toggleFlash(this)
        );

        // ================= ROTATE =================
        iconRotate.setOnClickListener(v -> {
            currentRotation = (currentRotation + 90) % 360;
            SettingsStore.save(this, "camera_rotation", currentRotation);

            Toast.makeText(
                    this,
                    "Rotation: " + currentRotation + "°",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // ================= TUNE (OPEN OVERLAY) =================
        iconTune.setOnClickListener(v ->
                startActivity(new Intent(this, Settings_04.class))
        );

        // ================= SETTING (GLOBAL SETTINGS) =================
        iconSetting.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity_15.class))
        );
    }

    // =====================================================
    // PERMISSION HELPER
    // =====================================================

    private void openWithPermission(Runnable action, Object... views) {
        for (Object v : views) {
            if (v instanceof ImageView) {
                ((ImageView) v).setOnClickListener(view -> {
                    if (!PermissionUtils.hasAll(this)) {
                        startActivity(new Intent(this, per_req_20.class));
                        return;
                    }
                    action.run();
                });
            } else if (v instanceof TextView) {
                ((TextView) v).setOnClickListener(view -> {
                    if (!PermissionUtils.hasAll(this)) {
                        startActivity(new Intent(this, per_req_20.class));
                        return;
                    }
                    action.run();
                });
            }
        }
    }
}
