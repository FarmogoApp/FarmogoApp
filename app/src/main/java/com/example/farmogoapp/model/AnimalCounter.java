package com.example.farmogoapp.model;

public class AnimalCounter {
    private String prefix;
    private int counter;

    public AnimalCounter(String prefix, int counter) {
        this.prefix = prefix;
        this.counter = counter;
    }
    public AnimalCounter() {

    }
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String toString(){
        return prefix + counter;
    }

    public void incrementAnimalCounter(){
        this.counter+=1;
    }
}
