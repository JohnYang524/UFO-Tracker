package com.lyft.android.ufotracker.ui.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.lyft.android.ufotracker.R;

/*
*  Sighting data model.
* */
@Entity(tableName = "sighting")
public class Sighting {

    @NonNull
    @PrimaryKey
    public String id;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "type")
    public SightingType type;
    @ColumnInfo(name = "speed")
    public String speed;

    public Sighting(String id, String date, SightingType type, String speed) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.speed = speed;
    }

    public String getDate() {
        return date;
    }

    public SightingType getType() {
        return type;
    }

    public String getSpeed() {
        return speed;
    }

    public enum SightingType {
        BLOB(SightingCategory.STRANGE_FLYERS, R.drawable.ic_blob),
        LAMPSHADE(SightingCategory.STRANGE_FLYERS, R.drawable.ic_ufo),
        MYSTERIOUS_LIGHTS(SightingCategory.MYSTERIOUS_LIGHTS, R.drawable.ic_lights);

        public SightingCategory category;
        public int imageId;
        SightingType(SightingCategory category, int imageId) {
            this.category = category;
            this.imageId = imageId;
        }
    }

    public enum SightingCategory {
        STRANGE_FLYERS(1),
        MYSTERIOUS_LIGHTS(2);

        public int tabIndex;
        SightingCategory(int tabIndex) {
            this.tabIndex = tabIndex;
        }
    }

}
