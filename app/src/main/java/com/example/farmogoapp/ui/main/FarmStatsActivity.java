package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.farmogoapp.R;

import java.util.Random;

public class FarmStatsActivity extends Activity {

    private TextView yougerCows;
    private TextView cows;
    private TextView bulls;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm_stats);
        registerViews();
        registerListeners();
        fillData();
    }

    private void registerViews() {
        yougerCows = findViewById(R.id.cows_younger);
        cows = findViewById(R.id.cows);
        bulls = findViewById(R.id.bulls);
        button = findViewById(R.id.search);
    }

    private void registerListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); // TODO: set destination
                startActivity(intent);
            }
        });
    }

    private void fillData() {
        Random r = new Random();
        yougerCows.setText(String.valueOf(r.nextInt(100)));
        cows.setText(String.valueOf(r.nextInt(100)));
        bulls.setText(String.valueOf(r.nextInt(100)));
    }

}
