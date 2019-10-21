package com.example.farmogoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.farmogoapp.R;

public class RegistrationActivity extends Activity {
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        btnLogin = findViewById(R.id.register_button);
        registerListeners();
    }
    private void registerListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent2);
            }
        });
    }
}
