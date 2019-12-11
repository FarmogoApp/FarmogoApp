package com.example.farmogoapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class SessionData {

    public static final String ACTUAL_USER = "actual.user";
    public static final String ACTUAL_FARM = "actual.farm";
    public static final String FARMS = "farms";
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
    public User getActualUser(){
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
