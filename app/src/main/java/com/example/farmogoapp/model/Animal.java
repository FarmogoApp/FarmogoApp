package com.example.farmogoapp.model;

public class Animal {

    private long id;

    private boolean selected;

    public Animal(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getOfficialId(){
        return String.format("ES%012d",this.id);
    }


}
