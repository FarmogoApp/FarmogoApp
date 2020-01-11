package com.example.farmogoapp.I18nUtils;

import com.example.farmogoapp.model.incidences.PregnancyType;

public class PregnancyTypeI18n extends TypeI18N {
    private PregnancyType pregnancyType;

    public PregnancyTypeI18n(PregnancyType pregnancyType, String typeLocaleDefault) {
        super(typeLocaleDefault);
        this.pregnancyType = pregnancyType;
    }

    public PregnancyType getPregnancyType() {
        return pregnancyType;
    }
}
