package com.example.farmogoapp.ui.main.animalIncidence;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

public class AnimalIncidence extends AppCompatActivity {
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_incidence);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner= findViewById(R.id.incidencespiner);
        String[] incidences = getResources().getStringArray(R.array.incidences);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, incidences);
        spinner.setAdapter(adapter);
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
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentBirthIncidence.newInstance()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentTreatmentIncidence.newInstance()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentPregnancyIncidence.newInstance()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentWheighingIncidence.newInstance()).commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentScheduledIncidence.newInstance()).commit();
                        break;

                    case 5:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, DeathCowFragment.newInstance()).commit();
                        break;
                    case 6:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, AbattoirFragment.newInstance()).commit();
                        break;

                    case 7:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, SellCowFragment.newInstance()).commit();
                        break;
                    case 8:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, FragmentNewIncidence.newInstance()).commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, new FragmentBirthIncidence()).commit();
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


}

}
