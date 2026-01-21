package com.example.gunjan_siddhisoftwarecompany;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;
import com.google.common.util.concurrent.ListenableFuture;

public class Settings_04 extends AppCompatActivity {

    private ImageView iconGrid, iconFocus, bgImage, iconGallery;
    private ImageView iconStamp, iconPhotos,iconSetting,btnCapture;
    private TextView txtStamp, txtPhotos;
    private androidx.camera.core.Camera camera;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_04);

        iconGrid    = findViewById(R.id.iconGrid);
        iconFocus   = findViewById(R.id.iconFocus);
        iconGallery = findViewById(R.id.iconGallery);
        bgImage     = findViewById(R.id.imgPartyText1); // stamp preview
        iconSetting  = findViewById(R.id.iconSetting);
        iconStamp    = findViewById(R.id.iconStamp);
        txtStamp     = findViewById(R.id.txtStamp);
        btnCapture = findViewById(R.id.btnCapture);
        iconPhotos   = findViewById(R.id.iconPhotos);
        txtPhotos    = findViewById(R.id.txtPhotos);
        // GRID
        iconGrid.setOnClickListener(v ->
                startActivity(new Intent(this, grid_05.class))
        );

        // FOCUS
        iconFocus.setOnClickListener(v ->
                startActivity(new Intent(this, focus_06.class))
        );

        // STAMP (preview always allowed, edit guarded inside Stamp_7)
        bgImage.setOnClickListener(v ->
                startActivity(new Intent(this, stamp_0_up
                        .class))
        );
        if (PermissionUtils.hasAll(this)) {
            startCamera();
        } else {
            // If no permission, you might want to finish() or ask again
            startActivity(new Intent(this, per_req_20.class));
            finish();
        }
        iconSetting.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity_15.class))
        );
        // GALLERY (permission required)
        iconGallery.setOnClickListener(v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            startActivity(new Intent(this, open_img_11.class));
        });
        View.OnClickListener openStamp = v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                finish();
                return;
            }
            startActivity(new Intent(this, stamp_0_up.class));
        };

        // Inside Settings_04 class

        btnCapture.setOnClickListener(v -> {
            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50);
                takePhoto(); // Now we need to add this method below!
            });
        });


        iconStamp.setOnClickListener(v ->
                startActivity(new Intent(this, stamp_0_up.class)));
        txtStamp.setOnClickListener(openStamp);


        // ================= MY PHOTOS =================
        iconPhotos.setOnClickListener(v ->
                startActivity(new Intent(this, MyPhotosActivity.class))
        );

        txtPhotos.setOnClickListener(v ->
                startActivity(new Intent(this, MyPhotosActivity.class))
        );

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
                        android.widget.Toast.makeText(Settings_04.this, "Photo Saved!", android.widget.Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                    }
                });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        finish(); // clean close overlay
    }
}
