package com.Martin_Romain_Felix.mastermind.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Martin_Romain_Felix.mastermind.adaptateur.HistoriqueAdapter;
import com.example.mastermind.R;

public class HistoriqueActivity extends AppCompatActivity {
    private Button btnRetour;
    private RecyclerView historyRecyclerView;
    private HistoriqueAdapter historiqueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        btnRetour = findViewById(R.id.btnRetour);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        historyRecyclerView = findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // List<Historique> lvhistorique = // Récupérer les données de la base de données ici

        // historiqueAdapter = new HistoriqueAdapter(lvhistorique);
        // historyRecyclerView.setAdapter(historiqueAdapter);
    }
}