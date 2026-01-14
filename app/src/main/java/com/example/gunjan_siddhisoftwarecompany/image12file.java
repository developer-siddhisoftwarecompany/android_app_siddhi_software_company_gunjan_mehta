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

        // Row 1 – File Name
        View row1 = findViewById(R.id.row1);
        setRow(row1,
                R.drawable._12_2_image_folder,
                "File Name",
                "FB_IMG_169096355200");

        // Row 2 – Image Path
        View row2 = findViewById(R.id.row2);
        setRow(row2,
                R.drawable._12_1_image_folder,
                "Image Path",
                "/storage/emulated/0/DCIM");

        // Row 3 – File Size
        View row3 = findViewById(R.id.row3);
        setRow(row3,
                R.drawable._12_3_image_folder,
                "File Size",
                "450 KB");

        // Row 4 – Resolution
        View row4 = findViewById(R.id.row4);
        setRow(row4,
                R.drawable._12_4_image,
                "Resolution",
                "1440 × 1080");

        // Row 5 – Date & Time
        View row5 = findViewById(R.id.row5);
        setRow(row5,
                R.drawable._12_5_image_cal,
                "Date & Time",
                "Feb 30, 2022 09:56");
    }

    private void setRow(View row, int icon, String label, String value) {

        ImageView img = row.findViewById(R.id.imgIcon);
        TextView txtLabel = row.findViewById(R.id.txtLabel);
        TextView txtValue = row.findViewById(R.id.txtValue);

        img.setImageResource(icon);
        txtLabel.setText(label);
        txtValue.setText(value);
    }
}
