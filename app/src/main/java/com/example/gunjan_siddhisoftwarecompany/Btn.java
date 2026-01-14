package com.example.gunjan_siddhisoftwarecompany;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Btn  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btns);

        findViewById(R.id.btn1).setOnClickListener(v ->
                startActivity(new Intent(this, grid_05.class)));

        findViewById(R.id.btn2).setOnClickListener(v ->
                startActivity(new Intent(this, cam1.class)));

        findViewById(R.id.btn3).setOnClickListener(v ->
                startActivity(new Intent(this, settings_04.class)));
        findViewById(R.id.btn4).setOnClickListener(v ->
                startActivity(new Intent(this, focus_06.class)));

        findViewById(R.id.btn5).setOnClickListener(v ->
                startActivity(new Intent(this, Date_time_07.class)));
        findViewById(R.id.btn6).setOnClickListener(v ->
                startActivity(new Intent(this, LanguagesActivity16.class)));

        findViewById(R.id.btn7).setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.btn8).setOnClickListener(v ->
                startActivity(new Intent(this, MyPhotosActivity.class)));
        findViewById(R.id.btn9).setOnClickListener(v ->
                startActivity(new Intent(this, PermissionActivity.class)));
        findViewById(R.id.btn10).setOnClickListener(v ->
                startActivity(new Intent(this, PermissionActivity1.class)));
        findViewById(R.id.btn11).setOnClickListener(v ->
                startActivity(new Intent(this, Stamp_7.class)));
        findViewById(R.id.btn12).setOnClickListener(v ->
                startActivity(new Intent(this, loc_09.class)));
        findViewById(R.id.btn13).setOnClickListener(v ->
                startActivity(new Intent(this, loc10.class)));
        findViewById(R.id.btn14).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity_15.class)));
        findViewById(R.id.btn15).setOnClickListener(v ->
                startActivity(new Intent(this, SubsActivity16.class)));
        findViewById(R.id.btn16).setOnClickListener(v ->
                startActivity(new Intent(this, SubsActivity17.class)));
         findViewById(R.id.btn18).setOnClickListener(v ->
                startActivity(new Intent(this, open_img_11.class)));

        findViewById(R.id.btn19).setOnClickListener(v ->
                startActivity(new Intent(this, shortcut_13.class)));
        findViewById(R.id.btn20).setOnClickListener(v ->
                startActivity(new Intent(this, withoutsav_14.class)));

        findViewById(R.id.btn21).setOnClickListener(v ->
                startActivity(new Intent(this, thanku_18.class)));
        findViewById(R.id.btn22).setOnClickListener(v ->
                startActivity(new Intent(this, open_img_19.class)));
        findViewById(R.id.btn23).setOnClickListener(v ->
                startActivity(new Intent(this, per_req_20.class)));
        findViewById(R.id.btn24).setOnClickListener(v ->
                startActivity(new Intent(this, howtouse_16_u.class)));
        findViewById(R.id.btn25).setOnClickListener(v ->
                startActivity(new Intent(this, drawer6_up.class)));
        findViewById(R.id.btn26).setOnClickListener(v ->
                startActivity(new Intent(this, cam_1_up.class)));
        findViewById(R.id.btn27).setOnClickListener(v ->
                startActivity(new Intent(this, stamp_0_up.class)));
        findViewById(R.id.btn28).setOnClickListener(v ->
                startActivity(new Intent(this, stamp_1_up.class)));
        findViewById(R.id.btn30).setOnClickListener(v ->
                startActivity(new Intent(this, menu_done_8_up.class)));
       findViewById(R.id.btn32).setOnClickListener(v ->
                startActivity(new Intent(this, activity_preview.class)));
        findViewById(R.id.btn31).setOnClickListener(v ->
                startActivity(new Intent(this, image12file.class)));

    }
}
