package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class withoutsav_14 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.without_saving_14);

        // NO: Just close this dialog and go back to camera WITHOUT saving
        findViewById(R.id.btnNo).setOnClickListener(v -> {
            setResult(RESULT_CANCELED); // Signal: Don't save
            finish();
        });

        // SAVE: Close this and tell the main screen to save
        findViewById(R.id.btnSave).setOnClickListener(v -> {
            setResult(RESULT_OK); // Signal: Save changes
            finish();
        });

        findViewById(R.id.btnClose).setOnClickListener(v -> finish());
    }
}
//public class withoutsav_14 extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.without_saving_14);
//
//    }
//}
