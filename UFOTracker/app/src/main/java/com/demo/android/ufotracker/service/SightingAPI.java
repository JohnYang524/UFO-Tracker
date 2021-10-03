package com.demo.android.ufotracker.service;

import com.demo.android.ufotracker.model.Sighting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SightingAPI {
    @GET("/sightings")
    Call<List<Sighting>> getAllSightings();

    @POST("/sightings/new")
    Call<Sighting> createSighting(@Body Sighting sighting);

    @PUT("/sightings/edit/{id}")
    Call<Sighting> editSighting(@Path("id") String id, @Body Sighting sighting);

    @DELETE("/sighting/{id}")
    Call<Sighting> deleteSighting(@Path("id") String id);
}
