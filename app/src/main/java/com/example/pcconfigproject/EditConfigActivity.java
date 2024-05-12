package com.example.pcconfigproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditConfigActivity extends AppCompatActivity {
    private static final String LOG_TAG = EditConfigActivity.class.getName();
    private FirebaseFirestore mFirestore;
    private CollectionReference mConfigurations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_config);

        // Firebase
        mFirestore = FirebaseFirestore.getInstance();
        mConfigurations = mFirestore.collection("Configurations");

        String configName = getIntent().getStringExtra("item_name");
        String configCpu = getIntent().getStringExtra("item_cpu");
        String configMobo = getIntent().getStringExtra("item_mobo");
        String configRam = getIntent().getStringExtra("item_ram");
        String configGpu = getIntent().getStringExtra("item_gpu");
        String configPsu = getIntent().getStringExtra("item_psu");
        String configCase = getIntent().getStringExtra("item_case");
        String configSsd = getIntent().getStringExtra("item_ssd");

        configCpu = "Processzor: " + configCpu;
        configMobo = "Alaplap: " + configMobo;
        configRam = "Mamória: " + configRam;
        configGpu = "Videókártya: " + configGpu;
        configPsu = "Tápegység: " + configPsu;
        configCase = "Gépház: " + configCase;
        configSsd = "SSD: " + configSsd;


        EditText nameText = findViewById(R.id.nameText);
        TextView cpuText = findViewById(R.id.configCpu);
        TextView moboText = findViewById(R.id.configMobo);
        TextView ramText = findViewById(R.id.configRam);
        TextView gpuText = findViewById(R.id.configGpu);
        TextView psuText = findViewById(R.id.configTap);
        TextView caseText = findViewById(R.id.configCase);
        TextView ssdText = findViewById(R.id.configSsd);

        nameText.setText(configName);
        cpuText.setText(configCpu);
        moboText.setText(configMobo);
        ramText.setText(configRam);
        gpuText.setText(configGpu);
        psuText.setText(configPsu);
        caseText.setText(configCase);
        ssdText.setText(configSsd);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cancel(View view) {
        finish();
    }

    public void update(View view) {
        String configId = getIntent().getStringExtra("item_id");
        EditText configName = findViewById(R.id.nameText);
        String newConfigName = configName.getText().toString();

        mConfigurations.document(configId)
                .update("name", newConfigName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "Dokumentum sikeresen frissítve!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Hiba a dokumentum frissítése közben", e);
                    }
                });
        finish();
    }
}