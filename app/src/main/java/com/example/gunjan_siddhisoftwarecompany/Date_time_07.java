package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Date_time_07 extends AppCompatActivity {

    private static final String KEY_DATE = "stamp_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_07_stamp);

        TextView opt1 = findViewById(R.id.opt1);
        TextView opt2 = findViewById(R.id.opt2);
        TextView opt3 = findViewById(R.id.opt3);

        RadioButton r1 = findViewById(R.id.radio1);
        RadioButton r2 = findViewById(R.id.radio2);
        RadioButton r3 = findViewById(R.id.radio3);

        boolean is24 = DateFormat.is24HourFormat(this);
        Date now = new Date();

        String f1 = new SimpleDateFormat(
                is24 ? "dd/MM/yy HH:mm" : "dd/MM/yy hh:mm a",
                Locale.getDefault()
        ).format(now);

        String f2 = new SimpleDateFormat(
                is24 ? "EEEE, dd MMM yyyy HH:mm" : "EEEE, dd MMM yyyy hh:mm a",
                Locale.getDefault()
        ).format(now);

        String f3 = new SimpleDateFormat(
                "EEEE, dd MMM yyyy",
                Locale.getDefault()
        ).format(now);

        opt1.setText(f1);
        opt2.setText(f2);
        opt3.setText(f3);

        opt1.setOnClickListener(v -> saveAndClose(f1));
        opt2.setOnClickListener(v -> saveAndClose(f2));
        opt3.setOnClickListener(v -> saveAndClose(f3));

        r1.setOnClickListener(v -> saveAndClose(f1));
        r2.setOnClickListener(v -> saveAndClose(f2));
        r3.setOnClickListener(v -> saveAndClose(f3));

        findViewById(R.id.btnClose).setOnClickListener(v -> finish());
    }

    private void saveAndClose(String value) {
        SettingsStore.save(this, KEY_DATE, value);
        android.content.Intent resultIntent = new android.content.Intent();
        resultIntent.putExtra("selected_date", value);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
