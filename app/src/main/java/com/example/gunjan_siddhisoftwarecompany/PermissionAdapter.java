package com.example.gunjan_siddhisoftwarecompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {

    Context context;
    List<PermissionModel> list;

    public PermissionAdapter(Context context, List<PermissionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_feature_permission, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PermissionModel model = list.get(position);

        holder.imgIcon.setImageResource(model.icon);
        holder.txtTitle.setText(model.title);
        holder.txtDesc.setText(model.desc);

        if (model.checked) {
            holder.imgTick.setVisibility(View.VISIBLE);
        } else {
            holder.imgTick.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon, imgTick;
        TextView txtTitle, txtDesc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            imgTick = itemView.findViewById(R.id.imgTick);
            txtTitle = itemView.findViewById(R.id.txtFeatureTitle);
            txtDesc = itemView.findViewById(R.id.txtFeatureDesc);
        }
    }
}
