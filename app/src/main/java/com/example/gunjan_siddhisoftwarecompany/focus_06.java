package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;

public class focus_06 extends AppCompatActivity {

    private static final String KEY_FOCUS = "camera_focus";

    ImageView iconBack, iconSettings;
    ImageView focusBox;

    // focus states
    private final String[] focusModes = {"auto", "tap", "manual"};
    private int currentIndex = 1; // default = tap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_06_pg_in_figma);

        // ===== INIT =====
        iconBack     = findViewById(R.id.iconGallery);   // back
        iconSettings = findViewById(R.id.iconSettings);
        focusBox     = findViewById(R.id.focusBox);

        // ===== BACK =====
        iconBack.setOnClickListener(v -> finish());

        // ===== SETTINGS =====
        iconSettings.setOnClickListener(v ->
                startActivity(new android.content.Intent(this, SettingsActivity_15.class))
        );

        // ===== RESTORE SAVED FOCUS =====
        String savedFocus = SettingsStore.get(this, KEY_FOCUS, "tap");
        for (int i = 0; i < focusModes.length; i++) {
            if (focusModes[i].equals(savedFocus)) {
                currentIndex = i;
                break;
            }
        }

        Toast.makeText(this, "Focus: " + focusModes[currentIndex], Toast.LENGTH_SHORT).show();

        // ===== FOCUS BOX CLICK =====
        focusBox.setOnClickListener(v -> {
            currentIndex++;
            if (currentIndex >= focusModes.length) currentIndex = 0;

            String mode = focusModes[currentIndex];
            SettingsStore.save(this, KEY_FOCUS, mode);

            Toast.makeText(this, "Focus: " + mode, Toast.LENGTH_SHORT).show();
        });
    }
}
