package com.Martin_Romain_Felix.mastermind.activites;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.Martin_Romain_Felix.mastermind.dao.GestionBD;
import com.Martin_Romain_Felix.mastermind.dao.Partie;
import com.Martin_Romain_Felix.mastermind.modele.Code;
import com.Martin_Romain_Felix.mastermind.modele.Configurations;
import com.Martin_Romain_Felix.mastermind.modele.Couleurs;
import com.Martin_Romain_Felix.mastermind.modele.Mastermind;
import com.example.mastermind.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class JeuActivity extends AppCompatActivity implements View.OnClickListener {
    //Attributs éléments graphiques
    private ImageView menu;
    private GridLayout grilleJeu;
    private GridLayout grillePalette;
    private GridLayout grilleFeedback;
    private Button validerChoix;

    //Attributs partie
    static Mastermind partieMastermind;
    static Configurations configurations;
    static int longueur;
    static int couleurs;
    static int tentatives;
    static boolean fini = false;

    //Code secret et tentative de code du joueur
    static Code code;
    static String[] codeJoueur;

    static String idCodeSecret;
    static String courriel;

    final String TAG = "MesMessages";
    //final String URL_POINT_ENTREE = "http://10.0.2.2:3000";
    final String URL_POINT_ENTREE = "http://192.168.2.68:3000";

    private int couleurChoisie = 0;
    int indiceCouleurChoisie;
    private int nbCase;

    //Données BD
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOM = "Mastermind.db";
    GestionBD gestionnaireBD;

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

        //Créer la BD
        gestionnaireBD = new GestionBD(this, DATABASE_NOM, null, DATABASE_VERSION);

        //Ouvrir/fermer le menu quand on appuie dessus
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirMenu();
            }
        });

        //CHERCHER LES CONFIGURATIONS
        configurations = AccueilActivity.configurations;
        longueur = configurations.getLongueur();
        couleurs = configurations.getNbCouleurs();
        tentatives = configurations.getNbTentatives();

        Log.e(TAG, "NOMBRE DECOULEURS: " + couleurs);

        //Instancier le tableau de tentative de code avec la longueur
        codeJoueur = new String[longueur];

        //Instancier Mastermind avec le nb de tentatives
        partieMastermind = new Mastermind(configurations.getNbTentatives());

        //Initialiser les grilles (Jeu, palette, feedback)
        initialiserGrilles();

        //--------------------------------CRÉER PARTIE DE MASTERMIND--------------------------------

        //D'abord chercher un code secret
        try {
            //Si ça marche, cette fonction cherche le code et lance la partie
            chercherCodeSecret();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }


    }

    //************************************************************************************//

    @SuppressLint("ResourceType")
    private void initialiserGrilles() {
        //---------------------GRILLE DE JEU---------------------
        //
        grilleJeu = findViewById(R.id.gridJeu);
        grilleJeu.setColumnCount(longueur);
        grilleJeu.setRowCount(tentatives);

        //Ajouter chaque bouton à la grille de jeu
        for (int i = 0; i < longueur*tentatives; i++) {
            Button btn = new Button(this);
            btn.setBackground(getDrawable(R.drawable.bouton_rond));
            btn.getBackground().setTint(getColor(R.color.gray));


            grilleJeu.addView(btn);

            //Paramètres d'affichage des boutons
            ViewGroup.LayoutParams params;
            params = btn.getLayoutParams();
            params.width = 90;
            params.height = 90;
            ((ViewGroup.MarginLayoutParams)params).setMargins(10,5,10,5);
        }

        int nbChild = grilleJeu.getChildCount();

        //Gestion des clics des boutons de la grille de jeu
        for(int i = 0; i < nbChild; i++)
        {
            Button btn = (Button) grilleJeu.getChildAt(i);
            int index = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Nbr de cases restantes à jouer
                    nbCase = longueur*(tentatives - partieMastermind.getNbTentatives());

                    //Prend 0 ou 1 dependament is situe dans bonne zone
                    int ok = 0;

                    if (!fini) {
                        //Trouver la ligne où on peut jouer
                        for(int i = nbCase - longueur; i < nbCase; i++)
                        {
                            if(v == grilleJeu.getChildAt(i))
                                ok = 1;
                        }

                        //Si le clic est sur la bonne ligne et que la couleur est choisie
                        if(ok == 1 && couleurChoisie != 0) {
                            //On met la couleur du bouton
                            btn.getBackground().setTint(couleurChoisie);

                            //On met le code de la couleur choisie dans le tableau du code du joueur à
                            // la position du bouton cliqué
                            codeJoueur[index%longueur] = Couleurs.couleursString[indiceCouleurChoisie];
                        }

                        //Sinon, si le clic est sur la bonne ligne et qu'il y a pas de couleur choisie
                        else if (ok == 1)
                            Toast.makeText(JeuActivity.this,"Choisir une couleur", Toast.LENGTH_SHORT).show();

                            //Sinon, le clic n'est pas sur la bonne ligne
                        else
                            Toast.makeText(JeuActivity.this,"Vous ne pouvez pas jouer sur cette ligne", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //---------------------GRILLE FEEDBACK---------------------
        //
        grilleFeedback = findViewById(R.id.gridFeedback);
        grilleFeedback.setColumnCount(2);
        grilleFeedback.setRowCount(tentatives);

        for (int i = 0; i < tentatives*2; i++) {
            TextView btn = new TextView(this);

            //Attributs de styles du texte
            btn.setText("0");
            btn.setBackgroundResource(R.color.white);
            btn.setTextColor(getColor(R.color.black));
            btn.setTypeface(null, Typeface.BOLD);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            btn.setTextAlignment(grilleFeedback.TEXT_ALIGNMENT_CENTER);

            grilleFeedback.addView(btn);

            ViewGroup.LayoutParams params;
            params = btn.getLayoutParams();
            params.width = 70;
            params.height = 90;
            ((ViewGroup.MarginLayoutParams)params).setMargins(10,5,10,5);
        }


        //---------------------GRILLE PALETTE---------------------
        //
        grillePalette = findViewById(R.id.gridPalette);
        grillePalette.setColumnCount(4);
        grillePalette.setRowCount(2);

        for (int i = 0; i < couleurs; i++) {
            Button btn = new Button(this);
            btn.setBackground(getDrawable(R.drawable.bouton_rond));
            btn.setBackgroundColor(Couleurs.couleurs[i]);
            btn.getBackground().setTint(Couleurs.couleurs[i]);
            btn.setOnClickListener(JeuActivity.this);

            grillePalette.addView(btn);

            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Choisir la couleur et prendre l'indice de la couleur
                    indiceCouleurChoisie = finalI;
                    couleurChoisie = ((ColorDrawable) v.getBackground()).getColor();
                }
            });

            ViewGroup.LayoutParams params;
            params = btn.getLayoutParams();
            params.width = 110;
            params.height = 110;
            ((ViewGroup.MarginLayoutParams)params).setMargins(10,5,10,5);
        }

        //Bouton valider choix
        validerChoix = findViewById(R.id.validerChoix);
    }







    //-------------------Méthode clic pour choisir la couleur de la palette--------------
    @Override
    public void onClick(View v) {
        couleurChoisie = ((ColorDrawable) v.getBackground()).getColor();
        Log.i(TAG, "COULEUR: " + couleurChoisie);
    }

    //-----------------------------MÉTHODE CHERCHER STATISTIQUES------------------------------
    private void chercherStatistiques(String idCode) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder()
                .url(URL_POINT_ENTREE + "/stats?idCode=" + idCode)
                .build();

        Log.e(TAG, "CODE: " + idCode);

        new Thread() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(requete).execute();
                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();
                    Log.i(TAG, "Requête GET stats: " + jsonData);

                    JeuActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONArray donneesJSON = new JSONArray(jsonData);

                                //S'il y a des statistiques, on va les chercher et les afficher
                                if (jsonData.contains("id")) {
                                    //Transformer le string en données json
                                    JSONObject objectJSON = donneesJSON.getJSONObject(0);


                                    //Chercher les textview
                                    TextView record = findViewById(R.id.record);
                                    TextView emailRecord = findViewById(R.id.emailRecord);

                                    //Mettre le texte
                                    record.setText(objectJSON.getString("record"));
                                    emailRecord.setText(objectJSON.getString("courriel"));
                                }

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

    //-----------------------------MÉTHODE POST NOUVELLES STATISTIQUES-----------------------------
    //On appelle cette méthode SI on a battu le record (vérifier à la fin du jeu)
    private void postStatistique(String idCode, String record, String courriel) {

        //D'abord, chercher le nombre de stats pour déterminer l'ID de la statistique
        final int[] idStat = new int[1];

        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder()
                .url(URL_POINT_ENTREE + "/stats")
                .get()
                .build();

        //THREAD EXÉCUTER LA REQUÊTE
        new Thread() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(requete).execute();
                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();

                    JeuActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //Créer un tableau JSON avec TOUS les codes
                                JSONArray tableauJson = new JSONArray(jsonData);

                                Log.e(TAG, String.valueOf(tableauJson.length()));

                                //Trouver la taille du tableau
                                idStat[0] = tableauJson.length() + 1;
                                Log.e(TAG, String.valueOf(idStat[0]));

                                //Créer l'objet JSON pour y stocker les données qu'on veut POST
                                JSONObject statsJSON = new JSONObject();

                                //Stocker les données en paramètres dans l'objet JSON
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        statsJSON.put("id", String.valueOf(idStat[0]));
                                        statsJSON.put("idCode", idCode);
                                        statsJSON.put("record", record);
                                        statsJSON.put("courriel", courriel);
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                //Faire la requête
                                final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                                OkHttpClient client2 = new OkHttpClient();

                                RequestBody corpsRequete = RequestBody.create(String.valueOf(statsJSON), JSON);
                                Request requete2 = new Request.Builder()
                                        .url(URL_POINT_ENTREE + "/stats")
                                        .post(corpsRequete)
                                        .build();

                                //EXÉCUTER LA REQUÊTE
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            client2.newCall(requete2).execute();
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }.start();


                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }


    //-----------------------------MÉTHODE CHERCHER UN CODE SECRET------------------------------
    private void chercherCodeSecret() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder()
                .url(URL_POINT_ENTREE + "/codesSecrets")
                .build();

        new Thread() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(requete).execute();
                    ResponseBody responseBody = response.body();
                    String jsonData = responseBody.string();

                    JeuActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //Créer un tableau JSON avec TOUS les codes
                                JSONArray tableauJson = new JSONArray(jsonData);

                                //Itérer le tableau pour trouver les codes qui conviennent aux
                                //configurations choisies par l'utilisateur
                                ArrayList<Integer> idCandidats = new ArrayList<Integer>();

                                for (int i = 0; i < tableauJson.length(); i++) {
                                    JSONObject jsonObject = tableauJson.getJSONObject(i);

                                    //Si le code a le nb de couleurs et la longueur conforme
                                    //aux configurations, on le garde comme candidat
                                    Log.e(TAG, "NOMBRE DE COULEURS: " + jsonObject.getInt("nbCouleurs"));
                                    if (jsonObject.getInt("nbCouleurs") == configurations.getNbCouleurs()
                                            && jsonObject.getJSONArray("code").length() == configurations.getLongueur()) {
                                        idCandidats.add(jsonObject.getInt("id"));
                                    }
                                }

                                //Choisir un des ID au hasard parmi les candidats
                                int index = (int) (Math.random() * idCandidats.size());
                                int idCode = idCandidats.get(index);
                                Log.i(TAG, "ID au hasard: " + idCode);

                                idCodeSecret = String.valueOf(idCode);

                                //Chercher l'objet JSON à l'indice et prendre le tableau
                                //qui correspond au code
                                JSONObject c = tableauJson.getJSONObject(idCode);
                                JSONArray tableauCode = c.getJSONArray("code");

                                //Mettre le code dans un tableau de strings
                                String[] codeSecret = new String[tableauCode.length()];
                                for(int i = 0; i < tableauCode.length(); i++){
                                    codeSecret[i] = tableauCode.getString(i);
                                }

                                //TEST: Afficher tableau[0], id du code et nbCouleurs
                                Log.v(TAG, codeSecret[0]);
                                Log.v(TAG, String.valueOf(idCode));
                                Log.v(TAG, String.valueOf(configurations.getNbCouleurs()));

                                //Afficher les statistiques selon l'ID de ce code
                                chercherStatistiques(idCodeSecret);

                                //INITIALISER LA PARTIE
                                code = new Code(codeSecret);
                                partieMastermind.setSecretCode(code);

                                //Lancer la méthode qui gère le jeu
                                startGame();

                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                                throw new RuntimeException(e);
                            }
                            catch (IOException e) {
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

    //--------------------------------------MÉTHODE DU JEU-------------------------------------------
    @SuppressLint({"SuspiciousIndentation", "ResourceAsColor"})
    private void startGame() {
        Log.i(TAG, "Partie commencée!");
        Log.i(TAG, "Code secret pendant partie: " + partieMastermind.getSecretCode().getCouleurs()[0]);

        //Gérer la validation de choix
        validerChoix.setOnClickListener(v -> {
            int nbCouleursMises = 0;

            //Vérifier que toutes les cases ont une couleur
            for (int i = 0; i < codeJoueur.length; i++) {
                if (codeJoueur[i] != null)
                    nbCouleursMises++;
            }

            //Si toutes les cases n'ont pas une couleur, on ne vérifie pas
            if (nbCouleursMises != longueur)
                Toast.makeText(JeuActivity.this,"Il faut remplir toute la ligne pour valider le choix",
                        Toast.LENGTH_SHORT).show();

            //sinon, on fait la tentative
            else {
                //Créer l'instance de code
                Code codeFinal = new Code(codeJoueur);

                //Faire la tentative
                partieMastermind.faireTentative(codeFinal);

                //Réinitialiser le tableau du code du joueur
                for (int i = 0; i < codeJoueur.length; i++) {
                    Log.i("GUESS DU JOUEUR", codeJoueur[i]);
                    codeJoueur[i] = null;
                }

                //Afficher le feedback
                int nbChild = grilleFeedback.getChildCount();

                //Gestion des clics des boutons de la grille de jeu (on affiche le feedback
                //dans 2 cases, donc on fait une boucle
                for(int i = 0; i < 2; i++) {
                    TextView textFeedback = (TextView) grilleFeedback.getChildAt(tentatives * 2 - i -1 - (partieMastermind.getNbTentatives()-1)*2);

                    //On part du dernier, on affiche le nombre de couleurs trouvées correctement
                    if(i == 0) {
                        int couleurCorrectesNb = partieMastermind.getFeedbacks().get(partieMastermind.getNbTentatives() - 1).getCorrectCouleur();

                        Log.e(TAG, "NB DE COULEURS CORRECTES: " + couleurCorrectesNb);
                        //textFeedback.setText(partieMastermind.getFeedbacks().get(partieMastermind.getNbTentatives()-1).getCorrectCouleur());
                        textFeedback.setText(String.valueOf(couleurCorrectesNb));
                        textFeedback.setTextColor(getColor(R.color.yellow));
                        textFeedback.setTypeface(null, Typeface.BOLD);
                    }

                    //Le
                    else if(i == 1) {
                        int positionsCorrectesNb = partieMastermind.getFeedbacks().get(partieMastermind.getNbTentatives()-1).getCorrectPosition();
                        textFeedback.setText(String.valueOf(positionsCorrectesNb));
                        textFeedback.setTextColor(getColor(R.color.green));
                        textFeedback.setTypeface(null, Typeface.BOLD);
                    }
                }


                Log.i(TAG, "NB TENTATIVES: " + partieMastermind.getNbTentatives());
                //Vérifier si le joueur a gagné ou pas
                if (partieMastermind.estCodeTrouve()) {
                    Toast.makeText(JeuActivity.this,"Vous avez gagné", Toast.LENGTH_SHORT).show();

                    //Chercher le record
                    TextView tvRecord = findViewById(R.id.record);
                    String scoreRecord = tvRecord.getText().toString();

                    //S'il n'y a aucun record pour le code, c'est automatiquement un record
                    if (scoreRecord.equals("aucun") || partieMastermind.getNbTentatives() <= Integer.parseInt(scoreRecord)) {
                        postStatistique(idCodeSecret, String.valueOf(partieMastermind.getNbTentatives()), courriel);
                    }

                   gestionnaireBD.ajouterPartieBD(courriel, Arrays.toString(partieMastermind.getSecretCode().getCouleurs()),
                       couleurs, "Gagné", partieMastermind.getNbTentatives());

                    //DÉSACTIVER ABANDONNER, VALIDER CHOIX
                    validerChoix.setEnabled(false);
                    fini = true;
                }


                else if (partieMastermind.estPartieTerminee()) {
                    Toast.makeText(JeuActivity.this,"Vous avez perdu", Toast.LENGTH_SHORT).show();

                    gestionnaireBD.ajouterPartieBD(courriel, Arrays.toString(partieMastermind.getSecretCode().getCouleurs()),
                            couleurs, "Perdu", partieMastermind.getNbTentatives());

                    //DÉSACTIVER ABANDONNER, VALIDER CHOIX
                    validerChoix.setEnabled(false);
                    fini = true;
                }
            }
        });
    }


    //--------------------------------------MENU DE PAUSE-------------------------------------------

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
                popupMenu.dismiss();
                fini = false;
                recreate();
            }
        });

        //Clic sur bouton abandonner
        btnAbandonner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fini) {
                    //Ouvrir autre popup pour confirmer si le joueur veut vraiment annuler
                    popupMenu.dismiss();

                    //Ouvrir menu abandon
                    ouvrirMenuAbandon();
                }
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

    //*********************************************//

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
                //Mettre la partie dans la base de données
                gestionnaireBD.ajouterPartieBD(courriel, Arrays.toString(partieMastermind.getSecretCode().getCouleurs()),
                        couleurs, "Abandonnée", partieMastermind.getNbTentatives());

                finish();
            }
        });
    }

}