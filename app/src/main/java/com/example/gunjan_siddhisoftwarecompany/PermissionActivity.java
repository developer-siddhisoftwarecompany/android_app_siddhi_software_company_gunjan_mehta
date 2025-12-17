package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity {

    boolean cam = false;
    boolean loc = false;
    boolean gallery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission);

        ImageView toggleCamera   = findViewById(R.id.toggleCamera);
        ImageView toggleLocation = findViewById(R.id.toggleLocation);
        ImageView togglePhotos   = findViewById(R.id.togglePhotos);
        ImageView topImage       = findViewById(R.id.topImage);

        toggleCamera.setOnClickListener(v -> {
            cam = !cam;
            toggleCamera.setImageResource(
                    cam ? R.drawable.toggle_on : R.drawable.toggle_off
            );
        });

        toggleLocation.setOnClickListener(v -> {
            loc = !loc;
            toggleLocation.setImageResource(
                    loc ? R.drawable.toggle_on : R.drawable.toggle_off
            );
        });

        togglePhotos.setOnClickListener(v -> {
            gallery = !gallery;
            togglePhotos.setImageResource(
                    gallery ? R.drawable.toggle_on : R.drawable.toggle_off
            );
        });

        topImage.setOnClickListener(v -> {
            if (cam && loc && gallery) {
                startActivity(
                        new Intent(PermissionActivity.this, MainActivity.class)
                );
                finish();
            } else {
                Toast.makeText(
                        this,
                        "Please enable all permissions",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
