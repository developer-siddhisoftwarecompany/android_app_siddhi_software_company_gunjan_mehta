package com.example.gunjan_siddhisoftwarecompany.util;

public class ChangeTracker {
    public static boolean changed = false;

    public static void mark() {
        changed = true;
    }

    public static void reset() {
        changed = false;
    }
}
