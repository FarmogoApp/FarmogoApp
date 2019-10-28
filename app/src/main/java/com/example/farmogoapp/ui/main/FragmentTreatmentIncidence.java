package com.example.farmogoapp.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

public class FragmentTreatmentIncidence extends Fragment {
    private Button saveButton;
    private View view;
    public static FragmentTreatmentIncidence newInstance() {
        FragmentTreatmentIncidence fragment = new FragmentTreatmentIncidence();
        return fragment;
    }
    public FragmentTreatmentIncidence() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_treatment_incidence, container, false);
        saveButton = view.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.incidence_saved),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
