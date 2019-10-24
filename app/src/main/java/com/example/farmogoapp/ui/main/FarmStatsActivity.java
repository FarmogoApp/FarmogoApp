package com.example.farmogoapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

import java.util.Random;

public class FarmStatsActivity extends AppCompatActivity {

    private TextView yougerCowsTextView;
    private TextView cowsTextView;
    private TextView bullsTextView;
    private Button searchButton;
    private ImageButton addExplotation;
    private ImageButton modifyExplotation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm_stats);
        registerViews();
        registerListeners();
        fillData();
    }

    private void registerViews() {
        yougerCowsTextView = findViewById(R.id.cows_younger);
        cowsTextView = findViewById(R.id.cows);
        bullsTextView = findViewById(R.id.bulls);
        searchButton = findViewById(R.id.search);
        addExplotation = findViewById(R.id.addExplotation);
        modifyExplotation = findViewById(R.id.modifyExplotation);
    }

    private void registerListeners() {

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmStatsActivity.this, SeachAnimalsActivity.class);
                startActivity(intent);
            }
        });

        addExplotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmStatsActivity.this, AddExploitationActivity.class);
                startActivity(intent);
            }
        });
        modifyExplotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmStatsActivity.this, AddExploitationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillData() {
        Random r = new Random();
        yougerCowsTextView.setText(String.valueOf(r.nextInt(100)));
        cowsTextView.setText(String.valueOf(r.nextInt(100)));
        bullsTextView.setText(String.valueOf(r.nextInt(100)));
    }

}
