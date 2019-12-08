package com.example.farmogoapp.io.response;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firebaseUuid")
    String firebaseUuid;
    @SerializedName("email")
    String email;
    @SerializedName("telephone")
    String telephone;
    @SerializedName("name")
    String name;
    @SerializedName("uuid")
    String uuid;

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public User(String firebaseUuid, String email, String telephone, String name, String uuid){
        this.firebaseUuid = firebaseUuid;
        this.email = email;
        this.telephone = telephone;
        this.name = name;
        this.uuid = uuid;
    }
}
