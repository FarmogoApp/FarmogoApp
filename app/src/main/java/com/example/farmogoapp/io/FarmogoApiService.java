package com.example.farmogoapp.io;

import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.model.User;
import com.example.farmogoapp.model.incidences.Incidence;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FarmogoApiService {
    @GET("animalTypes")
    Call<ArrayList<AnimalType>> getAnimalTypes();

    @GET("races")
    Call<ArrayList<Race>> getRaces();

    @GET("farms")
    Call<ArrayList<Farm>> getFarms();

    @GET("farms/{id}")
    Call<Farm> getFarm(@Path("id") String idFarm);

    @POST("users")
    Call<User> createUser(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("incidences")
    Call<Incidence> createIncidence(@Body Incidence incidence);

    @GET("incidences")
    Call<ArrayList<Incidence>> getIncidences();

    @GET("animals/{id}")
    Call<Animal> getAnimal(@Path("id") String idAnimal);

    @GET("animals")
    Call<List<Animal>> getAllAnimals();

    @Headers("Content-Type: application/json")
    @POST("animals")
    Call<Animal> postAnimal(@Body Animal animal);

    @GET("farms/{id}/lastIncidences")
    Call<ArrayList<Incidence>> getLastIncidences(@Path("id") String idFarm);

    @GET("users/firebase/{uuid}")
    Call<User> getByFirebaseUuid(@Path("uuid") String uuid);

    @GET("animals/{id}/incidences")
    Call<ArrayList<Incidence>> getAnimalIncidences(@Path("id") String idAnimal);
}

