package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Settings_04 extends AppCompatActivity {
    ImageView iconGrid,iconFocus, bgImage ,iconGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_04);
        iconGrid = findViewById(R.id.iconGrid);
        iconFocus = findViewById(R.id.iconFocus);
        iconGallery = findViewById(R.id.iconGallery);
        bgImage = findViewById(R.id.imgPartyText1);
        // click → open grid_05
        iconGrid.setOnClickListener(v -> {
            Intent intent = new Intent(Settings_04.this, grid_05.class);
            startActivity(intent);
        });

        iconFocus.setOnClickListener(v -> {
            Intent intent = new Intent(Settings_04.this, focus_06.class);
            startActivity(intent);
        });
        bgImage.setOnClickListener(v -> {
            Intent intent = new Intent(Settings_04.this, Stamp_7.class);
            startActivity(intent);
        });
        iconGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Settings_04.this, open_img_11.class);
            startActivity(intent);
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed(); // goes back to MainActivity
    }

}