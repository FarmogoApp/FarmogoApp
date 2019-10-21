package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

public class AnimalInfoActivity extends AppCompatActivity {

    private Button btnList;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_info);
        btnList = findViewById(R.id.list);
        btnBack = findViewById(R.id.back);
        registerListerners();
    }

    private void registerListerners() {
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalInfoActivity.this, AnimalListActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AnimalInfoActivity.this, SeachAnimalsActivity.class);
                startActivity(intent2);
            }
        });
    }

}
