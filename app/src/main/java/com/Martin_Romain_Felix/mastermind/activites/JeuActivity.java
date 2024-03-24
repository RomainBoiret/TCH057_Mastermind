package com.Martin_Romain_Felix.mastermind.activites;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;

public class JeuActivity extends AppCompatActivity {


    private ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jeu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Chercher le bouton menu
        menu = findViewById(R.id.menu);

        //Ouvrir/fermer le menu quand on appuie dessus
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirMenu();
            }
        });
    }

    //Fonction ouvrir menu
    private void ouvrirMenu() {
        //Créer le menu
        Dialog popupMenu = new Dialog(this);
        popupMenu.setContentView(R.layout.layout_popup_jeu);
        popupMenu.show();

        //Chercher les boutons DANS la popup
        Button btnAbandonner = popupMenu.findViewById(R.id.btnAbandonner);
        Button btnNouvellePartie = popupMenu.findViewById(R.id.btnNouvellePartie);
        Button btnRetourAccueil = popupMenu.findViewById(R.id.btnRetourAccueil);


        //Clic sur bouton nouvelle partie
        btnNouvellePartie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                //À FAIRE
                //




            }
        });

        //Clic sur bouton abandonner
        btnAbandonner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ouvrir autre popup pour confirmer si le joueur veut vraiment annuler
                popupMenu.dismiss();

                //Ouvrir menu abandon
                ouvrirMenuAbandon();
            }
        });

        //Clic sur bouton retour à l'accueil
        btnRetourAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ferme la popup et on finit l'activité
                popupMenu.dismiss();
                finish();
            }
        });

    }


    //Fonction qui ouvre la 2ème popup pour abandonner
    public void ouvrirMenuAbandon() {
        //Créer le menu popup
        Dialog confirmerAbandon = new Dialog(this);
        confirmerAbandon.setContentView(R.layout.layout_popup_abandonner);
        confirmerAbandon.show();

        //Chercher les boutons DANS la popup
        Button btnAnnulerAbandon = confirmerAbandon.findViewById(R.id.btnAnnulerAbandon);
        Button btnConfirmerAbandon = confirmerAbandon.findViewById(R.id.btnConfirmerAbandon);

        //Clic sur annuler
        btnAnnulerAbandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On ferme la popup et on reouvre le menu de pause
                confirmerAbandon.dismiss();
                ouvrirMenu();
            }
        });

        //Clic sur abandonner
        btnConfirmerAbandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //À FAIRE
                //



            }
        });
    }

}