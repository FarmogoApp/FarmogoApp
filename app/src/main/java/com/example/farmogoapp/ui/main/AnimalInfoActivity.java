package com.example.farmogoapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;

public class AnimalInfoActivity extends AppCompatActivity {

    private Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnList = findViewById(R.id.list);
        registerListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListeners() {
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalInfoActivity.this, AnimalListActivity.class);
                startActivity(intent);
            }
        });
    }

}
