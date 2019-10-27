package com.example.farmogoapp.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;

public class FragmentWheighingncidence extends Fragment {

    public static FragmentWheighingncidence newInstance() {
        FragmentWheighingncidence fragment = new FragmentWheighingncidence();
        return fragment;
    }
    public FragmentWheighingncidence() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pregnancy_incidence, container, false);
    }

}
