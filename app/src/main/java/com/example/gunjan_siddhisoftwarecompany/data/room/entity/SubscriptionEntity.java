package com.example.gunjan_siddhisoftwarecompany.data.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscription_table")
public class SubscriptionEntity {
    @PrimaryKey
    public int id = 1; // We only need one row to track the user

    public long trialStartDate; // Time in milliseconds
    public int usageCount;      // Number of times stamp was used
    public boolean isPremium;   // Lifetime/Monthly status
    public String lastPaymentId; // To store API payment reference
}