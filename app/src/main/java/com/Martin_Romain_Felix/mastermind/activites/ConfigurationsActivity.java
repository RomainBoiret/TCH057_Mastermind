package com.Martin_Romain_Felix.mastermind.activites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;

public class ConfigurationsActivity extends AppCompatActivity {
    private EditText lengthEditText;
    private EditText colorEditText;
    private EditText attemptEditText;
    private Button saveButton;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);

        lengthEditText = findViewById(R.id.lengthEditText);
        colorEditText = findViewById(R.id.colorEditText);
        attemptEditText = findViewById(R.id.attemptEditText);
        saveButton = findViewById(R.id.saveButton);
        btnCancel = findViewById(R.id.btnCancel);

        //Bouton sauvegarder les infos
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Get les champs d'input
                String strLength = lengthEditText.getText().toString();
                String strColor = colorEditText.getText().toString();
                String strAttempt = attemptEditText.getText().toString();

                //Vérifier si les champs sont vides
                if (TextUtils.isEmpty(strLength) || TextUtils.isEmpty(strColor) || TextUtils.isEmpty(strAttempt)) {
                    Toast.makeText(ConfigurationsActivity.this, "Les champs ne peuvent pas être vides", Toast.LENGTH_SHORT).show();
                }

                //S'ils ne sont pas vides
                else {
                    int length = Integer.parseInt(strLength);
                    int color = Integer.parseInt(strColor);
                    int attempt = Integer.parseInt(strAttempt);

                    //On vérifie si les données sont correctes
                    if (length < 2 || length > 6 || color < 2 || color > 8 || attempt < 8 || attempt > 12) {
                        Toast.makeText(ConfigurationsActivity.this, "Les données entrées sont invalides!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ConfigurationsActivity.this, "Succès! Les configurations ont été changées.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Bouton annuler
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}