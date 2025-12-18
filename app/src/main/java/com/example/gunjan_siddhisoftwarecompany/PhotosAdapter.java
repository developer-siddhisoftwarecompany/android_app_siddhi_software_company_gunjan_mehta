package com.example.gunjan_siddhisoftwarecompany;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<PhotoItem> list;

    public PhotosAdapter(List<PhotoItem> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PhotoItem item = list.get(position);

        if (holder instanceof DateVH) {
            ((DateVH) holder).txtDate.setText(item.date);
        } else {
            ((PhotoVH) holder).imgPhoto.setImageResource(item.imageRes);
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
