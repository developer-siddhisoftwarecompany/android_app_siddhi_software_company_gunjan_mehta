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
//               camera= cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
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

//mello
//
//
//
//
//
//
//
//
//package com.example.gunjan_siddhisoftwarecompany;
//
//import static androidx.core.content.ContextCompat.startActivity;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog; // Added for the dialog
//import android.content.ContentValues;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.OrientationEventListener;
//import android.view.View;
//import android.widget.EditText; // Added for the dialog input
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
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
//import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
//import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//import com.google.common.util.concurrent.ListenableFuture;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date; // Added for timestamping
//import java.util.Locale;
//import android.Manifest;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//
//public class MainActivity extends AppCompatActivity {
//    private final ActivityResultLauncher<String> requestPermissionLauncher =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    Toast.makeText(this, "Notifications enabled!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Notifications denied. Check settings.", Toast.LENGTH_LONG).show();
//                }
//            });
//    private ImageButton btnCapture;
//    private OrientationEventListener orientationEventListener;
//    private ImageView iconStamp, iconPhotos, iconGallery, iconFlash, iconRotate, iconTune, iconSetting,iconFlip;
//    private TextView txtStamp, txtPhotos;
//    private androidx.camera.core.Camera camera;
//    private boolean isFlashOn = false;
//    private int currentRotation = 0;
//    private ImageCapture imageCapture;
//    private int lensFacing = CameraSelector.LENS_FACING_BACK;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        askNotificationPermission();
//        // ================= INIT VIEWS =================
//        btnCapture = findViewById(R.id.btnCapture);
//        iconStamp = findViewById(R.id.iconStamp);
//        txtStamp = findViewById(R.id.txtStamp);
//        iconPhotos = findViewById(R.id.iconPhotos);
//        txtPhotos = findViewById(R.id.txtPhotos);
//        iconGallery = findViewById(R.id.iconGallery);
//        iconFlash = findViewById(R.id.iconFlash);
//        iconRotate = findViewById(R.id.iconRotate);
//        iconTune = findViewById(R.id.iconTune);
//        iconSetting = findViewById(R.id.iconSetting);
//        iconFlip = findViewById(R.id.iconFlip);
//        if (PermissionUtils.hasAll(this)) {
//            startCamera();
//        } else {
//            AppNavigator.openPermissionRequired(this);
//        }
//
//        currentRotation = SettingsStore.get(this, "camera_rotation", 0);
//
//        iconRotate.setOnClickListener(v -> {
//           currentRotation = (currentRotation + 90) % 360;
//            SettingsStore.save(this, "camera_rotation", currentRotation);
//            float targetRotation = -currentRotation;
//
//            applyRotation(targetRotation);
//           Toast.makeText(
//                    this,
//                    "Rotation: " + currentRotation + "°",
//                    Toast.LENGTH_SHORT
//            ).show();
//        });
//        iconFlip.setOnClickListener(v -> {
//            // Toggle between front and back
//            if (lensFacing == CameraSelector.LENS_FACING_BACK) {
//                lensFacing = CameraSelector.LENS_FACING_FRONT;
//            } else {
//                lensFacing = CameraSelector.LENS_FACING_BACK;
//            }
//
//            // A nice little animation to show it's flipping
//            v.animate().rotationBy(180).setDuration(300).start();
//
//            // Restart the camera with the new lens
//            startCamera();
//        });
//        // ================= UPDATED CAPTURE LOGIC =================
//        btnCapture.setOnClickListener(v -> {
//            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
//                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50);
//                // Call the folder check before taking a photo
//                checkFolderAndCapture();
//            });
//        });
//
//        // =
//        // ================ NAVIGATION =================
//        iconPhotos.setOnClickListener(v -> startActivity(new Intent(this, MyPhotosActivity.class)));
//        txtPhotos.setOnClickListener(v -> startActivity(new Intent(this, MyPhotosActivity.class)));
//
//        iconTune.setOnClickListener(v -> startActivity(new Intent(this, Settings_04.class)));
//        iconSetting.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity_15.class)));
//
//        iconGallery.setOnClickListener(v -> {
//            if (!PermissionUtils.hasAll(this)) {
//                startActivity(new Intent(this, per_req_20.class));
//                return;
//            }
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent, 101);
//        });
//
//        iconFlash.setOnClickListener(v -> {
//            if (camera == null) return;
//            isFlashOn = !isFlashOn;
//            camera.getCameraControl().enableTorch(isFlashOn);
//        });
//        // Define the logic once
//        View.OnClickListener openStamp = v -> {
//            if (!PermissionUtils.hasAll(MainActivity.this)) {
//                // If permissions are missing, go to permission request activity
//                Intent intent = new Intent(MainActivity.this, per_req_20.class);
//                startActivity(intent);
//                finish(); // Removes Main Activity from the backstack
//            } else {
//                // If permissions are OK, go to the Stamp activity
//                Intent intent = new Intent(MainActivity.this, stamp_0_up.class);
//                startActivity(intent);
//            }
//        };
//
//// Assign the logic to both views
//        iconStamp.setOnClickListener(openStamp);
//        txtStamp.setOnClickListener(openStamp);
//    }
//
//    // ================= FOLDER DIALOG LOGIC =================
//
//    private void checkFolderAndCapture() {
//        // Retrieve saved folder name from SettingsStore
//        String folderName = SettingsStore.get(this, "custom_folder_name", "");
//
//        if (folderName.isEmpty()) {
//            // First-time user: Show dialog to write the name
//            showFolderDialog();
//        } else {
//            // Returning user: Capture photo into existing folder
//            takePhoto(folderName);
//        }
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // Resetting UI to 0 before leaving makes the transition back smoother
//        applyRotation(0);
//    }
//    private void showFolderDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Create Company Folder");
//        builder.setMessage("Enter a name. All your photos will be saved in this folder.");
//
//        final EditText input = new EditText(this);
//        input.setHint("e.g., Project");
//        builder.setView(input);
//
//        builder.setPositiveButton("Save & Capture", (dialog, which) -> {
//            String name = input.getText().toString().trim();
//            if (!name.isEmpty()) {
//                // Save name permanently
//                SettingsStore.save(this, "custom_folder_name", name);
//                takePhoto(name);
//            } else {
//                Toast.makeText(this, "Folder name is required!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//        builder.show();
//    }
//
//    private void takePhoto(String folderName) {
//        if (imageCapture == null) return;
//
//        // Create local timestamp for filename
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        String fileName = "IMG_" + timeStamp;
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
//        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//
//        // Set dynamic folder path: Pictures/[UserWrittenName]
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName);
//        }
//
//        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
//                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//                .build();
//
//        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
//                new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults results) {
//                        Toast.makeText(MainActivity.this, "Photo saved in: " + folderName, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCaptureException err) {
//                        Toast.makeText(MainActivity.this, "Capture failed: " + err.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
//    private void applyRotation(float degree) {
//        // List all views that need to turn when the phone/camera rotates
//        View[] viewsToRotate = {
//                iconStamp, txtStamp,
//                iconPhotos, txtPhotos,
//                iconGallery, iconFlash,
//                iconRotate, iconTune, iconSetting,iconFlip
//        };
//
//        for (View view : viewsToRotate) {
//            if (view != null) {
//                view.animate()
//                        .rotation(degree)
//                        .setDuration(300) // Professional transition speed
//                        .start();
//            }
//        }
//    }
//    private void askNotificationPermission() {
//        // Only Android 13 (API 33) and above require runtime permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                    PackageManager.PERMISSION_GRANTED) {
//                // Already granted, no action needed
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // Optional: Show an educational UI here explaining WHY you need notifications
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
//            }
//        }
//    }
//    private void startCamera() {
//        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
//        cameraProviderFuture.addListener(() -> {
//            try {
//                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                Preview preview = new Preview.Builder().build();
//                PreviewView viewFinder = findViewById(R.id.viewFinder);
//                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
//                imageCapture = new ImageCapture.Builder().build();
//                CameraSelector CameraSelector = new CameraSelector.Builder()
//                        .requireLensFacing(lensFacing)
//                        .build();
//                cameraProvider.unbindAll();
//                camera = cameraProvider.bindToLifecycle(this, CameraSelector, preview, imageCapture);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//    @SuppressLint("WrongConstant")
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Check if it's the gallery request (101) and the user actually picked an image
//        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
//            Uri selectedImage = data.getData();
//
//            if (selectedImage != null) {
//                // 1. Grant permission to read this URI across different activities
//                try {
//                    final int takeFlags = data.getFlags()
//                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    getContentResolver().takePersistableUriPermission(selectedImage, takeFlags);
////                    getContentResolver().takePersistableUriPermission(selectedImage,
////                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                // 2. Prepare the data for open_img_11
//                ArrayList<String> galleryList = new ArrayList<>();
//                galleryList.add(selectedImage.toString());
//
//                // 3. Start the viewer with the required extras
//                Intent intent = new Intent(this, open_img_11.class);
//                intent.putStringArrayListExtra("imageList", galleryList); // Critical key
//                intent.putExtra("position", 0);
//                startActivity(intent);
//            }
//        }
//    }
//}


// mello donwwwwwwwwwwwwwwwwwweeeeeeeeeeeeeeeeeeee



package com.example.gunjan_siddhisoftwarecompany;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "Notifications enabled!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Notifications denied.", Toast.LENGTH_LONG).show();
                }
            });

    private ImageButton btnCapture;
    private ImageView iconStamp, iconPhotos, iconGallery, iconFlash, iconRotate, iconTune, iconSetting, iconFlip;
    private TextView txtStamp, txtPhotos;
    private androidx.camera.core.Camera camera;
    private boolean isFlashOn = false;
    private int currentRotation = 0;
    private ImageCapture imageCapture;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private FusedLocationProviderClient fusedLocationClient;
    private boolean isGpsReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askNotificationPermission();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY, null
            ).addOnSuccessListener(location -> {
                if (location != null) {
                    isGpsReady = true;
                }
            });
        }

        // ================= INIT VIEWS =================
        btnCapture = findViewById(R.id.btnCapture);
        iconStamp = findViewById(R.id.iconStamp);
        txtStamp = findViewById(R.id.txtStamp);
        iconPhotos = findViewById(R.id.iconPhotos);
        txtPhotos = findViewById(R.id.txtPhotos);
        iconGallery = findViewById(R.id.iconGallery);
        iconFlash = findViewById(R.id.iconFlash);
        iconRotate = findViewById(R.id.iconRotate);
        iconTune = findViewById(R.id.iconTune);
        iconSetting = findViewById(R.id.iconSetting);
        iconFlip = findViewById(R.id.iconFlip);

        if (PermissionUtils.hasAll(this)) {
            startCamera();
        } else {
            AppNavigator.openPermissionRequired(this);
        }

        currentRotation = SettingsStore.get(this, "camera_rotation", 0);

        iconRotate.setOnClickListener(v -> {
            currentRotation = (currentRotation + 90) % 360;
            SettingsStore.save(this, "camera_rotation", currentRotation);
            applyRotation(-currentRotation);
            Toast.makeText(this, "Rotation: " + currentRotation + "°", Toast.LENGTH_SHORT).show();
        });

        iconFlip.setOnClickListener(v -> {
            lensFacing = (lensFacing == CameraSelector.LENS_FACING_BACK) ?
                    CameraSelector.LENS_FACING_FRONT : CameraSelector.LENS_FACING_BACK;
            v.animate().rotationBy(180).setDuration(300).start();
            startCamera();
        });

        btnCapture.setOnClickListener(v -> {
            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50);
                checkFolderAndCapture();
            });
        });

        iconPhotos.setOnClickListener(v -> startActivity(new Intent(this, MyPhotosActivity.class)));
        txtPhotos.setOnClickListener(v -> startActivity(new Intent(this, MyPhotosActivity.class)));
        iconTune.setOnClickListener(v -> startActivity(new Intent(this, Settings_04.class)));
        iconSetting.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity_15.class)));

        iconGallery.setOnClickListener(v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 101);
        });

        iconFlash.setOnClickListener(v -> {
            if (camera == null) return;
            isFlashOn = !isFlashOn;
            camera.getCameraControl().enableTorch(isFlashOn);
        });

        View.OnClickListener openStamp = v -> {
            if (!PermissionUtils.hasAll(MainActivity.this)) {
                startActivity(new Intent(MainActivity.this, per_req_20.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, stamp_0_up.class));
            }
        };
        iconStamp.setOnClickListener(openStamp);
        txtStamp.setOnClickListener(openStamp);
    }

    private void checkFolderAndCapture() {
        String folderName = SettingsStore.get(this, "custom_folder_name", "");
        if (folderName.isEmpty()) {
            showFolderDialog();
        } else {
            takePhoto(folderName);
        }
    }

    private void showFolderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Folder");
        final EditText input = new EditText(this);
        input.setHint("e.g., Project");
        builder.setView(input);
        builder.setPositiveButton("Save & Capture", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) {
                SettingsStore.save(this, "custom_folder_name", name);
                takePhoto(name);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void takePhoto(String folderName) {
        if (imageCapture == null) return;

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timeStamp;

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName);
        }

        // GPS Logic
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

//            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
//                ImageCapture.Metadata metadata = new ImageCapture.Metadata();
//                if (location != null) {
//                    metadata.setLocation(location);
//                }
//
//                ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
//                        .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//                        .setMetadata(metadata)
//                        .build();
//
//                performCapture(outputOptions, folderName);
//            });
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                ImageCapture.Metadata metadata = new ImageCapture.Metadata();
                if (location != null) {
                    metadata.setLocation(location);
                    ImageCapture.OutputFileOptions outputOptions =
                            new ImageCapture.OutputFileOptions.Builder(
                                    getContentResolver(),
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    contentValues
                            ).setMetadata(metadata).build();

                    performCapture(outputOptions, folderName);

                } else {
                    fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY, null
                    ).addOnSuccessListener(loc -> {
                        if (loc != null) metadata.setLocation(loc);
                        ImageCapture.OutputFileOptions outputOptions =
                                new ImageCapture.OutputFileOptions.Builder(
                                        getContentResolver(),
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        contentValues
                                ).setMetadata(metadata).build();

                        performCapture(outputOptions, folderName);

                    });
                }
            });

        } else {
            // No GPS Permission - Standard capture
            ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
                    .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    .build();
            performCapture(outputOptions, folderName);
        }
    }

    private void performCapture(ImageCapture.OutputFileOptions outputOptions, String folderName) {
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults results) {
                        Toast.makeText(MainActivity.this, "Photo saved in: " + folderName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException err) {
                        Toast.makeText(MainActivity.this, "Capture failed: " + err.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void applyRotation(float degree) {
        View[] viewsToRotate = {iconStamp, txtStamp, iconPhotos, txtPhotos, iconGallery, iconFlash, iconRotate, iconTune, iconSetting, iconFlip};
        for (View view : viewsToRotate) {
            if (view != null) view.animate().rotation(degree).setDuration(300).start();
        }
    }

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                PreviewView viewFinder = findViewById(R.id.viewFinder);
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
                imageCapture = new ImageCapture.Builder().build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(lensFacing)
                        .build();

                cameraProvider.unbindAll();
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        applyRotation(0);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission(selectedImage, takeFlags);
                } catch (Exception e) { e.printStackTrace(); }

                ArrayList<String> galleryList = new ArrayList<>();
                galleryList.add(selectedImage.toString());
                Intent intent = new Intent(this, open_img_11.class);
                intent.putStringArrayListExtra("imageList", galleryList);
                intent.putExtra("position", 0);
                intent.putExtra("source", "gallery");
                startActivity(intent);
            }
        }
    }
}











// ================= STAMP =================
//        View.OnClickListener openStamp = v -> {
//            if (!PermissionUtils.hasAll(this)) {
//                startActivity(new Intent(this, per_req_20.class));
//                finish();
//                return;
//            }
//            startActivity(new Intent(this, stamp_0_up.class));
//        };
//
//        iconStamp.setOnClickListener(v ->
//                startActivity(new Intent(this, stamp_0_up.class)));
//        txtStamp.setOnClickListener(openStamp);

// ================= STAMP =================
//        View.OnClickListener stampListener = v -> {
//            // 1. Check permissions using your PermissionUtils helper
//            if (PermissionUtils.hasAll(MainActivity.this)) {
//                // 2. Use your AppNavigator helper to open the page
//                AppNavigator.openStamp(MainActivity.this);
//            } else {
//                // 3. Use your helper to open the permission request page
//                AppNavigator.openPermissionRequired(MainActivity.this);
//            }
//        };

// Set the listener to both the Icon and the Text
//        iconStamp.setOnClickListener(stampListener);
//        txtStamp.setOnClickListener(stampListener);
//        ------------
//        iconStamp.setOnClickListener(v ->
//                startActivity(new Intent(this, stamp_0_up.class))
//        );
//
//        txtStamp.setOnClickListener(v ->
//                startActivity(new Intent(this,stamp_0_up.class))
//        );


// ================= STAMP =================
//    private void takePhoto() {
//        if (imageCapture == null) return;
//        ImageCapture.Metadata metadata = new ImageCapture.Metadata();
//        String name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
//                .format(System.currentTimeMillis());
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
//        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image");
//        }
//
//        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
//                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//                .setMetadata(metadata)
//                .build();
//
//        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
//                new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        Toast.makeText(MainActivity.this, "Photo saved successfully!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCaptureException exception) {
//                        Toast.makeText(MainActivity.this, "Capture failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//private void takePhoto() {
//    if (imageCapture == null) return;
//
//    // Check for location permissions before attempting to fetch
//    if (androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//            == android.content.pm.PackageManager.PERMISSION_GRANTED){
//
//        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
////            ImageCapture.Metadata metadata = new ImageCapture.Metadata();
//
//            // If location is found, attach it to the metadata
//            if (location != null) {
//                processCaptureWithLocation(location);
////                metadata.setLocation(location);
//            }
//
////            saveImageWithMetadata(metadata);
////        }
//    } else {
//        // Fallback if no location permissions, just save without location
//        saveImageWithMetadata(new ImageCapture.Metadata());
//    }
//}
//
//    // Helper to keep takePhoto clean
//    private void saveImageWithMetadata(ImageCapture.Metadata metadata) {
//        String name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
//                .format(System.currentTimeMillis());
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
//        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image");
//        }
//
//        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
//                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//                .setMetadata(metadata) // Attaches the location data
//                .build();
//
//        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
//                new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        Toast.makeText(MainActivity.this, "Photo saved with location!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCaptureException exception) {
//                        Toast.makeText(MainActivity.this, "Capture failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void openWithPermission(Runnable action, Object... views) {
//        for (Object v : views) {
//            if (v instanceof ImageView) {
//                ((ImageView) v).setOnClickListener(view -> {
//                    if (!PermissionUtils.hasAll(this)) {
//                        startActivity(new Intent(this, per_req_20.class));
//                        return;
//                    }
//                    action.run();
//                });
//            } else if (v instanceof TextView) {
//                ((TextView) v).setOnClickListener(view -> {
//                    if (!PermissionUtils.hasAll(this)) {
//                        startActivity(new Intent(this, per_req_20.class));
//                        return;
//                    }
//                    action.run();
//                });
//            }
//        }
//
//    }
//
//}
