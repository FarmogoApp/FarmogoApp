package com.example.farmogoapp.ui.main.animallist;

import android.annotation.SuppressLint;
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
import com.example.farmogoapp.model.Animal;

import java.util.List;

import static android.graphics.Typeface.BOLD;

public class animal_list_adapter extends RecyclerView.Adapter<animal_list_adapter.MyViewHolder> {
    private List<Animal> Animal_Id_List;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public Animal animal;
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
    public animal_list_adapter(List<Animal> IdList) {
        Animal_Id_List = IdList;

    }

    @Override
    public animal_list_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View contactView = inflater.inflate(R.layout.recycler_view_animal_list, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(contactView);
        ImageButton removeButton = contactView.findViewById(R.id.animal_list_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Animal_Id_List.remove(viewHolder.animal);
               notifyDataSetChanged();
            }
        });

        // Return a new holder instance

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(animal_list_adapter.MyViewHolder viewHolder, int position) {
        // Get the data model based on position
        Animal animal_id= Animal_Id_List.get(position);
        viewHolder.animal = animal_id;
        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        SpannableString spannableString = new SpannableString(animal_id.getOfficialId());
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

        textView.setText(spannableString);

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
