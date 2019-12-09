package com.example.farmogoapp.io.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Farms {
    @SerializedName("uuid")
    String uuid;
    @SerializedName("name")
    String name;
    @SerializedName("officialId")
    String getOfficialId;
    @SerializedName("buildings")
    ArrayList<Building> buildings;

    public Farms(){

    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOfficialId(String getOfficialId) {
        this.getOfficialId = getOfficialId;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getOfficialId() {
        return getOfficialId;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    @Override
    public String toString() {
        return name;
    }

}
