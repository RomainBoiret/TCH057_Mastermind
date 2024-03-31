package com.Martin_Romain_Felix.mastermind.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Martin_Romain_Felix.mastermind.adaptateur.HistoriqueAdapter;
import com.Martin_Romain_Felix.mastermind.dao.GestionBD;
import com.example.mastermind.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoriqueActivity extends AppCompatActivity {
    private Button btnRetour;
    private HistoriqueAdapter historiqueAdapter;

    //Données de la BD
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOM = "Mastermind.db";
    GestionBD gestionnaireDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        //Créer l'instance de la BD
        gestionnaireDataBase = new GestionBD(this, DATABASE_NOM, null, DATABASE_VERSION);

        btnRetour = findViewById(R.id.btnRetour);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // List<Historique> lvhistorique = // Récupérer les données de la base de données ici

        // historiqueAdapter = new HistoriqueAdapter(lvhistorique);
        // historyRecyclerView.setAdapter(historiqueAdapter);


        ArrayList<HashMap<String, String>> listeParties = gestionnaireDataBase.getPartiesBD();

        //TEST:
        //Toast.makeText(getApplicationContext(), listeParties.get(0).get("courriel")).show();

        ListView lv = (ListView) findViewById(R.id.lvHistorique);

        ListAdapter adapter = new SimpleAdapter(HistoriqueActivity.this, listeParties, R.layout.item_historique,
                new String[]{"courriel","code", "nbCouleurs", "resultat", "nbTentatives"},
                new int[]{R.id.txtemail, R.id.txtcode, R.id.txtcouleurs, R.id.txtresultat, R.id.txtnbtentatives});
        lv.setAdapter(adapter);


    }
}