package com.example.farmogoapp.ui.main;

import java.util.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;

import java.util.ArrayList;

public class AnimalListActivity extends AppCompatActivity {

    private Button btnIncidence;
    private Button btnGestion;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<String> animal_Id_List = new ArrayList<String>();
    animal_Id_List.add("1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnIncidence = findViewById(R.id.Incidencia);
        btnGestion = findViewById(R.id.Gestion);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        recyclerView.setHasFixedSize(true);
        mAdapter = new animal_list_adapter(animal_Id_List);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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