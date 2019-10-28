package com.example.farmogoapp.ui.main.animalInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;
import com.example.farmogoapp.ui.main.animallist.animal_list_adapter;

import java.util.ArrayList;
import java.util.Random;

public class AnimalInfoActivity extends AppCompatActivity {

    private Button btnList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<HistoryInfo> animal_History = new ArrayList<>();
    HistoryInfo historyInfo1 = new HistoryInfo("dolor","Selevit","28/10/2019");
    HistoryInfo historyInfo2 = new HistoryInfo("dolor","Selevit","28/10/2019");

    //animal_History.add(historyInfo1);
    //animal_History.add(historyInfo1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnList = findViewById(R.id.list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_animalInfo);
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
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalInfoActivity.this, AnimalListActivity.class);
                startActivity(intent);
            }
        });
    }

}
