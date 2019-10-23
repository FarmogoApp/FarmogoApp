package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;

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
                Toast toast1 = Toast.makeText(getApplicationContext(), "Login Succesfull", Toast.LENGTH_SHORT);
                toast1.show();
                Intent intent = new Intent(LoginActivity.this, ExploitationActivity.class);
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
