package com.example.farmogoapp.ui.main.animallist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.ui.main.animalIncidence.AnimalIncidence;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnimalListActivity extends AppCompatActivity {

    private Button btnIncidence;
    private RecyclerView recyclerView;
    private AnimalListAdapter mAdapter;
    private Farm farm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnIncidence = findViewById(R.id.Incidencia);
        recyclerView = findViewById(R.id.recyclerview_list);
        recyclerView.setHasFixedSize(true);
        prepareDataAdapter();
        Log.e("AnimalListActivity",SessionData.getInstance().getAnimalCardObj().get(0).getUuid() );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadFarmData(SessionData.getInstance().getAnimalCardObj().get(0).getUuid());
        registerListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.updateAnimalList();
    }

    private void loadFarmData(String farmId) {
        Call<Farm> farmCall = FarmogoApiJacksonAdapter.getApiService().getFarm(farmId);

        farmCall.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                Farm data = response.body();

                if(data != null){
                    updateFarm(data);
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {
                t.printStackTrace();
                Log.e("AnimalListActivity","farm error" );
            }
        });
    }

    private void updateFarm(Farm data) {
        this.farm = data;
        registerListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void prepareDataAdapter() {
        mAdapter = new AnimalListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void registerListeners() {
        btnIncidence.setOnClickListener(v -> {
            Intent intent = new Intent(AnimalListActivity.this, AnimalIncidence.class);
            intent.putExtra("incidenceType", (Integer) 2);
            //if(SessionData.getInstance().getAnimalCardObj().size() == 1){
               // intent.putExtra("animalId", (String) SessionData.getInstance().getAnimalCardObj().get(0).getUuid());
               // intent.putExtra("animalOfficialId", (String) SessionData.getInstance().getAnimalCardObj().get(0).getOfficialId());
               // intent.putExtra("farmId", (String) SessionData.getInstance().getAnimalCardObj().get(0).getFarmId());
                //intent.putExtra("farmAnimalCounter", (String) this.farm.getAnimalCounter().toString());
            //}
            startActivity(intent);
            finish();

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animallist, menu);
        MenuItem clear = menu.findItem(R.id.clear);
        clear.setOnMenuItemClickListener(item -> {
            SessionData.getInstance().clearCart();
            mAdapter.updateAnimalList();
            return true;
        });
        return true;
    }


}