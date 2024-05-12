package com.example.pcconfigproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CompareActivity extends AppCompatActivity {
    private static final String LOG_TAG = CompareActivity.class.getName();
    private Spinner spinner1, spinner2;
    private FirebaseFirestore mFirestore;
    private CollectionReference mConfigurations;
    private ArrayList<String> configNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compare);

        spinner1 = findViewById(R.id.config1Spinner);
        spinner2 = findViewById(R.id.config2Spinner);

        mFirestore = FirebaseFirestore.getInstance();
        mConfigurations = mFirestore.collection("Configurations");

        configNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, configNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        queryData();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void queryData() {
        mConfigurations.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String name = document.getString("name");
                configNames.add(name);
            }
            adapter.notifyDataSetChanged();
        });
    }

    public void compareConfig(View view) {
        String configName1 = spinner1.getSelectedItem().toString();
        String configName2 = spinner2.getSelectedItem().toString();

        TextView cpu1 = findViewById(R.id.configCpu1);
        TextView cpu2 = findViewById(R.id.configCpu2);

        TextView mobo1 = findViewById(R.id.configMobo1);
        TextView mobo2 = findViewById(R.id.configMobo2);

        TextView ram1 = findViewById(R.id.configRam1);
        TextView ram2 = findViewById(R.id.configRam2);

        TextView gpu1 = findViewById(R.id.configGpu1);
        TextView gpu2 = findViewById(R.id.configGpu2);

        TextView psu1 = findViewById(R.id.configTap1);
        TextView psu2 = findViewById(R.id.configTap2);

        TextView case1 = findViewById(R.id.configCase1);
        TextView case2 = findViewById(R.id.configCase2);

        TextView ssd1 = findViewById(R.id.configSsd1);
        TextView ssd2 = findViewById(R.id.configSsd2);

        mConfigurations.whereEqualTo("name", configName1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ConfigItem item = document.toObject(ConfigItem.class);
                cpu1.setText(item.getCpu());
                mobo1.setText(item.getMobo());
                ram1.setText(item.getRam());
                gpu1.setText(item.getGpu());
                psu1.setText(item.getPsu());
                case1.setText(item.getPcCase());
                ssd1.setText(item.getSsd());
                //Log.d(LOG_TAG, "Config1: " + item.getCpu());
            }
        });

        mConfigurations.whereEqualTo("name", configName2).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ConfigItem item = document.toObject(ConfigItem.class);
                cpu2.setText(item.getCpu());
                mobo2.setText(item.getMobo());
                ram2.setText(item.getRam());
                gpu2.setText(item.getGpu());
                psu2.setText(item.getPsu());
                case2.setText(item.getPcCase());
                ssd2.setText(item.getSsd());
                Log.d(LOG_TAG, "Config2: " + item.getCpu());
            }
        });

    }

    public void cancel(View view) {
        finish();
    }
}