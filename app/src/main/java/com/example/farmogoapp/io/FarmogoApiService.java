package com.example.farmogoapp.io;

import com.example.farmogoapp.io.response.animalTypesResponse;
import com.example.farmogoapp.model.incidences.Incidence;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FarmogoApiService {
    @GET("animalTypes")
    Call<ArrayList<animalTypesResponse>> getAnimalTypes();

    @GET("incidences")
    Call<ArrayList<Incidence>> getIncidences();
}
