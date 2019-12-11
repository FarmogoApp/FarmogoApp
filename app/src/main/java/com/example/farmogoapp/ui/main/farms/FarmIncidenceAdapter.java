package com.example.farmogoapp.ui.main.farms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidenceBirth;
import com.example.farmogoapp.model.incidences.IncidenceDischarge;
import com.example.farmogoapp.model.incidences.IncidencePregnancy;
import com.example.farmogoapp.model.incidences.IncidenceTreatment;
import com.example.farmogoapp.model.incidences.IncidenceType;
import com.example.farmogoapp.model.incidences.IncidenceVisitor;
import com.example.farmogoapp.model.incidences.IncidenceWeight;

import java.util.List;

public class FarmIncidenceAdapter extends RecyclerView.Adapter<FarmIncidenceAdapter.MyViewHolder> {
    private List<Incidence> incidencesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView incidence_birth_animal_id;
        public TextView incidence_birth_type;
        public TextView incidence_birth_sex;
        public TextView incidence_birth_date;

        public TextView incidence_treatment_animal_id;
        public TextView incidence_treatment_type;
        public TextView incidence_treatment_medicine;
        public TextView incidence_treatment_date;

        public TextView incidence_weight_animal_id;
        public TextView incidence_weight_type;
        public TextView incidence_weight_weight;
        public TextView incidence_weight_date;





        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            incidence_birth_animal_id = (TextView) itemView.findViewById(R.id.incidence_birth_animal_id);
            incidence_birth_type = (TextView) itemView.findViewById(R.id.incidence_birth_type);
            incidence_birth_sex = (TextView) itemView.findViewById(R.id.incidence_birth_sex);
            incidence_birth_date = (TextView) itemView.findViewById(R.id.incidence_birth_date);

            incidence_treatment_animal_id = (TextView) itemView.findViewById(R.id.incidence_treatment_animal_id);
            incidence_treatment_type = (TextView) itemView.findViewById(R.id.incidence_treatment_type);
            incidence_treatment_medicine = (TextView) itemView.findViewWithTag(R.id.incidence_treatment_medicine);
            incidence_treatment_date = (TextView) itemView.findViewById(R.id.incidence_treatment_date);

            incidence_weight_animal_id = (TextView) itemView.findViewById(R.id.incidence_weight_animal_id);
            incidence_weight_type = (TextView) itemView.findViewById(R.id.incidence_weight_type);
            incidence_weight_weight = (TextView) itemView.findViewWithTag(R.id.incidence_weight_weight);
            incidence_weight_date = (TextView) itemView.findViewById(R.id.incidence_weight_date);
        }
    }
    public FarmIncidenceAdapter(List<Incidence> incidences) {
        incidencesList = incidences;
    }

    @Override
    public int getItemViewType(int position) {
        return incidencesList.get(position).getType().ordinal();
    }

    @Override
    public FarmIncidenceAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        IncidenceType type = IncidenceType.values()[viewType];

        View view=null;
        switch (type){
            case BIRTH:
                view = inflater.inflate(R.layout.recycler_view_incidence_birth, parent, false);
                break;
            case WEIGHT:
                view = inflater.inflate(R.layout.recycler_view_incidence_weight, parent, false);
                break;
            case DISCHARGE:
                view = inflater.inflate(R.layout.recycler_view_incidence_discharge, parent, false);
                break;
            case PREGNANCY:
                view = inflater.inflate(R.layout.recycler_view_incidence_pregnancy, parent, false);
                break;
            case TREATMENT:
                view = inflater.inflate(R.layout.recycler_view_incidence_treatment, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final FarmIncidenceAdapter.MyViewHolder viewHolder, int position) {
        Incidence incidence1 = incidencesList.get(position);
        incidence1.accept(new IncidenceVisitor(){
                              @Override
                              public void visit(IncidenceDischarge obj) {
                              }

                              @Override
                              public void visit(IncidencePregnancy obj) {
                              }

                              @Override
                              public void visit(IncidenceTreatment obj) {
                                  TextView animal_treatment_id_tv = viewHolder.incidence_treatment_animal_id;
                                  animal_treatment_id_tv.setText(obj.getAnimalId());

                                  TextView animal_treatment_type_tv = viewHolder.incidence_treatment_type;
                                  animal_treatment_type_tv.setText(obj.getTreatmentType().toString());

                                  TextView animal_treatment_medicine_tv = viewHolder.incidence_treatment_medicine;
                                  animal_treatment_medicine_tv.setText(obj.getMedicine());

                                  TextView animal_treatment_date_tv = viewHolder.incidence_treatment_date;
                                  animal_treatment_date_tv.setText(obj.getDate().toString());
                              }

                              @Override
                              public void visit(IncidenceWeight obj) {
                                  TextView animal_weight_id_tv = viewHolder.incidence_weight_animal_id;
                                  animal_weight_id_tv.setText(obj.getAnimalId());

                                  TextView animal_weight_type_tv = viewHolder.incidence_weight_type;
                                  animal_weight_type_tv.setText(obj.getType().toString());

                                  TextView animal_weight_weight_tv = viewHolder.incidence_weight_weight;
                                  animal_weight_weight_tv.setText(obj.getWeight());

                                  TextView animal_weight_date_tv = viewHolder.incidence_weight_date;
                                  animal_weight_date_tv.setText(obj.getDate().toString());
                              }

                              @Override
                              public void visit(IncidenceBirth obj) {
                              }


            });
        }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return incidencesList.size();
    }

}
