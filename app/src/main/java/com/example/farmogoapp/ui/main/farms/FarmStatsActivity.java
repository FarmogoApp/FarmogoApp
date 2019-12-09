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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiAdapter;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;

import com.example.farmogoapp.model.FarmHistory;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;
import com.example.farmogoapp.ui.main.searchanimal.SeachAnimalsActivity;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmStatsActivity extends AppCompatActivity {
    private Button btnGestion;
    private TextView yougerCowsTextView;
    private TextView cowsTextView;
    private TextView bullsTextView;
    private Button searchButton;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;


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
        ArrayList<FarmHistory> Farm_History = new ArrayList<>();
        FarmHistory FarmHistory1 = new FarmHistory("ES19022525", "Causa1", "Selevit", "28/10/2019");
        FarmHistory FarmHistory2 = new FarmHistory("ES19022529", "Causa2", "Selevit", "28/10/2019");
        Farm_History.add(FarmHistory1);
        Farm_History.add(FarmHistory2);
        registerListeners();
        loadFarms();

        recyclerView = findViewById(R.id.recyclerviewStatistics);
        recyclerView.setHasFixedSize(true);
        mAdapter = new farm_stats_adapter(Farm_History);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fillData();
        initSpinnerFarmChoose();
        registerListeners();


        Call<ArrayList<Incidence>> incidences = FarmogoApiJacksonAdapter.getApiService(this).getIncidences();
        incidences.enqueue(new Callback<ArrayList<Incidence>>() {
            @Override
            public void onResponse(Call<ArrayList<Incidence>> call, Response<ArrayList<Incidence>> response) {
                ArrayList<Incidence> data = response.body();
                Log.d("TEST INDICENCES", "size: " + data.size());
                for (Incidence i : data) {
                    Log.d("type", i.getType().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Incidence>> call, Throwable t) {
                t.printStackTrace();
                Log.e("TEST INDICENCES", "error");
            }
        });
    }

    private void registerViews() {
        yougerCowsTextView = findViewById(R.id.cows_younger);
        cowsTextView = findViewById(R.id.cows);
        bullsTextView = findViewById(R.id.bulls);
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //int item = parent.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), getString(R.string.farm) + ":" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                FarmStatsActivity.this.fillData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void fillData() {
        Random r = new Random();
        yougerCowsTextView.setText(String.valueOf(r.nextInt(100)));
        cowsTextView.setText(String.valueOf(r.nextInt(100)));
        bullsTextView.setText(String.valueOf(r.nextInt(100)));


    }
    private void initSpinnerFarmChoose() {
        String[] incidences = getResources().getStringArray(R.array.incidences_example);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, incidences);
        spinner.setAdapter(adapter);


    }
    private void loadFarms() {
        final Call<ArrayList<Farm>> farm = FarmogoApiJacksonAdapter.getApiService(this).getFarms();
        farm.enqueue(new Callback<ArrayList<Farm>>() {
            @Override
            public void onResponse(Call<ArrayList<Farm>> call, Response<ArrayList<Farm>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Farm> farm = response.body();
                    ArrayAdapter farmAdapter = new ArrayAdapter(FarmStatsActivity.this, R.layout.spinner, farm);
                    Spinner farmSpinner = (Spinner) findViewById(R.id.spinnerstatistics);
                    farmSpinner.setAdapter(farmAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Farm>> call, Throwable t) {

            }
        });
    }
}
