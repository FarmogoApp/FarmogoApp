package com.example.farmogoapp.I18nUtils;

import com.example.farmogoapp.model.incidences.TreatmentType;

public class TreatmentTypeI18n extends TypeI18N {
     private TreatmentType treatmentType;

    public TreatmentTypeI18n(TreatmentType treatmentType, String typeLocaleDefault) {
        super(typeLocaleDefault);
        this.treatmentType = treatmentType;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }
}
