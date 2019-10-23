package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

public class AnimalIncidence extends AppCompatActivity {
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_incidence);
        spinner= findViewById(R.id.incidencespiner);



        String[] incidences =new String[]{"opcion1","opcion2","Add new incidence+"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, incidences);
        spinner.setAdapter(adapter);
        registerListeners();
    }
    private void registerListeners() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //LinearLayout ll = findViewById(R.id.linearLayout2);
                //ll.removeAllViews();
                //int item = parent.getSelectedItemPosition();
                //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position).toString() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
}
}
