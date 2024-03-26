package com.Martin_Romain_Felix.mastermind.activites;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.Martin_Romain_Felix.mastermind.dao.Partie;
import com.Martin_Romain_Felix.mastermind.modele.Configurations;
import com.Martin_Romain_Felix.mastermind.modele.Couleurs;
import com.example.mastermind.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class JeuActivity extends AppCompatActivity {
    //Attributs éléments graphiques
    private ImageView menu;
    private GridLayout grilleJeu;
    private GridLayout grillePalette;
    private GridLayout grilleFeedback;

    //Attributs partie
    private Partie partie;
    private Configurations configurations;
    final String TAG = "MesMessages";
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";

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

        //Ouvrir/fermer le menu quand on appuie dessus
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirMenu();
            }
        });

        configurations = AccueilActivity.configurations;

        int longueur = configurations.getLongueur();
        int couleurs = configurations.getNbCouleurs();
        int tentatives = configurations.getNbTentatives();

        //GRILLE DE JEU
        grilleJeu = findViewById(R.id.gridJeu);
        grilleJeu.setColumnCount(longueur);
        grilleJeu.setRowCount(tentatives);

        for (int i = 0; i < longueur*tentatives; i++) {
            Button btn = new Button(this);
            btn.setBackground(getDrawable(R.drawable.bouton_rond));

            grilleJeu.addView(btn);

            ViewGroup.LayoutParams params;
            params = btn.getLayoutParams();
            params.width = 90;
            params.height = 90;
            ((ViewGroup.MarginLayoutParams)params).setMargins(10,5,10,5);
        }

        //GRILLE FEEDBACK
        grilleFeedback = findViewById(R.id.gridFeedback);
        grilleFeedback.setColumnCount(1);
        grilleFeedback.setRowCount(tentatives);


        for (int i = 0; i < tentatives; i++) {
            Button btn = new Button(this);
            btn.setBackground(getDrawable(R.drawable.bouton_rond));

            grilleFeedback.addView(btn);

            ViewGroup.LayoutParams params;
            params = btn.getLayoutParams();
            params.width = 90;
            params.height = 90;
            ((ViewGroup.MarginLayoutParams)params).setMargins(10,5,10,5);
        }

        //GRILLE PALETTE
        grillePalette = findViewById(R.id.gridPalette);
        grillePalette.setColumnCount(4);
        grillePalette.setRowCount(2);

        for (int i = 0; i < couleurs; i++) {
            Button btn = new Button(this);
            btn.setBackground(getDrawable(R.drawable.bouton_rond));
            btn.getBackground().setTint(Couleurs.couleurs[i]);

            grillePalette.addView(btn);

            ViewGroup.LayoutParams params;
            params = btn.getLayoutParams();
            params.width = 110;
            params.height = 110;
            ((ViewGroup.MarginLayoutParams)params).setMargins(10,5,10,5);
        }



        //Créer une partie de Mastermind
        partie = new Partie("test@hotmail.com", "ffffa500ffffa500ffffa500ffffa500", couleurs, "", 0);

        //-------------------CRÉER PARTIE DE MASTERMIND----------------

        //D'abord chercher un code secret
        try {
            chercherCodeSecret();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }

        //Mastermind partie = new Mastermind();
        //   À FAIRE...







    }


    //-----------------------------FONCTION CHERCHER UN CODE SECRET------------------------------
    private void chercherCodeSecret() throws IOException {
        //Prendre un indice code au hasard parmi les 450 codes
        Random rand = new Random();
        int indexRandomCode = rand.nextInt(450);

        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder()
                .url(URL_POINT_ENTREE + "/codesSecrets/" + indexRandomCode)
                .build();

        new Thread() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(requete).execute();
                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();
                    Log.i(TAG, "Code Mastermind: " + jsonData);

                    JeuActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //Créer un tableau JSON avec nos données
                                JSONObject jsonCode = new JSONObject(jsonData);
                                Log.i(TAG, "Objet JSON " + jsonCode);

                                //Chercher l'objet JSON à l'indice et prendre le tableau
                                //qui correspond au code
                                JSONArray tableauCode = jsonCode.getJSONArray("code");
                                String[] codeSecret = new String[tableauCode.length()];

                                for(int i = 0; i < tableauCode.length(); i++){
                                    codeSecret[i] = tableauCode.getString(i);
                                }

                                //Chercher l'ID du code secret et le nb de couleurs
                                int idCode = jsonCode.getInt("id");
                                int nbCouleurs = jsonCode.getInt("nbCouleurs");

                                //TEST: Afficher tableau[0], id du code et nbCouleurs
                                Log.v(TAG, codeSecret[0]);
                                Log.v(TAG, String.valueOf(idCode));
                                Log.v(TAG, String.valueOf(nbCouleurs));


                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }





    //-------------------------------------MENU DE PAUSE-----------------------------------
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