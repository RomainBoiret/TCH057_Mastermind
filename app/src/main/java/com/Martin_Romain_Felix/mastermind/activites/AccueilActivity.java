package com.Martin_Romain_Felix.mastermind.activites;

import android.content.Intent;
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

import com.Martin_Romain_Felix.mastermind.modele.Configurations;
import com.example.mastermind.R;

public class AccueilActivity extends AppCompatActivity {

    EditText inputCourriel;
    Button btnJouer;
    Button btnConfigurations;
    Button btnHistorique;
    static Configurations configurations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Chercher les boutons et le input de courriel
        inputCourriel = findViewById(R.id.inputCourriel);
        btnJouer = findViewById(R.id.btnJouer);
        btnConfigurations = findViewById(R.id.btnConfigurations);
        btnHistorique = findViewById(R.id.btnHistorique);

        //CONFIGURATIONS INITIALES
        configurations = new Configurations(4, 8, 10);

        //Démarrer activité jouer
        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Créer intention pour jouer SI le courriel n'est pas vide
                if (!inputCourriel.getText().toString().matches("")) {
                    //On lance l'activité du jeu
                    Intent intent = new Intent(getApplicationContext(), JeuActivity.class);
                    JeuActivity.courriel = inputCourriel.getText().toString();


                    startActivity(intent);
                }

                //sinon, mettre erreur
                else
                    Toast.makeText(AccueilActivity.this, "Vous devez inscrire votre courriel pour jouer", Toast.LENGTH_SHORT).show();
            }
        });

        //Démarrer activité Configurations
        btnConfigurations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Créer intention
                Intent intent = new Intent(getApplicationContext(), ConfigurationsActivity.class);
                startActivity(intent);
            }
        });


        //Démarrer activité historique
        btnHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Créer intention
                Intent intent = new Intent(getApplicationContext(), HistoriqueActivity.class);
                startActivity(intent);
            }
        });


    }
}