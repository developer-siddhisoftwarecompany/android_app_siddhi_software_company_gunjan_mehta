package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gunjan_siddhisoftwarecompany.util.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity1 extends AppCompatActivity {

    RecyclerView recyclerFeatures;
    List<PermissionModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_0);

        recyclerFeatures = findViewById(R.id.recyclerFeatures);
        recyclerFeatures.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        list.add(new PermissionModel(
                R.drawable.permissionn_0_a_cal,
                "Stamp Data",
                "Date, Time, Location & Weather on photo",
                false
        ));

        list.add(new PermissionModel(
                R.drawable.permissionn_0_a_loc,
                "Map Data",
                "Latitude, Longitude & Address",
                false
        ));

        list.add(new PermissionModel(
                R.drawable.permissionn_0_a_folder,
                "File & Folder Options",
                "Manage files automatically",
                false
        ));

        list.add(new PermissionModel(
                R.drawable.permissionn_0_a_cam,
                "Camera Settings",
                "Advanced camera controls",
                false
        ));

        PermissionAdapter adapter = new PermissionAdapter(
                this,
                list,
                this::checkAllPermissions
        );

        recyclerFeatures.setAdapter(adapter);
    }
    public void checkAllPermissions() {
        for (PermissionModel m : list) {
            if (!m.isChecked()) {
                Toast.makeText(this, "Please check all features", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Actually request Android System Permissions for Location
        if (!PermissionUtils.hasLocation(this)) {
            PermissionUtils.requestLocation(this, 200);

        } else {
            goToStampScreen();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToStampScreen();
            } else {
                Toast.makeText(this, "Location permission is needed for Automatic address.", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void goToStampScreen() {
        startActivity(new Intent(this, stamp_0_up.class));
        finish();
    }
//    public void checkAllPermissions() {
//        for (PermissionModel m : list) {
//            if (!m.isChecked()) return;
//        }
//        // All checked
//        startActivity(new Intent(this, stamp_0_up.class));
//        finish();
//    }
////            startActivity(new Intent(this, stamp_0_up.class));
////            finish();
// This should open your per_req_20 or the system dialog
//            startActivity(new Intent(this, per_req_20.class));
}
