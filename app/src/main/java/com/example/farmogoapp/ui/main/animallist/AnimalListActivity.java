package com.example.farmogoapp.ui.main.animallist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.R;
import com.example.farmogoapp.ui.main.AnimalIncidence;
import com.example.farmogoapp.ui.main.RegisterCowActivity;

import com.example.farmogoapp.ui.main.UnregistercowActivity;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;
import com.example.farmogoapp.ui.searchanimal.SearchAnimalsAdapter;

import java.util.ArrayList;
import java.util.Random;


public class AnimalListActivity extends AppCompatActivity {

    private Button btnIncidence;
    private Button btnGestion;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnIncidence = findViewById(R.id.Incidencia);
        btnGestion = findViewById(R.id.Gestion);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        recyclerView.setHasFixedSize(true);
        prepareDataAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void prepareDataAdapter() {
        ArrayList<Animal> animal_Id_List = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            animal_Id_List.add(new Animal(Math.abs(r.nextLong() % 1_000_000_000_000L)));
        }

        mAdapter = new animal_list_adapter(animal_Id_List);
        recyclerView.setAdapter(mAdapter);
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
                popUp();
            }
        });
    }
    private void popUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("What do you want?")

                .setCancelable(false)
                .setPositiveButton("Register cow",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AnimalListActivity.this, RegisterCowActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton("Unegister cow",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(AnimalListActivity.this, UnregistercowActivity.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}