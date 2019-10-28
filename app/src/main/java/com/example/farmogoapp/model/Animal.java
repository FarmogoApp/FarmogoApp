package com.example.farmogoapp.model;

public class Animal {

    private long id;

    public Animal(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOfficialId(){
        return String.format("ES%012d",this.id);
    }
}
