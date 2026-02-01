//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.gunjan_siddhisoftwarecompany.data.room.AppDatabase;
//import com.example.gunjan_siddhisoftwarecompany.data.room.entity.SubscriptionEntity;
//
//public class SubsActivity17 extends AppCompatActivity {
//TextView btnStartTrial;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.subscription_17);
//        View cardMonthly = findViewById(R.id.cardMonthly);
//        View cardYearly = findViewById(R.id.cardYearly);
//        TextView btnStartTrial=findViewById(R.id.btnStartTrial);
//        findViewById(R.id.btnStartTrial).setOnClickListener(v -> {
//            new Thread(() -> {
//                AppDatabase db = AppDatabase.getInstance(this);
//                SubscriptionEntity sub = new SubscriptionEntity();
//                sub.id = 1;
//                sub.trialStartDate = System.currentTimeMillis();
//                sub.isPremium = false; // It's a trial, not a full purchase yet
//                db.subscriptionDao().updateSubscription(sub);
//
//                runOnUiThread(() -> {
//                    startActivity(new Intent(this, thanku_18.class));
//                });
//            }).start();
//        });
//        cardMonthly.setOnClickListener(v -> {
//            // Apply orange border to Monthly, reset Yearly to standard
//            cardMonthly.setBackgroundResource(R.drawable.bg_card_border_orange_17);
//            cardYearly.setBackgroundResource(R.drawable.bg_card_round_i11);
//        });
//
//        cardYearly.setOnClickListener(v -> {
//            // Apply orange border to Yearly, reset Monthly to standard
//            cardYearly.setBackgroundResource(R.drawable.bg_card_border_orange_17);
//            cardMonthly.setBackgroundResource(R.drawable.bg_card_round_i11);
//        });
//
//        setFeature(
//                R.id.rowStamp,
//                R.drawable.subs_plan_mid_1_cam,
//                "Add Stamp On Photos",
//                true,
//                true
//        );
//
//        setFeature(
//                R.id.rowCollection,
//                R.drawable.subs_plan_mid_2_brush,
//                "Unique Stamp Collection",
//                true,
//                false
//        );
//
//        setFeature(
//                R.id.rowSupport,
//                R.drawable.subs_plan_mid_3_rib,
//                "Premium Support",
//                true,
//                false
//        );
//
//        setFeature(
//                R.id.rowAds,
//                R.drawable.subs_plan_mid_4_ads,
//                "No More Ads",
//                true,
//                false
//        );
//    }
//
//    private void setFeature(
//            int rowId,
//            int featureIcon,
//            String text,
//            boolean premium,
//            boolean basic
//    ) {
//        View row = findViewById(rowId);
//
//        ImageView imgFeature = row.findViewById(R.id.imgFeature);
//        TextView txtFeature = row.findViewById(R.id.txtFeature);
//        ImageView imgPremium = row.findViewById(R.id.imgPremium);
//        ImageView imgBasic = row.findViewById(R.id.imgBasic);
//
//        imgFeature.setImageResource(featureIcon);
//        txtFeature.setText(text);
//
//        imgPremium.setImageResource(
//                premium ? R.drawable.subs_plan_mid_tick: R.drawable.subs_plan_mid_cross
//        );
//
//        imgBasic.setImageResource(
//                basic ? R.drawable.subs_plan_mid_tick : R.drawable.subs_plan_mid_cross
//        );
//        findViewById(R.id.btnStartTrial).setOnClickListener(v ->
//                startActivity(new Intent(this, thanku_18.class))
//        );
//
//        findViewById(R.id.btnClose).setOnClickListener(v -> finish());
//
//    }
//
//}









package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.data.room.AppDatabase;
import com.example.gunjan_siddhisoftwarecompany.data.room.entity.SubscriptionEntity;
import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;

public class SubsActivity17 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_17);
        Log.d("SUBS17", "onCreate called");
        // 1. Admin/Premium Bypass
        if (SubscriptionUtils.isAdmin(this) || SubscriptionUtils.isPremium(this)) {
            startActivity(new Intent(this, thanku_18.class));
            finish();
            return;
        }
        Toast.makeText(this, "SubsActivity17 opened", Toast.LENGTH_SHORT).show();

        // 2. Initialize Views
        View cardMonthly = findViewById(R.id.cardMonthly);
        View cardYearly = findViewById(R.id.cardYearly);
        TextView btnStartTrial = findViewById(R.id.btnStartTrial);
        ImageView btnClose = findViewById(R.id.btnClose);

        // 3. Trial Button Logic
        btnStartTrial.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    AppDatabase db = AppDatabase.getInstance(this);
                    SubscriptionEntity sub = new SubscriptionEntity();
                    sub.id = 1; // Used for "WHERE id = 1" in your DAO
                    sub.trialStartDate = System.currentTimeMillis();
                    sub.isPremium = true;

                    db.subscriptionDao().updateSubscription(sub);

                    runOnUiThread(() -> {
//                        startActivity(new Intent(this, thanku_18.class));
                        Intent i = new Intent(this, thanku_18.class);
//                        finish();
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

        // 4. Plan Selection UI Logic
        cardMonthly.setOnClickListener(v -> {
            cardMonthly.setBackgroundResource(R.drawable.bg_card_border_orange_17);
            cardYearly.setBackgroundResource(R.drawable.bg_card_round_i11);
        });

        cardYearly.setOnClickListener(v -> {
            cardYearly.setBackgroundResource(R.drawable.bg_card_border_orange_17);
            cardMonthly.setBackgroundResource(R.drawable.bg_card_round_i11);
        });

        // 5. Setup Features
        setupAllFeatures();

        // 6. Close Button
        btnClose.setOnClickListener(v -> finish());
    }

    private void setupAllFeatures() {
        setFeature(R.id.rowStamp, R.drawable.subs_plan_mid_1_cam, "Add Stamp On Photos", true, true);
        setFeature(R.id.rowCollection, R.drawable.subs_plan_mid_2_brush, "Unique Stamp Collection", true, false);
        setFeature(R.id.rowSupport, R.drawable.subs_plan_mid_3_rib, "Premium Support", true, false);
        setFeature(R.id.rowAds, R.drawable.subs_plan_mid_4_ads, "No More Ads", true, false);
    }

    private void setFeature(int rowId, int icon, String text, boolean premium, boolean basic) {
        View row = findViewById(rowId);
        if (row == null) return;

        ImageView imgFeature = row.findViewById(R.id.imgFeature);
        TextView txtFeature = row.findViewById(R.id.txtFeature);
        ImageView imgPremium = row.findViewById(R.id.imgPremium);
        ImageView imgBasic = row.findViewById(R.id.imgBasic);

        imgFeature.setImageResource(icon);
        txtFeature.setText(text);
        imgPremium.setImageResource(premium ? R.drawable.subs_plan_mid_tick : R.drawable.subs_plan_mid_cross);
        imgBasic.setImageResource(basic ? R.drawable.subs_plan_mid_tick : R.drawable.subs_plan_mid_cross);
    }
}