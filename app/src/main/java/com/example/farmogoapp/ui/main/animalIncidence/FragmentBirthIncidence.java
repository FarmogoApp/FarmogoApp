package com.example.farmogoapp.ui.main.animalIncidence;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidenceBirth;
import com.example.farmogoapp.ui.main.SessionData;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;
import com.example.farmogoapp.ui.main.farms.FarmStatsActivity;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBirthIncidence extends Fragment{

    private View view;
    private Button registerCow;
    private Calendar calendar = Calendar.getInstance();
    private EditText date;
    private Spinner racesp;
    private Spinner sexsp;
    private EditText eTMotherOfficialIdEdit;
    private EditText eTofficialId;
    private String animalUuid;
    private String animalOfficialId;

    public static FragmentBirthIncidence newInstance() {
        FragmentBirthIncidence fragment = new FragmentBirthIncidence();
        return fragment;
    }
    public FragmentBirthIncidence() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            animalUuid = this.getArguments().getString("animalId", "");
            animalOfficialId = this.getArguments().getString("animalOfficialId", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view = inflater.inflate(R.layout.fragment_birth_incidence, container, false);

        //if(getArguments().getString("animalId") != null){
            //Log.e("asdasdad",getArguments().getString("animalId"));
           // idAnimal = this.getArguments().getString("animalId", "");
        //}

        initializeRaceSpinner();
        registerViews();
        eTMotherOfficialIdEdit.setText(this.animalOfficialId);
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(calendar.getTime()));
        registerListeners();

        return view;
    }

    private void registerCow() {

        // Get user input
        /*
        String officialId = etOfficialId.getText().toString();
        Animal selectedMother = (Animal) spnMotherId.getSelectedItem();
        if(birthDate == null) birthDate = new Date(clvBirthDate.getDate());
        String origin = etOrigin.getText().toString();
        String selectedSex = spnSex.getSelectedItem().toString();
        AnimalType selectedAnimalType = (AnimalType) spnAnimalType.getSelectedItem();
        Race selectedRace = (Race) spnRace.getSelectedItem();
        RegisterCowActivity.Location location = (RegisterCowActivity.Location) spnLocation.getSelectedItem();

        // Set animal
        Animal animal = new Animal();
        animal.setOfficialId(officialId);
        animal.setMotherId(selectedMother.getUuid());
        animal.setMotherOfficialId(selectedMother.getOfficialId());
        animal.setBirthDay(birthDate);
        animal.setOrigin(origin);
        animal.setSex(selectedSex);
        animal.setAnimalTypeId(selectedAnimalType.getUuid());
        animal.setRaceId(selectedRace.getUuid());
        animal.setDivisionId(location.getDivision().getUuid());
        animal.setFarmId(currentFarm.getUuid());

*//*
        String dates = date.getText().toString();
        String[] parts = dates.split("/");
        String day = parts[0]; // day
        String month = parts[1]; // month
        //String year = parts[2]; // year


        IncidenceBirth birth = new IncidenceBirth();
            birth.setBirthDate(LocalDate.of(2019, 11, 27));
            birth.setOfficialId("ES12345566778");
            birth.setRaceId(raceE.getUuid());
            birth.setCreatedBy(user.getUuid());
            birth.setOfficialId(farm.getAnimalCounter().toString());
            birth.setSex("Male");
            birth.setFarmId(farmA.getUuid());
            birth.setAnimalId(animalA.getUuid());

        */

        String officialId = eTofficialId.getText().toString();
        Race raceSelected = (Race) racesp.getSelectedItem();
        String sexSelected = (String) sexsp.getSelectedItem();

        IncidenceBirth incidenceBirth = new IncidenceBirth();
        incidenceBirth.setBirthDate(new Date(date.getText().toString()));

        incidenceBirth.setRaceId(raceSelected.getUuid());
        incidenceBirth.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        //incidenceBirth.setOfficialId();
        incidenceBirth.setSex(sexSelected);
       // incidenceBirth.setFarmId();
        //incidenceBirth.setAnimalId();



        // POST incidence
        Call<Incidence> incidenceCall = FarmogoApiJacksonAdapter.getApiService(getContext()).createIncidence(incidenceBirth);
        incidenceCall.enqueue(new Callback<Incidence>() {
            @Override
            public void onResponse(Call<Incidence> call, Response<Incidence> response) {

                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getContext(), AnimalInfoActivity.class);
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
        registerCow = view.findViewById(R.id.registerCow);
        date = view.findViewById(R.id.editTextBirth);
        racesp = view.findViewById(R.id.raceBirthSpinner);
        sexsp = view.findViewById(R.id.genderBirthSpinner);
        eTMotherOfficialIdEdit = view.findViewById(R.id.MotherOfficialIdEdit);
        eTofficialId = view.findViewById(R.id.officialIdEdit);

    }

    private void registerListeners() {

        registerCow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerCow();
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.register_cow),Toast.LENGTH_SHORT).show();
            }
        });

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date.setText(sdf.format(calendar.getTime()));
            }

        };

        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), datePickerListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void initializeRaceSpinner() {
        Call<ArrayList<Race>> raceCall = FarmogoApiJacksonAdapter.getApiService(getContext()).getRaces();

        raceCall.enqueue(new Callback<ArrayList<Race>>() {
            @Override
            public void onResponse(Call<ArrayList<Race>> call, Response<ArrayList<Race>> response) {
                ArrayList<Race> data = response.body();

                if(data != null){
                    ArrayAdapter raceAdapater = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner, data);
                    racesp.setAdapter(raceAdapater);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Race>> call, Throwable t) {
                t.printStackTrace();
                Log.e("FragmentBirthIncidence","Races error" );
            }
        });
    }

}