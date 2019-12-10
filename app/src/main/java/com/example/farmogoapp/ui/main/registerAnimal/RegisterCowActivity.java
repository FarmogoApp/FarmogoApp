package com.example.farmogoapp.ui.main.registerAnimal;

import androidx.appcompat.app.AppCompatActivity;
import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.ui.main.farms.FarmStatsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCowActivity extends AppCompatActivity implements Callback{
    private EditText etOfficialId;
    private Spinner spnMotherId;
    private CalendarView clvBirthDate;
    private EditText etOrigin;
    private Spinner spnSex;
    private Spinner spnAnimalType;
    private Spinner spnRace;
    private Spinner spnLocation;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeAnimalTypeSpinner();
        initializeRaceSpinner();
        initializeLocationSpinner();
        initializeSexSpinner();
        
        setContentView(R.layout.activity_register_cow);
        findAllComponents();

        registerListeners();
    }



    private void findAllComponents(){
        etOfficialId = findViewById(R.id.etOfficialId);
        spnMotherId = findViewById(R.id.spnMotherId);
        clvBirthDate = findViewById(R.id.clvBirthDate);
        etOrigin = findViewById(R.id.etOrigin);
        spnSex = findViewById(R.id.spnSex);
        spnAnimalType = findViewById(R.id.spnAnimalType);
        spnRace = findViewById(R.id.spnRace);
        spnLocation = findViewById(R.id.spnLocation);
        btnRegister = findViewById(R.id.btnRegister);

    }

    private void registerAnimal() {

        // Get user input
        String officialId = etOfficialId.getText().toString();
        Animal selectedMother = (Animal) spnMotherId.getSelectedItem();
        Date birthDate = new Date(clvBirthDate.getDate());
        String origin = etOrigin.getText().toString();
        String selectedSex = spnSex.getSelectedItem().toString();
        AnimalType selectedAnimalType = (AnimalType) spnAnimalType.getSelectedItem();
        Race selectedRace = (Race) spnRace.getSelectedItem();
        // TODO: Location


        Log.e("Register", "officialId: " + officialId);
        Log.e("Register", "selectedMother ID: " + selectedMother.getOfficialId());
        Log.e("Register", "birthDate: " + birthDate.toString());
        Log.e("Register", "origin: " + origin);
        Log.e("Register", "selectedSex: " + selectedSex);
        Log.e("Register", "selectedAnimalType: " + selectedAnimalType.getDescription());
        Log.e("Register", "Location: " + "TODO");


        // Set animal
        Animal animal = new Animal();
        animal.setOfficialId(officialId);
        animal.setAnimalTypeId(selectedAnimalType.getUuid());
        animal.setRaceId(selectedRace.getUuid());


    }


    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAnimal();
                Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                toast1.show();
                Intent intent = new Intent(RegisterCowActivity.this, FarmStatsActivity.class);
                startActivity(intent);
            }
        });
    }




    @Override
    public void onResponse(Call call, Response response) {

    }

    @Override
    public void onFailure(Call call, Throwable t) {

    };


    private void initializeAnimalTypeSpinner() {

        Call<ArrayList<AnimalType>> animalTypes = FarmogoApiJacksonAdapter.getApiService(this).getAnimalTypes();

        animalTypes.enqueue(new Callback<ArrayList<AnimalType>>() {
            @Override
            public void onResponse(Call<ArrayList<AnimalType>> call, Response<ArrayList<AnimalType>> response) {
                ArrayList<AnimalType> data = response.body();

                if(data != null){
                    ArrayAdapter animalTypeAdapater = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, data);
                    spnAnimalType.setAdapter(animalTypeAdapater);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AnimalType>> call, Throwable t) {
                t.printStackTrace();
                Log.e("RegisterCowActivity","AnimalType error" );
            }
        });
    }

    private void initializeRaceSpinner() {
        Call<ArrayList<Race>> races = FarmogoApiJacksonAdapter.getApiService(this).getRaces();

        races.enqueue(new Callback<ArrayList<Race>>() {
            @Override
            public void onResponse(Call<ArrayList<Race>> call, Response<ArrayList<Race>> response) {
                ArrayList<Race> data = response.body();

                if(data != null){
                    ArrayAdapter raceAdapater = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, data);
                    spnRace.setAdapter(raceAdapater);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Race>> call, Throwable t) {
                t.printStackTrace();
                Log.e("RegisterCowActivity","Races error" );
            }
        });
    }

    private void initializeLocationSpinner() {

    }

    private void initializeSexSpinner() {

        ArrayList<String> sexs = new ArrayList<>();
        sexs.add("Male");
        sexs.add("Female");
        ArrayAdapter sexAdapater = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, sexs);
        spnSex.setAdapter(sexAdapater);

    }

}
