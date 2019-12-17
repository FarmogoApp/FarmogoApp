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
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.incidences.DischargeType;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidenceDischarge;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExitFragment extends Fragment {
    private Button unregisterButton;
    private View view;
    private Spinner spDischargeType;
    private EditText eTDischargeDestination;
    private EditText eTDischargeCertificate;
    private EditText eTDischargeObs;
    private String animalOfficialId;
    private String farmId;
    private ArrayList<DischargeType> dischargeType;
    private Integer incidenceType;
    private String animalUuid;

    public static ExitFragment newInstance() {
        ExitFragment fragment = new ExitFragment();
        return fragment;
    }
    public ExitFragment() {
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

        view= inflater.inflate(R.layout.exit_fragment, container, false);
        registerViews();
        dischargeType = new ArrayList<DischargeType>(Arrays.asList(getDischargeTypes()));
        ArrayAdapter dischargeTypeAdapater = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner, dischargeType);
        spDischargeType.setAdapter(dischargeTypeAdapater);
        registerListeners();
        return  view;
    }

    public DischargeType[] getDischargeTypes() {
        return DischargeType.values();
    }

    private void saveIncidenceSimple() {

        /*  IncidenceDischarge incidenceDischarge = new IncidenceDischarge();
            incidenceDischarge.setHealthRegister("test register");
            incidenceDischarge.setDischargeType(DischargeType.Slaughterhouse);
            incidenceDischarge.setObservations("observations");
            incidenceDischarge.setDone(false);
            incidenceDischarge.setAnimalId(animalA.getUuid());
            incidenceDischarge.setCreatedBy(user.getUuid());
            incidenceDischarge.setFarmId(farmA.getUuid());*/

        DischargeType dischargeType = (DischargeType) spDischargeType.getSelectedItem();
        IncidenceDischarge incidenceDischarge = new IncidenceDischarge();
        incidenceDischarge.setHealthRegister(eTDischargeCertificate.getText().toString());
        incidenceDischarge.setDischargeType(dischargeType);
        incidenceDischarge.setObservations(eTDischargeObs.getText().toString());
        incidenceDischarge.setDischargeDestination(eTDischargeDestination.getText().toString());
        incidenceDischarge.setDone(false);
        incidenceDischarge.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        incidenceDischarge.setAnimalId(this.animalUuid);
        incidenceDischarge.setFarmId(this.farmId);

        // POST incidence
        CreateDischargeIncidence(incidenceDischarge);
    }

    private void saveIncidenceMultiple() {

        /*  IncidenceDischarge incidenceDischarge = new IncidenceDischarge();
            incidenceDischarge.setHealthRegister("test register");
            incidenceDischarge.setDischargeType(DischargeType.Slaughterhouse);
            incidenceDischarge.setObservations("observations");
            incidenceDischarge.setDone(false);
            incidenceDischarge.setAnimalId(animalA.getUuid());
            incidenceDischarge.setCreatedBy(user.getUuid());
            incidenceDischarge.setFarmId(farmA.getUuid());*/

        DischargeType dischargeType = (DischargeType) spDischargeType.getSelectedItem();
        IncidenceDischarge incidenceDischarge = new IncidenceDischarge();
        incidenceDischarge.setHealthRegister(eTDischargeCertificate.getText().toString());
        incidenceDischarge.setDischargeType(dischargeType);
        incidenceDischarge.setObservations(eTDischargeObs.getText().toString());
        incidenceDischarge.setDischargeDestination(eTDischargeDestination.getText().toString());
        incidenceDischarge.setDone(false);
        incidenceDischarge.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        for(Animal animal : SessionData.getInstance().getAnimalCardObj()) {

            incidenceDischarge.setAnimalId(animal.getUuid());
            incidenceDischarge.setFarmId(animal.getFarmId());
            // POST incidence
            CreateDischargeIncidence(incidenceDischarge);
        }
        getActivity().finish();
    }

    private void CreateDischargeIncidence(IncidenceDischarge incidenceDischarge) {
        Call<Incidence> incidenceCall = FarmogoApiJacksonAdapter.getApiService().createIncidence(incidenceDischarge);
        incidenceCall.enqueue(new Callback<Incidence>() {
            @Override
            public void onResponse(Call<Incidence> call, Response<Incidence> response) {

                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast.show();

                    if (incidenceType == 1) {
                        Intent intent = new Intent(getContext(), AnimalInfoActivity.class);
                        intent.putExtra("animalId", (String) incidenceDischarge.getAnimalId());
                        startActivity(intent);
                        getActivity().finish();
                    }else if(incidenceType == 2){
                        Intent intent = new Intent(getContext(), AnimalListActivity.class);
                        startActivity(intent);
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

    private void registerViews() {
        unregisterButton = view.findViewById(R.id.unregistercow);
        spDischargeType = view.findViewById(R.id.sp_dischargeType);
        eTDischargeDestination = view.findViewById(R.id.ed_DischargeDestination);
        eTDischargeCertificate = view.findViewById(R.id.ed_DischargeCertificate);
        eTDischargeObs = view.findViewById(R.id.ed_DischargeObs);
    }

    private void registerListeners() {


        unregisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(incidenceType == 1){saveIncidenceSimple();}
                if(incidenceType == 2){saveIncidenceMultiple();}
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.unregistersucces),Toast.LENGTH_SHORT).show();
            }
        });
    }
    }


