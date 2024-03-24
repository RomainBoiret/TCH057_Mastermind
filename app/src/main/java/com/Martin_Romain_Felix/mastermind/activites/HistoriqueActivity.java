package com.Martin_Romain_Felix.mastermind.activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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