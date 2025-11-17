package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity {

    boolean cam = false, loc = false, gallery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        ImageView toggleCamera = findViewById(R.id.toggleCamera);
        ImageView toggleLocation = findViewById(R.id.toggleLocation);
        ImageView togglePhotos = findViewById(R.id.togglePhotos);

        toggleCamera.setOnClickListener(v -> {
            cam = !cam;
            toggleCamera.setImageResource(cam ? R.drawable.toggle_on : R.drawable.toggle_off);
        });

        toggleLocation.setOnClickListener(v -> {
            loc = !loc;
            toggleLocation.setImageResource(loc ? R.drawable.toggle_on : R.drawable.toggle_off);
        });

        togglePhotos.setOnClickListener(v -> {
            gallery = !gallery;
            togglePhotos.setImageResource(gallery ? R.drawable.toggle_on : R.drawable.toggle_off);
        });
    }
}
