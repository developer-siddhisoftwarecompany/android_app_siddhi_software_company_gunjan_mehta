package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class loc10 extends AppCompatActivity {

    EditText edtManualAddress;
    TextView btnSave, btnCancel;
    ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_10_pg_in_figma);

        edtManualAddress = findViewById(R.id.edtManualAddress);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnClose = findViewById(R.id.btnClose);

        // CLOSE / CANCEL
        btnCancel.setOnClickListener(v -> finish());
        btnClose.setOnClickListener(v -> finish());

        // SAVE MANUAL ADDRESS
        btnSave.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("manual_address", edtManualAddress.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        });

    }
}
