package com.example.farmogoapp.model;

import java.io.Serializable;

public class Race implements Serializable {
    private String uuid;
    private String name;

    public Race(){
    }

    public Race(String name){
        this.name = name;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

