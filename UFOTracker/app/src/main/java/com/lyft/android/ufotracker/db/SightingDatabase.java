package com.lyft.android.ufotracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lyft.android.ufotracker.ui.model.Sighting;

/**
*  Room database for saving Sightings.
 *  Singleton class
*/
@Database(entities = {Sighting.class}, version = 1)
public abstract class SightingDatabase extends RoomDatabase {
    private static final String DB_NAME = "sighting_db";
    private static SightingDatabase instance;

    public static synchronized SightingDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SightingDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return instance;
    }

    public abstract SightingDAO sightingDAO();
}
