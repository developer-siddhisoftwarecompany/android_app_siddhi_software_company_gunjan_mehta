package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
import com.google.common.util.concurrent.ListenableFuture;

public class grid_05 extends AppCompatActivity {

  private   ImageView btnGridBack, iconGrid;

    private   TextView gridNone, grid3x3, gridPhi, grid4x2;
    private  ImageView tickNone, tick3x3, tickPhi, tick4x2,btnCapture;
    private androidx.camera.core.Camera camera;
    private ImageCapture imageCapture;
    private static final String KEY_GRID = "camera_grid";
    private CameraGridView05 gridOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_05);
        iconGrid = findViewById(R.id.iconGrid);
        gridOverlay = findViewById(R.id.gridOverlay);
        btnGridBack = findViewById(R.id.btnGridBack);

        gridNone = findViewById(R.id.gridNone);
        grid3x3  = findViewById(R.id.grid3x3);
        gridPhi  = findViewById(R.id.gridPhi);
        grid4x2  = findViewById(R.id.grid4x2);

        tickNone = findViewById(R.id.tickNone);
        tick3x3  = findViewById(R.id.tick3x3);
        tickPhi  = findViewById(R.id.tickPhi);
        tick4x2  = findViewById(R.id.tick4x2);
        btnCapture = findViewById(R.id.btnCapture);
        // Restore saved grid
        restoreGrid();


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


        btnGridBack.setOnClickListener(v -> finish());


        gridNone.setOnClickListener(v -> selectGrid("none"));
        tickNone.setOnClickListener(v -> selectGrid("none"));

        grid3x3.setOnClickListener(v -> selectGrid("3x3"));
        tick3x3.setOnClickListener(v -> selectGrid("3x3"));

        gridPhi.setOnClickListener(v -> selectGrid("phi"));
        tickPhi.setOnClickListener(v -> selectGrid("phi"));

        grid4x2.setOnClickListener(v -> selectGrid("4x2"));
        tick4x2.setOnClickListener(v -> selectGrid("4x2"));
    }

    private void restoreGrid() {
        String grid = SettingsStore.get(this, KEY_GRID, "none");
        selectGrid(grid, false);
    }

    private void selectGrid(String grid) {
        selectGrid(grid, true);
    }

    private void selectGrid(String grid, boolean save) {

        tickNone.setImageResource(
                grid.equals("none") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );
        tick3x3.setImageResource(
                grid.equals("3x3") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );
        tickPhi.setImageResource(
                grid.equals("phi") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );
        tick4x2.setImageResource(
                grid.equals("4x2") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );

        if (save) {
            SettingsStore.save(this, KEY_GRID, grid);
            ChangeTracker.mark();
        }
        if (gridOverlay != null) {
            gridOverlay.setGridType(grid);
        }

        if (save) {
            SettingsStore.save(this, KEY_GRID, grid);
            ChangeTracker.mark();
        }
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
                        android.widget.Toast.makeText(grid_05.this, "Photo Saved!", android.widget.Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                    }
                });
    }
}
