package com.example.farmogoapp.ui.main.animalInfo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.model.HistoryInfo;
import com.example.farmogoapp.ui.main.animalIncidence.AnimalIncidence;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;
import com.example.farmogoapp.ui.main.searchanimal.SeachAnimalsActivity;

import java.util.ArrayList;

public class AnimalInfoActivity extends AppCompatActivity {

    private Button btnList;
    private boolean state;
    private ImageButton btnAddRemove;
    private Button btnIncidences;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAddRemove = findViewById(R.id.mas);
        btnIncidences = findViewById(R.id.IncidenciaAnimalInfo);
        state = true;

        ArrayList<HistoryInfo> animal_History = new ArrayList<>();
        HistoryInfo historyInfo1 = new HistoryInfo("dolor","Selevit","28/10/2019");
        HistoryInfo historyInfo2 = new HistoryInfo("dolor","Selevit","28/10/2019");
        animal_History.add(historyInfo1);
        animal_History.add(historyInfo2);

        recyclerView = findViewById(R.id.recyclerview_animalInfo);
        recyclerView.setHasFixedSize(true);
        mAdapter = new animal_info_adapter(animal_History);
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
        btnAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state){
                    btnAddRemove.setImageResource(android.R.drawable.ic_menu_delete);
                }else{
                    btnAddRemove.setImageResource(android.R.drawable.ic_menu_add);
                }
                state=!state;
            }
        });

        btnIncidences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalInfoActivity.this, AnimalIncidence.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animalinfo, menu);

        MenuItem list = menu.findItem(R.id.list_selected);
        list.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(AnimalInfoActivity.this, AnimalListActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }

}
