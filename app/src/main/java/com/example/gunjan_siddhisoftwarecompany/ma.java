//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.ContentValues;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
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
//import com.example.gunjan_siddhisoftwarecompany.util.CameraController;
//import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
//import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//import com.google.common.util.concurrent.ListenableFuture;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Locale;
//
//public class MainActivity extends AppCompatActivity {
//    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
//    // Bottom
//    private ImageButton btnCapture;
//    private ImageView iconStamp, iconPhotos;
//    private TextView txtStamp, txtPhotos;
//
//    // Top
//    private ImageView iconGallery, iconFlash, iconRotate, iconTune, iconSetting;
//    private androidx.camera.core.Camera camera;
//    private boolean isFlashOn = false;
//
//    private int currentRotation = 0;
//    private ImageCapture imageCapture;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // ================= INIT VIEWS =================
//        fusedLocationClient = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this);
//        btnCapture = findViewById(R.id.btnCapture);
//
//        iconStamp = findViewById(R.id.iconStamp);
//        txtStamp = findViewById(R.id.txtStamp);
//
//        iconPhotos = findViewById(R.id.iconPhotos);
//        txtPhotos = findViewById(R.id.txtPhotos);
//
//        iconGallery = findViewById(R.id.iconGallery);
//        iconFlash = findViewById(R.id.iconFlash);
//        iconRotate = findViewById(R.id.iconRotate);
//        iconTune = findViewById(R.id.iconTune);
//        iconSetting = findViewById(R.id.iconSetting);
//
//        if (PermissionUtils.hasAll(this)) {
//            startCamera();
//        } else {
//            AppNavigator.openPermissionRequired(this);
//        }
//        // ================= RESTORE STATE =================
//        currentRotation = SettingsStore.get(this, "camera_rotation", 0);
//
//        // ================= CAPTURE =================
//        // ================= CAPTURE =================
//        btnCapture.setOnClickListener(v -> {
//            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
//                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50);
//                takePhoto();
//            });
//        });
//
//
//        Button crashButton = findViewById(R.id.btn_test_crash);
//
//        crashButton.setText("Test Crash");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//// ================= STAMP NAVIGATION =================
//        View.OnClickListener stampListener = v -> {
//            // Check if permissions are granted before moving forward
//            if (!PermissionUtils.hasAll(MainActivity.this)) {
//                // If no permission, go to permission request screen
//                Intent intent = new Intent(MainActivity.this, per_req_20.class);
//                startActivity(intent);
//            } else {
//                // DIRECT NAVIGATION: Go to stamp_0_up activity
//                Intent intent = new Intent(MainActivity.this, stamp_0_up.class);
//                startActivity(intent);
//            }
//        };
//
//// Apply the listener to both the icon and the text label
//        if (iconStamp != null) iconStamp.setOnClickListener(stampListener);
//        if (txtStamp != null) txtStamp.setOnClickListener(stampListener);
//
////        View.OnClickListener stampListener = v -> {
////            // Use your helper to ensure permissions are granted before opening
////            if (PermissionUtils.hasAll(MainActivity.this)) {
////                AppNavigator.openStamp(MainActivity.this);
////            } else {
////                AppNavigator.openPermissionRequired(MainActivity.this);
////            }
////        };
////
////        // Apply to both the icon and the text
////        iconStamp.setOnClickListener(stampListener);
////        txtStamp.setOnClickListener(stampListener);
//
////        View.OnClickListener stampListener = v -> {
////
////            if (!PermissionUtils.hasAll(MainActivity.this)) {
////                // Permission screen
////                Intent intent = new Intent(MainActivity.this, per_req_20.class);
////                startActivity(intent);
////                return;
////            }
////
////            // Open Stamp screen directly
////            Intent intent = new Intent(MainActivity.this, stamp_0_up.class);
////            startActivity(intent);
////        };
////
////// Apply to both icon and text
////        iconStamp.setOnClickListener(stampListener);
////        txtStamp.setOnClickListener(stampListener);
//
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
//
//        // ================= GALLERY =================
//        // ================= GALLERY =================
//        iconGallery.setOnClickListener(v -> {
//            // 1. Safety Check: Ensure permissions are granted
//            if (!PermissionUtils.hasAll(this)) {
//                startActivity(new Intent(this, per_req_20.class));
//                return;
//            }
//
//            // 2. Intent to open the phone's Gallery/Local Storage
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*"); // This ensures only images are shown
//            startActivityForResult(intent, 101);
//        });
//
//        // ================= FLASH =================
////        iconFlash.setOnClickListener(v ->
////                CameraController.toggleFlash(this)
////        );
//        iconFlash.setOnClickListener(v -> {
//
//            if (camera == null) {
//                Toast.makeText(this, "Camera not ready", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // toggle flashlight
//            isFlashOn = !isFlashOn;
//            camera.getCameraControl().enableTorch(isFlashOn);
//        });
//
//        // ================= ROTATE =================
//        iconRotate.setOnClickListener(v -> {
//            currentRotation = (currentRotation + 90) % 360;
//            SettingsStore.save(this, "camera_rotation", currentRotation);
//
//            Toast.makeText(
//                    this,
//                    "Rotation: " + currentRotation + "°",
//                    Toast.LENGTH_SHORT
//            ).show();
//        });
//
//        // ================= TUNE (OPEN OVERLAY) =================
//        iconTune.setOnClickListener(v ->
//                startActivity(new Intent(this, Settings_04.class))
//        );
//
//        // ================= SETTING (GLOBAL SETTINGS) =================
//        iconSetting.setOnClickListener(v ->
//                startActivity(new Intent(this, SettingsActivity_15.class))
//        );
//    }
//
//    // =====================================================
//    // PERMISSION HELPER
//    // =====================================================
//    private void startCamera() {
//        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
//
//        cameraProviderFuture.addListener(() -> {
//            try {
//                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//
//                // Setup Preview
//                Preview preview = new Preview.Builder().build();
////                PreviewView viewFinder = findViewById(R.id.viewFinder);
////                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
//                PreviewView viewFinder = (PreviewView) findViewById(R.id.viewFinder);
//                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
//                // Setup ImageCapture
//                imageCapture = new ImageCapture.Builder()
//                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                        .build();
//
//                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
//
//                cameraProvider.unbindAll();
//                camera= cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
//                camera.getCameraControl().enableTorch(false);
//                isFlashOn = false;
//
//            } catch (Exception e) {
//                Toast.makeText(this, "Failed to start camera: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//
//    private void takePhoto() {
//        if (imageCapture == null) return;
//
//        if (androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == android.content.pm.PackageManager.PERMISSION_GRANTED) {
//
//            // Try getting the last known location
//            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
//                if (location != null) {
//                    // If found, save immediately
//                    processCaptureWithLocation(location);
//                } else {
//                    // If last location is null, request a single fresh update
//                    com.google.android.gms.location.LocationRequest locationRequest =
//                            com.google.android.gms.location.LocationRequest.create()
//                                    .setPriority(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY)
//                                    .setNumUpdates(1);
//
//                    fusedLocationClient.requestLocationUpdates(locationRequest,
//                            new com.google.android.gms.location.LocationCallback() {
//                                @Override
//                                public void onLocationResult(@NonNull com.google.android.gms.location.LocationResult locationResult) {
//                                    processCaptureWithLocation(locationResult.getLastLocation());
//                                }
//                            }, android.os.Looper.getMainLooper());
//                }
//            });
//        } else {
//            saveImageWithMetadata(new ImageCapture.Metadata());
//        }
//    }
//
//    // Helper to bundle location into metadata
//    private void processCaptureWithLocation(android.location.Location location) {
//        ImageCapture.Metadata metadata = new ImageCapture.Metadata();
//        if (location != null) {
//            metadata.setLocation(location);
//        }
//        saveImageWithMetadata(metadata);
//    }
//
//    // Helper to perform the actual file saving
//    private void saveImageWithMetadata(ImageCapture.Metadata metadata) {
//        String name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
//                .format(System.currentTimeMillis());
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
//        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//
//        // Save to the public Pictures/CameraX-Image folder
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image");
//        }
//
//        // Combine metadata (GPS) with the file options
//        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
//                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//                .setMetadata(metadata)
//                .build();
//
//        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
//                new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        Toast.makeText(MainActivity.this, "Saved with Location!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCaptureException exception) {
//                        Toast.makeText(MainActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
//            Uri selectedImage = data.getData();
//            try {
//                getContentResolver().takePersistableUriPermission(selectedImage,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // Take persistent permission so other activities can read this URI
////            final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
////
//
////            ArrayList<String> list = new ArrayList<>();
////            list.add(selectedImage.toString());
////            Intent intent = new Intent(this, open_img_11.class);
////            intent.putExtra("imagePath", selectedImage.toString());
////            startActivity(intent);
//            ArrayList<String> galleryList = new ArrayList<>();
//            galleryList.add(selectedImage.toString());
//
//            // 2. Start open_img_11
//            Intent intent = new Intent(this, open_img_11.class);
//            // CRITICAL: This key MUST be "imageList"
//            intent.putStringArrayListExtra("imageList", galleryList);
//            intent.putExtra("position", 0);
//            startActivity(intent);
//        }
//
////   @Override
////   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
////            android.net.Uri selectedImageUri = data.getData();
////
////            if (selectedImageUri != null) {
////                Intent intent = new Intent(MainActivity.this, open_img_11.class);
////                // WE USE THE KEY "imagePath" TO MATCH YOUR open_img_11 CODE
////                intent.putExtra("imagePath", selectedImageUri.toString());
////                startActivity(intent);
////            }
////        }
////    }
//    }
//}
//
//
//
