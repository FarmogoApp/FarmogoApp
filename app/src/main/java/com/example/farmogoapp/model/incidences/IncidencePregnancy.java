package com.example.farmogoapp.model.incidences;

public class IncidencePregnancy extends Incidence {
    private PregnancyType pregnancyType;

    public IncidencePregnancy() {
        super(IncidenceType.PREGNANCY);
    }

    public PregnancyType getPregnancyType() {
        return pregnancyType;
    }

    public void setPregnancyType(PregnancyType pregnancyType) {
        this.pregnancyType = pregnancyType;
    }

    @Override
    public void accept(IncidenceVisitor visitor) {
        visitor.visit(this);
    }
}
