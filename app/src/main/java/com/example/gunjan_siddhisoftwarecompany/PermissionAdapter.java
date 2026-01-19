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

    public interface OnPermissionChangeListener {
        void onPermissionChanged();
    }

    Context context;
    List<PermissionModel> list;
    OnPermissionChangeListener listener;

    public PermissionAdapter(Context context, List<PermissionModel> list, OnPermissionChangeListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
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

        holder.imgTick.setVisibility(model.checked ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            model.setChecked(!model.isChecked());
            notifyItemChanged(position);

            if (context instanceof PermissionActivity1) {
                ((PermissionActivity1) context).checkAllPermissions();
            }
        });

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
