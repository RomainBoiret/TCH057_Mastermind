package com.Martin_Romain_Felix.mastermind.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mastermind.R;

public class ConfigurationsActivity extends AppCompatActivity {
    private EditText txtLongueur;
    private EditText txtNbCouleurs;
    private EditText txtNbTentatives;
    private Button btnEnregistrer;
    private Button btnAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);

        txtLongueur = findViewById(R.id.txtLongueur);
        txtNbCouleurs = findViewById(R.id.txtNbCouleurs);
        txtNbTentatives = findViewById(R.id.txtNbTentatives);
        btnEnregistrer = findViewById(R.id.btnEnregistrer);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        // Bouton sauvegarder les infos
        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Get les champs d'input
                String Longueur = txtLongueur.getText().toString();
                String NbCouleurs = txtNbCouleurs.getText().toString();
                String NbTentatives = txtNbTentatives.getText().toString();

                // Vérifier si les champs sont vides
                if (TextUtils.isEmpty(Longueur) || TextUtils.isEmpty(NbCouleurs) || TextUtils.isEmpty(NbTentatives)) {
                    Toast.makeText(ConfigurationsActivity.this, "Les champs ne peuvent pas être vides", Toast.LENGTH_SHORT).show();
                }

                // S'ils ne sont pas vides
                else {
                    int longueur = Integer.parseInt(Longueur);
                    int couleurs = Integer.parseInt(NbCouleurs);
                    int tentatives = Integer.parseInt(NbTentatives);

                    // On vérifie si les données sont correctes
                    if (longueur < 2 || longueur > 6 || couleurs < 2 || couleurs > 8 || tentatives < 8 || tentatives > 12)
                    {
                        Toast.makeText(ConfigurationsActivity.this, "Les données entrées sont invalides!", Toast.LENGTH_SHORT).show();

                    } else {

                        //Mettre les nouvelles configurations
                        AccueilActivity.configurations.setLongueur(longueur);
                        AccueilActivity.configurations.setNbCouleurs(couleurs);
                        AccueilActivity.configurations.setNbTentatives(tentatives);

                        Toast.makeText(ConfigurationsActivity.this, "Succès! Les configurations ont été changées.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        // Bouton annuler
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
