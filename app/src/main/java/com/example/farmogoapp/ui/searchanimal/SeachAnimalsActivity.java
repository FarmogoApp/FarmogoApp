package com.example.farmogoapp.ui.searchanimal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.ui.main.AnimalInfoActivity;
import com.example.farmogoapp.ui.main.RegisterCow;

import java.util.ArrayList;
import java.util.Random;

public class SeachAnimalsActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView resultListView;
    private SearchAnimalsAdapter searchAnimalsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchanimal_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerViews();
        registerListerners();
        prepareDataAdapter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchanimal, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(searchView.getQuery());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doSearch(searchView.getQuery());
                return true;
            }
        });

        MenuItem item = menu.findItem(R.id.register_animal);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(SeachAnimalsActivity.this, RegisterCow.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void registerListerners() {


        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SeachAnimalsActivity.this, AnimalInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void prepareDataAdapter() {
        ArrayList<Animal> testData = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            testData.add(new Animal(Math.abs(r.nextLong() % 1_000_000_000_000L)));
        }

        searchAnimalsAdapter = new SearchAnimalsAdapter(this, testData);
        resultListView.setAdapter(searchAnimalsAdapter);
    }

    private void doSearch(CharSequence query) {
        searchAnimalsAdapter.setFilter(query);
    }

    private void registerViews() {
        resultListView = findViewById(R.id.result_list);
    }
}
