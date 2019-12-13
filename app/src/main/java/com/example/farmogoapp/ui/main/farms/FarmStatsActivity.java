package com.example.farmogoapp.ui.main.farms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;

import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.ui.main.SessionData;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;
import com.example.farmogoapp.ui.main.searchanimal.SeachAnimalsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.stream.Collectors.groupingBy;

public class FarmStatsActivity extends AppCompatActivity {
    private Button btnGestion;
    private TextView yougerCowsTextView;
    private TextView cowsTextView;
    private TextView bullsTextView;
    private Button searchButton;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private Farm actualFarm;
    private ArrayList<AnimalType> animalType;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.farmstats, menu);

        MenuItem item = menu.findItem(R.id.add_farm);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(FarmStatsActivity.this, AddExploitationActivity.class);
                startActivity(intent);
                return true;
            }
        });

        MenuItem list = menu.findItem(R.id.edit_farm);
        list.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(FarmStatsActivity.this, AddExploitationActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm_stats);
        registerViews();
        fillData();
        registerListeners();
        loadFarms();

    }

    private void registerViews() {
        /*yougerCowsTextView = findViewById(R.id.cows_younger);
        cowsTextView = findViewById(R.id.cows);
        bullsTextView = findViewById(R.id.bulls);*/
        searchButton = findViewById(R.id.search);
        spinner = findViewById(R.id.spinnerstatistics);
        btnGestion = findViewById(R.id.Gestion);
    }

    private void registerListeners() {

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmStatsActivity.this, SeachAnimalsActivity.class);
                startActivity(intent);
            }
        });
        btnGestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmStatsActivity.this, RegisterCowActivity.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadFarmStats();
                SessionData.getInstance().setActualFarm((Farm) parentView.getItemAtPosition(position));
                loadHistoric(SessionData.getInstance().getActualFarm().getUuid());
                FarmStatsActivity.this.fillData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(FarmStatsActivity.this, "Nothing Selected", Toast.LENGTH_SHORT);
            }

        });
    }

    private void fillData() {
        Random r = new Random();
        //yougerCowsTextView.setText(String.valueOf(r.nextInt(100)));
        //cowsTextView.setText(String.valueOf(r.nextInt(100)));
        //bullsTextView.setText(String.valueOf(r.nextInt(100)));
    }

    private void loadFarmStats() {
        final Call<List<Animal>> farmStats = FarmogoApiJacksonAdapter.getApiService(this).getAllAnimals();
        farmStats.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {

                List<Animal> animals = SessionData.getInstance().getAnimals();
                Farm actualFarm = SessionData.getInstance().getActualFarm();
                Map<String, Long> collect = animals.stream()
                        .filter( animal -> actualFarm.getUuid().equals(animal.getFarmId()))
                        .collect(Collectors.groupingBy(Animal::getAnimalTypeId, Collectors.counting()));


                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.statisticsrelativelayout);
                linearLayout.removeAllViews();
                collect.forEach((k,v) ->{
                    Optional<AnimalType> type = SessionData.getInstance().getAnimalType(k);
                    if(type.isPresent()) {
                        ImageView imageView = new ImageView(FarmStatsActivity.this);

                        switch (type.get().getDescription()){
                            case "Cow":
                                imageView.setImageResource(R.drawable.cow);
                                break;
                            case "Bull":
                                imageView.setImageResource(R.drawable.bull);
                                break;
                            case "Calf":
                                imageView.setImageResource(R.drawable.bull);
                        }
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                        imageView.setLayoutParams(params);
                        linearLayout.addView(imageView);

                        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lparams.setMarginEnd(5);

                        TextView typeTv = new TextView(FarmStatsActivity.this);
                        TextView valueTv = new TextView(FarmStatsActivity.this);
                        typeTv.setLayoutParams(lparams);
                        valueTv.setLayoutParams(lparams);

                        typeTv.setText(type.get().toString());
                        typeTv.setPadding(5, 5, 5, 5);

                        valueTv.setText(v.toString());
                        valueTv.setTextSize(20);
                        valueTv.setPadding(5, 5, 5, 5);
                        linearLayout.addView(typeTv);
                        linearLayout.addView(valueTv);

                    }

                });
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.e("Error loadFarmStats", "error");

            }
        });
    }

    private void loadFarms() {
        final Call<ArrayList<Farm>> farm = FarmogoApiJacksonAdapter.getApiService(this).getFarms();
        farm.enqueue(new Callback<ArrayList<Farm>>() {
            @Override
            public void onResponse(Call<ArrayList<Farm>> call, Response<ArrayList<Farm>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Farm> farm = response.body();
                    ArrayAdapter farmAdapter = null;
                    SessionData.getInstance().setFarms(farm);
                    farmAdapter = new ArrayAdapter(FarmStatsActivity.this, R.layout.spinner, SessionData.getInstance().getFarms());

                    spinner = (Spinner) findViewById(R.id.spinnerstatistics);
                    spinner.setAdapter(farmAdapter);

                    if (SessionData.getInstance().getActualFarm() == null) {
                        SessionData.getInstance().setActualFarm(SessionData.getInstance().getFarms().get(0));
                    }
                    loadHistoric(SessionData.getInstance().getActualFarm().getUuid());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Farm>> call, Throwable t) {
            }
        });
    }

    private void loadHistoric(String idFarm) {
        Call<ArrayList<Incidence>> lastIncidences = FarmogoApiJacksonAdapter.getApiService(this).getLastIncidences(idFarm);
        lastIncidences.enqueue(new Callback<ArrayList<Incidence>>() {
            @Override
            public void onResponse(Call<ArrayList<Incidence>> call, Response<ArrayList<Incidence>> response) {
                refreshRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Incidence>> call, Throwable t) {

            }
        });
    }

    private void refreshRecyclerView(ArrayList<Incidence> lastIncidences) {
        recyclerView = findViewById(R.id.recyclerviewStatistics);
        recyclerView.setHasFixedSize(true);
        mAdapter = new IncidenceAdapter(lastIncidences);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
