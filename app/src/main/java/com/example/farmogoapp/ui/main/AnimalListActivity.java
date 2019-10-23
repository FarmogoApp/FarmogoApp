package com.example.farmogoapp.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

public class AnimalListActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnIncidence;
    private Button btnGestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);
        btnBack = findViewById(R.id.back2);
        btnIncidence = findViewById(R.id.Incidencia);
        btnGestion = findViewById(R.id.Gestion);
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
               // Intent intent = new Intent(AnimalListActivity.this, AnimalInfoActivity.class);
              //  startActivity(intent);
            }
        });


    }
    private void popUp() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("¿Que desea hacer?").setCancelable(false).setPositiveButton("Alta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AnimalListActivity.this, RegisterCowActivity.class);
                startActivity(intent);
                dialog.cancel();
            }
        });

        alert.setNegativeButton("Baja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AnimalListActivity.this, UnregistercowActivity.class);
                startActivity(intent);
                dialog.cancel();


            }
        });
    }

}