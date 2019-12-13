package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.ui.main.farms.FarmStatsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadDataActivity extends Activity {



    private CountDownLatch countDownLatch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.await);
        loadAll();

    }

    public void loadAll() {

        countDownLatch = new CountDownLatch(4);
        final Runnable r = () -> {
            this.updatefarms();
            this.updateAnimals();
            this.updateAnimalTypes();
            this.updateRaces();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, FarmStatsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        };
        Thread t= new Thread(r);
        t.start();


    }


    public void updatefarms() {
        FarmogoApiJacksonAdapter.getApiService(this).getFarms().enqueue(new Callback<ArrayList<Farm>>() {
            @Override
            public void onResponse(Call<ArrayList<Farm>> call, Response<ArrayList<Farm>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Farm> farms = response.body();
                    SessionData sessionData = SessionData.getInstance();
                    sessionData.setFarms(farms);
                    if (sessionData.getActualFarm() == null && !farms.isEmpty()) {
                        sessionData.setActualFarm(farms.get(0));
                    }
                } else {
                    // TODO: ?????
                }
                Log.d("LoadDataActivity","farms ends");
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ArrayList<Farm>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity","farms ends");
                countDownLatch.countDown();
            }
        });
    }


    public void updateRaces() {
        FarmogoApiJacksonAdapter.getApiService(this).getRaces().enqueue(new Callback<ArrayList<Race>>() {
            @Override
            public void onResponse(Call<ArrayList<Race>> call, Response<ArrayList<Race>> response) {
                if (response.isSuccessful()) {
                    SessionData.getInstance().setRaces(response.body());
                } else {
                    // TODO: ?????
                }
                Log.d("LoadDataActivity","races ends");
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ArrayList<Race>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity","races ends");
                countDownLatch.countDown();
            }
        });
    }

    public void updateAnimalTypes() {
        FarmogoApiJacksonAdapter.getApiService(this).getAnimalTypes().enqueue(new Callback<ArrayList<AnimalType>>() {
            @Override
            public void onResponse(Call<ArrayList<AnimalType>> call, Response<ArrayList<AnimalType>> response) {
                if (response.isSuccessful()) {
                    SessionData.getInstance().setAnimalTypes(response.body());
                } else {
                    // TODO: ?????
                }
                Log.d("LoadDataActivity","animaltypes ends");
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ArrayList<AnimalType>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity","animaltypes ends");
                countDownLatch.countDown();
            }
        });
    }

    public void updateAnimals() {
        FarmogoApiJacksonAdapter.getApiService(this).getAllAnimals().enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    SessionData.getInstance().setAnimals(response.body());
                } else {
                    // TODO: ?????
                }
                Log.d("LoadDataActivity","animals ends");
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity","animals ends");
                countDownLatch.countDown();
            }
        });
    }
}
