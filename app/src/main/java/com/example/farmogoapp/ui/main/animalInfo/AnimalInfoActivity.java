package com.example.farmogoapp.ui.main.animalInfo;

import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.FarmogoApiJacksonAdapter;
import com.example.farmogoapp.model.Animal;
import com.example.farmogoapp.model.HistoryInfo;
import com.example.farmogoapp.model.incidences.Incidence;
import com.example.farmogoapp.ui.main.animalIncidence.AnimalIncidence;
import com.example.farmogoapp.ui.main.animallist.AnimalListActivity;
import com.example.farmogoapp.ui.main.registerAnimal.RegisterCowActivity;
import com.example.farmogoapp.ui.main.searchanimal.SeachAnimalsActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalInfoActivity extends AppCompatActivity {

    private Button btnList;
    private boolean state;
    private ImageButton btnAddRemove;
    private Button btnIncidences;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Switch nfcSwitch;
    private NfcAdapter nfcAdapter;
    private Animal animal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_info);

        if (getIntent().getAction() == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            loadAnimalDataFromNfc(getIntent());
        }

        if (getIntent().hasExtra("animalId")) {
            loadAnimalData(getIntent().getStringExtra("animalId"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAddRemove = findViewById(R.id.mas);
        btnIncidences = findViewById(R.id.IncidenciaAnimalInfo);
        state = true;

        ArrayList<HistoryInfo> animal_History = new ArrayList<>();
        HistoryInfo historyInfo1 = new HistoryInfo("dolor", "Selevit", "28/10/2019");
        HistoryInfo historyInfo2 = new HistoryInfo("dolor", "Selevit", "28/10/2019");
        animal_History.add(historyInfo1);
        animal_History.add(historyInfo2);

        recyclerView = findViewById(R.id.recyclerview_animalInfo);
        recyclerView.setHasFixedSize(true);
        mAdapter = new animal_info_adapter(animal_History);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerListeners();

        nfcSwitch = findViewById(R.id.writeNfc);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcSwitch.setEnabled(nfcAdapter != null);
        nfcSwitch.setChecked(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        enableNfcWriter();

    }

    @Override
    protected void onPause() {
        super.onPause();
        disableNfcWriter();
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
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
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


    private void loadAnimalDataFromNfc(Intent intent) {
        loadAnimalData(intent.getData().getPathSegments().get(0));
    }

    private void loadAnimalData(String idAnimal) {
        final Call<Animal> animal = FarmogoApiJacksonAdapter.getApiService(this).getAnimal(idAnimal);
        animal.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                updateViewWithAnimal(response.body());
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AnimalInfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateViewWithAnimal(Animal animal) {
        this.animal = animal;
        TextView officialId = findViewById(R.id.id_example);
        TextView sex = findViewById(R.id.genere_example);
        TextView race = findViewById(R.id.raza_exemple);
        TextView farm = findViewById(R.id.farmNumber_exemple);
        TextView mother = findViewById(R.id.mother_exemple);
        officialId.setText(animal.getOfficialId());
        sex.setText(animal.getSex());
        race.setText(animal.getRaceId()); // TODO: get race
        farm.setText(animal.getFarmId()); // TODO: get farm
        mother.setText(animal.getMotherId()); // TODO: get official id of mother

        //TODO: update list of incidences

    }


    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void registerListeners() {
        btnAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state) {
                    btnAddRemove.setImageResource(android.R.drawable.ic_menu_delete);
                } else {
                    btnAddRemove.setImageResource(android.R.drawable.ic_menu_add);
                }
                state = !state;
            }
        });

        btnIncidences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalInfoActivity.this, AnimalIncidence.class);
                intent.putExtra("animalId", (String) animal.getUuid());
                intent.putExtra("animalOfficialId", (String) animal.getOfficialId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animalinfo, menu);

        MenuItem list = menu.findItem(R.id.list_selected);
        list.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(AnimalInfoActivity.this, AnimalListActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }

}
