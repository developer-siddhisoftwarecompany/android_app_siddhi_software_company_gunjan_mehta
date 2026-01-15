package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.gunjan_siddhisoftwarecompany.util.funtionsAll;

public class focus_06 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_06_pg_in_figma);

        ImageView iconGallery, iconSettings;

        iconGallery = findViewById(R.id.iconGallery);
        iconSettings = findViewById(R.id.iconSettings);
        iconGallery.setOnClickListener(v -> {
            Intent intent = new Intent(focus_06.this, SettingsActivity_15.class);
            startActivity(intent);

        });
        iconSettings.setOnClickListener(v ->

                funtionsAll.openSettings(this)
        );
    }
}