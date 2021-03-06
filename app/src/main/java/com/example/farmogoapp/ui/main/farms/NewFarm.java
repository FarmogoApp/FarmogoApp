package com.example.farmogoapp.ui.main.farms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.DataUpdater;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.AnimalCounter;
import com.example.farmogoapp.model.Building;
import com.example.farmogoapp.model.Division;
import com.example.farmogoapp.model.Farm;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFarm extends AppCompatActivity {

    private EditText txtName;
    private EditText txtCounterPrefix;
    private EditText txtCounter;
    private EditText txtOfficialId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_farm);
        if (SessionData.getInstance().getFarms() != null && !SessionData.getInstance().getFarms().isEmpty()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        registerViews();
    }

    @Override
    public void onBackPressed() {
        if (SessionData.getInstance().getFarms() == null || SessionData.getInstance().getFarms().isEmpty()) {

            Toast.makeText(this, getString(R.string.need_farm_to_work), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    public void saveFarm(View view) {
        Farm farm = new Farm();

        if (!checkFields()) {
            return;
        }

        farm.setName(txtName.getText().toString());
        AnimalCounter farmCounter = new AnimalCounter();
        farmCounter.setPrefix(txtCounterPrefix.getText().toString());
        farmCounter.setCounter(Long.parseLong(txtCounter.getText().toString()));
        farm.setAnimalCounter(farmCounter);
        farm.setOfficialId(txtOfficialId.getText().toString());


        List<Building> listBuildings = new ArrayList<Building>();

        Building build = new Building();

        Division division = new Division();
        division.setName(getString(R.string.default_division));

        List<Division> listDivisions = new ArrayList<Division>();
        listDivisions.add(division);
        build.setDivisions(listDivisions);
        build.setName(getString(R.string.default_building));
        listBuildings.add(build);
        farm.setBuildings(listBuildings);

        Call<Farm> call = FarmogoApiJacksonAdapter.getApiService().createFarm(farm);
        call.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                Farm farms = response.body();
                Intent intent = new Intent(NewFarm.this, AddExploitationActivity.class);
                SessionData.getInstance().setActualFarm(response.body());
                DataUpdater dataUpdater = new DataUpdater();
                dataUpdater.updatefarms();
                Toast.makeText(NewFarm.this, getString(R.string.new_farm_created), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {

            }
        });
    }

    public boolean checkFields() {

        if (txtName.getText().toString().isEmpty()) {
            txtName.setError(getString(R.string.required_field));
            return false;
        }
        if (txtCounterPrefix.getText().toString().isEmpty()) {
            txtCounterPrefix.setError(getString(R.string.required_field));
            return false;
        }
        if (txtCounter.getText().toString().isEmpty()) {
            txtCounter.setError(getString(R.string.required_field));
            return false;
        }
        if (txtOfficialId.getText().toString().isEmpty()) {
            txtOfficialId.setError(getString(R.string.required_field));
            return false;
        }
        return true;
    }

    public void registerViews() {
        txtName = findViewById(R.id.nameEdit);
        txtCounterPrefix = findViewById(R.id.prefixEdit);
        txtCounter = findViewById(R.id.counterEdit);
        txtOfficialId = findViewById(R.id.officialFarmIdEdit);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
}