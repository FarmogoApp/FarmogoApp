package com.example.farmogoapp.I18nUtils;

import com.example.farmogoapp.model.incidences.DischargeType;

public class DischargeTypeI18n extends TypeI18N {
    private DischargeType dischargeType;

    public DischargeTypeI18n(DischargeType dischargeType, String typeLocaleDefault) {
        super(typeLocaleDefault);
        this.dischargeType = dischargeType;
    }

    public DischargeType getDischargeType() {
        return dischargeType;
    }
}
