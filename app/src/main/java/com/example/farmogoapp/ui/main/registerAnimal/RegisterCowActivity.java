package com.example.farmogoapp.ui.main.registerAnimal;

import androidx.appcompat.app.AppCompatActivity;
import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.response.AnimalType;
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




        // Animal Type
        Call<ArrayList<AnimalType>> animalTypes = FarmogoApiJacksonAdapter.getApiService(this).getAnimalTypes();
        animalTypes.enqueue(new Callback<ArrayList<AnimalType>>() {
            @Override
            public void onResponse(Call<ArrayList<AnimalType>> call, Response<ArrayList<AnimalType>> response) {
                ArrayList<AnimalType> data = response.body();

                // Set Animal Type Spinner
                if(data!= null){

                    ArrayList<String> animalTypes = new ArrayList<>();
                    for (AnimalType animalType : data) {
                        animalTypes.add(animalType.getDescription());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            RegisterCowActivity.this, android.R.layout.simple_spinner_item, animalTypes);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    animalTypeSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AnimalType>> call, Throwable t) {
                t.printStackTrace();
                Log.e("TEST AnimalType","error" );
            }
        });

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
}
