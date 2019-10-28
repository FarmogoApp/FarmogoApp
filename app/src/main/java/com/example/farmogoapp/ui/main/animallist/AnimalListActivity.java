package com.example.farmogoapp.ui.main.animallist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.ui.main.AnimalIncidence;
import com.example.farmogoapp.ui.main.AnimalInfoActivity;

import java.util.ArrayList;
import java.util.Random;


public class AnimalListActivity extends AppCompatActivity {

    private Button btnIncidence;
    private Button btnGestion;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<String> animal_Id_List = new ArrayList<>();
    Random r = new Random();
       // for (int i = 0; i < 100; i++)
    for (int i = 0; i < 100; i++) {
        animal_Id_List.add(String.format("%04d", r.nextInt(10000)));
    }
    //List<String> animal_Id_List = new ArrayList<String>();
    //animal_Id_List.add("7587");
    //animal_Id_List.add("7578");

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