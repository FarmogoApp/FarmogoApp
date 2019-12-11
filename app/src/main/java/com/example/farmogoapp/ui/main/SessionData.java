package com.example.farmogoapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.farmogoapp.model.Farm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class SessionData {

    public static final String ACTUAL_FARM = "actual.farm";
    public static final String FARMS = "farms";
    private static SessionData instance;
    private Context context;
    private ObjectMapper objectMapper;

    private SessionData(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    static void init (Context context){
        instance = new SessionData(context);
    }

    public static SessionData getInstance() {
        return instance;
    }

    public void setActualFarm(Farm farm) throws IOException {
        saveObject(ACTUAL_FARM, farm);
    }

    public Farm getActualFarm() throws IOException {
        return loadObject(ACTUAL_FARM, Farm.class);
    }

    public void setFarms(List<Farm> farms) throws IOException {
        saveObject(FARMS, farms);
    }

    public List<Farm> getFarms() throws IOException {
        return loadObject(FARMS, new TypeReference<List<Farm>>() {});
    }
    

    private void saveObject(String key, Object value) throws IOException {
        StringWriter sw = new StringWriter();
        objectMapper.writeValue(sw, value);
        String str = sw.toString();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit()
                .putString(key, str)
                .commit();
    }

    private <T> T loadObject(String key, Class<T> tClass) throws IOException {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String str = defaultSharedPreferences.getString(key, null);
        if (str == null) return null;
        return objectMapper.readValue(str, tClass);
    }

    private <T> T loadObject(String key, TypeReference<T> tRef) throws IOException {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String str = defaultSharedPreferences.getString(key, null);
        if (str == null) return null;
        return objectMapper.readValue(str, tRef);
    }

}
