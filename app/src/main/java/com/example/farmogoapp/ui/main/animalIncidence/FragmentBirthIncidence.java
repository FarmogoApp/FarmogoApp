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

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidenceBirth;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
    private EditText eT_obs;
    private String animalOfficialId;
    private String farmId;
    private String farmAnimalCounter;
    private Integer incidenceType;
    private String animalUuid;

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
            farmId = this.getArguments().getString("farmId", "");
            farmAnimalCounter = this.getArguments().getString("farmAnimalCounter", "");
            incidenceType = this.getArguments().getInt("incidenceType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view = inflater.inflate(R.layout.fragment_birth_incidence, container, false);

        initializeRaceSpinner();
        registerViews();
        eTMotherOfficialIdEdit.setText(this.animalOfficialId);
        eTofficialId.setText(this.farmAnimalCounter);
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(calendar.getTime()));
        registerListeners();

        return view;
    }

    private void registerCow() {
        if(!checkFields()){
            return;
        }

        /*parametros que seteamos en el test del back
        IncidenceBirth birth = new IncidenceBirth();
            birth.setBirthDate(LocalDate.of(2019, 11, 27));
            birth.setRaceId(raceE.getUuid());
            birth.setCreatedBy(user.getUuid());
            birth.setOfficialId(farm.getAnimalCounter().toString());
            birth.setSex("Male");
            birth.setFarmId(farmA.getUuid());
            birth.setAnimalId(animalA.getUuid());

        */
        String dates = date.getText().toString();
        String[] parts = dates.split("/");
        int day = Integer.valueOf(parts[0]); // day
        int month = Integer.valueOf(parts[1]); // month
        int year = Integer.valueOf(parts[2]); // year

        Race raceSelected = (Race) racesp.getSelectedItem();
        String sexSelected = (String) sexsp.getSelectedItem();

        IncidenceBirth incidenceBirth = new IncidenceBirth();
        incidenceBirth.setBirthDate(LocalDate.of(year, month, day));
        incidenceBirth.setRaceId(raceSelected.getUuid());
        incidenceBirth.setCreatedBy(SessionData.getInstance().getActualUser().getUuid());
        incidenceBirth.setOfficialId(eTofficialId.getText().toString());
        incidenceBirth.setSex(sexSelected);
        incidenceBirth.setFarmId(this.farmId);
        incidenceBirth.setAnimalId(animalUuid);
        incidenceBirth.setObservations(eT_obs.getText().toString());

        // POST incidence
        Call<Incidence> incidenceCall = FarmogoApiJacksonAdapter.getApiService().createIncidence(incidenceBirth);
        incidenceCall.enqueue(new Callback<Incidence>() {
            @Override
            public void onResponse(Call<Incidence> call, Response<Incidence> response) {

                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.registration_succesful), Toast.LENGTH_SHORT);
                    toast.show();



                    // Get animal
                    Call<Animal> animalCall = FarmogoApiJacksonAdapter.getApiService().getAnimal(incidenceBirth.getOfficialId());
                    animalCall.enqueue(new Callback<Animal>() {
                        @Override
                        public void onResponse(Call<Animal> call, Response<Animal> response) {

                            if (response.isSuccessful()) {
                                Animal data = response.body();
                                Intent intent = new Intent(getContext(), AnimalInfoActivity.class);
                                intent.putExtra("animalId", (String) data.getUuid());//animal info del nuevo animal
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast toast = Toast.makeText(getContext(), getString(R.string.registration_failed), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Animal> call, Throwable t) {
                            Toast toast = Toast.makeText(getContext(), getString(R.string.registration_failed), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                    /*
                    DataUpdater dataUpdater = new DataUpdater();
                    //dataUpdater.updateAll();
                    dataUpdater.updateAnimals();
                    String childUuid = incidenceBirth.getAnimalId();
                    List<Animal> animals = SessionData.getInstance().getAnimals();
                    if (animals != null) {
                        //ArrayList<Animal>  = new ArrayList<>();

                        for (Animal animal : animals) {
                            if (animal.getOfficialId().equals(incidenceBirth.getOfficialId())) {
                                childUuid = animal.getUuid();
                            }
                        }

                    }
                    intent.putExtra("animalId", (String) childUuid);//animal info del nuevo animal
                    startActivity(intent);
                    getActivity().finish();
*/
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

    public boolean checkFields(){

        if (eTofficialId.getText().toString().isEmpty()) {
            eTofficialId.setError("Camp obligatori");
            return false;
        }

        if (date.getText().toString().isEmpty()) {
            date.setError("Camp obligatori");
            return false;
        }
        return true;
    }


    private void registerViews() {
        registerCow = view.findViewById(R.id.registerCow);
        date = view.findViewById(R.id.editTextBirth);
        racesp = view.findViewById(R.id.raceBirthSpinner);
        sexsp = view.findViewById(R.id.genderBirthSpinner);
        eTMotherOfficialIdEdit = view.findViewById(R.id.MotherOfficialIdEdit);
        eTofficialId = view.findViewById(R.id.officialIdEdit);
        eT_obs = view.findViewById(R.id.ed_obs);
    }

    private void registerListeners() {

        registerCow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(incidenceType == 1){registerCow();}
                //if(incidenceType == 2){}
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
        Call<ArrayList<Race>> raceCall = FarmogoApiJacksonAdapter.getApiService().getRaces();

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