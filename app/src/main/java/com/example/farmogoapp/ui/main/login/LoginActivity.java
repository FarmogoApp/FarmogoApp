package com.example.farmogoapp.ui.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.ui.main.farmStats.FarmStatsActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegistre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnLogin = findViewById(R.id.login_button);
        btnRegistre = findViewById(R.id.registration_button);
        registerListeners();
    }

    private void registerListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.login_succesfull), Toast.LENGTH_SHORT);
                toast1.show();
                Intent intent = new Intent(LoginActivity.this, FarmStatsActivity.class);
                startActivity(intent);
            }
        });
        btnRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}