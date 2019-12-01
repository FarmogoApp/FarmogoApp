package com.example.farmogoapp.ui.main.animalIncidence;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.farmogoapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExitFragment extends Fragment {
    private Button saveButton;
    public static ExitFragment newInstance() {
        ExitFragment fragment = new ExitFragment();
        return fragment;
    }
    public ExitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.exit_fragment, container, false);
        saveButton = view.findViewById(R.id.inregistercow);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getView().getContext(),getActivity().getString(R.string.unregistersucces),Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }
    }


