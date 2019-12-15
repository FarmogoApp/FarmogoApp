package com.example.farmogoapp.ui.main.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.LoadDataActivity;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.User;
import com.example.farmogoapp.ui.main.login.LoginActivity;


public class Settings extends AppCompatActivity {

    private Button clearCache;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerViews();
        registerListeners();
    }


    private void registerViews() {
        clearCache = findViewById(R.id.clear_cache);
        logout = findViewById(R.id.logout);
    }

    private void registerListeners() {

        clearCache.setOnClickListener(v -> {
            User actualUser = SessionData.getInstance().getActualUser();
            SessionData.getInstance().clearAll();
            SessionData.getInstance().setActualUser(actualUser);
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.cache_memory), Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(Settings.this, LoadDataActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            SessionData.getInstance().clearAll();
            Intent intent = new Intent(Settings.this, LoginActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
}
