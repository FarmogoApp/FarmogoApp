package com.example.farmogoapp.ui.main.animalIncidence;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidenceWeight;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWheighingIncidence extends Fragment {
    private Button saveButton;
    private View view;
    private EditText eTWheinghingPesaje;
    private EditText eTWheinghingObs;
    private String animalOfficialId;
    private String farmId;

    public static FragmentWheighingIncidence newInstance() {
        FragmentWheighingIncidence fragment = new FragmentWheighingIncidence();
        return fragment;
    }
    public FragmentWheighingIncidence() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            animalOfficialId = this.getArguments().getString("animalOfficialId", "");
            farmId = this.getArguments().getString("farmId", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weighing_incidence, container, false);
        registerViews();

        registerListeners();

        return view;
    }

    private void saveIncidence() {

        /*  IncidenceWeight incidence = new IncidenceWeight();
            incidence.setDone(true);
            incidence.setWeight(100);
            incidence.setAnimalId(animalA.getUuid());
            incidence.setCreatedBy(user.getUuid());
            incidence.setFarmId(farmA.getUuid());
            incidence.setDate(LocalDate.of(2018, 5, 1));*/

        /*String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date.setText(sdf.format(calendar.getTime()));*/
        IncidenceWeight incidenceWeight = new IncidenceWeight();
        incidenceWeight.setDone(true);
        incidenceWeight.setWeight(Integer.valueOf(eTWheinghingPesaje.getText().toString()));
        incidenceWeight.setAnimalId(this.animalOfficialId);
        incidenceWeight.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        incidenceWeight.setFarmId(this.farmId);

        // POST incidence
        Call<Incidence> incidenceCall = FarmogoApiJacksonAdapter.getApiService().createIncidence(incidenceWeight);
        incidenceCall.enqueue(new Callback<Incidence>() {
            @Override
            public void onResponse(Call<Incidence> call, Response<Incidence> response) {

                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getContext(), AnimalInfoActivity.class);
                    intent.putExtra("animalId", (String) incidenceWeight.getAnimalId());
                    startActivity(intent);

                } else {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.registration_failed), Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Incidence> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(), getString(R.string.registration_failed), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void registerViews() {
        saveButton = view.findViewById(R.id.saveWheinghing);
        eTWheinghingPesaje = view.findViewById(R.id.ed_WheinghingPesaje);
        eTWheinghingObs = view.findViewById(R.id.ed_WheinghingObs);
    }

    private void registerListeners() {


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveIncidence();
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.incidence_saved),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
