package com.example.pcconfigproject;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ConfigListActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private static final String LOG_TAG = ConfigListActivity.class.getName();
    private FirebaseUser user;
    private RecyclerView configRecyclerView;
    private ArrayList<ConfigItem> configItemList;
    private ConfigItemAdapter mAdapter;
    private int gridNumber = 1;

    private FirebaseFirestore mFirestore;
    private CollectionReference mConfigurations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        configRecyclerView = findViewById(R.id.configListRecyclerView);
        configRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));

        configItemList = new ArrayList<>();

        mAdapter = new ConfigItemAdapter(this, configItemList);
        configRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mConfigurations = mFirestore.collection("Configurations");

        //queryData();

        //initializeData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryData();
    }

    public void queryData() {
        configItemList.clear();

        mConfigurations.orderBy("name").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ConfigItem item = document.toObject(ConfigItem.class);
                item.setId(document.getId());
                configItemList.add(item);
            }
            if (configItemList.size() == 0) {
                initializeData();
                queryData();
            }
            //adatok letoltese az adatbazisbol
            mAdapter.notifyDataSetChanged();
        });
    }

    public void deleteConfig(ConfigItem item) {
        DocumentReference ref = mConfigurations.document(item._getId());

        ref.delete().addOnSuccessListener(success -> {
            Log.d(LOG_TAG, "Config is successfully deleted: " + item._getId());
        }).addOnFailureListener(faliure -> {
            Toast.makeText(this, "Config " + item._getId() + " cannot be deleted.", Toast.LENGTH_LONG).show();
        });

        queryData();
    }

    public void updateItem(ConfigItem item) {
        Intent intent = new Intent(this, EditConfigActivity.class);
        intent.putExtra("item_id",item._getId());
        intent.putExtra("item_name", item.getName());
        intent.putExtra("item_cpu", item.getCpu());
        intent.putExtra("item_mobo", item.getMobo());
        intent.putExtra("item_ram", item.getRam());
        intent.putExtra("item_gpu", item.getGpu());
        intent.putExtra("item_psu", item.getPsu());
        intent.putExtra("item_case", item.getPcCase());
        intent.putExtra("item_ssd", item.getSsd());

        startActivity(intent);
    }

    private void initializeData() {
        String[] configsList = getResources().getStringArray(R.array.configNames);
        String[] configsCpu = getResources().getStringArray(R.array.configCpu);
        String[] configsMobo = getResources().getStringArray(R.array.configMobo);
        String[] configsRam = getResources().getStringArray(R.array.configRam);
        String[] configsGpu = getResources().getStringArray(R.array.configGpu);
        String[] configsPsu = getResources().getStringArray(R.array.configPsu);
        String[] configsPcCase = getResources().getStringArray(R.array.configPcCase);
        String[] configsSsd = getResources().getStringArray(R.array.configSsd);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.configImages);

        //configItemList.clear();

        for (int i = 0; i < configsList.length; i++) {
            mConfigurations.add(new ConfigItem(configsList[i], configsCpu[i], configsMobo[i], configsRam[i], configsGpu[i], configsPsu[i], configsPcCase[i], configsSsd[i], itemsImageResource.getResourceId(i, 0)));
            //configItemList.add(new ConfigItem(configsList[i], configsCpu[i], configsMobo[i], configsRam[i], configsGpu[i], configsPsu[i], configsPcCase[i], configsSsd[i], itemsImageResource.getResourceId(i, 0)));
        }
        itemsImageResource.recycle();
        //mAdapter.notifyDataSetChanged();

    }


    public void addNewConfig(View view) {
        Log.d(LOG_TAG, "Add button click");
        Intent intent = new Intent(this, CreateConfigActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void compareConfigs(View view) {
        Log.d(LOG_TAG, "Compare button click");
        Intent intent = new Intent(this, CompareActivity.class);
        startActivity(intent);
    }

    public void accountLogout(View view) {
        Log.d(LOG_TAG, "Logout button click");
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}