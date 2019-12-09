package com.example.farmogoapp.io.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Building {
    @SerializedName("uuid")
    String uuid;
    @SerializedName("name")
    String name;
    @SerializedName("divisions")
    ArrayList<Division> divisions;

    public Building(){

    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDivisions(ArrayList<Division> divisions) {
        this.divisions = divisions;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Division> getDivisions() {
        return divisions;
    }

}
