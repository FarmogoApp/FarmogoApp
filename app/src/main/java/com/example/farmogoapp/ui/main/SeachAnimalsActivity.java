package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;

import com.example.farmogoapp.R;

import java.util.ArrayList;
import java.util.Random;

public class SeachAnimalsActivity extends Activity {

    private SearchView searchView;
    private ListView resultListView;
    private ArrayAdapter<String> resultsListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_search);
        registerViews();
        registerListerners();
        prepareDataAdapter();
    }

    private void registerListerners() {

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

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                //startActivity(intent);
            }
        });
    }

    private void prepareDataAdapter() {
        ArrayList<String> testData = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            testData.add(String.format("%04d", r.nextInt(10000)));
        }
        resultsListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData.toArray(new String[testData.size()]));
        resultsListAdapter.getFilter().filter("");
        resultListView.setAdapter(resultsListAdapter);
    }

    private void doSearch(CharSequence query) {
        resultsListAdapter.getFilter().filter(query);
        resultsListAdapter.notifyDataSetChanged();
    }

    private void registerViews() {
        searchView = findViewById(R.id.search);
        resultListView = findViewById(R.id.result_list);
    }
}
