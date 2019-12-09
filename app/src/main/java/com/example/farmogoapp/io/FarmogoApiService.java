package com.example.farmogoapp.io;

import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.model.User;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.incidences.Incidence;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FarmogoApiService {
    @GET("animalTypes")
    Call<ArrayList<AnimalType>> getAnimalTypes();

    @GET("races")
    Call<ArrayList<Race>> getRaces();

    @GET("farms")
    Call<ArrayList<Farm>> getFarms();

    @POST("users")
    Call<User> createUser(@Body User user);

    @GET("incidences")
    Call<ArrayList<Incidence>> getIncidences();

    @GET("animals/{id}")
    Call<Animal> getAnimal(@Path("id") String idAnimal);

    @GET("animals")
    Call<List<Animal>> getAllAnimals();

}

