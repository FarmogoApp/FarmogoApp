package com.example.farmogoapp.ui.main.animalIncidence;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AnimalIncidence extends AppCompatActivity {
    private Spinner spinner;
    private String animalOfficialId;
    private String farmId;
    private String farmAnimalCounter;
    private Integer incidenceType;
    private String animalId;

    private boolean hasMales = false;
    private boolean hasFemales = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_incidence);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner= findViewById(R.id.incidencespiner);

        List<Animal> animalCardObj = SessionData.getInstance().getAnimalCardObj();

        hasMales = animalCardObj.stream().anyMatch(a -> "male".equalsIgnoreCase(a.getSex()));
        hasFemales = animalCardObj.stream().anyMatch(a -> "female".equalsIgnoreCase(a.getSex()));

        if (getIntent().hasExtra("animalOfficialId")) {this.animalOfficialId = getIntent().getStringExtra("animalOfficialId");}
        if (getIntent().hasExtra("animalId")) {
            this.animalId = getIntent().getStringExtra("animalId");
            Optional<Animal> animal = SessionData.getInstance().getAnimal(this.animalId);

            if (animal.isPresent()){
                hasMales = "male".equalsIgnoreCase(animal.get().getSex());
                hasFemales = "female".equalsIgnoreCase(animal.get().getSex());
            }else{
                finish();
            }
        }


        if (getIntent().hasExtra("farmId")) {this.farmId = getIntent().getStringExtra("farmId");}
        if (getIntent().hasExtra("farmAnimalCounter")) {this.farmAnimalCounter = getIntent().getStringExtra("farmAnimalCounter");}
        if (getIntent().hasExtra("incidenceType")) {
            this.incidenceType = getIntent().getIntExtra("incidenceType", 1);
            chooseIncidenceType();
        }

    }

    private void chooseIncidenceType() {
        //if (this.incidenceType.equals(1) || SessionData.getInstance().getAnimalCardObj().size() == 1){//una vaca en el carrito
        if (this.incidenceType.equals(1)){
            String[] incidences = getResources().getStringArray(R.array.incidencesSimple);
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, incidences);
            spinner.setAdapter(adapter);
            registerListenersSimple();
        }else if (this.incidenceType == 2){
            String[] incidences = getResources().getStringArray(R.array.incidencesMultiple);
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, incidences);
            spinner.setAdapter(adapter);
            registerListenerMultiple();
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListenerMultiple() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Bundle bundleTreatment = new Bundle();
                        FragmentTreatmentIncidence fragmentTreatment = new FragmentTreatmentIncidence();

                        if (animalOfficialId != null){ bundleTreatment.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundleTreatment.putString("animalId", animalId);}
                        if (farmId != null){ bundleTreatment.putString("farmId", farmId); }
                        if (incidenceType != null){ bundleTreatment.putInt("incidenceType", incidenceType); }
                        fragmentTreatment.setArguments(bundleTreatment);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentTreatment).commit();

                        break;
                    case 1:

                        if (hasMales){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Fragment, new IncompatibleIncidence(R.string.incomptabile_pregnancy_male)).commit();
                            break;
                        }

                        Bundle bundlePregnancy = new Bundle();
                        FragmentPregnancyIncidence fragmentPregnancy = new FragmentPregnancyIncidence();

                        if (animalOfficialId != null){ bundlePregnancy.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundlePregnancy.putString("animalId", animalId);}
                        if (farmId != null){ bundlePregnancy.putString("farmId", farmId);}
                        if (incidenceType != null){ bundlePregnancy.putInt("incidenceType", incidenceType); }
                        fragmentPregnancy.setArguments(bundlePregnancy);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentPregnancy).commit();
                        break;
                    case 2:

                        Bundle bundleWheighing = new Bundle();
                        FragmentWheighingIncidence fragmentWheighing = new FragmentWheighingIncidence();

                        if (animalOfficialId != null){bundleWheighing.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundleWheighing.putString("animalId", animalId);}
                        if (farmId != null){ bundleWheighing.putString("farmId", farmId);}
                        if (incidenceType != null){ bundleWheighing.putInt("incidenceType", incidenceType); }
                        fragmentWheighing.setArguments(bundleWheighing);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentWheighing).commit();

                        break;
                    case 3:

                        Bundle bundleDischarge = new Bundle();
                        ExitFragment fragmentDischarge = new ExitFragment();
                        if (animalOfficialId != null){ bundleDischarge.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundleDischarge.putString("animalId", animalId);}
                        if (farmId != null){ bundleDischarge.putString("farmId", farmId); }
                        if (incidenceType != null){ bundleDischarge.putInt("incidenceType", incidenceType); }
                        fragmentDischarge.setArguments(bundleDischarge);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentDischarge).commit();
                        break;

                    case 4:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentScheduledIncidence.newInstance()).commit();

                        break;

                    default:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, new FragmentWheighingIncidence()).commit();
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void registerListenersSimple() {


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Bundle bundleTreatment = new Bundle();
                        FragmentTreatmentIncidence fragmentTreatment = new FragmentTreatmentIncidence();

                        if (animalOfficialId != null){ bundleTreatment.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundleTreatment.putString("animalId", animalId);}
                        if (farmId != null){ bundleTreatment.putString("farmId", farmId); }
                        if (incidenceType != null){ bundleTreatment.putInt("incidenceType", incidenceType); }
                        fragmentTreatment.setArguments(bundleTreatment);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentTreatment).commit();

                        break;
                    case 1:
                        if (hasMales){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Fragment, new IncompatibleIncidence(R.string.incomptabile_birth_male)).commit();
                            break;
                        }
                        Bundle bundleBirth = new Bundle();
                        FragmentBirthIncidence fragmentBirth = new FragmentBirthIncidence();

                        if (animalOfficialId != null){ bundleBirth.putString("animalOfficialId", animalOfficialId); }
                        if (animalId != null){ bundleBirth.putString("animalId", animalId);}
                        if (farmId != null){ bundleBirth.putString("farmId", farmId); }
                        if (farmAnimalCounter != null){ bundleBirth.putString("farmAnimalCounter", farmAnimalCounter);}
                        if (incidenceType != null){ bundleBirth.putInt("incidenceType", incidenceType); }
                        fragmentBirth.setArguments(bundleBirth);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentBirth).commit();
                        break;

                    case 2:
                        if (hasMales){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Fragment, new IncompatibleIncidence(R.string.incomptabile_pregnancy_male)).commit();
                            break;
                        }
                        Bundle bundlePregnancy = new Bundle();
                        FragmentPregnancyIncidence fragmentPregnancy = new FragmentPregnancyIncidence();

                        if (animalOfficialId != null){ bundlePregnancy.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundlePregnancy.putString("animalId", animalId);}
                        if (farmId != null){ bundlePregnancy.putString("farmId", farmId);}
                        if (incidenceType != null){ bundlePregnancy.putInt("incidenceType", incidenceType); }
                        fragmentPregnancy.setArguments(bundlePregnancy);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentPregnancy).commit();
                        break;
                    case 3:

                        Bundle bundleWheighing = new Bundle();
                        FragmentWheighingIncidence fragmentWheighing = new FragmentWheighingIncidence();

                        if (animalOfficialId != null){bundleWheighing.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundleWheighing.putString("animalId", animalId);}
                        if (farmId != null){ bundleWheighing.putString("farmId", farmId);}
                        if (incidenceType != null){ bundleWheighing.putInt("incidenceType", incidenceType); }
                        fragmentWheighing.setArguments(bundleWheighing);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentWheighing).commit();

                        break;
                    case 4:

                        Bundle bundleDischarge = new Bundle();
                        ExitFragment fragmentDischarge = new ExitFragment();
                        if (animalOfficialId != null){ bundleDischarge.putString("animalOfficialId", animalOfficialId);}
                        if (animalId != null){ bundleDischarge.putString("animalId", animalId);}
                        if (farmId != null){ bundleDischarge.putString("farmId", farmId); }
                        if (incidenceType != null){ bundleDischarge.putInt("incidenceType", incidenceType); }
                        fragmentDischarge.setArguments(bundleDischarge);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentDischarge).commit();

                        break;

                    case 5:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentScheduledIncidence.newInstance()).commit();

                        break;

                    default:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, new FragmentWheighingIncidence()).commit();
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


}

}
