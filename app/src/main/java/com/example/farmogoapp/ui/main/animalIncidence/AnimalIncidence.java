package com.example.farmogoapp.ui.main.animalIncidence;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

public class AnimalIncidence extends AppCompatActivity {
    private Spinner spinner;
    private String animalUuid;
    private String animalOfficialId;
    private String farmId;
    private String farmAnimalCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_incidence);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner= findViewById(R.id.incidencespiner);
        String[] incidences = getResources().getStringArray(R.array.incidences);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, incidences);
        spinner.setAdapter(adapter);
        if (getIntent().hasExtra("animalId")) {
            Log.e("asdasfffffffffffffffffffdad", getIntent().getStringExtra("animalId"));
            this.animalUuid = getIntent().getStringExtra("animalId");
            this.animalOfficialId = getIntent().getStringExtra("animalOfficialId");
            this.farmId = getIntent().getStringExtra("farmId");
            this.farmAnimalCounter = getIntent().getStringExtra("farmAnimalCounter");
        }
        registerListeners();
    }


    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListeners() {


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Bundle bundleBirth = new Bundle();
                        FragmentBirthIncidence fragmentBirth = new FragmentBirthIncidence();
                        if (animalUuid != null){
                            bundleBirth.putString("animalId", animalUuid);
                            bundleBirth.putString("animalOfficialId", animalOfficialId);
                            bundleBirth.putString("farmId", farmId);
                            bundleBirth.putString("farmAnimalCounter", farmAnimalCounter);
                            fragmentBirth.setArguments(bundleBirth);
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentBirth).commit();
                        break;
                    case 1:
                        Bundle bundleTreatment = new Bundle();
                        FragmentTreatmentIncidence fragmentTreatment = new FragmentTreatmentIncidence();
                        if (animalUuid != null){
                            bundleTreatment.putString("animalId", animalUuid);
                            bundleTreatment.putString("animalOfficialId", animalOfficialId);
                            bundleTreatment.putString("farmId", farmId);
                            fragmentTreatment.setArguments(bundleTreatment);
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentTreatment).commit();

                        break;
                    case 2:

                        Bundle bundlePregnancy = new Bundle();
                        FragmentPregnancyIncidence fragmentPregnancy = new FragmentPregnancyIncidence();
                        if (animalUuid != null){
                            bundlePregnancy.putString("animalId", animalUuid);
                            bundlePregnancy.putString("animalOfficialId", animalOfficialId);
                            bundlePregnancy.putString("farmId", farmId);
                            fragmentPregnancy.setArguments(bundlePregnancy);
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentPregnancy).commit();
                        break;
                    case 3:

                        Bundle bundleWheighing = new Bundle();
                        FragmentWheighingIncidence fragmentWheighing = new FragmentWheighingIncidence();
                        if (animalUuid != null){
                            bundleWheighing.putString("animalId", animalUuid);
                            bundleWheighing.putString("animalOfficialId", animalOfficialId);
                            bundleWheighing.putString("farmId", farmId);
                            fragmentWheighing.setArguments(bundleWheighing);
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentWheighing).commit();

                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentScheduledIncidence.newInstance()).commit();
                        break;

                    case 5:

                        Bundle bundleDischarge = new Bundle();
                        ExitFragment fragmentDischarge = new ExitFragment();
                        if (animalUuid != null){
                            bundleDischarge.putString("animalId", animalUuid);
                            bundleDischarge.putString("animalOfficialId", animalOfficialId);
                            bundleDischarge.putString("farmId", farmId);
                            fragmentDischarge.setArguments(bundleDischarge);
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragmentDischarge).commit();

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
