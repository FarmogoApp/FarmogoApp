package com.example.farmogoapp.model.incidences;

public class IncidenceTreatment extends Incidence {

    private TreatmentType treatmentType;
    private String medicine;
    private String dose;

    public IncidenceTreatment() {
        super(IncidenceType.TREATMENT);
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @Override
    public void accept(IncidenceVisitor visitor) {
        visitor.visit(this);
    }
}
