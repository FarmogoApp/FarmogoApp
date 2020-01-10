package com.example.farmogoapp;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.farmogoapp.model.AnimalType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class I18nUtils {

    public class AnimalTypeI18N{
        private AnimalType animalType;
        private String descI18N;

        public AnimalTypeI18N(AnimalType animalType, String descI18N) {
            this.animalType = animalType;
            this.descI18N = descI18N;
        }

        public AnimalType getAnimalType() {
            return animalType;
        }
        @Override
        public String toString(){
            return descI18N;
        }
    }


    private Context context;

    public I18nUtils(Context context) {
        this.context = context;
    }

    /**
     * Given a sex in the default locale, return it in EN locale
     * @param sexDefaultLocale
     * @return sex in EN locale
     */
    public String getSexENLocale(String sexDefaultLocale){
        String[] sexsEN =  getLocalizedResources( context, Locale.ENGLISH).getStringArray(R.array.sexs);
        String[] sexsDefault=  getLocalizedResources(context, Locale.getDefault()).getStringArray(R.array.sexs);

        for (int i = 0; i < sexsDefault.length ; i++) {
            if(sexsDefault[i].equalsIgnoreCase(sexDefaultLocale)) return sexsEN[i];
        }
        return sexDefaultLocale;
    }

    public List<AnimalTypeI18N> generateI18NAnimalTypeList(List<AnimalType> animalTypeList){
        List<AnimalTypeI18N> animalTypeI18NList = new ArrayList<>();

        String[] animalTypesEN =  getLocalizedResources(context, Locale.ENGLISH).getStringArray(R.array.animalTypes);
        String[] animalTypesDefault=  getLocalizedResources(context, Locale.getDefault()).getStringArray(R.array.animalTypes);


        for (AnimalType animalType: animalTypeList){
            for (int i = 0; i < animalTypesEN.length; i++) {
                if(animalType.getDescription().equalsIgnoreCase(animalTypesEN[i])) {
                    animalTypeI18NList.add(new AnimalTypeI18N(animalType, animalTypesDefault[i]));
                }
            }
        }

        return animalTypeI18NList;
    }

    /**
     * Return resources of a specific locale
     * @param context
     * @param locale
     * @return Resources of a locale
     */
    @NonNull
    private Resources getLocalizedResources(Context context, Locale locale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(locale);
        Context localizedContext = context.createConfigurationContext(conf);
        return localizedContext.getResources();
    }
}
