package com.example.farmogoapp.I18nUtils;

import com.example.farmogoapp.model.AnimalType;

public class AnimalTypeI18n extends TypeI18N {
    private AnimalType animalType;

    public AnimalTypeI18n(AnimalType animalType, String typeLocaleDefault) {
        super(typeLocaleDefault);
        this.animalType = animalType;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }
}