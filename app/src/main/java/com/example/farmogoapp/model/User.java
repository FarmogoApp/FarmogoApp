package com.example.farmogoapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String uuid;
    private String email;
    private String name;
    private String telephone;
    private List<String> farmsAccessible;
    private String firebaseUuid;
    private List<String> firebaseNotificationTokens;

    public User() {
    }

    public User(String firebaseUuid, String email, String telephone, String name, String uuid){
        this.firebaseUuid = firebaseUuid;
        this.email = email;
        this.telephone = telephone;
        this.name = name;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFarmsAccessible() {
        return farmsAccessible;
    }

    public void setFarmsAccessible(List<String> farmsAccessible) {
        this.farmsAccessible = farmsAccessible;
    }

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public void setFirebaseUuid(String firebaseUuid) {
        this.firebaseUuid = firebaseUuid;
    }

    public List<String> getFirebaseNotificationTokens() {
        return firebaseNotificationTokens;
    }

    public void setFirebaseNotificationTokens(List<String> firebaseNotificationTokens) {
        this.firebaseNotificationTokens = firebaseNotificationTokens;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
