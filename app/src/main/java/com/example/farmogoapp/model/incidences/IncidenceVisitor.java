package com.example.farmogoapp.model.incidences;


public interface IncidenceVisitor {

    void visit(IncidenceDischarge obj);

    void visit(IncidencePregnancy obj);

    void visit(IncidenceTreatment obj);

    void visit(IncidenceWeight obj);

    void visit(IncidenceBirth obj);

}
