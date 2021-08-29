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

}
