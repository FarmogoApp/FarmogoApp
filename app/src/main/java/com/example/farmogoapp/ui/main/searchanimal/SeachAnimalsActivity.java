package com.example.farmogoapp.ui.main.searchanimal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        item.setOnMenuItemClickListener(item12 -> {
            Intent intent = new Intent(SeachAnimalsActivity.this, RegisterCowActivity.class);
            startActivity(intent);
            return true;
        });

        MenuItem list = menu.findItem(R.id.list_selected);
        list.setOnMenuItemClickListener(item1 -> {
            Intent intent = new Intent(SeachAnimalsActivity.this, AnimalListActivity.class);
            startActivity(intent);
            return true;
        });
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        searchAnimalsAdapter.updateAnimals();
    }

    private void prepareDataAdapter() {
        searchAnimalsAdapter = new SearchAnimalsAdapter(SeachAnimalsActivity.this);
        resultListView.setAdapter(searchAnimalsAdapter);
    }

    private void doSearch(CharSequence query) {
        searchAnimalsAdapter.setFilter(query);
    }

    private void registerViews() {
        resultListView = findViewById(R.id.result_list);
    }
}
