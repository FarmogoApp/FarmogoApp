package com.example.farmogoapp.ui.main.animalIncidence;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidencePregnancy;
import com.example.farmogoapp.model.incidences.PregnancyType;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPregnancyIncidence extends Fragment {

    private Button saveButton;
    private View view;
    private Spinner spPregnancyType;
    private ArrayList<PregnancyType> pregnancyTypes;
    private EditText eTPregnancyObs;
    private String animalOfficialId;
    private String farmId;
    private Integer incidenceType;
    private String animalUuid;


    public static FragmentPregnancyIncidence newInstance() {

        FragmentPregnancyIncidence fragment = new FragmentPregnancyIncidence();
        return fragment;
    }
    public FragmentPregnancyIncidence() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            animalUuid = this.getArguments().getString("animalId", "");
            animalOfficialId = this.getArguments().getString("animalOfficialId", "");
            farmId = this.getArguments().getString("farmId", "");
            incidenceType = this.getArguments().getInt("incidenceType");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pregnancy_incidence, container, false);

        registerViews();
        pregnancyTypes = new ArrayList<PregnancyType>(Arrays.asList(getPregancyTypes()));
        ArrayAdapter pregnancyTypeAdapater = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner, pregnancyTypes);
        spPregnancyType.setAdapter(pregnancyTypeAdapater);
        registerListeners();

        return view;
    }

    public PregnancyType[] getPregancyTypes() { return PregnancyType.values(); }

    private void registerViews() {
        saveButton = view.findViewById(R.id.savePregnancy);
        spPregnancyType = view.findViewById(R.id.sp_pregnancyType);
        eTPregnancyObs = view.findViewById(R.id.ed_pregnancyObs);
    }

    private void registerListeners() {

        saveButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(incidenceType == 1){saveIncidenceSimple();}
                if(incidenceType == 2){saveIncidenceMultiple();}
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.incidence_saved),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveIncidenceSimple() {

    /*IncidencePregnancy incidencePregnancy = new IncidencePregnancy();
            incidencePregnancy.setPregnancyType(PregnancyType.Zeal);
            incidencePregnancy.setCreatedBy(user.getUuid());
            incidencePregnancy.setFarmId(farm.getUuid());
            incidencePregnancy.setAnimalId(animalB.getUuid());*/
        PregnancyType pregnancyType = (PregnancyType) spPregnancyType.getSelectedItem();
        IncidencePregnancy incidencePregnancy = new IncidencePregnancy();

        incidencePregnancy.setPregnancyType(pregnancyType);
        incidencePregnancy.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        incidencePregnancy.setObservations(eTPregnancyObs.getText().toString());
        incidencePregnancy.setAnimalId(this.animalUuid);
        incidencePregnancy.setFarmId(this.farmId);
        // POST incidence
        createPregnancyIncidence(incidencePregnancy);
    }

    private void saveIncidenceMultiple() {

        PregnancyType pregnancyType = (PregnancyType) spPregnancyType.getSelectedItem();
        IncidencePregnancy incidencePregnancy = new IncidencePregnancy();

        incidencePregnancy.setPregnancyType(pregnancyType);
        incidencePregnancy.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        incidencePregnancy.setObservations(eTPregnancyObs.getText().toString());

        for(Animal animal : SessionData.getInstance().getAnimalCardObj()) {

            incidencePregnancy.setAnimalId(animal.getUuid());
            incidencePregnancy.setFarmId(animal.getFarmId());
            // POST incidence
            createPregnancyIncidence(incidencePregnancy);
        }
        Intent intent = new Intent(getContext(), AnimalListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    private void createPregnancyIncidence(IncidencePregnancy incidencePregnancy) {
        Call<Incidence> incidenceCall = FarmogoApiJacksonAdapter.getApiService().createIncidence(incidencePregnancy);
        incidenceCall.enqueue(new Callback<Incidence>() {
            @Override
            public void onResponse(Call<Incidence> call, Response<Incidence> response) {

                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast.show();

                    if (incidenceType == 1) {
                        Intent intent = new Intent(getContext(), AnimalInfoActivity.class);
                        intent.putExtra("animalId", (String) incidencePregnancy.getAnimalId());
                        startActivity(intent);
                        getActivity().finish();
                        /*
                    }else if(incidenceType == 2){
                        Intent intent = new Intent(getContext(), AnimalListActivity.class);
                        startActivity(intent);*/
                    }

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

}
