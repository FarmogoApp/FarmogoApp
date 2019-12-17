package com.example.farmogoapp.io;

import android.util.Log;

import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.Race;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataUpdater {

    private CountDownLatch countDownLatch =null;

    public void syncUpdateAll(){
        countDownLatch = new CountDownLatch(4);
        updateAllCommon();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateAll(){
        countDownLatch = null;
        updateAllCommon();
    }
    private void updateAllCommon(){
        this.updatefarms();
        this.updateAnimals();
        this.updateAnimalTypes();
        this.updateRaces();
}

    public void updatefarms() {
        FarmogoApiJacksonAdapter.getApiService().getFarms().enqueue(new Callback<ArrayList<Farm>>() {
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
                Log.d("LoadDataActivity", "farms ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ArrayList<Farm>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity", "farms ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        });
    }


    public void updateRaces() {
        FarmogoApiJacksonAdapter.getApiService().getRaces().enqueue(new Callback<ArrayList<Race>>() {
            @Override
            public void onResponse(Call<ArrayList<Race>> call, Response<ArrayList<Race>> response) {
                if (response.isSuccessful()) {
                    SessionData.getInstance().setRaces(response.body());
                } else {
                    // TODO: ?????
                }
                Log.d("LoadDataActivity", "races ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ArrayList<Race>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity", "races ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        });
    }

    public void updateAnimalTypes() {
        FarmogoApiJacksonAdapter.getApiService().getAnimalTypes().enqueue(new Callback<ArrayList<AnimalType>>() {
            @Override
            public void onResponse(Call<ArrayList<AnimalType>> call, Response<ArrayList<AnimalType>> response) {
                if (response.isSuccessful()) {
                    SessionData.getInstance().setAnimalTypes(response.body());
                } else {
                    // TODO: ?????
                }
                Log.d("LoadDataActivity", "animaltypes ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ArrayList<AnimalType>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity", "animaltypes ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        });
    }

    public void updateAnimals() {
        FarmogoApiJacksonAdapter.getApiService().getAllAnimals().enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    SessionData.getInstance().setAnimals(response.body());
                } else {
                    // TODO: ?????
                }
                Log.d("LoadDataActivity", "animals ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                // TODO: ?????
                Log.d("LoadDataActivity", "animals ends");
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        });
    }

    public void updateAnimalsJose() {
        FarmogoApiJacksonAdapter.getApiService().getAllAnimals().enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    SessionData.getInstance().setAnimals(response.body());
                } else {
                    // TODO: ?????
                }

            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                // TODO: ?????
            }
        });
    }
}
