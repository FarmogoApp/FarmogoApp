package com.example.farmogoapp.ui.main;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AbattoirFragment extends Fragment {


    public AbattoirFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abattoir_fragment, container, false);
    }

}
