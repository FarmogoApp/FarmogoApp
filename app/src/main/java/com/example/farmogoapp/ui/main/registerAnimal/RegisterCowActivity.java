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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCowActivity extends AppCompatActivity implements Callback {
    private EditText etOfficialId;
    private Spinner spnMotherId;
    private CalendarView clvBirthDate;
    private EditText etOrigin;
    private Spinner spnSex;
    private Spinner spnAnimalType;
    private Spinner spnRace;
    private Spinner spnLocation;
    private Button btnRegister;

    Date birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_register_cow);
        findAllComponents();
        registerListeners();

        initializeAnimalTypeSpinner();
        initializeRaceSpinner();
        initializeLocationSpinner();
        initializeMotherIDSpinner();
    }


    private void findAllComponents() {
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


    /**
     * Get user input and register animal
     */
    private void registerAnimal() {

        // Get user input
        String officialId = etOfficialId.getText().toString();
        Animal selectedMother = (Animal) spnMotherId.getSelectedItem();
        if(birthDate == null) birthDate = new Date(clvBirthDate.getDate());
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
        animal.setMotherId(selectedMother.getUuid());
        animal.setMotherOfficialId(selectedMother.getOfficialId());
        animal.setBirthDay(birthDate);
        animal.setOrigin(origin);
        animal.setSex(selectedSex);
        animal.setAnimalTypeId(selectedAnimalType.getUuid());
        animal.setRaceId(selectedRace.getUuid());
        // animal.setDivisionId();

        Call<Animal> animalCall = FarmogoApiJacksonAdapter.getApiService(this).postAnimal(animal);
        animalCall.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {

                if(response.isSuccessful()) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast1.show();
                    Intent intent = new Intent(RegisterCowActivity.this, FarmStatsActivity.class);
                    startActivity(intent);

                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.registration_failed), Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.registration_failed), Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
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
            }
        });

        clvBirthDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView clv, int year, int month, int date) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, date);
                birthDate = cal.getTime();
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

        Call<ArrayList<AnimalType>> animalTypeCall = FarmogoApiJacksonAdapter.getApiService(this).getAnimalTypes();

        animalTypeCall.enqueue(new Callback<ArrayList<AnimalType>>() {
            @Override
            public void onResponse(Call<ArrayList<AnimalType>> call, Response<ArrayList<AnimalType>> response) {
                ArrayList<AnimalType> data = response.body();

                if(data != null) {
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
        Call<ArrayList<Race>> raceCall = FarmogoApiJacksonAdapter.getApiService(this).getRaces();

        raceCall.enqueue(new Callback<ArrayList<Race>>() {
            @Override
            public void onResponse(Call<ArrayList<Race>> call, Response<ArrayList<Race>> response) {
                ArrayList<Race> data = response.body();

                if(data != null) {
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


    /**
     * Get all animals and filter by sex female
     */
    private void initializeMotherIDSpinner() {
        Call<List<Animal>> animalCall = FarmogoApiJacksonAdapter.getApiService(this).getAllAnimals();

        animalCall.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                List<Animal> data = response.body();

                if(data != null) {
                    ArrayList<Animal> mothers = new ArrayList<>();

                    for (Animal animal: data) {
                        if(animal.getSex().equalsIgnoreCase("Female")){
                            mothers.add(animal);
                        }
                    }

                    ArrayAdapter mothersAdapater = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, mothers);
                    spnMotherId.setAdapter(mothersAdapater);
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                t.printStackTrace();
                Log.e("RegisterCowActivity","Get all animals error" );
            }
        });
    }

    private void initializeLocationSpinner() {
        // TODO: Get buildings from current farm
    }
}
