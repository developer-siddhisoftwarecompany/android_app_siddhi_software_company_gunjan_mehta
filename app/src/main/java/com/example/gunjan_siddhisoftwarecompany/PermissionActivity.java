package com.example.gunjan_siddhisoftwarecompany;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PermissionActivity extends AppCompatActivity {

    private ImageView toggleCamera, toggleLocation, togglePhotos, topImage;

    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> updateUIAndProceed()
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasAllPermissions()) {
            openMain();
            return;
        }

        setContentView(R.layout.activity_permission);

        toggleCamera   = findViewById(R.id.toggleCamera);
        toggleLocation = findViewById(R.id.toggleLocation);
        togglePhotos   = findViewById(R.id.togglePhotos);
        topImage       = findViewById(R.id.topImage);

        updateToggleUI();

        toggleCamera.setOnClickListener(v -> requestPermissions());
        toggleLocation.setOnClickListener(v -> requestPermissions());
        togglePhotos.setOnClickListener(v -> requestPermissions());

        topImage.setOnClickListener(v -> {
            if (hasAllPermissions()) {
                openMain();
            } else {
                Toast.makeText(
                        this,
                        "Please allow all permissions to continue",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void requestPermissions() {
        permissionLauncher.launch(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_MEDIA_IMAGES
        });
    }

    private boolean hasAllPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void updateToggleUI() {
        toggleCamera.setImageResource(
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
                        ? R.drawable.toggle_on
                        : R.drawable.toggle_off
        );

        toggleLocation.setImageResource(
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                        ? R.drawable.toggle_on
                        : R.drawable.toggle_off
        );

        togglePhotos.setImageResource(
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                        == PackageManager.PERMISSION_GRANTED
                        ? R.drawable.toggle_on
                        : R.drawable.toggle_off
        );
    }

    private void updateUIAndProceed() {
        updateToggleUI();

        if (hasAllPermissions()) {
            openMain();
        }
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
