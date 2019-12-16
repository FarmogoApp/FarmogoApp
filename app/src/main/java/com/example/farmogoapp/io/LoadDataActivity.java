package com.example.farmogoapp.io;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.farmogoapp.R;
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

public class LoadDataActivity extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.await);
        loadAll();
        if (existsData()) {
            startNextActivity();
        }
    }

    public boolean existsData() {
        return SessionData.getInstance().getFarms() != null;
    }

    public void loadAll() {

        final Runnable r = () -> {

            DataUpdater dataUpdater = new DataUpdater();
            dataUpdater.syncUpdateAll();

            Log.d(this.getClass().getName(), "DATA LOADED");

            if (getLifecycle().getCurrentState().equals(Lifecycle.State.RESUMED)) {
                startNextActivity();
            }

            finish();
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void startNextActivity() {
        Intent intent = new Intent(this, FarmStatsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Log.d(this.getClass().getName(), "STATED NEXT ACTIVITY");
    }


}
