package com.example.farmogoapp.ui.main.animallist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.R;
import com.example.farmogoapp.ui.main.animalIncidence.AnimalIncidence;


public class AnimalListActivity extends AppCompatActivity {

    private Button btnIncidence;
    private RecyclerView recyclerView;
    private AnimalListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnIncidence = findViewById(R.id.Incidencia);
        recyclerView = findViewById(R.id.recyclerview_list);
        recyclerView.setHasFixedSize(true);
        prepareDataAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.updateAnimalList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void prepareDataAdapter() {
        mAdapter = new AnimalListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void registerListeners() {
        btnIncidence.setOnClickListener(v -> {
            Intent intent = new Intent(AnimalListActivity.this, AnimalIncidence.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animallist, menu);
        MenuItem clear = menu.findItem(R.id.clear);
        clear.setOnMenuItemClickListener(item -> {
            SessionData.getInstance().clearCart();
            mAdapter.updateAnimalList();
            return true;
        });
        return true;
    }


}