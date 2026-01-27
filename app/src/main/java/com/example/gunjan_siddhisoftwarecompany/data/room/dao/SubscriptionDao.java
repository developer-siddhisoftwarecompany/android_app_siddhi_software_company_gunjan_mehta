package com.example.gunjan_siddhisoftwarecompany.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.gunjan_siddhisoftwarecompany.data.room.entity.SubscriptionEntity;

@Dao
public interface SubscriptionDao {
    @Query("SELECT * FROM subscription_table WHERE id = 1")
    SubscriptionEntity getSubscription();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateSubscription(SubscriptionEntity sub);
}