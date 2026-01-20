package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class open_img_11 extends AppCompatActivity {

    // We store the path globally so the (i) button can access it
    private String currentImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_image_11);

        // Initialize UI elements
        ImageView mainPreview = findViewById(R.id.imgPhoto);
        ImageView btnInfo = findViewById(R.id.btnInfo);
        ImageView btnBack = findViewById(R.id.btnBack);

        // 1. Get the URI/Path from the Intent using the key "imagePath"
        // Note: Change this to "imageUri" if your PhotosAdapter specifically uses that
        currentImageUri = getIntent().getStringExtra("imagePath");

        // 2. Load the actual image using Glide
        if (currentImageUri != null) {
            Glide.with(this)
                    .load(currentImageUri)
                    .into(mainPreview);
        }

        // 3. Setup the (i) button to pass data to your info screen
        btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(this, image12file.class);
            // This MUST match the getStringExtra("imageUri") in your image12file.java
            intent.putExtra("imageUri", currentImageUri);
            startActivity(intent);
        });

        // 4. Setup back button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}
//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//
//
////public class open_img_11 extends AppCompatActivity {
////    String currentPath;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.open_image_11);
////
////        ImageView mainPreview = findViewById(R.id.imgPhoto); // Ensure ID matches your XML
////        ImageView btnInfo = findViewById(R.id.btnInfo); // Your (i) button
////
////        // Get the path passed from the Adapter
////        imageUriString = getIntent().getStringExtra("imageUri");
////
////        if (imageUriString != null) {
////            Glide.with(this)
////                    .load(Uri.parse(imageUriString))
////                    .into(mainPreview);
////        }
////
////        // Setup the (i) button to pass the path again
////        btnInfo.setOnClickListener(v -> {
////            Intent intent = new Intent(this, image12file.class);
////            intent.putExtra("PATH_KEY", currentPath);
////            startActivity(intent);
////        });
////    }
////}
//
////public class open_img_11 extends AppCompatActivity {
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.open_image_11);
////        ImageView btnBack, btnInfo;
////        btnBack = findViewById(R.id.btnBack);
////        btnInfo = findViewById(R.id.btnInfo);
////        btnBack.setOnClickListener(v -> {
////            finish(); // returns to previous page (Main / Gallery)
////        });
////        btnInfo.setOnClickListener(v -> {
////            Intent i = new Intent(this, image12file.class);
////            i.putExtra("path", "dummy");
////            startActivity(i);
////        });
////
////    }
////    }
