package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_preview extends AppCompatActivity {

    // All preview layouts
    private final int[] previewLayouts = {
            R.layout.activity_preview_2,
            R.layout.activity_preview_3,
            R.layout.activity_preview_4
    };

    private int currentIndex = 0;

    // UI
    private ImageView btnBack, btnPrev, btnNext;
    private TextView txtCounter, btnUseStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPreview();
    }

    // ================= LOAD PREVIEW =================
    private void loadPreview() {
        setContentView(previewLayouts[currentIndex]);

        btnBack     = findViewById(R.id.btnBack);
        btnPrev     = findViewById(R.id.btnPrev);
        btnNext     = findViewById(R.id.btnNext);
        txtCounter  = findViewById(R.id.txtCounter);
        btnUseStamp = findViewById(R.id.btnUseStamp);

        updateCounter();
        setupClicks();
    }

    // ================= CLICKS =================
    private void setupClicks() {

        btnBack.setOnClickListener(v -> finish());

        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadPreview();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentIndex < previewLayouts.length - 1) {
                currentIndex++;
                loadPreview();
            }
        });

        btnUseStamp.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Stamp selected: Preview " + (currentIndex + 1),
                    Toast.LENGTH_SHORT
            ).show();

            // 🔒 Later:
            // SettingsStore.save(this, "selected_stamp", currentIndex);
            finish();
        });
    }

    // ================= COUNTER =================
    private void updateCounter() {
        txtCounter.setText(
                String.format("%02d/%02d", currentIndex + 1, previewLayouts.length)
        );
    }
}
