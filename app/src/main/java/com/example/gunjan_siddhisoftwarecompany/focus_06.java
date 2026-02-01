//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ImageCapture;
//import androidx.camera.core.ImageCaptureException;
//import androidx.camera.core.Preview;
//import androidx.camera.lifecycle.ProcessCameraProvider;
//import androidx.camera.view.PreviewView;
//import androidx.core.content.ContextCompat;
//
//import com.example.gunjan_siddhisoftwarecompany.util.AppNavigator;
//
//import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
//import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//import com.google.common.util.concurrent.ListenableFuture;
//
//public class focus_06 extends AppCompatActivity {
//
//    private static final String KEY_FOCUS = "camera_focus";
//
//    ImageView iconBack, iconSettings;
//    ImageView focusBox;
//    private ImageView iconStamp, iconPhotos,btnCapture;
//    private androidx.camera.core.Camera camera;
//    private ImageCapture imageCapture;
//    private TextView txtStamp, txtPhotos;
//
//
//    // focus states
//    private final String[] focusModes = {"auto", "tap", "manual"};
//    private int currentIndex = 1; // default = tap
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.focus_06_pg_in_figma);
//
//        // ===== INIT =====
//        iconBack     = findViewById(R.id.iconGallery);   // back
//        iconSettings = findViewById(R.id.iconSettings);
//        focusBox     = findViewById(R.id.focusBox);
//        ImageView imgPartyText1 = findViewById(R.id.imgPartyText1);
//        btnCapture = findViewById(R.id.btnCapture);
//        iconStamp    = findViewById(R.id.iconStamp);
//        txtStamp     = findViewById(R.id.txtStamp);
//
//        iconPhotos   = findViewById(R.id.iconPhotos);
//        txtPhotos    = findViewById(R.id.txtPhotos);
//        // ===== BACK =====
//        iconBack.setOnClickListener(v -> finish());
//
//        // ===== SETTINGS =====
//        iconSettings.setOnClickListener(v ->
//                startActivity(new android.content.Intent(this, SettingsActivity_15.class))
//        );
//        imgPartyText1.setOnClickListener(v -> {
//            // Open Stamp Editor
//            startActivity(new Intent(focus_06.this, stamp_0_up.class));
//        });
//        // ===== RESTORE SAVED FOCUS =====
//        String savedFocus = SettingsStore.get(this, KEY_FOCUS, "tap");
//        for (int i = 0; i < focusModes.length; i++) {
//            if (focusModes[i].equals(savedFocus)) {
//                currentIndex = i;
//                break;
//            }
//        }
//
//        Toast.makeText(this, "Focus: " + focusModes[currentIndex], Toast.LENGTH_SHORT).show();
//
//        // ===== FOCUS BOX CLICK =====
//        focusBox.setOnClickListener(v -> {
//            currentIndex++;
//            if (currentIndex >= focusModes.length) currentIndex = 0;
//
//            String mode = focusModes[currentIndex];
//            SettingsStore.save(this, KEY_FOCUS, mode);
//
//            Toast.makeText(this, "Focus: " + mode, Toast.LENGTH_SHORT).show();
//        });
//        View.OnClickListener openStamp = v -> {
//            if (!PermissionUtils.hasAll(this)) {
//                startActivity(new Intent(this, per_req_20.class));
//                finish();
//                return;
//            }
//            startActivity(new Intent(this, stamp_0_up.class));
//        };
//
////        iconStamp.setOnClickListener(v ->
////                startActivity(new Intent(this, stamp_0_up.class)));
//        txtStamp.setOnClickListener(openStamp);
//        iconStamp.setOnClickListener(openStamp);
//        if (PermissionUtils.hasAll(this)) {
//            startCamera();
//        } else {
//            // If no permission, you might want to finish() or ask again
//            startActivity(new Intent(this, per_req_20.class));
//            finish();
//        }
//        btnCapture.setOnClickListener(v -> {
//            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
//                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50);
//                takePhoto(); // Now we need to add this method below!
//            });
//        });
//
//        // ================= MY PHOTOS =================
//        iconPhotos.setOnClickListener(v ->
//                startActivity(new Intent(this, MyPhotosActivity.class))
//        );
//
//        txtPhotos.setOnClickListener(v ->
//                startActivity(new Intent(this, MyPhotosActivity.class))
//        );
//
////        txtPhotos.setOnClickListener(openMyPhotos);
//
//    }
//    private void startCamera() {
//        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
//        cameraProviderFuture.addListener(() -> {
//            try {
//                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                Preview preview = new Preview.Builder().build();
//
//                // IMPORTANT: Link to the viewFinder in settings_04 layout
//                PreviewView viewFinder = findViewById(R.id.viewFinder);
//                setupTapToFocus(viewFinder);
//                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
//
//                imageCapture = new ImageCapture.Builder().build();
//                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
//
//                cameraProvider.unbindAll();
//                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//    private void takePhoto() {
//        if (imageCapture == null) return;
//
//        // Create a name and location to save the photo
//        long timeStamp = System.currentTimeMillis();
//        android.content.ContentValues contentValues = new android.content.ContentValues();
//        contentValues.put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + timeStamp);
//        contentValues.put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//
//        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
//                .Builder(getContentResolver(),
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                contentValues)
//                .build();
//
//        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
//                new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        android.widget.Toast.makeText(focus_06.this, "Photo Saved!", android.widget.Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCaptureException exception) {
//                        exception.printStackTrace();
//                    }
//                });
//    }
//    @SuppressLint("ClickableViewAccessibility")
//    private void setupTapToFocus(PreviewView viewFinder) {
//        viewFinder.setOnTouchListener((v, event) -> {
//            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
//                // 1. Get touch coordinates
//                float x = event.getX();
//                float y = event.getY();
//
//                // 2. Position the focus square UI
//                focusBox.setVisibility(View.VISIBLE);
//                focusBox.setX(x - (focusBox.getWidth() / 2f));
//                focusBox.setY(y - (focusBox.getHeight() / 2f));
//
//                // 3. Simple animation to make it look professional
//                focusBox.setScaleX(1.5f);
//                focusBox.setScaleY(1.5f);
//                focusBox.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).withEndAction(() ->
//                        focusBox.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
//                ).start();
//                // 4. Tell CameraX to focus at this point
//                if (camera != null) {
//                    com.androidx.camera.core.MeteringPointFactory factory = viewFinder.getMeteringPointFactory();
//                    com.androidx.camera.core.MeteringPoint point = factory.createPoint(x, y);
//                    com.androidx.camera.core.FocusMeteringAction action = new com.androidx.camera.core.FocusMeteringAction.Builder(point)
//                            .setAutoCancelDuration(3, java.util.concurrent.TimeUnit.SECONDS)
//                            .build();
//
//                    camera.getCameraControl().startFocusAndMetering(action);
//                }
//                return true;
//            }
//            return true;
//        });
//    }
//    @Override
//    public void onConfigurationChanged(@NonNull android.content.res.Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Rotate the focus box UI to match the tilt
//        if (newConfig.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
//            focusBox.setRotation(-90);
//        } else {
//            focusBox.setRotation(0);
//        }
//    }
//}

package com.example.gunjan_siddhisoftwarecompany;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

public class focus_06 extends AppCompatActivity {

    private static final String KEY_FOCUS = "camera_focus";
    private ImageView iconBack, iconSettings,iconGallery, focusBox, iconStamp, iconPhotos, btnCapture,iconCamera,iconFlash;
    private Camera camera; // Fixed variable
    private ImageCapture imageCapture;
    private TextView txtStamp, txtPhotos;
    // Add these at the top with your other private variables
    private ImageView iconRotate; // Added missing variable
    private boolean isFlashOn = false; // Added missing variable
    private int currentRotation = 0; // Added missing variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_06_pg_in_figma);

        // ===== INIT VIEWS =====
        iconBack = findViewById(R.id.iconGallery);
        iconSettings = findViewById(R.id.iconSettings);
        focusBox = findViewById(R.id.focusBox);
        btnCapture = findViewById(R.id.btnCapture);
        iconStamp = findViewById(R.id.iconStamp);
        txtStamp = findViewById(R.id.txtStamp);
        iconPhotos = findViewById(R.id.iconPhotos);
        txtPhotos = findViewById(R.id.txtPhotos);
        iconFlash = findViewById(R.id.iconFlash);
        iconCamera = findViewById(R.id.iconCamera);
        iconGallery = findViewById(R.id.iconGallery);
        focusBox.setVisibility(View.GONE); // Hide by default

        iconBack.setOnClickListener(v -> finish());

        if (PermissionUtils.hasAll(this)) {
            startCamera();
        } else {
            startActivity(new Intent(this, per_req_20.class));
            finish();
        }

        btnCapture.setOnClickListener(v -> {
            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50);
                takePhoto();
            });
        });
        // Inside onCreate of Settings_04.java

// FLASH LOGIC
        iconFlash = findViewById(R.id.iconFlash);
        iconFlash.setOnClickListener(v -> {
            if (camera != null) {
                isFlashOn = !isFlashOn;
                // This tells the camera hardware to turn the light on/off
                camera.getCameraControl().enableTorch(isFlashOn);
                Toast.makeText(this, isFlashOn ? "Flash ON" : "Flash OFF", Toast.LENGTH_SHORT).show();
            }
        });

// ROTATE LOGIC
        iconCamera = findViewById(R.id.iconCamera);
        iconCamera.setOnClickListener(v -> {
            // 1. Change the rotation degree
            currentRotation = (currentRotation + 90) % 360;

            // 2. Animate the icons so they actually turn
            applyRotation(-currentRotation);

            // 3. Update CameraX so photos saved from this screen are oriented correctly
            if (imageCapture != null) {
                imageCapture.setTargetRotation(getSurfaceRotation(currentRotation));
            }
        });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                PreviewView viewFinder = findViewById(R.id.viewFinder);

                setupTapToFocus(viewFinder); // Link tap logic
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;


                // PROFESSIONAL FIX: Assign the camera object correctly
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }
    private void applyRotation(float degree) {
        View[] views = {iconStamp, txtStamp, iconPhotos, txtPhotos, iconGallery, iconFlash, iconCamera, iconSettings, btnCapture};
        for (View v : views) {
            if (v != null) v.animate().rotation(degree).setDuration(300).start();
        }
    }

    private int getSurfaceRotation(int degrees) {
        switch (degrees) {
            case 90: return android.view.Surface.ROTATION_270;
            case 180: return android.view.Surface.ROTATION_180;
            case 270: return android.view.Surface.ROTATION_90;
            default: return android.view.Surface.ROTATION_0;
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void setupTapToFocus(PreviewView viewFinder) {
        viewFinder.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float x = event.getX();
                float y = event.getY();

                // 1. Move the Focus Square UI
                focusBox.setVisibility(View.VISIBLE);
                focusBox.setX(x - (focusBox.getWidth() / 2f));
                focusBox.setY(y - (focusBox.getHeight() / 2f));

                // 2. Animation for focus square
                focusBox.setScaleX(1.5f);
                focusBox.setScaleY(1.5f);
                focusBox.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start();

                // 3. Fix: Use correct androidx.camera.core classes
                if (camera != null) {
                    MeteringPointFactory factory = viewFinder.getMeteringPointFactory();
                    MeteringPoint point = factory.createPoint(x, y);
                    FocusMeteringAction action = new FocusMeteringAction.Builder(point)
                            .setAutoCancelDuration(3, TimeUnit.SECONDS)
                            .build();

                    camera.getCameraControl().startFocusAndMetering(action);
                }
                return true;
            }
            return true;
        });
    }

//    private void takePhoto() {
//        if (imageCapture == null) return;
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + System.currentTimeMillis());
//        contentValues.put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//
//        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(
//                getContentResolver(), android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues).build();
//
//        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
//            @Override
//            public void onImageSaved(@NonNull ImageCapture.OutputFileResults results) {
//                Toast.makeText(focus_06.this, "Photo Saved!", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onError(@NonNull ImageCaptureException err) { err.printStackTrace(); }
//        });
//    }
private void takePhoto() {
    if (imageCapture == null) return;

    // 1. Get the folder name user wrote at runtime
    String folderName = SettingsStore.get(this, "custom_folder_name", "General");

    ContentValues contentValues = new ContentValues();
    contentValues.put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + System.currentTimeMillis());
    contentValues.put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

    // 2. Add this part to fix the gallery issue
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
        contentValues.put(android.provider.MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName);
    }

    ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(
            getContentResolver(),
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues).build();

    imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
        @Override
        public void onImageSaved(@NonNull ImageCapture.OutputFileResults results) {
            // Updated toast to confirm the folder
            Toast.makeText(focus_06.this, "Photo Saved in " + folderName, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onError(@NonNull ImageCaptureException err) { err.printStackTrace(); }
    });
}

    @Override
    public void onConfigurationChanged(@NonNull android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Automatically rotate the focus square so it matches your tilt
        if (newConfig.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            focusBox.setRotation(-90);
        } else {
            focusBox.setRotation(0);
        }
    }
}