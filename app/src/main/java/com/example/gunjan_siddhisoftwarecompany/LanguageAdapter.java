package com.example.gunjan_siddhisoftwarecompany;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    List<LanguageModel> list;

    public LanguageAdapter(LanguagesActivity16 languagesActivity16, List<LanguageModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_language_row_16, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        LanguageModel m = list.get(i);

        h.imgFlag.setImageResource(m.flag);
        h.txtLanguage.setText(m.name);
        h.txtNative.setText(m.nativeName);
        h.imgCheck.setVisibility(m.selected ? View.VISIBLE : View.GONE);

        h.itemView.setOnClickListener(v -> {
            for (LanguageModel lm : list) lm.selected = false;
            m.selected = true;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFlag, imgCheck;
        TextView txtLanguage, txtNative;

        ViewHolder(View v) {
            super(v);
            imgFlag = v.findViewById(R.id.imgFlag);
            imgCheck = v.findViewById(R.id.imgCheck);
            txtLanguage = v.findViewById(R.id.txtLanguage);
            txtNative = v.findViewById(R.id.txtNative);
        }
    }
}
