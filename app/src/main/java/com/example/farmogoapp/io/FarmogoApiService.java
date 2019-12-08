package com.example.farmogoapp.io;

import com.example.farmogoapp.io.response.AnimalTypes;
import com.example.farmogoapp.io.response.User;
import com.example.farmogoapp.io.response.Farms;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FarmogoApiService {
    @GET("animalTypes")
    Call<ArrayList<AnimalTypes>> getAnimalTypes();

    @GET("farms")
    Call<ArrayList<Farms>> getFarms();

    @POST("users")
    Call<User> createUser(@Body User user);

}

