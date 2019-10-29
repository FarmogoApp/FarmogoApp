package com.example.farmogoapp.ui.main.farms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.model.FarmHistory;

import java.util.List;

public class farm_stats_adapter extends RecyclerView.Adapter<farm_stats_adapter.MyViewHolder> {
    private List<FarmHistory> Farm_History;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView animal_idTextView;
        public TextView incidenceTextView;
        public TextView medicineTextView;
        public TextView dateTextView;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            animal_idTextView = (TextView) itemView.findViewById(R.id.statistics_animal_id);
            incidenceTextView = (TextView) itemView.findViewById(R.id.statistics_animal_incidence);
            medicineTextView = (TextView) itemView.findViewById(R.id.statistics_animal_medicine);
            dateTextView = (TextView) itemView.findViewById(R.id.statistics_animal_date);
        }
    }
    public farm_stats_adapter(List<FarmHistory> farmhistory) {
        Farm_History = farmhistory;

    }

    @Override
    public farm_stats_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_view_statistics, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(farm_stats_adapter.MyViewHolder viewHolder, int position) {
        // Get the data model based on position
        String animal_id = Farm_History.get(position).getmAnimalId();
        String animal_incidence = Farm_History.get(position).getmIncidence();
        String animal_medicine = Farm_History.get(position).getmMedicine();
        String animal_date = Farm_History.get(position).getmDate();

        // Set item views based on your views and data model
        TextView textView = viewHolder.incidenceTextView;
        textView.setText(animal_incidence);
        TextView textView2 = viewHolder.medicineTextView;
        textView2.setText(animal_medicine);
        TextView textView3 = viewHolder.dateTextView;
        textView3.setText(animal_date);
        TextView textView4 = viewHolder.animal_idTextView;
        textView4.setText(animal_id);
        //Button button = viewHolder.messageButton;
       // button.setText(id_List.isOnline() ? "Message" : "Offline");
       // button.setEnabled(id_List.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return Farm_History.size();
    }

}
