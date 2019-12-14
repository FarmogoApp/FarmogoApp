package com.example.farmogoapp.io;

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
        this.objectMapper = FarmogoApiJacksonAdapter.getObjectMapper();
    }

    public static void init(Context context) {
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

    public Optional<Farm> getFarm(String uuid) {
        return getFarms().stream().filter(f -> f.getUuid().equals(uuid)).findAny();
    }

    public void setAnimals(List<Animal> animals) {
        saveObject(ANIMALS, animals);
    }

    public List<Animal> getAnimals() {
        List<Animal> animals = loadObject(ANIMALS, new TypeReference<List<Animal>>() {
        });
        List<String> animalCart = getAnimalCart();
        animals.forEach(a -> a.setSelected(animalCart.contains(a.getUuid())));
        return animals;
    }

    public Optional<Animal> getAnimal(String uuid) {
        List<String> animalCart = getAnimalCart();
        return getAnimals().stream()
                .filter(a -> a.getUuid().equals(uuid))
                .peek( a -> a.setSelected(animalCart.contains(a.getUuid())))
                .findAny();
    }

    public void setAnimalTypes(List<AnimalType> animalTypes) {
        saveObject(ANIMAL_TYPES, animalTypes);
    }

    public List<AnimalType> getAnimalTypes() {
        return loadObject(ANIMAL_TYPES, new TypeReference<List<AnimalType>>() {
        });
    }

    public Optional<AnimalType> getAnimalType(String uuid) {
        return getAnimalTypes().stream().filter(a -> a.getUuid().equals(uuid)).findAny();
    }

    public void setRaces(List<Race> races) {
        saveObject(RACES, races);
    }

    public List<Race> getRaces() {
        return loadObject(RACES, new TypeReference<List<Race>>() {
        });
    }

    public Optional<Race> getRace(String uuid) {
        return getRaces().stream().filter(a -> a.getUuid().equals(uuid)).findAny();
    }

    public List<String> getAnimalCart() {
        List<String> animals = loadObject(ANIMAL_CART, new TypeReference<ArrayList<String>>() {
        });
        if (animals == null) return new ArrayList<>();
        return animals;
    }

    public List<Animal> getAnimalCardObj() {
        List<String> animals = loadObject(ANIMAL_CART, new TypeReference<ArrayList<String>>() {
        });
        List<Animal> animals1 = getAnimals();
        return animals1.stream().filter(a -> animals.contains(a.getUuid())).collect(Collectors.toList());
    }

    public void addAnimalToCart(String animalId) {
        List<String> animalCart = getAnimalCart();
        animalCart.add(animalId);
        saveObject(ANIMAL_CART, animalCart);
    }

    public void removeAnimalFromCart(String animalId) {
        List<String> animalCart = getAnimalCart();
        animalCart.remove(animalId);
        saveObject(ANIMAL_CART, animalCart);
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
            Log.d("SessionData save", key + " -> " + str);
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
            Log.d("SessionData load", key + " -> " + str);
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
            Log.d("SessionData load", key + " -> " + str);
            if (str == null) return null;
            return objectMapper.readValue(str, tRef);
        } catch (IOException ex) {
            Toast.makeText(context, "SESSION DATA ERROR", Toast.LENGTH_SHORT).show();
            Log.e("SessionData", ex.getMessage());
            return null;
        }
    }

}
