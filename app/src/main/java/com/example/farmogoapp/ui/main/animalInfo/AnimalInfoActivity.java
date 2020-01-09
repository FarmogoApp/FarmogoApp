package com.example.farmogoapp.ui.main.animalInfo;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.io.SessionData;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.Farm;
import com.example.farmogoapp.model.Race;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.ui.main.animalIncidence.AnimalIncidence;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;
import com.example.farmogoapp.ui.main.farms.IncidenceAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalInfoActivity extends AppCompatActivity {

    private Button btnList;
    private ImageButton btnAddRemove;
    private Button btnIncidences;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Switch nfcSwitch;
    private NfcAdapter nfcAdapter;
    private Animal animal;
    private Farm farm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAddRemove = findViewById(R.id.mas);
        btnIncidences = findViewById(R.id.IncidenciaAnimalInfo);

        if (getIntent().getAction() == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            loadAnimalDataFromNfc(getIntent());
        }

        if (getIntent().hasExtra("animalId")) {
            loadAnimalData(getIntent().getStringExtra("animalId"));
        }
        registerListeners();

        nfcSwitch = findViewById(R.id.writeNfc);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcSwitch.setEnabled(nfcAdapter != null);
        nfcSwitch.setChecked(false);
        loadHistoric(getIntent().getStringExtra("animalId"));
    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        enableNfcWriter();

    }

    @Override
    protected void onPause() {
        super.onPause();
        disableNfcWriter();
    }

    private void loadHistoric(String idAnimal) {
        Call<ArrayList<Incidence>> animalIncidence = FarmogoApiJacksonAdapter.getApiService().getAnimalIncidences(idAnimal);
        animalIncidence.enqueue(new Callback<ArrayList<Incidence>>() {
            @Override
            public void onResponse(Call<ArrayList<Incidence>> call, Response<ArrayList<Incidence>> response) {
                refreshRecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Incidence>> call, Throwable t) {

            }
        });
    }


    private void refreshRecyclerView(ArrayList<Incidence> lastIncidences) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_animalInfo);
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter mAdapter = new IncidenceAdapter(lastIncidences, getApplicationContext().getApplicationContext(),true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void enableNfcWriter() {
        if (nfcAdapter != null) {
            Intent i = new Intent(this, this.getClass());
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            IntentFilter[] fillterArr = new IntentFilter[]{};
            nfcAdapter.enableForegroundDispatch(this, pIntent, fillterArr, null);
        }
    }

    private void disableNfcWriter() {
        if (nfcAdapter != null)
            nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("intent", "new inten.  NFC detected " + intent.getAction());
        if (!nfcSwitch.isChecked()) return;
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            writeTag(intent);
        }

    }

    private void writeTag(Intent intent) {
        Tag myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(myTag);
        try {
            Log.d("intent", "connection to tag");
            ndef.connect();
            NdefMessage message;
            NdefRecord[] records = new NdefRecord[1];
            records[0] = NdefRecord.createUri("farmogo://animal/" + animal.getUuid());
            message = new NdefMessage(records);
            ndef.writeNdefMessage(message);
            ndef.close();
            nfcSwitch.setChecked(false);
            Toast.makeText(this, "Tag has been written", Toast.LENGTH_SHORT).show();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFarmData(String farmId) {
        Call<Farm> farmCall = FarmogoApiJacksonAdapter.getApiService().getFarm(farmId);

        farmCall.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                Farm data = response.body();

                if(data != null){
                    updateFarm(data);
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {
                t.printStackTrace();
                Log.e("AnimalInfoActivity","farm error" );
            }
        });
    }

    private void updateFarm(Farm data) {
        this.farm = data;
        TextView farmtv = findViewById(R.id.farmNumber_exemple);
        farmtv.setText(this.farm.getOfficialId());
    }

    private void loadAnimalDataFromNfc(Intent intent) {
        loadAnimalData(intent.getData().getPathSegments().get(0));
    }

    private void loadAnimalData(String idAnimal) {
        updateViewWithAnimal(SessionData.getInstance().getAnimal(idAnimal).orElse(new Animal()));
    }

    public void updateViewWithAnimal(Animal animal) {
        this.animal = animal;
        TextView officialId = findViewById(R.id.id_example);
        TextView sex = findViewById(R.id.genere_example);
        TextView race = findViewById(R.id.raza_exemple);

        TextView mother = findViewById(R.id.mother_exemple);
        officialId.setText(animal.getOfficialId());

        String[] stringArray = getResources().getStringArray(R.array.sexs);
        sex.setText(stringArray[animal.getSex().equals("Male") ? 0 : 1]);

        SessionData instance = SessionData.getInstance();
        Optional<Race> race1 = instance.getRace(animal.getRaceId());
        loadFarmData(this.animal.getFarmId());

        race.setText(race1.orElse(new Race()).getName());
        mother.setText(animal.getMotherOfficialId());
        btnAddRemove.setImageResource( animal.isSelected()? android.R.drawable.ic_menu_delete : android.R.drawable.ic_menu_add );

        //TODO: update list of incidences

    }


    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListeners() {
        btnAddRemove.setOnClickListener(v -> {

            if (animal.isSelected()){
                SessionData.getInstance().removeAnimalFromCart(animal.getUuid());
                animal.setSelected(false);
                btnAddRemove.setImageResource(android.R.drawable.ic_menu_add);
            }else{
                SessionData.getInstance().addAnimalToCart(animal.getUuid());
                animal.setSelected(true);
                btnAddRemove.setImageResource(android.R.drawable.ic_menu_delete);

            }
            invalidateOptionsMenu();
        });

        btnIncidences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AnimalInfoActivity.this, AnimalIncidence.class);
                intent.putExtra("incidenceType", (Integer) 1);
                intent.putExtra("animalId", (String) animal.getUuid());
                intent.putExtra("animalOfficialId", (String) animal.getOfficialId());
                intent.putExtra("farmId", (String) animal.getFarmId());
                intent.putExtra("farmAnimalCounter", (String) farm.getAnimalCounter().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animalinfo, menu);

        MenuItem list = menu.findItem(R.id.list_selected);
        if(!SessionData.getInstance().getAnimalCardObj().isEmpty()) {
            list.setVisible(true);
        }else {
            list.setVisible(false);
        }
        TextView txtCount = list.getActionView().findViewById(R.id.cart_badge);
        if(!SessionData.getInstance().getAnimalCardObj().isEmpty()){
            txtCount.setText(String.valueOf(SessionData.getInstance().getAnimalCardObj().size()));
        }

        return true;
    }

    public void goToList(View view){
        Intent intent = new Intent(AnimalInfoActivity.this, AnimalListActivity.class);
        startActivity(intent);
    }

}
