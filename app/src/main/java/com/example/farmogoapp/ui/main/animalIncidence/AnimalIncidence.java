package com.example.farmogoapp.ui.main.animalIncidence;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalIncidence extends AppCompatActivity {
    private Spinner spinner;
    public String animalUuid;
    public String animalOfficialid;



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
            this.animalOfficialid = getIntent().getStringExtra("animalOfficialId");
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

                        Bundle args = new Bundle();
                        FragmentBirthIncidence fragment = new FragmentBirthIncidence();
                        if (animalUuid != null){
                            args.putString("animalId", animalUuid);
                            args.putString("animalOfficialId", animalOfficialid);
                            fragment.setArguments(args);
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Fragment, fragment).commit();
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
                                .replace(R.id.Fragment, ExitFragment.newInstance()).commit();
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
