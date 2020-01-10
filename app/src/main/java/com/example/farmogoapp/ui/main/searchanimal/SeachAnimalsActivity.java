package com.example.farmogoapp.ui.main.searchanimal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;

public class SeachAnimalsActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView resultListView;
    private SearchAnimalsAdapter searchAnimalsAdapter;
    private ImageView filterSwitchFarmsOne;
    private ImageView filterSwitchFarmsMany;
    private CheckBox filterDischarged;
    private TextView filterText;
    private Farm farm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchanimal_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        farm = SessionData.getInstance().getActualFarm();
        registerViews();

        filterText.setText(getString(R.string.farm) + " " + farm.getOfficialId());

        filterSwitchFarmsOne.setOnClickListener((v) -> {
            searchAnimalsAdapter.setAllFarms(false);
            searchAnimalsAdapter.updateList();

            ImageViewCompat.setImageTintList(filterSwitchFarmsMany,
                    ColorStateList.valueOf(ContextCompat.getColor(SeachAnimalsActivity.this, R.color.grey)));
            ImageViewCompat.setImageTintList(filterSwitchFarmsOne,
                    ColorStateList.valueOf(ContextCompat.getColor(SeachAnimalsActivity.this, R.color.black)));

            filterText.setText(getString(R.string.farm) + " " + farm.getOfficialId());

        });
        filterSwitchFarmsMany.setOnClickListener((v) -> {
            searchAnimalsAdapter.setAllFarms(true);
            searchAnimalsAdapter.updateList();

            ImageViewCompat.setImageTintList(filterSwitchFarmsOne,
                    ColorStateList.valueOf(ContextCompat.getColor(SeachAnimalsActivity.this, R.color.grey)));
            ImageViewCompat.setImageTintList(filterSwitchFarmsMany,
                    ColorStateList.valueOf(ContextCompat.getColor(SeachAnimalsActivity.this, R.color.black)));

            filterText.setText(getString(R.string.all_farms));
        });

        filterDischarged.setOnCheckedChangeListener((buttonView, isChecked) -> {
            searchAnimalsAdapter.setAllAnimals(isChecked);
            searchAnimalsAdapter.updateList();
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
        if (!SessionData.getInstance().getAnimalCardObj().isEmpty()) {
            list.setVisible(true);
        } else {
            list.setVisible(false);
        }
        TextView txtCount = list.getActionView().findViewById(R.id.cart_badge);
        if (!SessionData.getInstance().getAnimalCardObj().isEmpty()) {
            txtCount.setText(String.valueOf(SessionData.getInstance().getAnimalCardObj().size()));
        }

        return true;
    }

    public void goToList(View view) {
        Intent intent = new Intent(SeachAnimalsActivity.this, AnimalListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        searchAnimalsAdapter.updateAnimals();
    }

    private void prepareDataAdapter() {
        searchAnimalsAdapter = new SearchAnimalsAdapter(SeachAnimalsActivity.this, farm.getUuid());
        searchAnimalsAdapter.setAllFarms(false);
        resultListView.setAdapter(searchAnimalsAdapter);
    }

    private void doSearch(CharSequence query) {
        searchAnimalsAdapter.setFilter(query);
    }

    private void registerViews() {
        resultListView = findViewById(R.id.result_list);
        filterSwitchFarmsOne = findViewById(R.id.filterSwitchFarmsOne);
        filterSwitchFarmsMany = findViewById(R.id.filterSwitchFarmsMany);
        filterDischarged = findViewById(R.id.filterDischarged);
        filterText = findViewById(R.id.filterText);

    }


}
