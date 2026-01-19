package com.example.gunjan_siddhisoftwarecompany;

public class PhotoItem {


    public static final int TYPE_DATE = 0;
    public static final int TYPE_PHOTO = 1;

    public int type;
    public String dateText;
    public String imagePath;

    // Constructor for Date Header
    public PhotoItem(String dateText) {
        this.dateText = dateText;
        this.type = TYPE_DATE;
    }

    // Constructor for Real Photo Path
    public PhotoItem(String imagePath, int type) {
        this.imagePath = imagePath;
        this.type = type;
    }
}