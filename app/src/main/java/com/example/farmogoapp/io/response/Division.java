package com.example.farmogoapp.io.response;

import com.google.gson.annotations.SerializedName;

class Division {
    @SerializedName("uuid")
    String uuid;
    @SerializedName("name")
    private String name;


    public Division(){

    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

}
