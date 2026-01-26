package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;

public class open_img_11 extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TextView tvCount;
    private ArrayList<String> imageList;
    private int currentPosition; // Declared to fix the error

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_image_11);

        // 1. Initialize UI elements
        viewPager = findViewById(R.id.viewPager);
        tvCount = findViewById(R.id.tvCount);
        ImageView btnInfo = findViewById(R.id.btnInfo);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnShare = findViewById(R.id.btnShare);

        // 2. Get Data from Intent
        // This list will be either from your App photos or Local DB photos
        imageList = getIntent().getStringArrayListExtra("imageList");
        currentPosition = getIntent().getIntExtra("position", 0);

        // Safety check to prevent crashes if list is empty
        if (imageList == null || imageList.isEmpty()) {
//            imageList = new ArrayList<>();
            Toast.makeText(this, "No image found", Toast.LENGTH_SHORT).show();
            finish();
            return;

        }

        // 3. Setup the Slider Adapter
        viewPager = findViewById(R.id.viewPager);
        ImageSliderAdapter11 adapter = new ImageSliderAdapter11(this, imageList);
        viewPager.setAdapter(adapter);

        // 4. Move to the specific image clicked
        viewPager.setCurrentItem(currentPosition, false);

        // Initial text update (e.g., "1 / 10")
        updateHeader(currentPosition);

        // 5. Update count and position when user swipes left/right
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                updateHeader(position);
            }
        });

        // 6. Info Button ((i) button)
        // This will take the CURRENT swiped image path to the details screen
        btnInfo.setOnClickListener(v -> {
            if (!imageList.isEmpty()) {
                String currentPath = imageList.get(viewPager.getCurrentItem());
                String source = getIntent().getStringExtra("source");
                Intent intent = new Intent(this, image12file.class);
                intent.putExtra("imageUri", currentPath);
                intent.putExtra("source", source);
                startActivity(intent);
            }
        });

        // 7. Back Button
        btnBack.setOnClickListener(v -> finish());
    }

    // Helper method to set the text in the top bar
    private void updateHeader(int position) {
        if (imageList != null && !imageList.isEmpty()) {
            tvCount.setText((position + 1) + " / " + imageList.size());
        }
    }
}
//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ImageView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//public class open_img_11 extends AppCompatActivity {
//    private ViewPager2 viewPager;
//    private TextView tvCount;
//    private ArrayList<String> imageList;
//    // We store the path globally so the (i) button can access it
//    private String currentImageUri;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.open_image_11);
//
//        // Initialize UI elements
//        viewPager = findViewById(R.id.viewPager); // Change ID in XML to viewPager
//        tvCount = findViewById(R.id.tvCount);
//        ImageView btnInfo = findViewById(R.id.btnInfo);
//        ImageView btnBack = findViewById(R.id.btnBack);
//        imageList = getIntent().getStringArrayListExtra("imageList");
//        currentPosition = getIntent().getIntExtra("position", 0);
//
//        if (imageList == null) {
//            imageList = new ArrayList<>();
//        }
//        ImageSliderAdapter11 adapter = new ImageSliderAdapter11(this, imageList);
//        viewPager.setAdapter(adapter);
//// Jump to the image that was clicked in the gallery
//        viewPager.setCurrentItem(currentPosition, false);
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                currentPosition = position;
//                // Update the "1 / 5" text
//                tvCount.setText((currentPosition + 1) + " / " + imageList.size());
//            }
//        });
//        // 1. Get the URI/Path from the Intent using the key "imagePath"
//        // Note: Change this to "imageUri" if your PhotosAdapter specifically uses that
//        currentImageUri = getIntent().getStringExtra("imagePath");
//
//        // 2. Load the actual image using Glide
//        if (currentImageUri != null) {
//            Glide.with(this)
//                    .load(currentImageUri)
//                    .into(mainPreview);
//        }
//
//        // 3. Setup the (i) button to pass data to your info screen
//        btnInfo.setOnClickListener(v -> {
//            Intent intent = new Intent(this, image12file.class);
//            // This MUST match the getStringExtra("imageUri") in your image12file.java
//            intent.putExtra("imageUri", currentImageUri);
//            startActivity(intent);
//        });
//
//        // 4. Setup back button
//        if (btnBack != null) {
//            btnBack.setOnClickListener(v -> finish());
//        }
//    }
//}
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
