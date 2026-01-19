package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;

public class Settings_04 extends AppCompatActivity {

    private ImageView iconGrid, iconFocus, bgImage, iconGallery;
    private ImageView iconStamp, iconPhotos,iconSetting;
    private TextView txtStamp, txtPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_04);

        iconGrid    = findViewById(R.id.iconGrid);
        iconFocus   = findViewById(R.id.iconFocus);
        iconGallery = findViewById(R.id.iconGallery);
        bgImage     = findViewById(R.id.imgPartyText1); // stamp preview
        iconSetting  = findViewById(R.id.iconSetting);
        iconStamp    = findViewById(R.id.iconStamp);
        txtStamp     = findViewById(R.id.txtStamp);

        iconPhotos   = findViewById(R.id.iconPhotos);
        txtPhotos    = findViewById(R.id.txtPhotos);
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
                startActivity(new Intent(this, stamp_0_up
                        .class))
        );

        iconSetting.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity_15.class))
        );
        // GALLERY (permission required)
        iconGallery.setOnClickListener(v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            startActivity(new Intent(this, open_img_11.class));
        });
        View.OnClickListener openStamp = v -> {
            if (!PermissionUtils.hasAll(this)) {
                startActivity(new Intent(this, per_req_20.class));
                finish();
                return;
            }
            startActivity(new Intent(this, stamp_0_up.class));
        };

        iconStamp.setOnClickListener(v ->
                startActivity(new Intent(this, stamp_0_up.class)));
        txtStamp.setOnClickListener(openStamp);


        // ================= MY PHOTOS =================
        iconPhotos.setOnClickListener(v ->
                startActivity(new Intent(this, MyPhotosActivity.class))
        );

        txtPhotos.setOnClickListener(v ->
                startActivity(new Intent(this, MyPhotosActivity.class))
        );

    }

    @Override
    public void onBackPressed() {
        finish(); // clean close overlay
    }
}
