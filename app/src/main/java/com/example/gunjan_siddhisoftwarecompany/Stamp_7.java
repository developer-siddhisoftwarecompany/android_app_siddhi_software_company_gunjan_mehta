package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Stamp_7 extends AppCompatActivity {

    // 🔴 Change tracker
    boolean isChanged = false;

    ImageView imgTransLine, imgTransValue, stamp, btnBack;
    TextView txtTransparencyValue;
    ConstraintLayout dateRow, addressRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamp_07_in_figma_design);

        // init views
        imgTransLine = findViewById(R.id.imgTransLine);
        imgTransValue = findViewById(R.id.imgTransValue);
        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);
        stamp = findViewById(R.id.stamp);

        dateRow = findViewById(R.id.dateRow);
        addressRow = findViewById(R.id.addressRow);
        btnBack = findViewById(R.id.btnBack);

        // ================= TRANSPARENCY SLIDER =================
        imgTransLine.post(() -> {

            int lineWidth = imgTransLine.getWidth();
            int thumbWidth = imgTransValue.getWidth();

            imgTransLine.setOnTouchListener((v, event) -> {

                if (event.getAction() == MotionEvent.ACTION_DOWN ||
                        event.getAction() == MotionEvent.ACTION_MOVE) {

                    float x = event.getX();

                    if (x < 0) x = 0;
                    if (x > lineWidth) x = lineWidth;

                    int percent = Math.round((x / lineWidth) * 100);

                    txtTransparencyValue.setText(percent + " %");

                    float thumbX = x - (thumbWidth / 2f);
                    imgTransValue.setTranslationX(thumbX);

                    stamp.setAlpha(percent / 100f);

                    // 🔴 mark change
                    isChanged = true;

                    return true;
                }
                return false;
            });
        });

        // ================= DATE & TIME =================
        dateRow.setOnClickListener(v -> {
            isChanged = true;
            Intent intent = new Intent(Stamp_7.this, Date_time_07.class);
            startActivity(intent);
        });

        // ================= ADDRESS =================
        addressRow.setOnClickListener(v -> {
            isChanged = true;
            showLocationChoice();
        });

        // ================= BACK BUTTON (TOP) =================
        btnBack.setOnClickListener(v -> handleExit());
    }

    // ================= LOCATION CHOICE =================
    private void showLocationChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS Location");

        String[] options = {"Current", "Manual"};

        builder.setItems(options, (dialog, which) -> {

            if (which == 0) {
                Intent intent = new Intent(Stamp_7.this, loc_09.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(Stamp_7.this, loc10.class);
                startActivity(intent);
            }
        });

        builder.show();
    }

    // ================= HANDLE EXIT =================
    private void handleExit() {

        if (!isChanged) {
            finish();
            return;
        }

        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Are you sure?")
                .setMessage("Are you sure you want to edit or go back to the camera?")
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Save", (dialog, which) -> {
                    // later: save data here
                    finish();
                })
                .show();
    }

    // ================= SYSTEM BACK =================
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handleExit();
    }
}