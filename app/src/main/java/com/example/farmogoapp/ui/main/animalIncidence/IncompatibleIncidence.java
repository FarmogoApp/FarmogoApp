package com.example.farmogoapp.ui.main.animalIncidence;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.farmogoapp.R;

public class IncompatibleIncidence extends Fragment {


    private View view;
    private int error;

    public IncompatibleIncidence(int error){
        this.error = error;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_incompatible_incidence, container, false);
        TextView txtError = view.findViewById(R.id.textError);
        txtError.setText(error);
        return view;
    }
}
