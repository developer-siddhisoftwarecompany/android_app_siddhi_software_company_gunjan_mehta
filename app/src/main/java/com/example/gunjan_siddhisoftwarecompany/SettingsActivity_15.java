package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity_15 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_15_figma);
        Toast.makeText(this, "Settings Activity Loaded", Toast.LENGTH_LONG).show();
        setRow(R.id.rowLanguage, R.drawable.setting_15_1, "Language", "English");
        setRow(R.id.rowSubscription, R.drawable.setting_15_2, "Subscription", "");
        setRow(R.id.rowShortcut, R.drawable.setting_15_3, "Shortcut", "");
        setRow(R.id.rowHowTo, R.drawable.setting_15_4, "How to use?", "");
        setRow(R.id.rowShare, R.drawable.setting_15_5, "Share App", "");
        setRow(R.id.rowRate, R.drawable.setting_15_6, "Rate App", "");
    }

    private void setRow(int rowId, int icon, String title, String value) {
        View row = findViewById(rowId);

        ImageView imgIcon = row.findViewById(R.id.imgIcon);
        TextView txtTitle = row.findViewById(R.id.txtTitle);
        TextView txtValue = row.findViewById(R.id.txtValue);

        imgIcon.setImageResource(icon);
        txtTitle.setText(title);

        if (value == null || value.isEmpty()) {
            txtValue.setVisibility(View.GONE);
        } else {
            txtValue.setText(value);
            txtValue.setVisibility(View.VISIBLE);
        }
    }
}
