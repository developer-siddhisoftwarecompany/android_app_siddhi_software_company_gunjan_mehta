package com.example.gunjan_siddhisoftwarecompany.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gunjan_siddhisoftwarecompany.data.room.dao.PhotoDao;
import com.example.gunjan_siddhisoftwarecompany.data.room.entity.PhotoEntity;

@Database(entities = {PhotoEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PhotoDao photoDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "camera_app_db"
            ).build();
        }
        return INSTANCE;
    }
}
