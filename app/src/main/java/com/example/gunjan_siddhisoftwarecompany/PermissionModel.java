package com.example.gunjan_siddhisoftwarecompany;

public class PermissionModel {

    int icon;
    String title;
    String desc;
    boolean checked;

    public PermissionModel(int icon, String title, String desc, boolean checked) {
        this.icon = icon;
        this.title = title;
        this.desc = desc;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
