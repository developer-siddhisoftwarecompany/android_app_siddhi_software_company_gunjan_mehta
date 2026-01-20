package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;

public class stamp_0_up extends AppCompatActivity {

    private static final int REQ_LOCATION = 101;

    private static final String KEY_DATE = "stamp_date";
    private static final String KEY_LOCATION = "stamp_location";
    private static final String KEY_ALPHA = "stamp_alpha";
    private static final String KEY_COLOR = "stamp_color";
    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
    private static final String KEY_STAMP_DRAWABLE = "stamp_drawable";

    private ImageView btnBack, btnDone, stampPreview;
    private TextView txtDateValue, addressText, txtTransparencyValue;
    private ImageView c1, c2, c3, c4, c5, c6;

    private SeekBar seekTransparency;
    private TextView txtSeekValue;

    private int currentAlpha = 60;
    private int currentColor = 0xFFFF9800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamp_0_up_layer_figma);

        // ===== INIT VIEWS =====
        btnBack = findViewById(R.id.btnBack);
        btnDone = findViewById(R.id.btnDone);
        stampPreview = findViewById(R.id.stamp);

        txtDateValue = findViewById(R.id.txtDateValue);
        addressText = findViewById(R.id.addressText);
        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);

        txtSeekValue = findViewById(R.id.txtSeekValue);
        seekTransparency = findViewById(R.id.seekTransparency);

        c1 = findViewById(R.id.colorCircle1);
        c2 = findViewById(R.id.colorCircle2);
        c3 = findViewById(R.id.colorCircle3);
        c4 = findViewById(R.id.colorCircle4);
        c5 = findViewById(R.id.colorCircle5);
        c6 = findViewById(R.id.colorCircle6);

        restoreSavedData();

        // ===== BACK =====
        btnBack.setOnClickListener(v ->
                startActivity(new Intent(this, withoutsav_14.class))
        );

        btnDone.setOnClickListener(v -> {
            ChangeTracker.mark();
            finish();
        });

        // ===== DATE =====
        findViewById(R.id.dateRow).setOnClickListener(v ->
                startActivity(new Intent(this, Date_time_07.class))
        );

        // ===== LOCATION =====
        findViewById(R.id.addressRow).setOnClickListener(v -> {
            if (!PermissionUtils.hasLocation(this)) {
                startActivity(new Intent(this, per_req_20.class));
                return;
            }
            startActivityForResult(new Intent(this, loc_09.class), REQ_LOCATION);
        });

        // ===== STAMP (PREMIUM) =====
        stampPreview.setOnClickListener(v -> {
            if (!SubscriptionUtils.isPremium(this)) {
                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
                return;
            }

            int drawable = R.drawable.christma_text_setting_pg;
            stampPreview.setImageResource(drawable);
            SettingsStore.save(this, KEY_STAMP_DRAWABLE, drawable);
            ChangeTracker.mark();
        });

        // ===== SEEK BAR =====
        seekTransparency.setProgress(currentAlpha);
        txtSeekValue.setText(String.valueOf(currentAlpha));
        moveSeekLabel(currentAlpha);

        seekTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress < 10) progress = 10;

                currentAlpha = progress;
                txtTransparencyValue.setText(progress + " %");
                stampPreview.setAlpha(progress / 100f);

                txtSeekValue.setText(String.valueOf(progress));
                moveSeekLabel(progress);

                SettingsStore.save(stamp_0_up.this, KEY_ALPHA, progress);
                ChangeTracker.mark();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {
                txtSeekValue.setVisibility(View.VISIBLE);
            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                txtSeekValue.setVisibility(View.VISIBLE);
            }
        });

        // ===== ARROW DROPDOWN =====
        findViewById(R.id.iconTransArrow).setOnClickListener(v -> {

            final String[] values = {"10","20","30","40","50","60","70","80","90","100"};

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Transparency")
                    .setItems(values, (dialog, which) -> {

                        int selected = Integer.parseInt(values[which]);

                        currentAlpha = selected;
                        seekTransparency.setProgress(selected);

                        txtTransparencyValue.setText(selected + " %");
                        stampPreview.setAlpha(selected / 100f);

                        txtSeekValue.setText(String.valueOf(selected));
                        moveSeekLabel(selected);

                        SettingsStore.save(this, KEY_ALPHA, selected);
                        ChangeTracker.mark();
                    })
                    .show();
        });

        // ===== COLORS =====
        setFreeColor(c1, 0xFFFF9800);
        setFreeColor(c2, 0xFF000000);
        setFreeColor(c3, 0xFFFFEB3B);
        setFreeColor(c4, 0xFF4CAF50);
        setFreeColor(c5, 0xFF009688);
        setFreeColor(c6, 0xFF2196F3);
    }

    // ===== MOVE FLOATING VALUE =====
    private void moveSeekLabel(int progress) {

        int max = seekTransparency.getMax();
        int width = seekTransparency.getWidth()
                - seekTransparency.getPaddingStart()
                - seekTransparency.getPaddingEnd();

        float percent = progress / (float) max;

        float x = seekTransparency.getX()
                + seekTransparency.getPaddingStart()
                + width * percent
                - txtSeekValue.getWidth() / 2f;

        txtSeekValue.setX(x);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_LOCATION && resultCode == RESULT_OK && data != null) {
            String address = data.getStringExtra("manual_address");
            if (address != null && !address.isEmpty()) {
                addressText.setText(address);
                SettingsStore.save(this, KEY_LOCATION, address);
                SettingsStore.save(this, KEY_LOCATION_MODE, "manual");
                ChangeTracker.mark();
            }
        }
    }

    private void setFreeColor(ImageView view, int color) {
        view.setOnClickListener(v -> {
            currentColor = color;
            stampPreview.setColorFilter(color);
            SettingsStore.save(this, KEY_COLOR, color);
            ChangeTracker.mark();
        });
    }

    private void restoreSavedData() {

        txtDateValue.setText(
                SettingsStore.get(this, KEY_DATE, txtDateValue.getText().toString())
        );

        String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
        addressText.setText(
                mode.equals("manual")
                        ? SettingsStore.get(this, KEY_LOCATION, "Tap to set location")
                        : "Automatic"
        );

        currentAlpha = SettingsStore.get(this, KEY_ALPHA, 60);
        if (currentAlpha < 10) currentAlpha = 10;

        txtTransparencyValue.setText(currentAlpha + " %");
        stampPreview.setAlpha(currentAlpha / 100f);
        seekTransparency.setProgress(currentAlpha);
        seekTransparency.post(() -> {
            txtSeekValue.setText(String.valueOf(currentAlpha));
            moveSeekLabel(currentAlpha);
        });

        currentColor = SettingsStore.get(this, KEY_COLOR, 0xFFFF9800);
//        stampPreview.setColorFilter(currentColor);
        stampPreview.clearColorFilter();

        int savedStamp = SettingsStore.get(
                this,
                KEY_STAMP_DRAWABLE,
                R.drawable.cal_grp_7_th_screen
        );
        stampPreview.setImageResource(savedStamp);
    }
}

//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
//import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;
//import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;
//import com.example.gunjan_siddhisoftwarecompany.util.SubscriptionUtils;
//
//public class stamp_0_up extends AppCompatActivity {
//
//    private static final int REQ_LOCATION = 101;
//
//    private static final String KEY_DATE = "stamp_date";
//    private static final String KEY_LOCATION = "stamp_location";
//    private static final String KEY_ALPHA = "stamp_alpha";
//    private static final String KEY_COLOR = "stamp_color";
//    private static final String KEY_LOCATION_MODE = "stamp_location_mode";
//
//    private ImageView btnBack, btnDone, stampPreview,iconTransArrow;
////    private ImageView imgPartyText1;
//    private TextView txtDateValue, addressText, txtTransparencyValue;
//    private ImageView c1, c2, c3, c4, c5, c6;
//
//    private int currentAlpha = 60;
//    private int currentColor = 0xFFFF9800;
//    private static final String KEY_STAMP_DRAWABLE = "stamp_drawable";
//    private TextView txtSeekValue;
//    private SeekBar seekTransparency;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.stamp_0_up_layer_figma);
//        seekTransparency = findViewById(R.id.seekTransparency);
//        txtSeekValue = findViewById(R.id.txtSeekValue);
//
//// initial state
//        seekTransparency.setProgress(currentAlpha);
//        txtSeekValue.setText(currentAlpha + "");
//        moveSeekLabel(currentAlpha);
//
//        btnBack = findViewById(R.id.btnBack);
//        btnDone = findViewById(R.id.btnDone);
//        stampPreview = findViewById(R.id.stamp);
//
//
//        txtDateValue = findViewById(R.id.txtDateValue);
//        addressText = findViewById(R.id.addressText);
//        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);
//
//        c1 = findViewById(R.id.colorCircle1);
//        c2 = findViewById(R.id.colorCircle2);
//        c3 = findViewById(R.id.colorCircle3);
//        c4 = findViewById(R.id.colorCircle4);
//        c5 = findViewById(R.id.colorCircle5);
//        c6 = findViewById(R.id.colorCircle6);
//
//
//        restoreSavedData();
//
//        btnBack.setOnClickListener(v ->
//                startActivity(new Intent(this, withoutsav_14.class))
//        );
//
//        btnDone.setOnClickListener(v -> {
//            ChangeTracker.mark();
//            finish();
//        });
//
//        // DATE (FREE)
//        findViewById(R.id.dateRow).setOnClickListener(v ->
//                startActivity(new Intent(this, Date_time_07.class))
//        );
//
//        // LOCATION (FREE)
//        findViewById(R.id.addressRow).setOnClickListener(v -> {
//            if (!PermissionUtils.hasLocation(this)) {
//                startActivity(new Intent(this, per_req_20.class));
//                return;
//            }
//            startActivityForResult(
//                    new Intent(this, loc_09.class),
//                    REQ_LOCATION
//            );
//        });
//        stampPreview.setOnClickListener(v -> {
//
//            if (!SubscriptionUtils.isPremium(this)) {
//                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            int drawable = R.drawable.christma_text_setting_pg;
//
//            stampPreview.setImageResource(drawable);
//            SettingsStore.save(this, KEY_STAMP_DRAWABLE, drawable);
//
//            ChangeTracker.mark();
//        });
//
////        stampPreview.setOnClickListener(v -> {
////            if (!SubscriptionUtils.isPremium(this)) {
////                Toast.makeText(this, "Premium feature", Toast.LENGTH_SHORT).show();
////                return;
////            }
////            stampPreview.setImageResource(R.drawable.christma_text_setting_pg);
////            ChangeTracker.mark();
////        });
//        // TRANSPARENCY (FREE)
////        SeekBar seekTransparency = findViewById(R.id.seekTransparency);
////
////        seekTransparency.setProgress(currentAlpha);
//
////        seekTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                currentAlpha = progress;
////                txtTransparencyValue.setText(progress + " %");
////                stampPreview.setAlpha(progress / 100f);
////
////                SettingsStore.save(stamp_0_up.this, KEY_ALPHA, progress);
////                ChangeTracker.mark();
////            }
////
////            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
////            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
////
////        });
//        SeekBar seekTransparency = findViewById(R.id.seekTransparency);
//
//        seekTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                if (progress < 10) progress = 10;
//
//                currentAlpha = progress;
//
//                txtTransparencyValue.setText(progress + " %");
//                stampPreview.setAlpha(progress / 100f);
//
//                txtSeekValue.setText(String.valueOf(progress));
//                moveSeekLabel(progress);
//
//                SettingsStore.save(stamp_0_up.this, KEY_ALPHA, progress);
//                ChangeTracker.mark();
//            }
//
//            @Override public void onStartTrackingTouch(SeekBar seekBar) {
//                txtSeekValue.setVisibility(View.VISIBLE);
//            }
//
//            @Override public void onStopTrackingTouch(SeekBar seekBar) {
//                txtSeekValue.setVisibility(View.VISIBLE);
//            }
//        });
//
//
//        findViewById(R.id.iconTransArrow).setOnClickListener(v -> {
//
//            final String[] values = {"10","20","30","40","50","60","70","80","90","100"};
//
//            new androidx.appcompat.app.AlertDialog.Builder(this)
//                    .setTitle("Transparency")
//                    .setItems(values, (dialog, which) -> {
//
//                        int selected = Integer.parseInt(values[which]);
//
//                        currentAlpha = selected;
//
//                        seekTransparency.setProgress(selected);
//
//                        txtTransparencyValue.setText(selected + " %");
//                        stampPreview.setAlpha(selected / 100f);
//
//                        txtSeekValue.setText(String.valueOf(selected));
//                        moveSeekLabel(selected);
//
//                        SettingsStore.save(this, KEY_ALPHA, selected);
//                        ChangeTracker.mark();
//                    })
//                    .show();
//        });
//
//
//        private void moveSeekLabel(int progress) {
//
//            int max = seekTransparency.getMax();
//            int width = seekTransparency.getWidth()
//                    - seekTransparency.getPaddingStart()
//                    - seekTransparency.getPaddingEnd();
//
//            float percent = progress / (float) max;
//
//            float x = seekTransparency.getX()
//                    + seekTransparency.getPaddingStart()
//                    + width * percent
//                    - txtSeekValue.getWidth() / 2f;
//
//            txtSeekValue.setX(x);
//        }
//
//
//
//        // COLORS (FREE)
//        setFreeColor(c1, 0xFFFF9800);
//        setFreeColor(c2, 0xFF000000);
//        setFreeColor(c3, 0xFFFFEB3B);
//        setFreeColor(c4, 0xFF4CAF50);
//        setFreeColor(c5, 0xFF009688);
//        setFreeColor(c6, 0xFF2196F3);
//
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQ_LOCATION && resultCode == RESULT_OK && data != null) {
//            String address = data.getStringExtra("manual_address");
//            if (address != null && !address.isEmpty()) {
//                addressText.setText(address);
//                SettingsStore.save(this, KEY_LOCATION, address);
//                SettingsStore.save(this, KEY_LOCATION_MODE, "manual");
//                ChangeTracker.mark();
//            }
//        }
//    }
//
//    private void setFreeColor(ImageView view, int color) {
//        view.setOnClickListener(v -> {
//            currentColor = color;
//            stampPreview.setColorFilter(color);
//            SettingsStore.save(this, KEY_COLOR, color);
//            ChangeTracker.mark();
//        });
//    }
//
//    private void restoreSavedData() {
//        txtDateValue.setText(
//                SettingsStore.get(this, KEY_DATE, txtDateValue.getText().toString())
//        );
//
//        String mode = SettingsStore.get(this, KEY_LOCATION_MODE, "current");
//        addressText.setText(
//                mode.equals("manual")
//                        ? SettingsStore.get(this, KEY_LOCATION, "Tap to set location")
//                        : "Automatic"
//        );
//
//        currentAlpha = SettingsStore.get(this, KEY_ALPHA, 60);
//        if (currentAlpha < 10) currentAlpha = 10;
//        txtTransparencyValue.setText(currentAlpha + " %");
//        stampPreview.setAlpha(currentAlpha / 100f);
//        SeekBar seekTransparency = findViewById(R.id.seekTransparency);
//        seekTransparency.setProgress(currentAlpha);
//        currentColor = SettingsStore.get(this, KEY_COLOR, 0xFFFF9800);
//        stampPreview.setColorFilter(currentColor);
//        int savedStamp = SettingsStore.get(
//                this,
//                KEY_STAMP_DRAWABLE,
//                R.drawable.cal_grp_7_th_screen   // default Christmas Party
//        );
//        stampPreview.setImageResource(savedStamp);
//    }
//}
