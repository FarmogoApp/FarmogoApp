package com.example.farmogoapp.model.incidences;


public class IncidenceWeight extends Incidence {

    private int weight;

    public IncidenceWeight() {
        super(IncidenceType.WEIGHT);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void accept(IncidenceVisitor visitor) {
        visitor.visit(this);
    }
}
