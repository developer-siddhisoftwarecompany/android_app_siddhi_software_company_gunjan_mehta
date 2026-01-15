package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyPhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);
        ImageView btnBack;
        //  RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerPhotos);

        //  Data list
        List<PhotoItem> list = new ArrayList<>();

        list.add(new PhotoItem("Fri, 28 July"));
        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));

        list.add(new PhotoItem("Tue, 27 July"));
        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));

        //  Adapter
        PhotosAdapter adapter = new PhotosAdapter(list);

        //  Grid layout (3 columns)
        GridLayoutManager manager = new GridLayoutManager(this, 3);

        //  Date = full width, Photo = 1 column
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return list.get(position).type == PhotoItem.TYPE_DATE ? 3 : 1;
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        btnBack = findViewById(R.id.btnBack);
        // Click listener to return to the previous screen
        btnBack.setOnClickListener(v -> {
            finish();


        });
    }
}
