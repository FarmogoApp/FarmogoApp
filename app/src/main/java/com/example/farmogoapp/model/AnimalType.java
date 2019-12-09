package com.example.farmogoapp.model;


import java.io.Serializable;

public class AnimalType implements Serializable {

    private String uuid;
    private String description;
    private String icon;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return description;
    }
}
