package com.example.gunjan_siddhisoftwarecompany;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnPhotoClickListener {
        void onPhotoClick(String imagePath);
    }

    private final List<PhotoItem> list;
    private final OnPhotoClickListener listener;

    public PhotosAdapter(List<PhotoItem> list, OnPhotoClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        if (viewType == PhotoItem.TYPE_DATE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_date, parent, false);
            return new DateVH(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_photo, parent, false);
            return new PhotoVH(v);
        }
    }

    @Override

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PhotoItem item = list.get(position);

        if (holder instanceof DateVH) {
            ((DateVH) holder).txtDate.setText(item.dateText);
        } else if (holder instanceof PhotoVH) {
            // Use Glide to load the image into the holder's ImageView
            Glide.with(holder.itemView.getContext())
                    .load(item.imagePath)
                    .centerCrop()
                    .into(((PhotoVH) holder).imgPhoto);

            // FIX: Set a Click Listener so the photo only opens when touched
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPhotoClick(item.imagePath);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class DateVH extends RecyclerView.ViewHolder {
        TextView txtDate;
        DateVH(View v) {
            super(v);
            txtDate = v.findViewById(R.id.txtDate);
        }
    }

    static class PhotoVH extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        PhotoVH(View v) {
            super(v);
            imgPhoto = v.findViewById(R.id.imgPhoto);
        }
    }
}


//public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    List<PhotoItem> list;
//
//    public PhotosAdapter(List<PhotoItem> list) {
//        this.list = list;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return list.get(position).type;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == PhotoItem.TYPE_DATE) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
//            return new DateVH(v);
//        } else {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
//            return new PhotoVH(v);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        PhotoItem item = list.get(position);
//
//        // FIXED: Using DateVH instead of DateViewHolder
//        if (holder instanceof DateVH) {
//            ((DateVH) holder).txtDate.setText(item.dateText);
//        }
//        // FIXED: Using PhotoVH instead of PhotoViewHolder
//        else if (holder instanceof PhotoVH) {
//            Glide.with(holder.itemView.getContext())
//                    .load(item.imagePath)
//                    .centerCrop()
//                    .into(((PhotoVH) holder).imgPhoto);
//
//            holder.itemView.setOnClickListener(v ->
//                    listener.onPhotoClick(item.imagePath)
//            );
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    // Class names must match what you use in onBindViewHolder
//    static class DateVH extends RecyclerView.ViewHolder {
//        TextView txtDate;
//        DateVH(View v) {
//            super(v);
//            txtDate = v.findViewById(R.id.txtDate);
//        }
//    }
//    public interface OnPhotoClickListener {
//        void onPhotoClick(String imagePath);
//    }
//    OnPhotoClickListener listener;
//
//    public PhotosAdapter(List<PhotoItem> list, OnPhotoClickListener listener) {
//        this.list = list;
//        this.listener = listener;
//    }
//
//    static class PhotoVH extends RecyclerView.ViewHolder {
//        ImageView imgPhoto;
//        PhotoVH(View v) {
//            super(v);
//            imgPhoto = v.findViewById(R.id.imgPhoto);
//        }
//    }
//}
//package com.example.gunjan_siddhisoftwarecompany;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.github.bumptech.glide.Glide;
//
//public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    List<PhotoItem> list;
//
//    public PhotosAdapter(List<PhotoItem> list) {
//        this.list = list;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return list.get(position).type;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        if (viewType == PhotoItem.TYPE_DATE) {
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_date, parent, false);
//            return new DateVH(v);
//        } else {
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_photo, parent, false);
//            return new PhotoVH(v);
//        }
//    }
//
//    @Override
//
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        PhotoItem item = list.get(position);
//
//        // Use the names you defined at the bottom: DateVH and PhotoVH
//        if (holder instanceof DateVH) {
//            ((DateVH) holder).txtDate.setText(item.dateText);
//        } else if (holder instanceof PhotoVH) {
//            // Use Glide here
//
//            com.github.bumptech.glide.Glide.with(holder.itemView.getContext())
//                    .load(item.imagePath)
//                    .centerCrop()
//                    .into(((PhotoVH) holder).imgPhoto);
//        }
//    }
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    static class DateVH extends RecyclerView.ViewHolder {
//        TextView txtDate;
//        DateVH(View v) {
//            super(v);
//            txtDate = v.findViewById(R.id.txtDate);
//        }
//    }
//
//    static class PhotoVH extends RecyclerView.ViewHolder {
//        ImageView imgPhoto;
//        PhotoVH(View v) {
//            super(v);
//            imgPhoto = v.findViewById(R.id.imgPhoto);
//        }
//    }
//}
