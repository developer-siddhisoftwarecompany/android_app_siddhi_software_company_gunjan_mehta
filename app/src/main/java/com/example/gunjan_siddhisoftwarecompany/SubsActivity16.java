package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubsActivity16 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_16);

        // Feature rows
        setupRow(
                R.id.rowStamp,
                R.drawable.subs_mid_1,
                "Add Stamp On Photos",
                true,
                true
        );

        setupRow(
                R.id.rowCollection,
                R.drawable.subs_mid_2,
                "Unique Stamp Collection",
                true,
                false
        );

        setupRow(
                R.id.rowSupport,
                R.drawable.subs_mid_3,
                "Premium Support",
                true,
                false
        );

        setupRow(
                R.id.rowAds,
                R.drawable.subs_mid_4,
                "No More Ads",
                true,
                false
        );
        findViewById(R.id.btnStartTrial1).setOnClickListener(v ->
                startActivity(new Intent(this, SubsActivity17.class))
        );

    }

    private void setupRow(int rowId, int icon, String text, boolean premium, boolean basic) {

        View row = findViewById(rowId);
        if (row == null) return;
        ImageView imgFeature = row.findViewById(R.id.imgFeature);
        TextView txtFeature = row.findViewById(R.id.txtFeature);
        ImageView imgPremium = row.findViewById(R.id.imgPremium);
        ImageView imgBasic = row.findViewById(R.id.imgBasic);

        if (imgFeature != null) imgFeature.setImageResource(icon);
        if (txtFeature != null) txtFeature.setText(text);

        if (imgPremium != null)
            imgPremium.setImageResource(premium ? R.drawable.subs_16_tick : R.drawable.subs_mins_16);

        if (imgBasic != null)
            imgBasic.setImageResource(basic ? R.drawable.subs_16_tick : R.drawable.subs_mins_16);
    }
}
