package com.example.farmogoapp.ui.main.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.model.User;
import com.example.farmogoapp.io.LoadDataActivity;
import com.example.farmogoapp.io.SessionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.farmogoapp.R.string.please_enter_email;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    private EditText email, password;
    String TAG = "APP: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        User actualUser = SessionData.getInstance().getActualUser();

        if (actualUser != null) {
            Log.d("LoginActivity", "Actual user" + actualUser.getUuid());
            Intent intent = new Intent(LoginActivity.this, LoadDataActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            return;
        }


        setContentView(R.layout.login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage(getString(R.string.log_in));


    }


    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        String m = email.getText().toString();
        String p = password.getText().toString();
        if (m.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(m).matches()) {
            email.setError(getString(please_enter_email));
            return;
        }

        if (p.isEmpty()) {
            password.setError(getString(R.string.please_enter_password));
            return;
        }


        pd.show();
        mAuth.signInWithEmailAndPassword(m, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.hide();
                            pd.dismiss();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            startSession(currentUser);

                        } else {
                            pd.hide();
                            Toast.makeText(LoginActivity.this, getString(R.string.user_auth_failed) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }

    private void startSession(FirebaseUser currentUser) {
        FarmogoApiJacksonAdapter.getApiService()
                .getByFirebaseUuid(currentUser.getUid()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                SessionData.getInstance().setActualUser(response.body());
                Intent intent = new Intent(LoginActivity.this, LoadDataActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, getString(R.string.user_auth_failed) + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void forgotPassword(View view) {
        String m = email.getText().toString();
        if (m.isEmpty()) {
            Toast.makeText(this, getString(please_enter_email), Toast.LENGTH_SHORT).show();
        } else {
            if (m.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(m).matches()) {
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(m)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getString(R.string.recovery_email), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.reset_password) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}
