package com.example.farmogoapp.model.incidences;

import java.time.LocalDate;

public class IncidenceBirth extends Incidence {
    private String childOfficialId;
    private String childId;
    private LocalDate birthDate;
    private String raceId;
    private String sex;
    private LocalDate enrollmentDate;

    public IncidenceBirth() {
        super(IncidenceType.BIRTH);
    }



    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getRaceId() {
        return raceId;
    }

    public void setRaceId(String raceId) {
        this.raceId = raceId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public void accept(IncidenceVisitor visitor) {
        visitor.visit(this);
    }

    public String getChildOfficialId() {
        return childOfficialId;
    }

    public void setChildOfficialId(String childOfficialId) {
        this.childOfficialId = childOfficialId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }
}
