package com.example.farmogoapp.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

public class FragmentTreatmentIncidence extends Fragment {

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
        return inflater.inflate(R.layout.fragment_treatment_incidence, container, false);
    }

}
