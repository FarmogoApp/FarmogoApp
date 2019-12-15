package com.example.farmogoapp.ui.main.searchanimal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;

public class SeachAnimalsActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView resultListView;
    private SearchAnimalsAdapter searchAnimalsAdapter;
    private SwitchCompat filterSwitch;
    private TextView filterText;
    private Farm farm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchanimal_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        farm = SessionData.getInstance().getActualFarm();
        registerViews();

        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                searchAnimalsAdapter.setAllFarms(isChecked);
                searchAnimalsAdapter.updateList();
                if(isChecked){
                    filterText.setText(getString(R.string.all_farms));
                }else{
                    filterText.setText(getString(R.string.farm)+": " + farm.getOfficialId());
                }
            }
        });

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
        searchAnimalsAdapter = new SearchAnimalsAdapter(SeachAnimalsActivity.this, farm.getUuid());
        searchAnimalsAdapter.setAllFarms(filterSwitch.isChecked());
        resultListView.setAdapter(searchAnimalsAdapter);
    }

    private void doSearch(CharSequence query) {
        searchAnimalsAdapter.setFilter(query);
    }

    private void registerViews() {
        resultListView = findViewById(R.id.result_list);
        filterSwitch = findViewById(R.id.filterSwitch);
        filterText = findViewById(R.id.filterText);

    }
}
