package com.example.farmogoapp.model.incidences;

import java.time.LocalDate;

public class IncidenceBirth extends Incidence {
    private String officialId;
    private LocalDate birthDate;
    private String raceId;
    private String sex;
    private LocalDate enrollmentDate;

    public IncidenceBirth() {
        super(IncidenceType.BIRTH);
    }

    public String getOfficialId() {
        return officialId;
    }

    public void setOfficialId(String officialId) {
        this.officialId = officialId;
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
}
