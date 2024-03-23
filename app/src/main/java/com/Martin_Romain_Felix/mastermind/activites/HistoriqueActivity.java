package com.Martin_Romain_Felix.mastermind.activites;

import android.os.Bundle;

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
    private RecyclerView historyRecyclerView;
    private HistoriqueAdapter historiqueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        historyRecyclerView = findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // List<Historique> lvhistorique = // Récupérer les données de la base de données ici

        // historiqueAdapter = new HistoriqueAdapter(lvhistorique);
        // historyRecyclerView.setAdapter(historiqueAdapter);
    }
}