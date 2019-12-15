package com.example.farmogoapp.ui.main.registerAnimal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Building;
import com.example.farmogoapp.model.Division;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.ui.main.farms.FarmStatsActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterCowActivity extends AppCompatActivity {


    class Location {
        private Building building;
        private Division division;

        public Location(Building building, Division division) {
            this.building = building;
            this.division = division;
        }

        public Building getBuilding() {
            return building;
        }

        public Division getDivision() {
            return division;
        }

        @Override
        public String toString() {
            return building.getName() + " - " + division.getName();
        }
    }


    private EditText etOfficialId;
    private Spinner spnMotherId;
    private EditText clvBirthDate;
    private EditText etOrigin;
    private Spinner spnSex;
    private Spinner spnAnimalType;
    private Spinner spnRace;
    private Spinner spnLocation;
    private Button btnRegister;
    private Calendar calendar = Calendar.getInstance();
    private Farm currentFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_register_cow);
        findAllComponents();

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        clvBirthDate.setText(sdf.format(calendar.getTime()));

        registerListeners();

        getSessionFarm();
        initializeMotherIdSpinner();
        initializeAnimalTypeSpinner();
        initializeRaceSpinner();
        initializeLocationSpinner();
    }

    /**
     * If it fails, then go back to FarmStats
     */
    private void getSessionFarm() {
        currentFarm = SessionData.getInstance().getActualFarm();
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

        String origin = etOrigin.getText().toString();
        String selectedSex = spnSex.getSelectedItem().toString();
        AnimalType selectedAnimalType = (AnimalType) spnAnimalType.getSelectedItem();
        Race selectedRace = (Race) spnRace.getSelectedItem();
        Location location = (Location) spnLocation.getSelectedItem();

        String dates = clvBirthDate.getText().toString();
        String[] parts = dates.split("/");
        int day = Integer.valueOf(parts[0]); // day
        int month = Integer.valueOf(parts[1]); // month
        int year = Integer.valueOf(parts[2]); // year
        // Set animal
        Animal animal = new Animal();
        animal.setOfficialId(officialId);
        animal.setMotherId(selectedMother.getUuid());
        animal.setMotherOfficialId(selectedMother.getOfficialId());
        animal.setBirthDay(LocalDate.of(year, month, day));
        animal.setOrigin(origin);
        animal.setSex(selectedSex);
        animal.setAnimalTypeId(selectedAnimalType.getUuid());
        animal.setRaceId(selectedRace.getUuid());
        animal.setDivisionId(location.getDivision().getUuid());
        animal.setFarmId(currentFarm.getUuid());

        // POST animal
        Call<Animal> animalCall = FarmogoApiJacksonAdapter.getApiService().postAnimal(animal);
        animalCall.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {

                if (response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(RegisterCowActivity.this, FarmStatsActivity.class);
                    startActivity(intent);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.registration_failed), Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.registration_failed), Toast.LENGTH_LONG);
                toast.show();
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

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                clvBirthDate.setText(sdf.format(calendar.getTime()));
            }

        };

        clvBirthDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new DatePickerDialog(RegisterCowActivity.this, datePickerListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void initializeAnimalTypeSpinner() {

        List<AnimalType> animalTypes = SessionData.getInstance().getAnimalTypes();
        if (animalTypes != null) {
            ArrayAdapter animalTypeAdapater = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, animalTypes);
            spnAnimalType.setAdapter(animalTypeAdapater);
        }
    }


    private void initializeRaceSpinner() {
        List<Race> races = SessionData.getInstance().getRaces();
        if (races != null) {
            ArrayAdapter raceAdapater = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, races);
            spnRace.setAdapter(raceAdapater);
        }
    }


    /**
     * Get all animals and filter by sex female
     */
    private void initializeMotherIdSpinner() {
        List<Animal> animals = SessionData.getInstance().getAnimals();
        if (animals != null) {
            ArrayList<Animal> mothers = new ArrayList<>();

            for (Animal animal : animals) {
                if (animal.getSex().equalsIgnoreCase("Female")) {
                    mothers.add(animal);
                }
            }

            ArrayAdapter mothersAdapater = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, mothers);
            spnMotherId.setAdapter(mothersAdapater);
        }
    }


    private void initializeLocationSpinner() {

        ArrayList<Location> locations = new ArrayList<>();

        for (Building building : currentFarm.getBuildings()) {
            for (Division division : building.getDivisions()) {
                Location location = new Location(building, division);
                locations.add(location);
                Log.e("location", location.toString());
            }
        }

        ArrayAdapter locationAdapter = new ArrayAdapter(RegisterCowActivity.this, R.layout.spinner, locations);
        spnLocation.setAdapter(locationAdapter);
    }
}
