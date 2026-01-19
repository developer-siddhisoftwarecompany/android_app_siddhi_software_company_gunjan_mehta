package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class image12file extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_info_12);

        // Populate the individual rows
        setRow(findViewById(R.id.row1), R.drawable._12_2_image_folder, "File Name", "FB_IMG_169096355200");
        setRow(findViewById(R.id.row2), R.drawable._12_1_image_folder, "Image Path", "/storage/0/emulated/IMG_202307278_4552.jpg");
        setRow(findViewById(R.id.row3), R.drawable._12_3_image_folder, "File Size", "450 KB");
        setRow(findViewById(R.id.row4), R.drawable._12_4_image, "Resolution", "14080 x 1440 (1.6 MP)");
        setRow(findViewById(R.id.row5), R.drawable._12_5_image_cal, "Date & Time", "Feb 30, 2022 09:56:23");
    }

    private void setRow(View row, int icon, String label, String value) {
        ImageView img = row.findViewById(R.id.imgIcon);
        TextView txtLabel = row.findViewById(R.id.txtLabel);
        TextView txtValue = row.findViewById(R.id.txtValue);
        String path = getIntent().getStringExtra("path");

        img.setImageResource(icon);
        txtLabel.setText(label + " : ");
        txtValue.setText(value);
    }
}