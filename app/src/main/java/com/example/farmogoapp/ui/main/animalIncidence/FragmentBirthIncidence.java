package com.example.farmogoapp.ui.main.animalIncidence;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentBirthIncidence extends Fragment {

    private View view;
    private Button registerCow;
    private Calendar calendar = Calendar.getInstance();
    private EditText date;

    public static FragmentBirthIncidence newInstance() {
        FragmentBirthIncidence fragment = new FragmentBirthIncidence();
        return fragment;
    }
    public FragmentBirthIncidence() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_birth_incidence, container, false);
        registerCow = view.findViewById(R.id.registerCow);
        date = view.findViewById(R.id.editTextBirth);
        registerCow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.register_cow),Toast.LENGTH_SHORT).show();
            }
        });
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(calendar.getTime()));
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


        return view;
    }

}
