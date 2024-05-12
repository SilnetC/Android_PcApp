package com.example.pcconfigproject;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateConfigActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = CreateConfigActivity.class.getName();
    private FirebaseFirestore mFirestore;
    private CollectionReference mConfigurations;
    private NotificationHandler mNotificationHandler;

    EditText configName;
    Spinner cpuList;
    Spinner moboSpinner;
    Spinner ramSpinner;
    Spinner gpuSpinner;
    Spinner psuSpinner;
    Spinner pcCaseSpinner;
    Spinner ssdSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_config);

        configName = findViewById(R.id.newConfigNameText);
        cpuList = findViewById(R.id.cpuSpinner);
        moboSpinner = findViewById(R.id.moboSpinner);
        ramSpinner = findViewById(R.id.ramSpinner);
        gpuSpinner = findViewById(R.id.gpuSpinner);
        psuSpinner = findViewById(R.id.psuSpinner);
        pcCaseSpinner = findViewById(R.id.pcCaseSpinner);
        ssdSpinner = findViewById(R.id.ssdSpinner);

        // Spinner inicializalas
        cpuList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> CPUadapter = ArrayAdapter.createFromResource(this, R.array.cpuList, android.R.layout.simple_spinner_item);
        CPUadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cpuList.setAdapter(CPUadapter);

        moboSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> MoboAdapter = ArrayAdapter.createFromResource(this, R.array.moboList, android.R.layout.simple_spinner_item);
        MoboAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moboSpinner.setAdapter(MoboAdapter);

        ramSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> ramAdapter = ArrayAdapter.createFromResource(this, R.array.ramList, android.R.layout.simple_spinner_item);
        ramAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ramSpinner.setAdapter(ramAdapter);

        gpuSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> gpuAdapter = ArrayAdapter.createFromResource(this, R.array.gpuList, android.R.layout.simple_spinner_item);
        gpuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gpuSpinner.setAdapter(gpuAdapter);

        psuSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> psuAdapter = ArrayAdapter.createFromResource(this, R.array.psuList, android.R.layout.simple_spinner_item);
        psuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psuSpinner.setAdapter(psuAdapter);

        pcCaseSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> caseAdapter = ArrayAdapter.createFromResource(this, R.array.pcCaseList, android.R.layout.simple_spinner_item);
        caseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pcCaseSpinner.setAdapter(caseAdapter);

        ssdSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> ssdAdapter = ArrayAdapter.createFromResource(this, R.array.ssdList, android.R.layout.simple_spinner_item);
        ssdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ssdSpinner.setAdapter(ssdAdapter);

        // Firebase
        mFirestore = FirebaseFirestore.getInstance();
        mConfigurations = mFirestore.collection("Configurations");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCpu = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, "CPU: " + selectedCpu);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveConfig(View view) {
        String newconfigName = configName.getText().toString();
        String cpuType = cpuList.getSelectedItem().toString();
        String moboType = moboSpinner.getSelectedItem().toString();
        String ramType = ramSpinner.getSelectedItem().toString();
        String gpuType = gpuSpinner.getSelectedItem().toString();
        String psuType = psuSpinner.getSelectedItem().toString();
        String caseType = pcCaseSpinner.getSelectedItem().toString();
        String ssdType = ssdSpinner.getSelectedItem().toString();
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.configImages);
        Log.i(LOG_TAG, "Nev: " + newconfigName + ", cpu: " + cpuType + ", alaplap: " + moboType + ", ram: " + ramType);

        mConfigurations.add(new ConfigItem(newconfigName, cpuType, moboType, ramType, gpuType, psuType, caseType, ssdType, itemsImageResource.getResourceId(0, 0)));
        mNotificationHandler = new NotificationHandler(this);
        mNotificationHandler.send("New config created successfully!");
        //ConfigListActivity nc = new ConfigListActivity();
        //nc.queryData();
        finish();
    }

    public void back(View view) {
        finish();
    }
}