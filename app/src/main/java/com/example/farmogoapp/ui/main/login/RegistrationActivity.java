package com.example.farmogoapp.ui.main.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiAdapter;
import com.example.farmogoapp.model.User;
import com.example.farmogoapp.ui.main.farms.FarmStatsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements Callback<User> {
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private EditText mail, epass, erpass, telephone, name;
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
        telephone = findViewById(R.id.registration_telephone);
        name = findViewById(R.id.registration_name);
        registerListeners();
    }
    private void registerListeners() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = mail.getText().toString().trim();
                String p = epass.getText().toString().trim();
                String rp = erpass.getText().toString().trim();
                final String nm = name.getText().toString().trim();
                final String tel = telephone.getText().toString().trim();

                if(!checkFields(m,p,rp,nm,tel)){
                    return;
                }
                mAuth.createUserWithEmailAndPassword(m, p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    addInfo(FirebaseAuth.getInstance().getCurrentUser(),tel,nm,"");
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "User Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    public boolean checkFields(String email, String password, String rpassword, String nm, String tel){

        if (nm.isEmpty()) {
            name.setError("S'ha d'introduir un nom");
            return false;
        }
        if (email.isEmpty()) {
            mail.setError("Error de email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Error de email incorrecto");
            return false;
        }

        if (tel.isEmpty()) {
            telephone.setError("S'ha d'introduir un telefon");
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

    public void addInfo(FirebaseUser user, String tel, String name, String uuid) {
        User finaluser = new User(user.getUid(),user.getEmail(), tel, name, null );
        Call<User> call = FarmogoApiAdapter.getApiService().createUser(finaluser);
        call.enqueue(this);
    }

        @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.isSuccessful()){
            User user = response.body();

            pd.setMessage("Creant usuari...");
            pd.show();
            Log.e("User Email:", user.getEmail());
            Log.e("User Nom:", user.getName());
            Log.e("User Telefon:", user.getTelephone());
            Log.e("User FireBaseUUID:", user.getFirebaseUuid());
            Log.e("User Uuid:", user.getUuid());

            Intent intent = new Intent(RegistrationActivity.this, FarmStatsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(this, "User Registration on MONGO Failed: " , Toast.LENGTH_SHORT).show();

    }

}
