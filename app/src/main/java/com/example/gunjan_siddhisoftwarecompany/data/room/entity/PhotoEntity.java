package com.example.gunjan_siddhisoftwarecompany.data.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photos")
public class PhotoEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    // Image file path
    public String imagePath;

    // Time info
    public long timestamp;

    // Location info
    public double latitude;
    public double longitude;
    public String fullAddress;
    public String shortAddress;

    // Camera state
    public boolean gridEnabled;
    public String gridType;   // none, 3x3, phi, 4x2
    public String ratio;      // 1:1, 4:3

    // Stamp info
    public boolean stamped;
    public String stampId;
}
