//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.widget.ImageView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class mpa extends AppCompatActivity {
//
//
//    private RecyclerView recyclerView;
//    private List<PhotoItem> photoList;
//    private PhotosAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_photos);
//
//        ImageView btnBack = findViewById(R.id.btnBack);
//        recyclerView = findViewById(R.id.recyclerPhotos);
//
//        btnBack.setOnClickListener(v -> finish());
//
//        photoList = new ArrayList<>();
//
//        // Layout manager
//        GridLayoutManager manager = new GridLayoutManager(this, 3);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return photoList.get(position).type == PhotoItem.TYPE_DATE ? 3 : 1;
//            }
//        });
//        recyclerView.setLayoutManager(manager);
//
//        // Adapter (ONLY ONE CONSTRUCTOR)
////            adapter = new PhotosAdapter(photoList, imagePath -> {
////                Intent intent = new Intent(MyPhotosActivity.this, open_img_11.class);
////                intent.putExtra("imagePath", imagePath);
////                startActivity(intent);
////            });
////            recyclerView.setAdapter(adapter);
//
//        // Inside onCreate of MyPhotosActivity
//        adapter = new PhotosAdapter(photoList, clickedPath -> {
//            ArrayList<String> onlyImages = new ArrayList<>();
//            int clickedPosition = 0;
//            int imageCounter = 0;
//
//            for (PhotoItem item : photoList) {
//                if (item.type == PhotoItem.TYPE_PHOTO) {
//                    // Use item.imagePath because that is what's in your PhotoItem class
//                    onlyImages.add(item.imagePath);
//
//                    // Compare the clicked path to find the right index
//                    if (item.imagePath.equals(clickedPath)) {
//                        clickedPosition = imageCounter;
//                    }
//                    imageCounter++;
//                }
//            }
//
//            Intent intent = new Intent(MyPhotosActivity.this, open_img_11.class);
//            intent.putStringArrayListExtra("imageList", onlyImages);
//            intent.putExtra("position", clickedPosition);
//            startActivity(intent);
//        });
//        recyclerView.setAdapter(adapter);
//        loadPhotosFromGallery();
//    }
//
//    private void loadPhotosFromGallery() {
//
//        Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//        String[] projection = {
//                MediaStore.Images.Media._ID,
//                MediaStore.Images.Media.DATE_TAKEN,
//                MediaStore.Images.Media.RELATIVE_PATH
//        };
//
//
//        String selection = MediaStore.Images.Media.RELATIVE_PATH + " LIKE ?";
//        String[] selectionArgs = new String[]{"%CameraX-Image%"};
//
//        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC";
//
//        try (Cursor cursor = getContentResolver().query(
//                collection,
//                projection,
//                selection,
//                selectionArgs,
//                sortOrder
//        )) {
//
//            if (cursor == null) return;
//
//            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//            int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
//
//            String lastDate = "";
//
//            while (cursor.moveToNext()) {
//
//                long id = cursor.getLong(idColumn);
//                long dateMillis = cursor.getLong(dateColumn);
//
//                Uri imageUri = Uri.withAppendedPath(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        String.valueOf(id)
//                );
//
//                String currentDate = new SimpleDateFormat(
//                        "EEE, dd MMM",
//                        Locale.getDefault()
//                ).format(new Date(dateMillis));
//
//                if (!currentDate.equals(lastDate)) {
//                    photoList.add(new PhotoItem(currentDate));
//                    lastDate = currentDate;
//                }
//
//                photoList.add(new PhotoItem(imageUri.toString(), PhotoItem.TYPE_PHOTO));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        adapter.notifyDataSetChanged();
//    }
//}
//
//
//
//
//
//
//
