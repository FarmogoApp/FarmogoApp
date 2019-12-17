package com.example.farmogoapp.ui.main.animalIncidence;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidenceTreatment;
import com.example.farmogoapp.model.incidences.TreatmentType;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTreatmentIncidence extends Fragment {
    private Button saveButton;
    private View view;
    private Spinner spTreatmentType;
    private EditText  eTTreatmentMedicine;
    private EditText  eTTreatmentDose;
    private EditText eTTreatmentObs;
    private String animalOfficialId;
    private String farmId;
    private ArrayList<TreatmentType> treatmentType;
    private Integer incidenceType;
    private String animalUuid;
    private Integer i = 0;


    public static FragmentTreatmentIncidence newInstance() {
        FragmentTreatmentIncidence fragment = new FragmentTreatmentIncidence();
        return fragment;
    }
    public FragmentTreatmentIncidence() {
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
        view = inflater.inflate(R.layout.fragment_treatment_incidence, container, false);


        registerViews();

        treatmentType = new ArrayList<TreatmentType>(Arrays.asList(getTreatmentTypes()));
        ArrayAdapter treatmentTypeAdapater = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner, treatmentType);
        spTreatmentType.setAdapter(treatmentTypeAdapater);
        registerListeners();
        return view;
    }

    public TreatmentType[] getTreatmentTypes() {
        return TreatmentType.values();
    }

    private void CreateTreatmentIncidence(IncidenceTreatment incidenceTreatment) {
        Call<Incidence> incidenceCall = FarmogoApiJacksonAdapter.getApiService().createIncidence(incidenceTreatment);
        incidenceCall.enqueue(new Callback<Incidence>() {
            @Override
            public void onResponse(Call<Incidence> call, Response<Incidence> response) {

                if (response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast.show();
                    if (incidenceType == 1) {
                        Intent intent = new Intent(getContext(), AnimalInfoActivity.class);
                        intent.putExtra("animalId", (String) incidenceTreatment.getAnimalId());
                        startActivity(intent);
                        getActivity().finish();
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

    private void saveIncidenceMultiple() {
        if(!checkFields()){
            return;
        }

        /*  IncidenceTreatment incidenceTreatment = new IncidenceTreatment();
            incidenceTreatment.setTreatmentType(TreatmentType.Vaccine);
            incidenceTreatment.setMedicine("tetanus");
            incidenceTreatment.setDose("100mg");
            incidenceTreatment.setCreatedBy(user.getUuid());
            incidenceTreatment.setAnimalId(animalA.getUuid());
            incidenceTreatment.setFarmId(farmA.getUuid());*/

        TreatmentType treatmentType = (TreatmentType) spTreatmentType.getSelectedItem();
        IncidenceTreatment incidenceTreatment = new IncidenceTreatment();

        incidenceTreatment.setTreatmentType(treatmentType);
        incidenceTreatment.setMedicine(eTTreatmentMedicine.getText().toString());
        incidenceTreatment.setDose(eTTreatmentDose.getText().toString());
        incidenceTreatment.setObservations(eTTreatmentObs.getText().toString());
        incidenceTreatment.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());

        //for(Animal animal : SessionData.getInstance().getAnimalCardObj()) {
        for(i = 0;i<SessionData.getInstance().getAnimalCardObj().size();i++ ){
            incidenceTreatment.setAnimalId(SessionData.getInstance().getAnimalCardObj().get(i).getUuid());
            incidenceTreatment.setFarmId(SessionData.getInstance().getAnimalCardObj().get(i).getFarmId());
            CreateTreatmentIncidence(incidenceTreatment);
        }
        Intent intent = new Intent(getContext(), AnimalListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void saveIncidenceSimple() {
        if(!checkFields()){
            return;
        }

        TreatmentType treatmentType = (TreatmentType) spTreatmentType.getSelectedItem();
        IncidenceTreatment incidenceTreatment = new IncidenceTreatment();

        incidenceTreatment.setTreatmentType(treatmentType);
        incidenceTreatment.setMedicine(eTTreatmentMedicine.getText().toString());
        incidenceTreatment.setDose(eTTreatmentDose.getText().toString());
        incidenceTreatment.setObservations(eTTreatmentObs.getText().toString());
        incidenceTreatment.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        incidenceTreatment.setAnimalId(this.animalUuid);
        incidenceTreatment.setFarmId(this.farmId);

        // POST incidence
        CreateTreatmentIncidence(incidenceTreatment);
    }

    public boolean checkFields(){

        if (eTTreatmentMedicine.getText().toString().isEmpty()) {
            eTTreatmentMedicine.setError("Camp obligatori");
            return false;
        }
        if (eTTreatmentDose.getText().toString().isEmpty()) {
            eTTreatmentDose.setError("Camp obligatori");
            return false;
        }
        return true;
    }

    private void registerViews() {
        saveButton = view.findViewById(R.id.saveTreatment);
        spTreatmentType = view.findViewById(R.id.sp_TreatmentType);
        eTTreatmentMedicine = view.findViewById(R.id.ed_TreatmentMedicine);
        eTTreatmentDose = view.findViewById(R.id.ed_TreatmentDose);
        eTTreatmentObs = view.findViewById(R.id.ed_Treatmentobs);
    }

    private void registerListeners() {

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(incidenceType == 1){saveIncidenceSimple();}
                if(incidenceType == 2){saveIncidenceMultiple();}
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.incidence_saved),Toast.LENGTH_SHORT).show();
            }
        });



    }

}
