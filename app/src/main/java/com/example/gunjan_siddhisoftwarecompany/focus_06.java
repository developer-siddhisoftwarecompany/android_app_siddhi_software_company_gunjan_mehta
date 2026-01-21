package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.example.gunjan_siddhisoftwarecompany.util.AppNavigator;

import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
import com.google.common.util.concurrent.ListenableFuture;

public class focus_06 extends AppCompatActivity {

    private static final String KEY_FOCUS = "camera_focus";

    ImageView iconBack, iconSettings;
    ImageView focusBox;
    private ImageView iconStamp, iconPhotos,btnCapture;
    private androidx.camera.core.Camera camera;
    private ImageCapture imageCapture;
    private TextView txtStamp, txtPhotos;


    // focus states
    private final String[] focusModes = {"auto", "tap", "manual"};
    private int currentIndex = 1; // default = tap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_06_pg_in_figma);

        // ===== INIT =====
        iconBack     = findViewById(R.id.iconGallery);   // back
        iconSettings = findViewById(R.id.iconSettings);
        focusBox     = findViewById(R.id.focusBox);
        ImageView imgPartyText1 = findViewById(R.id.imgPartyText1);
        btnCapture = findViewById(R.id.btnCapture);
        iconStamp    = findViewById(R.id.iconStamp);
        txtStamp     = findViewById(R.id.txtStamp);

        iconPhotos   = findViewById(R.id.iconPhotos);
        txtPhotos    = findViewById(R.id.txtPhotos);
        // ===== BACK =====
        iconBack.setOnClickListener(v -> finish());

        // ===== SETTINGS =====
        iconSettings.setOnClickListener(v ->
                startActivity(new android.content.Intent(this, SettingsActivity_15.class))
        );
        imgPartyText1.setOnClickListener(v -> {
            // Open Stamp Editor
            startActivity(new Intent(focus_06.this, stamp_0_up.class));
        });
        // ===== RESTORE SAVED FOCUS =====
        String savedFocus = SettingsStore.get(this, KEY_FOCUS, "tap");
        for (int i = 0; i < focusModes.length; i++) {
            if (focusModes[i].equals(savedFocus)) {
                currentIndex = i;
                break;
            }
        }

        Toast.makeText(this, "Focus: " + focusModes[currentIndex], Toast.LENGTH_SHORT).show();

        // ===== FOCUS BOX CLICK =====
        focusBox.setOnClickListener(v -> {
            currentIndex++;
            if (currentIndex >= focusModes.length) currentIndex = 0;

            String mode = focusModes[currentIndex];
            SettingsStore.save(this, KEY_FOCUS, mode);

            Toast.makeText(this, "Focus: " + mode, Toast.LENGTH_SHORT).show();
        });
        View.OnClickListener openStamp = v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                finish();
                return;
            }
            startActivity(new Intent(this, stamp_0_up.class));
        };

//        iconStamp.setOnClickListener(v ->
//                startActivity(new Intent(this, stamp_0_up.class)));
        txtStamp.setOnClickListener(openStamp);
        iconStamp.setOnClickListener(openStamp);
        if (PermissionUtils.hasAll(this)) {
            startCamera();
        } else {
            // If no permission, you might want to finish() or ask again
            startActivity(new Intent(this, per_req_20.class));
            finish();
        }
        btnCapture.setOnClickListener(v -> {
            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50);
                takePhoto(); // Now we need to add this method below!
            });
        });

        // ================= MY PHOTOS =================
        iconPhotos.setOnClickListener(v ->
                startActivity(new Intent(this, MyPhotosActivity.class))
        );

        txtPhotos.setOnClickListener(v ->
                startActivity(new Intent(this, MyPhotosActivity.class))
        );

//        txtPhotos.setOnClickListener(openMyPhotos);

    }
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();

                // IMPORTANT: Link to the viewFinder in settings_04 layout
                PreviewView viewFinder = findViewById(R.id.viewFinder);
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }
    private void takePhoto() {
        if (imageCapture == null) return;

        // Create a name and location to save the photo
        long timeStamp = System.currentTimeMillis();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + timeStamp);
        contentValues.put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
                .Builder(getContentResolver(),
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
                .build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        android.widget.Toast.makeText(focus_06.this, "Photo Saved!", android.widget.Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                    }
                });
    }
}
