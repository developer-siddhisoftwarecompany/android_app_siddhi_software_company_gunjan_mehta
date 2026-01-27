package com.example.gunjan_siddhisoftwarecompany.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gunjan_siddhisoftwarecompany.data.room.entity.PhotoEntity;
import com.example.gunjan_siddhisoftwarecompany.data.room.entity.SubscriptionEntity;

import java.util.List;

@Dao
public interface PhotoDao {

    // Insert photo (Camera)
    @Insert
    long insertPhoto(PhotoEntity photo);

    // Get all photos (Gallery)
    @Query("SELECT * FROM photos ORDER BY timestamp DESC")
    List<PhotoEntity> getAllPhotos();

    // Get single photo (Info / Map)
    @Query("SELECT * FROM photos WHERE id = :photoId")
    PhotoEntity getPhotoById(long photoId);

    // Update photo (Stamp applied)
    @Update
    void updatePhoto(PhotoEntity photo);
}
