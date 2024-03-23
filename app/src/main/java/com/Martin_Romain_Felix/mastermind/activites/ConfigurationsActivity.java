package com.Martin_Romain_Felix.mastermind.activites;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);

        lengthEditText = findViewById(R.id.lengthEditText);
        colorEditText = findViewById(R.id.colorEditText);
        attemptEditText = findViewById(R.id.attemptEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int length = Integer.parseInt(lengthEditText.getText().toString());
                int color = Integer.parseInt(colorEditText.getText().toString());
                int attempt = Integer.parseInt(attemptEditText.getText().toString());

                if (length < 2 || length > 6 || color < 2 || color > 8 || attempt < 8 || attempt > 12) {
                    Toast.makeText(ConfigurationsActivity.this, "Erreur des données", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConfigurationsActivity.this, "Les données sont valides", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}