package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LanguagesActivity16 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_16);

        RecyclerView recycler = findViewById(R.id.recyclerLanguages);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<LanguageModel> list = new ArrayList<>();
        list.add(new LanguageModel(R.drawable.language16_england, "English", "English", true));
        list.add(new LanguageModel(R.drawable.language16_india, "Hindi", "हिंदी", false));
        list.add(new LanguageModel(R.drawable.language16_french, "French", "Français", false));
        list.add(new LanguageModel(R.drawable.language16_portugal, "Portuguese", "Português", false));
        list.add(new LanguageModel(R.drawable.language16_italy, "Italian", "Italiano", false));
        list.add(new LanguageModel(R.drawable.language16_south_korea, "Korean", "한국어", false));
        list.add(new LanguageModel(R.drawable.language16_china, "Chinese", "中文", false));

        LanguageAdapter adapter = new LanguageAdapter(this, list);
        recycler.setAdapter(adapter);
    }
}
