package com.example.farmogoapp.ui.main.animalIncidence;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

public class FragmentNewIncidence extends Fragment {

    public static FragmentNewIncidence newInstance() {
        FragmentNewIncidence fragment = new FragmentNewIncidence();
        return fragment;
    }
    public FragmentNewIncidence() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_incidence, container, false);
    }

}
