package com.example.farmogoapp.ui.main.animalInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;

import java.util.List;

public class animal_info_adapter extends RecyclerView.Adapter<animal_info_adapter.MyViewHolder> {
    private List<String> Animal_History;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView incidenceTextView;
        public TextView medicineTextView;
        public TextView dateTextView;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            incidenceTextView = (TextView) itemView.findViewById(R.id.incidence);
            medicineTextView = (TextView) itemView.findViewById(R.id.medicine);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
        }
    }
    public animal_info_adapter(List<HistoryInfo> history) {
        Animal_History = history;

    }

    @Override
    public animal_info_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_view_animal_list, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(animal_info_adapter.MyViewHolder viewHolder, int position) {
        // Get the data model based on position
        String animal_id= Animal_Id_List.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(animal_id);
        //Button button = viewHolder.messageButton;
       // button.setText(id_List.isOnline() ? "Message" : "Offline");
       // button.setEnabled(id_List.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return Animal_Id_List.size();
    }

}
