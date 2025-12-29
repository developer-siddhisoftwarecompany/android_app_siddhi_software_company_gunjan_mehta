package com.example.gunjan_siddhisoftwarecompany;

public class PermissionModel {

    public int icon;
    public String title;
    public String desc;
    public boolean checked;

    public PermissionModel(int icon, String title, String desc, boolean checked) {
        this.icon = icon;
        this.title = title;
        this.desc = desc;
        this.checked = checked;
    }
}
