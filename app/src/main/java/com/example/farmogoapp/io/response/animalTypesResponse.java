package com.example.farmogoapp.io.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class animalTypesResponse {
    @SerializedName("uuid")
    String mUid;
    @SerializedName("description")
    String description;
    @SerializedName("icon")
    String icon;

    animalTypesResponse animalTypesResponse;

    public animalTypesResponse(String mUid, String description, String icon){
        this.mUid = mUid;
        this.description = description;
        this.icon = icon;
    }
    public String getmUid(){
        return this.mUid;
    }
    public String getDescription(){
        return this.description;
    }
    public String getIcon(){
        return this.icon;
    }

}