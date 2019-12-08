package com.example.farmogoapp.ui.main.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private EditText mail, epass, erpass;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pd =  new ProgressDialog(RegistrationActivity.this);
        btnRegister = findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.registration_email);
        epass = findViewById(R.id.registration_password1);
        erpass = findViewById(R.id.registration_password2);

        registerListeners();
    }
    private void registerListeners() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = mail.getText().toString().trim();
                String p = epass.getText().toString().trim();
                String rp = erpass.getText().toString().trim();
                if(!checkFields(m,p,rp)){
                    return;
                }
                mAuth.createUserWithEmailAndPassword(m, p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    addInfo(FirebaseAuth.getInstance().getCurrentUser());
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "User Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    public boolean checkFields(String email, String password, String rpassword){
        if (email.isEmpty()) {
            mail.setError("Error de email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Error de email incorrecto");
            return false;
        }

        if (password.isEmpty() || rpassword.isEmpty()) {
            epass.setError("S'ha d'introduir contrasenya");
            erpass.setError("S'ha d'introduir contrasenya");
            return false;
        }

        if (password.length() < 6) {
            epass.setError("El password es massa curt");
            return false;
        }

        if (!Objects.equals(password, rpassword)) {
            epass.setError("No coincideixen els passwords");
            erpass.setError("No coincideixen els passwords");
            return false;
        }

        return true;
    }

    public void addInfo(FirebaseUser uid) {
        FirebaseUser user = mAuth.getCurrentUser();
        pd.setMessage("Creant usuari...");
        pd.show();
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);

    }

        @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
}
