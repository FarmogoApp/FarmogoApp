package com.example.farmogoapp.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

public class FragmentDefaultIncidence extends Fragment {

    public static FragmentDefaultIncidence newInstance() {
        FragmentDefaultIncidence fragment = new FragmentDefaultIncidence();
        return fragment;
    }
    public FragmentDefaultIncidence() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_default_incidence, container, false);
    }

}
