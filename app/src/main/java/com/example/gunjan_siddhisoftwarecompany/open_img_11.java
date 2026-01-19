package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class open_img_11 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_image_11);
        ImageView btnBack, btnInfo;
        btnBack = findViewById(R.id.btnBack);
        btnInfo = findViewById(R.id.btnInfo);
        btnBack.setOnClickListener(v -> {
            finish(); // returns to previous page (Main / Gallery)
        });
        btnInfo.setOnClickListener(v -> {
            Intent i = new Intent(this, image12file.class);
            i.putExtra("path", "dummy");
            startActivity(i);
        });

    }
    }
