package com.example.farmogoapp.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;

import java.util.List;

public class animal_list_adapter extends RecyclerView.Adapter<animal_list_adapter.MyViewHolder> {
    private List<String> Animal_Id_List;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageButton messageButton;
        public ImageView animalImage;

        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.animal_listid);
            messageButton = (ImageButton) itemView.findViewById(R.id.animal_list_button);
            animalImage = (ImageView) itemView.findViewById(R.id.animal_list_image);
        }
    }
    public animal_list_adapter(List<String> IdList) {
        Animal_Id_List = IdList;

    }

    @Override
    public animal_list_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(animal_list_adapter.MyViewHolder viewHolder, int position) {
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
