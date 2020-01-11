package com.example.farmogoapp.I18nUtils;

public abstract class TypeI18N {

    private String typeLocaleDefault;

    public TypeI18N(String typeLocaleDefault) {
        this.typeLocaleDefault = typeLocaleDefault;
    }

    @Override
    public String toString() {
        return typeLocaleDefault;
    }
}