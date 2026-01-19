package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
            if (!m.isChecked()) return;
        }
        // All checked
        startActivity(new Intent(this, stamp_0_up.class));
        finish();
    }

}
