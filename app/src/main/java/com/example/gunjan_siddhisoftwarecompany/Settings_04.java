package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;

public class Settings_04 extends AppCompatActivity {

    private ImageView iconGrid, iconFocus, bgImage, iconGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_04);

        iconGrid    = findViewById(R.id.iconGrid);
        iconFocus   = findViewById(R.id.iconFocus);
        iconGallery = findViewById(R.id.iconGallery);
        bgImage     = findViewById(R.id.imgPartyText1); // stamp preview

        // GRID
        iconGrid.setOnClickListener(v ->
                startActivity(new Intent(this, grid_05.class))
        );

        // FOCUS
        iconFocus.setOnClickListener(v ->
                startActivity(new Intent(this, focus_06.class))
        );

        // STAMP (preview always allowed, edit guarded inside Stamp_7)
        bgImage.setOnClickListener(v ->
                startActivity(new Intent(this, stamp_0_up.class))
        );

        // GALLERY (permission required)
        iconGallery.setOnClickListener(v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            startActivity(new Intent(this, open_img_11.class));
        });
    }

    @Override
    public void onBackPressed() {
        finish(); // clean close overlay
    }
}
