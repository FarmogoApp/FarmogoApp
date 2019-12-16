package com.example.farmogoapp.ui.main.animallist;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Typeface.BOLD;

public class AnimalListAdapter extends RecyclerView.Adapter<AnimalListAdapter.MyViewHolder> {
    private List<Animal> animalsList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView nameTextView;
        ImageButton removeButton;
        ImageView animalImage;


        public MyViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = itemView.findViewById(R.id.animal_listid);
            removeButton = itemView.findViewById(R.id.animal_list_button);
            animalImage = itemView.findViewById(R.id.animal_list_image);
        }
    }


    public AnimalListAdapter() {
        this.animalsList = new ArrayList<>();
    }

    public void updateAnimalList(){
        this.animalsList = SessionData.getInstance().getAnimalCardObj();
        this.notifyDataSetChanged();
    }

    @Override
    public AnimalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View contactView = inflater.inflate(R.layout.recycler_view_animal_list, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(contactView);

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(AnimalListAdapter.MyViewHolder viewHolder, int position) {
        // Get the data model based on position
        Animal animal = animalsList.get(position);

        // Set item views based on your views and data model
        SpannableString spannableString = new SpannableString(animal.getOfficialId());
        spannableString
                .setSpan(new StyleSpan(BOLD),
                        spannableString.length() - 4,
                        spannableString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString
                .setSpan(new RelativeSizeSpan(2f),
                        spannableString.length() - 4,
                        spannableString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        viewHolder.nameTextView.setText(spannableString);

        viewHolder.removeButton.setOnClickListener(v -> {
            animalsList.remove(animal);
            SessionData.getInstance().removeAnimalFromCart(animal.getUuid());
            notifyDataSetChanged();
        });

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return animalsList.size();
    }

}