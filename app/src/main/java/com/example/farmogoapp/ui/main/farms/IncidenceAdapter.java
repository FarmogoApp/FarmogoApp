package com.example.farmogoapp.ui.main.farms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.AnimalType;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.model.incidences.IncidenceBirth;
import com.example.farmogoapp.model.incidences.IncidenceDischarge;
import com.example.farmogoapp.model.incidences.IncidencePregnancy;
import com.example.farmogoapp.model.incidences.IncidenceTreatment;
import com.example.farmogoapp.model.incidences.IncidenceType;
import com.example.farmogoapp.model.incidences.IncidenceVisitor;
import com.example.farmogoapp.model.incidences.IncidenceWeight;
import com.example.farmogoapp.ui.main.SessionData;

import java.util.List;
import java.util.Optional;

public class IncidenceAdapter extends RecyclerView.Adapter<IncidenceAdapter.MyViewHolder> {
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

        public TextView incidence_pregnancy_animal_id;
        public TextView incidence_pregnancy_type;
        public TextView incidence_pregnancy_date;

        public TextView incidence_discharge_animal_id;
        public TextView incidence_discharge_type;
        public TextView incidence_discharge_health_register;
        TextView incidence_discharge_date;

        IncidenceType incidenceType;


        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView, IncidenceType incidenceType) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.incidenceType = incidenceType;
            switch (incidenceType) {
                case WEIGHT:
                    incidence_weight_animal_id = itemView.findViewById(R.id.incidence_weight_animal_id);
                    incidence_weight_type = itemView.findViewById(R.id.incidence_weight_type);
                    incidence_weight_weight = itemView.findViewById(R.id.incidence_weight_weight);
                    incidence_weight_date = itemView.findViewById(R.id.incidence_weight_date);
                    break;
                case TREATMENT:
                    incidence_treatment_animal_id = itemView.findViewById(R.id.incidence_treatment_animal_id);
                    incidence_treatment_type = itemView.findViewById(R.id.incidence_treatment_type);
                    incidence_treatment_medicine = itemView.findViewById(R.id.incidence_treatment_medicine);
                    incidence_treatment_date = itemView.findViewById(R.id.incidence_treatment_date);
                    break;
                case PREGNANCY:
                    incidence_pregnancy_animal_id = itemView.findViewById(R.id.incidence_pregnancy_animal_id);
                    incidence_pregnancy_type = itemView.findViewById(R.id.incidence_pregnancy_type);
                    incidence_pregnancy_date = itemView.findViewById(R.id.incidence_pregnancy_date);
                    break;
                case DISCHARGE:
                    incidence_discharge_animal_id = itemView.findViewById(R.id.incidence_discharge_animal_id);
                    incidence_discharge_type = itemView.findViewById(R.id.incidence_discharge_dischargeType);
                    incidence_discharge_health_register = (TextView) itemView.findViewById(R.id.incidence_discharge_health_register);
                    incidence_discharge_date = itemView.findViewById(R.id.incidence_discharge_date);
                    break;
                case BIRTH:
                    incidence_birth_animal_id = itemView.findViewById(R.id.incidence_birth_animal_id);
                    incidence_birth_type = itemView.findViewById(R.id.incidence_birth_type);
                    incidence_birth_sex = itemView.findViewById(R.id.incidence_birth_sex);
                    incidence_birth_date = itemView.findViewById(R.id.incidence_birth_date);
                    break;
            }

        }
    }
    public IncidenceAdapter(List<Incidence> incidences) {
        incidencesList = incidences;
    }

    @Override
    public int getItemViewType(int position) {
        return incidencesList.get(position).getType().ordinal();
    }

    @Override
    public IncidenceAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
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
        return new MyViewHolder(view, type);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final IncidenceAdapter.MyViewHolder viewHolder, int position) {
        final Incidence incidence1 = incidencesList.get(position);
        Log.d("Position", "Position: " + position + " - " + viewHolder.incidenceType.name());
        incidence1.accept(new IncidenceVisitor(){
                              @Override
                              public void visit(IncidenceDischarge obj) {

                                  TextView incidence_discharge_animal_id_tv = viewHolder.incidence_discharge_animal_id;
                                  Optional<Animal> oficialId = SessionData.getInstance().getAnimal(obj.getAnimalId());
                                  if(oficialId.isPresent()){
                                      incidence_discharge_animal_id_tv.setText(oficialId.get().getOfficialId());
                                  }

                                  TextView incidence_discharge_type_tv = viewHolder.incidence_discharge_type;
                                  incidence_discharge_type_tv.setText(obj.getDischargeType().toString());

                                  TextView incidence_discharge_health_register_tv = viewHolder.incidence_discharge_health_register;
                                  incidence_discharge_health_register_tv.setText(obj.getHealthRegister());

                                  TextView incidence_discharge_date_tv = viewHolder.incidence_discharge_date;
                                  incidence_discharge_date_tv.setText(obj.getDate().toString());

                              }

                              @Override
                              public void visit(IncidencePregnancy obj) {
                                  TextView incidence_pregnancy_animal_id_tv = viewHolder.incidence_pregnancy_animal_id;
                                  Optional<Animal> oficialId = SessionData.getInstance().getAnimal(obj.getAnimalId());
                                  if(oficialId.isPresent()) {
                                      incidence_pregnancy_animal_id_tv.setText(oficialId.get().getOfficialId());
                                  }

                                  TextView incidence_pregnancy_type_tv = viewHolder.incidence_pregnancy_type;
                                  incidence_pregnancy_type_tv.setText(obj.getPregnancyType().toString());

                                  TextView incidence_pregnancy_date_tv = viewHolder.incidence_pregnancy_date;
                                  incidence_pregnancy_date_tv.setText(obj.getDate().toString());
                              }

                              @Override
                              public void visit(IncidenceTreatment obj) {
                                  TextView animal_treatment_id_tv = viewHolder.incidence_treatment_animal_id;
                                  Optional<Animal> oficialId = SessionData.getInstance().getAnimal(obj.getAnimalId());
                                  if(oficialId.isPresent()) {
                                      animal_treatment_id_tv.setText(oficialId.get().getOfficialId());
                                  }

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
                                  Optional<Animal> oficialId = SessionData.getInstance().getAnimal(obj.getAnimalId());
                                  if(oficialId.isPresent()) {
                                      animal_weight_id_tv.setText(oficialId.get().getOfficialId());
                                  }


                                  TextView animal_weight_type_tv = viewHolder.incidence_weight_type;
                                  animal_weight_type_tv.setText(obj.getType().toString());

                                  TextView animal_weight_weight_tv = viewHolder.incidence_weight_weight;
                                  animal_weight_weight_tv.setText(String.valueOf(obj.getWeight()));

                                  TextView animal_weight_date_tv = viewHolder.incidence_weight_date;
                                  animal_weight_date_tv.setText(obj.getDate().toString());
                              }

                              @Override
                              public void visit(IncidenceBirth obj) {
                                  TextView incidence_birth_animal_id = viewHolder.incidence_birth_animal_id;
                                  Optional<Animal> oficialId = SessionData.getInstance().getAnimal(obj.getAnimalId());
                                  if(oficialId.isPresent()) {
                                      incidence_birth_animal_id.setText(oficialId.get().getOfficialId());
                                  }


                                  TextView incidence_birth_type = viewHolder.incidence_birth_type;
                                  incidence_birth_type.setText(obj.getType().toString());

                                  TextView incidence_birth_sex = viewHolder.incidence_birth_sex;
                                  incidence_birth_sex.setText(obj.getSex());

                                  TextView incidence_birth_date = viewHolder.incidence_birth_date;
                                  incidence_birth_date.setText(obj.getDate().toString());

                              }
            });
        }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return incidencesList.size();
    }

}
