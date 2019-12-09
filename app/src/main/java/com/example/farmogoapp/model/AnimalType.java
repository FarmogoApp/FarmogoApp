package com.example.farmogoapp.io.response;

import com.google.gson.annotations.SerializedName;

public class AnimalType {
    @SerializedName("uuid")
    String uuid;
    @SerializedName("description")
    String description;
    @SerializedName("icon")
    String icon;

    public AnimalType() {
    }

    public AnimalType(String uuid, String description, String icon){
        this.uuid = uuid;
        this.description = description;
        this.icon = icon;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUuid(){
        return this.uuid;
    }
    public String getDescription(){
        return this.description;
    }
    public String getIcon(){
        return this.icon;
    }

}