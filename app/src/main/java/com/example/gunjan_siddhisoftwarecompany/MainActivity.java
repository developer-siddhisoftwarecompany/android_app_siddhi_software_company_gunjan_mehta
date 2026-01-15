package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton btnCapture;
    TextView txtStamp, txtPhotos;
    ImageView iconPhotos , iconSetting, iconGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       
        setContentView(R.layout.activity_main);

        // Initialize views
        btnCapture = findViewById(R.id.btnCapture);
        txtStamp = findViewById(R.id.txtStamp);
        txtPhotos = findViewById(R.id.txtPhotos);
        iconGallery = findViewById(R.id.iconGallery);
        // Click listeners
        btnCapture.setOnClickListener(v ->
                Toast.makeText(this, "Capture clicked", Toast.LENGTH_SHORT).show()
        );

        txtStamp.setOnClickListener(v ->
                Toast.makeText(this, "Stamp clicked", Toast.LENGTH_SHORT).show()
        );

        txtPhotos.setOnClickListener(v ->
                Toast.makeText(this, "Open Gallery", Toast.LENGTH_SHORT).show()
        );
        View.OnClickListener photoClickListener = v -> {
            // 1. Create the Intent
            Intent intent = new Intent(MainActivity.this, MyPhotosActivity.class);
            // 2. Start the activity
            startActivity(intent);
        };

        txtPhotos.setOnClickListener(photoClickListener);
        iconPhotos.setOnClickListener(photoClickListener);
        iconSetting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Settings_04.class);
            startActivity(intent);
        });
        iconGallery.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, open_img_11.class);
            startActivity(intent);
        });
    }
}
