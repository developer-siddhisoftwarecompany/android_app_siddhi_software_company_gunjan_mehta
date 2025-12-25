package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubsActivity17 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_17);

        setFeature(
                R.id.rowStamp,
                R.drawable.subs_plan_mid_1_cam,
                "Add Stamp On Photos",
                true,
                true
        );

        setFeature(
                R.id.rowCollection,
                R.drawable.subs_plan_mid_2_brush,
                "Unique Stamp Collection",
                true,
                false
        );

        setFeature(
                R.id.rowSupport,
                R.drawable.subs_plan_mid_3_rib,
                "Premium Support",
                true,
                false
        );

        setFeature(
                R.id.rowAds,
                R.drawable.subs_plan_mid_4_ads,
                "No More Ads",
                true,
                false
        );
    }

    private void setFeature(
            int rowId,
            int featureIcon,
            String text,
            boolean premium,
            boolean basic
    ) {
        View row = findViewById(rowId);

        ImageView imgFeature = row.findViewById(R.id.imgFeature);
        TextView txtFeature = row.findViewById(R.id.txtFeature);
        ImageView imgPremium = row.findViewById(R.id.imgPremium);
        ImageView imgBasic = row.findViewById(R.id.imgBasic);

        imgFeature.setImageResource(featureIcon);
        txtFeature.setText(text);

        imgPremium.setImageResource(
                premium ? R.drawable.subs_plan_mid_tick: R.drawable.subs_plan_mid_cross
        );

        imgBasic.setImageResource(
                basic ? R.drawable.subs_plan_mid_tick : R.drawable.subs_plan_mid_cross
        );
    }
}
