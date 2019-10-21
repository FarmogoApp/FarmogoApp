package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

public class AnimalListActivity extends AppCompatActivity {

    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);
        btnBack = findViewById(R.id.back2);
        registerListeners();
    }

    private void registerListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalListActivity.this, AnimalInfoActivity.class);
                startActivity(intent);
            }
        });
    }

}