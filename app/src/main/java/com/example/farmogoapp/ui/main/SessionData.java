package com.example.farmogoapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionData {

    public static final String ACTUAL_USER = "actual.user";
    public static final String ACTUAL_FARM = "actual.farm";
    public static final String FARMS = "farms";
    public static final String ANIMALS = "animals";
    public static final String ANIMAL_TYPES = "animalTypes";
    public static final String RACES = "races";
    public static final String ANIMAL_CART = "animalCart";

    private static SessionData instance;
    private Context context;
    private ObjectMapper objectMapper;

    private SessionData(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    static void init(Context context) {
        instance = new SessionData(context);
    }

    public static SessionData getInstance() {
        return instance;
    }

    public void setActualUser(User user) {
        saveObject(ACTUAL_USER, user);
    }

    public User getActualUser() {
        return loadObject(ACTUAL_USER, User.class);
    }

    public void setActualFarm(Farm farm) {
        saveObject(ACTUAL_FARM, farm);
    }

    public Farm getActualFarm() {
        return loadObject(ACTUAL_FARM, Farm.class);
    }

    public void setFarms(List<Farm> farms) {
        saveObject(FARMS, farms);
    }

    public List<Farm> getFarms() {
        return loadObject(FARMS, new TypeReference<List<Farm>>() {
        });
    }

    public void setAnimals(List<Animal> animals) {
        saveObject(ANIMALS, animals);
    }

    public List<Animal> getAnimals() {
        return loadObject(ANIMALS, new TypeReference<List<Animal>>() {
        });
    }

    public Optional<Animal> getAnimal(String uuid){
        return getAnimals().stream().filter(a -> a.getUuid().equals(uuid)).findAny();
    }

    public void setAnimalTypes(List<AnimalType> animalTypes) {
        saveObject(ANIMAL_TYPES, animalTypes);
    }

    public List<AnimalType> getAnimalTypes() {
        return loadObject(ANIMAL_TYPES, new TypeReference<List<AnimalType>>() {
        });
    }

    public Optional<AnimalType> getAnimalType(String uuid){
        return getAnimalTypes().stream().filter(a -> a.getUuid().equals(uuid)).findAny();
    }

    public void setRaces(List<Race> races) {
        saveObject(RACES, races);
    }

    public List<Race> getRaces() {
        return loadObject(RACES, new TypeReference<List<Race>>() {
        });
    }

    public Optional<Race> getRace(String uuid){
        return getRaces().stream().filter(a -> a.getUuid().equals(uuid)).findAny();
    }

    public List<String> getAnimalCart(){
        List<String> animals = loadObject(ANIMAL_CART, new TypeReference<ArrayList<String>>() {
        });
        if (animals == null) return new ArrayList<>();
        return animals;
    }

    public List<Animal> getAnimalCardObj(){
        List<String> animals = loadObject(ANIMAL_CART, new TypeReference<ArrayList<String>>() {
        });
        List<Animal> animals1 = getAnimals();
        return animals1.stream().filter(a -> animals.contains(a.getUuid())).collect(Collectors.toList());
    }

    public void addAnimalToCart(String animalId){
        saveObject(ANIMAL_CART, getAnimalCart().add(animalId));
    }

    public void removeAnimalFromCart(String animalId){
        saveObject(ANIMAL_CART, getAnimalCart().remove(animalId));
    }

    public void clearAll() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().clear().commit();
    }


    private void saveObject(String key, Object value) {
        try {
            StringWriter sw = new StringWriter();
            objectMapper.writeValue(sw, value);
            String str = sw.toString();
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            defaultSharedPreferences.edit()
                    .putString(key, str)
                    .commit();
        } catch (IOException ex) {
            Toast.makeText(context, "SESSION DATA ERROR", Toast.LENGTH_SHORT).show();
            Log.e("SessionData", ex.getMessage());
        }
    }

    private <T> T loadObject(String key, Class<T> tClass) {
        try {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String str = defaultSharedPreferences.getString(key, null);
            if (str == null) return null;
            return objectMapper.readValue(str, tClass);
        } catch (IOException ex) {
            Toast.makeText(context, "SESSION DATA ERROR", Toast.LENGTH_SHORT).show();
            Log.e("SessionData", ex.getMessage());
            return null;
        }
    }

    private <T> T loadObject(String key, TypeReference<T> tRef) {
        try {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String str = defaultSharedPreferences.getString(key, null);
            if (str == null) return null;
            return objectMapper.readValue(str, tRef);
        } catch (IOException ex) {
            Toast.makeText(context, "SESSION DATA ERROR", Toast.LENGTH_SHORT).show();
            Log.e("SessionData", ex.getMessage());
            return null;
        }
    }

}
