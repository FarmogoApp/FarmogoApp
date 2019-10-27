package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

public class AnimalListActivity extends AppCompatActivity {

    private Button btnIncidence;
    private Button btnGestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnIncidence = findViewById(R.id.Incidencia);
        btnGestion = findViewById(R.id.Gestion);
        registerListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListeners() {
        btnIncidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalListActivity.this, AnimalIncidence.class);
                startActivity(intent);
            }
        });
        btnGestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalListActivity.this, AnimalInfoActivity.class);
                startActivity(intent);
            }
        });
    }

}