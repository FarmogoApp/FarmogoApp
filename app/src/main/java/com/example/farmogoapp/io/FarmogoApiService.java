package com.example.farmogoapp.io;

import com.example.farmogoapp.io.response.AnimalTypes;
import com.example.farmogoapp.io.response.User;
import com.example.farmogoapp.io.response.Farms;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.incidences.Incidence;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FarmogoApiService {
    @GET("animalTypes")
    Call<ArrayList<AnimalTypes>> getAnimalTypes();

    @GET("farms")
    Call<ArrayList<Farms>> getFarms();

    @POST("users")
    Call<User> createUser(@Body User user);

    @GET("incidences")
    Call<ArrayList<Incidence>> getIncidences();

    @GET("animals/{id}")
    Call<Animal> getAnimal(@Path("id") String idAnimal);

}

