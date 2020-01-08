package com.example.farmogoapp.model;

public class AnimalCounter {
    private String prefix;
    private long counter;

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

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public String toString(){
        return String.format("%s%012d", prefix, counter);
    }

    public void incrementAnimalCounter(){
        this.counter+=1;
    }
}
