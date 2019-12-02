package com.example.farmogoapp.io;

import com.example.farmogoapp.io.response.animalTypesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FarmogoApiService {
    @GET("animalTypes")
    Call<ArrayList<animalTypesResponse>> getAnimalTypes();
}
