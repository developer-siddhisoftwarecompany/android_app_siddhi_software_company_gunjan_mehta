package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class per_req_20 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissionreq_20);

        TextView btnOk = findViewById(R.id.btnSave);
        TextView btnCancel = findViewById(R.id.btnNo);

        btnOk.setOnClickListener(v -> {
            startActivity(new Intent(this, PermissionActivity1.class));
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}
