package com.example.farmogoapp.model.incidences;


public class IncidenceDischarge extends Incidence {

    private DischargeType dischargeType;
    private String healthRegister;

    public IncidenceDischarge() {
        super(IncidenceType.DISCHARGE);
    }

    public DischargeType getDischargeType() {
        return dischargeType;
    }

    public void setDischargeType(DischargeType dischargeType) {
        this.dischargeType = dischargeType;
    }

    public String getHealthRegister() {
        return healthRegister;
    }

    public void setHealthRegister(String healthRegister) {
        this.healthRegister = healthRegister;
    }


    @Override
    public void accept(IncidenceVisitor visitor) {
        visitor.visit(this);
    }
}
