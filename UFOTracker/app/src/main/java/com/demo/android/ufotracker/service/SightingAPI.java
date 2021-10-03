package com.demo.android.ufotracker.service;

import com.demo.android.ufotracker.ui.model.Sighting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SightingAPI {
    @GET("/sightings")
    Call<List<Sighting>> getAllSightings();
}
