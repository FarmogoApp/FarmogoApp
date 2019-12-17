package com.example.farmogoapp.ui.main.searchanimal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.ui.main.animalInfo.AnimalInfoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static android.graphics.Typeface.BOLD;

public class SearchAnimalsAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private List<Animal> animalList;
    private List<Animal> animalListAvailable;
    private List<Animal> animalListVisible;
    private String farmId;
    private boolean allFarms;
    private boolean allAnimals;


    public SearchAnimalsAdapter(Activity activity, String farmId) {
        this.activity = activity;
        animalList = new ArrayList<>();
        animalListVisible = new ArrayList<>();
        this.farmId = farmId;
    }

    public void updateAnimals() {
        this.animalList = SessionData.getInstance().getAnimals();
        updateList();
    }

    public void updateList() {
        Stream<Animal> stream = animalList.stream();

        if (!allAnimals) {
            stream = stream.filter(a -> a.getDischargeDate() == null);
        }
        if (!allFarms) {
            stream = stream.filter(a -> farmId.equals(a.getFarmId()));
        }

        this.animalListAvailable = stream.collect(Collectors.toList());
        this.animalListVisible = this.animalListAvailable;

        this.notifyDataSetChanged();
    }

    public boolean isAllFarms() {
        return allFarms;
    }

    public void setAllFarms(boolean allFarms) {
        this.allFarms = allFarms;
    }

    public void setAllAnimals(boolean isChecked) {
        this.allAnimals = isChecked;
    }

    @Override
    public int getCount() {
        return animalListVisible.size();
    }

    @Override
    public Object getItem(int position) {
        return animalListVisible.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.searchanimal_itemlist, null);
        }
        final View row = v;

        final Animal animal = animalListVisible.get(position);
        v.setTag(animal.getUuid());
        TextView listid = v.findViewById(R.id.animal_listid);

        final ImageButton button = v.findViewById(R.id.animal_list_button);

        if (animal.isSelected()) {
        }

        button.setImageResource(animal.isSelected() ? android.R.drawable.ic_menu_delete : android.R.drawable.ic_menu_add);


        button.setOnClickListener(buttonView -> {
            if (animal.isSelected()) {
                SessionData.getInstance().removeAnimalFromCart(animal.getUuid());
                animal.setSelected(false);
                button.setImageResource(android.R.drawable.ic_menu_add);
                row.setBackgroundColor(activity.getColor(R.color.colorText));
            } else {
                SessionData.getInstance().addAnimalToCart(animal.getUuid());
                animal.setSelected(true);
                button.setImageResource(android.R.drawable.ic_menu_delete);
                row.setBackgroundColor(activity.getColor(R.color.colorSecondary));
            }

        });


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

        listid.setText(spannableString);
        v.setOnClickListener(this);
        return v;
    }

    public void setFilter(CharSequence text) {
        animalListVisible = new ArrayList<>();
        for (Animal animal : animalListAvailable) {
            String officialId = animal.getOfficialId();
            if (officialId.substring(2).startsWith(text.toString()) ||
                    officialId.substring(officialId.length() - 4).startsWith(text.toString())) {

                animalListVisible.add(animal);
            }
        }
        this.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, AnimalInfoActivity.class);
        intent.putExtra("animalId", (String) v.getTag());
        activity.startActivity(intent);
    }


}
