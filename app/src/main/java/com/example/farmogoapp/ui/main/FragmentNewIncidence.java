package com.example.farmogoapp.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

public class FragmentPregnancyIncidence extends Fragment {

    public static FragmentPregnancyIncidence newInstance() {
        FragmentPregnancyIncidence fragment = new FragmentPregnancyIncidence();
        return fragment;
    }
    public FragmentPregnancyIncidence() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pregnancy_incidence, container, false);
    }

}
