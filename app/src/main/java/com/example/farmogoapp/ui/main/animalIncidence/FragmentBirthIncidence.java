package com.example.farmogoapp.ui.main.animalIncidence;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

public class FragmentBirthIncidence extends Fragment {

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
        return inflater.inflate(R.layout.fragment_birth_incidence, container, false);
    }

}
