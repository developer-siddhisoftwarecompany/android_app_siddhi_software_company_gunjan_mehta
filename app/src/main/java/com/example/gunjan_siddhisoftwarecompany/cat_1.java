package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;

public class cat_1 extends AppCompatActivity {

    // keys (future API will read this)
    private static final String KEY_SELECTED_CARD = "cat_selected";

    // top
    ImageView btnBack;

    // category switch
    View trendingBig, trendingRightTop, trendingRightBottom;
    View fest1, fest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_1);

        // ===== INIT =====
        btnBack = findViewById(R.id.btnBack);

        trendingBig        = findViewById(R.id.trendingBig);
        trendingRightTop   = findViewById(R.id.trendingRightTop);
        trendingRightBottom= findViewById(R.id.trendingRightBottom);

        fest1 = findViewById(R.id.festivalGrid).findViewById(0);
        fest2 = findViewById(R.id.fest2);

        // ===== BACK =====
        btnBack.setOnClickListener(v -> finish());

        // ===== RESTORE SELECTION =====
        String saved = SettingsStore.get(this, KEY_SELECTED_CARD, "trendingRightTop");
        highlight(saved);

        // ===== CLICK LISTENERS =====
        trendingBig.setOnClickListener(v -> select("trendingBig"));
        trendingRightTop.setOnClickListener(v -> select("trendingRightTop"));
        trendingRightBottom.setOnClickListener(v -> select("trendingRightBottom"));

        fest1.setOnClickListener(v -> select("fest1"));
        fest2.setOnClickListener(v -> select("fest2"));
    }

    // ===============================
    // SELECTION HANDLER
    // ===============================
    private void select(String id) {
        SettingsStore.save(this, KEY_SELECTED_CARD, id);
        highlight(id);
    }

    // ===============================
    // HIGHLIGHT ORANGE BORDER
    // ===============================
    private void highlight(String id) {

        // reset all
        trendingBig.setBackgroundResource(R.drawable.bg_card_16);
        trendingRightTop.setBackgroundResource(R.drawable.bg_card_16);
        trendingRightBottom.setBackgroundResource(R.drawable.bg_card_16);
        fest1.setBackgroundResource(R.drawable.bg_card_16);
        fest2.setBackgroundResource(R.drawable.bg_card_16);

        // apply orange
        switch (id) {
            case "trendingBig":
                trendingBig.setBackgroundResource(R.drawable.bg_card_border_orange_17);
                break;
            case "trendingRightTop":
                trendingRightTop.setBackgroundResource(R.drawable.bg_card_border_orange_17);
                break;
            case "trendingRightBottom":
                trendingRightBottom.setBackgroundResource(R.drawable.bg_card_border_orange_17);
                break;
            case "fest1":
                fest1.setBackgroundResource(R.drawable.bg_card_border_orange_17);
                break;
            case "fest2":
                fest2.setBackgroundResource(R.drawable.bg_card_border_orange_17);
                break;
        }
    }
}
