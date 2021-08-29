package com.lyft.android.ufotracker.model;

public class Sighting {
    public enum SightingType {
        BLOB,
        LAMPSHADE,
        MYSTERIOUS_LIGHTS
    }

    String date;
    SightingType type;
    String speed;

    public Sighting(String date, SightingType type, String speed) {
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

}
