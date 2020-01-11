package com.example.farmogoapp.I18nUtils;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.farmogoapp.R;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.incidences.DischargeType;
import com.example.farmogoapp.model.incidences.PregnancyType;
import com.example.farmogoapp.model.incidences.TreatmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class I18nUtils {

    private Context context;

    private String[] sexsEN;
    private String[] animalTypesEN;
    private String[] pregnancyTypesEN;
    private String[] treatmentTypesEN;
    private String[] dischargeTypesEN;

    private String[] sexsDefault;
    private String[] animalTypesDefault;
    private String[] pregnancyTypesDefault;
    private String[] treatmentTypesDefault;
    private String[] dischargeTypesDefault;

    private List<TypeI18N> pregnancyTypes;
    private List<TypeI18N> treatmentTypes;
    private List<TypeI18N> dischargeTypes;

    public I18nUtils(Context context) {
        this.context = context;
        initializeArrays();
    }

    private void initializeArrays() {
        if (sexsEN == null)                 sexsEN = getLocalizedResources(context, Locale.ENGLISH).getStringArray(R.array.sexs);
        if (animalTypesEN == null)          animalTypesEN = getLocalizedResources(context, Locale.ENGLISH).getStringArray(R.array.animalTypes);
        if (pregnancyTypesEN == null)       pregnancyTypesEN = getLocalizedResources(context, Locale.ENGLISH).getStringArray(R.array.pregnancyTypes);
        if (treatmentTypesEN == null)       treatmentTypesEN = getLocalizedResources(context, Locale.ENGLISH).getStringArray(R.array.treatmentTypes);
        if (dischargeTypesEN == null)       dischargeTypesEN = getLocalizedResources(context, Locale.ENGLISH).getStringArray(R.array.dischargeTypes);

        if (sexsDefault == null)            sexsDefault = getLocalizedResources(context, Locale.getDefault()).getStringArray(R.array.sexs);
        if (animalTypesDefault == null)     animalTypesDefault = getLocalizedResources(context, Locale.getDefault()).getStringArray(R.array.animalTypes);
        if (pregnancyTypesDefault == null)  pregnancyTypesDefault = getLocalizedResources(context, Locale.getDefault()).getStringArray(R.array.pregnancyTypes);
        if (treatmentTypesDefault == null)  treatmentTypesDefault = getLocalizedResources(context, Locale.getDefault()).getStringArray(R.array.treatmentTypes);
        if (dischargeTypesDefault == null)  dischargeTypesDefault = getLocalizedResources(context, Locale.getDefault()).getStringArray(R.array.dischargeTypes);

    }

    public List<TypeI18N> getPregnancyTypesI18n(){
        if(pregnancyTypes == null){
            pregnancyTypes = new ArrayList<>();

            for (PregnancyType pregnancyType: PregnancyType.values()) {
                for (int i = 0; i < pregnancyTypesEN.length; i++) {
                    if(pregnancyType.toString().equalsIgnoreCase(pregnancyTypesEN[i])){
                        pregnancyTypes.add(new PregnancyTypeI18n(pregnancyType, pregnancyTypesDefault[i]));
                    }
                }
            }
        }
        return pregnancyTypes;
    }

    public List<TypeI18N> getTreatmentTypesI18n(){
        if(treatmentTypes == null){
            treatmentTypes = new ArrayList<>();

            for (TreatmentType treatmentType: TreatmentType.values()) {
                for (int i = 0; i < pregnancyTypesEN.length; i++) {
                    if(treatmentType.toString().equalsIgnoreCase(treatmentTypesEN[i])){
                        treatmentTypes.add(new TreatmentTypeI18n(treatmentType, treatmentTypesDefault[i]));
                    }
                }
            }
        }
        return treatmentTypes;
    }

    public List<TypeI18N> getDischargeTypesI18n(){
        if(dischargeTypes == null){
            dischargeTypes = new ArrayList<>();

            for (DischargeType dischargeType: DischargeType.values()) {
                for (int i = 0; i < dischargeTypesEN.length; i++) {
                    if(dischargeType.toString().equalsIgnoreCase(dischargeTypesEN[i])){
                        dischargeTypes.add(new DischargeTypeI18n(dischargeType, dischargeTypesDefault[i]));
                    }
                }
            }
        }
        return dischargeTypes;
    }


    public List<AnimalTypeI18n> generateI18NAnimalTypeList(List<AnimalType> animalTypeList){

        List<AnimalTypeI18n> animalTypeI18NList = new ArrayList<>();

        for (AnimalType animalType: animalTypeList){
            for (int i = 0; i < animalTypesEN.length; i++) {
                if(animalType.getDescription().equalsIgnoreCase(animalTypesEN[i])) {
                    animalTypeI18NList.add(new AnimalTypeI18n(animalType, animalTypesDefault[i]));
                }
            }
        }

        return animalTypeI18NList;
    }


    /**
     * Given a sex in the default locale, return it in locale EN
     * @param sexDefaultLocale
     * @return sex in EN locale
     */
    public String getSexLocaleEN(String sexDefaultLocale){

        for (int i = 0; i < sexsDefault.length ; i++) {
            if(sexsDefault[i].equalsIgnoreCase(sexDefaultLocale)) return sexsEN[i];
        }

        return sexDefaultLocale;
    }

    public String getAnimalTypeLocaleDefault(String animalTypeDescLocaleEN){

        for (int i = 0; i < animalTypesEN.length ; i++) {
            if(animalTypeDescLocaleEN.equalsIgnoreCase(animalTypesEN[i])){
                return animalTypesDefault[i];
            }
        }

        return animalTypeDescLocaleEN;
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
