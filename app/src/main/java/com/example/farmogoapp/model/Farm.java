package com.example.farmogoapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Farm implements Serializable {
    private String uuid;
    private String name;
    private String officialId;
    private List<Building> buildings;
    private AnimalCounter animalCounter;
    private String userOwnerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date subscriptionExpiration;

    public Farm() {
        animalCounter = new AnimalCounter();
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

    public String getOfficialId() {
        return officialId;
    }

    public void setOfficialId(String officialId) {
        this.officialId = officialId;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public AnimalCounter getAnimalCounter() {
        return animalCounter;
    }

    public void setAnimalCounter(AnimalCounter animalCounter) {
        this.animalCounter = animalCounter;
    }


    public String getUserOwnerId() {
        return userOwnerId;
    }

    public void setUserOwnerId(String userOwnerId) {
        this.userOwnerId = userOwnerId;
    }

    public Date getSubscriptionExpiration() {
        return subscriptionExpiration;
    }

    public void setSubscriptionExpiration(Date subscriptionExpiration) {
        this.subscriptionExpiration = subscriptionExpiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Farm farm = (Farm) o;
        return Objects.equals(uuid, farm.uuid) &&
                name.equals(farm.name) &&
                officialId.equals(farm.officialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, officialId);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

