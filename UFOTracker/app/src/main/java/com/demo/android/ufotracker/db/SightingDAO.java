package com.demo.android.ufotracker.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.demo.android.ufotracker.ui.model.Sighting;

import java.util.List;

/**
 *  Room database Sighting DAO
 */
@Dao
public interface SightingDAO {
    @Query("SELECT * FROM sighting")
    LiveData<List<Sighting>> getAll();

    @Query("SELECT * FROM sighting WHERE id IN (:sightingIds)")
    List<Sighting> loadAllByIds(int[] sightingIds);

//    @Query("SELECT * FROM sighting WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    Contact findByName(String first, String last);

    @Query("SELECT * FROM sighting WHERE id=:id")
    Sighting findById(String id);


    @Insert
    void insertAll(Sighting... sightings);

    @Delete
    void delete(Sighting sighting);

    @Query("DELETE FROM sighting")
    void nukeContactTable();
}
