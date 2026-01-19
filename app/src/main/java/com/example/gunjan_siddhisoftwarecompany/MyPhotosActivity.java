package com.example.gunjan_siddhisoftwarecompany;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyPhotosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PhotoItem> photoList;
    private PhotosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);

        ImageView btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerPhotos);

        btnBack.setOnClickListener(v -> finish());

        // Setup Layout Manager
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return photoList.get(position).type == PhotoItem.TYPE_DATE ? 3 : 1;
            }
        });
        recyclerView.setLayoutManager(manager);

        // Load the real data
        loadPhotosFromGallery();
    }

    private void loadPhotosFromGallery() {
        photoList = new ArrayList<>();

        Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN
        };

        // Filter to show only images from your app's folder if desired,
        // or just sort by newest first
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC";

        try (Cursor cursor = getContentResolver().query(collection, projection, null, null, sortOrder)) {
            if (cursor != null && cursor.moveToFirst()) {
                String lastDate = "";
                int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);

                do {
                    String path = cursor.getString(dataColumn);
                    long dateMillis = cursor.getLong(dateColumn);

                    // Format the date to check for headers
                    String currentDate = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
                            .format(new Date(dateMillis));

                    if (!currentDate.equals(lastDate)) {
                        photoList.add(new PhotoItem(currentDate));
                        lastDate = currentDate;
                    }

                    photoList.add(new PhotoItem(path, PhotoItem.TYPE_PHOTO));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new PhotosAdapter(photoList);
        recyclerView.setAdapter(adapter);
    }
}
//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MyPhotosActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_photos);
//        ImageView btnBack;
//        //  RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.recyclerPhotos);
//
//        //  Data list
//        List<PhotoItem> list = new ArrayList<>();
//
//        list.add(new PhotoItem("Fri, 28 July"));
//        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
//        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
//        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
//        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
//
//        list.add(new PhotoItem("Tue, 27 July"));
//        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
//        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
//        list.add(new PhotoItem(R.drawable.image_in_the_2_photo_pg));
//
//        //  Adapter
//        PhotosAdapter adapter = new PhotosAdapter(list);
//
//        //  Grid layout (3 columns)
//        GridLayoutManager manager = new GridLayoutManager(this, 3);
//
//        //  Date = full width, Photo = 1 column
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return list.get(position).type == PhotoItem.TYPE_DATE ? 3 : 1;
//            }
//        });
//
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);
//        btnBack = findViewById(R.id.btnBack);
//        // Click listener to return to the previous screen
//        btnBack.setOnClickListener(v -> {
//            finish();
//
//
//        });
//    }
//}
