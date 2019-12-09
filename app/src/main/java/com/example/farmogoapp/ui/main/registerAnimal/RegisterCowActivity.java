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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCowActivity extends AppCompatActivity implements Callback{
    private Button btnRegister;
    private Spinner raceSpinner;
    private Spinner animalTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeAnimalTypeSpinner();
        initializeRaceSpinner();
        initializeLocationSpinner();
        
        setContentView(R.layout.activity_register_cow);
        btnRegister = findViewById(R.id.btnregister);
        raceSpinner = findViewById(R.id.spinnerRace);
        animalTypeSpinner = findViewById(R.id.spinnerAnimalType);

        registerListeners();
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

    private void registerAnimal() {

        // Get user input
        AnimalType selectedAnimalType = (AnimalType) animalTypeSpinner.getSelectedItem();
        Race selectedRace = (Race) raceSpinner.getSelectedItem();

        Log.e("Register", selectedAnimalType.getDescription());
        Log.e("Register", selectedRace.getName());


        // Set animal
        Animal animal = new Animal();
        animal.setAnimalTypeId(selectedAnimalType.getUuid());


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
                    animalTypeSpinner.setAdapter(animalTypeAdapater);
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
                    raceSpinner.setAdapter(raceAdapater);
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

}
