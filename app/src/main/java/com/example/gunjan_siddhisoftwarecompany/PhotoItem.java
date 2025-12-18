package com.example.gunjan_siddhisoftwarecompany;

public class PhotoItem {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_PHOTO = 1;

    public int type;
    public String date;
    public int imageRes;

    // Date constructor
    public PhotoItem(String date) {
        this.type = TYPE_DATE;
        this.date = date;
    }

    // Photo constructor
    public PhotoItem(int imageRes) {
        this.type = TYPE_PHOTO;
        this.imageRes = imageRes;
    }
}
