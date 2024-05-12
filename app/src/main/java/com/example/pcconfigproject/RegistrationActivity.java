package com.example.pcconfigproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = RegistrationActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;
    EditText registrationNameText;
    EditText registrationEmail;
    EditText registrationPassword;
    EditText registrationPasswordCheck;
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secretKey != 99) {
            finish();
        }

        registrationNameText = findViewById(R.id.registrationNameText);
        registrationEmail = findViewById(R.id.registrationEmail);
        registrationPassword = findViewById(R.id.registrationPassword);
        registrationPasswordCheck = findViewById(R.id.registrationPasswordCheck);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userEmail = preferences.getString("userEmail", "");

        registrationEmail.setText(userEmail);

        mAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void register(View view) {
        String userName = registrationNameText.getText().toString();
        String userEmail = registrationEmail.getText().toString();
        String userPassword = registrationPassword.getText().toString();
        String userPasswordCheck = registrationPasswordCheck.getText().toString();

        if (!userPassword.equals(userPasswordCheck)) {
            Log.i(LOG_TAG, "A jelszavak nem egyeznek meg!");
            return;
        }

        Log.i(LOG_TAG, "Regisztrált: " + userName +  ", email: " + userEmail);

        //goList();

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully");
                    goList();
                } else {
                    Log.d(LOG_TAG, "User wasnt created successfully");
                    Toast.makeText(RegistrationActivity.this, "User wasnt created successfully: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void goList() {
        Intent intent = new Intent(this, ConfigListActivity.class);
        //intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}