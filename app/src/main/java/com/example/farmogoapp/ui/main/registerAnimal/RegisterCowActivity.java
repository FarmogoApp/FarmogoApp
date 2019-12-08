package com.example.farmogoapp.ui.main.registerAnimal;

import androidx.appcompat.app.AppCompatActivity;
import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiAdapter;
import com.example.farmogoapp.io.response.animalTypesResponse;
import com.example.farmogoapp.ui.main.farms.FarmStatsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCowActivity extends AppCompatActivity  implements Callback<ArrayList<animalTypesResponse>> {
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Call<ArrayList<animalTypesResponse>> call = FarmogoApiAdapter.getApiService().getAnimalTypes();
        call.enqueue(this);

        setContentView(R.layout.activity_register_cow);
        btnRegister =(Button) findViewById(R.id.btnregister);
        registerListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.registration_succesfull), Toast.LENGTH_SHORT);
                toast1.show();
                Intent intent = new Intent(RegisterCowActivity.this, FarmStatsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResponse(Call<ArrayList<animalTypesResponse>> call, Response<ArrayList<animalTypesResponse>> response) {
        if(response.isSuccessful()){
            ArrayList<animalTypesResponse> animalTypes = response.body();
            Log.e("ANIMAL TYPES:", String.valueOf(animalTypes.size()));
            for(int i = 0; i< animalTypes.size();i++){
                Log.e("Animal Tpye UID:", animalTypes.get(i).getmUid());
                Log.e("Animal Tpye Descriptio:", animalTypes.get(i).getDescription());
                Log.e("Animal Tpye Icon:", animalTypes.get(i).getIcon());


            }
        }
    }

    @Override
    public void onFailure(Call<ArrayList<animalTypesResponse>> call, Throwable t) {

    }
}
