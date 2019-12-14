package com.example.farmogoapp.model.incidences;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IncidenceWeight.class, name = "WEIGHT"),
        @JsonSubTypes.Type(value = IncidenceTreatment.class, name = "TREATMENT"),
        @JsonSubTypes.Type(value = IncidencePregnancy.class, name = "PREGNANCY"),
        @JsonSubTypes.Type(value = IncidenceDischarge.class, name = "DISCHARGE"),
        @JsonSubTypes.Type(value = IncidenceDischarge.class, name = "BIRTH")
})
public abstract class Incidence {
    private String uuid;
    private IncidenceType type;
    private String observations;
    private LocalDate date;
    private LocalDate dueDate;
    private boolean done;
    private boolean complete;
    private LocalDateTime created;
    private String createdBy;
    private String animalId;
    private String farmId;
    private LocalDate removeDate;
    private String removeReason;

    public Incidence(IncidenceType incidenceType) {
        type = incidenceType;
        created = LocalDateTime.now();
        date = LocalDate.now();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public IncidenceType getType() {
        return type;
    }

    public void setType(IncidenceType type) {
        this.type = type;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public LocalDate getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(LocalDate removeDate) {
        this.removeDate = removeDate;
    }

    public String getRemoveReason() {
        return removeReason;
    }

    public void setRemoveReason(String removeReason) {
        this.removeReason = removeReason;
    }

    public abstract void accept(IncidenceVisitor visitor);
}
