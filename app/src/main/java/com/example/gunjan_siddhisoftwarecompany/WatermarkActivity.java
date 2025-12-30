package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WatermarkActivity extends AppCompatActivity {

    RecyclerView recyclerImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watermark_1); // your XML name

        recyclerImages = findViewById(R.id.recyclerImages);

        int[] imageList = {
                R.drawable.watermark_0_gallary_image,
                R.drawable.watermark_0_crusise,
                R.drawable.watermark_0_gate,
                R.drawable.watermark_0_gate2,
                R.drawable.watermark_0_temple,
                R.drawable.watermark_0_unity
        };

        recyclerImages.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerImages.setAdapter(new ImageAdapterWatermark(this, imageList));
    }
}
