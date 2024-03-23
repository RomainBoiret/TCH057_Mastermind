package com.Martin_Romain_Felix.mastermind.activites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mastermind.R;

import java.util.List;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.ViewHolder> {
    private List<Historique> lvhistorique;

    public HistoriqueAdapter(List<Historique> _lvhistorique)
    {
        this.lvhistorique = _lvhistorique;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historique, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Historique historique = lvhistorique.get(position);
        holder.txtemail.setText("Email: " + historique.getEmail());
        holder.txtcode.setText("Code secret: " + historique.getCodeSecret());
        holder.txtcouleurs.setText("Nombre de couleurs: " + historique.getCouleur());
        holder.txtresultat.setText("Résultat: " + historique.getResultat());
        holder.txtnbtentatives.setText("Nombre de tentatives: " + historique.getNbTentatives());
    }

    @Override
    public int getItemCount()
    {
        return lvhistorique.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtemail;
        TextView txtcode;
        TextView txtcouleurs;
        TextView txtresultat;
        TextView txtnbtentatives;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtemail = itemView.findViewById(R.id.txtemail);
            txtcode = itemView.findViewById(R.id.txtcode);
            txtcouleurs = itemView.findViewById(R.id.txtcouleurs);
            txtresultat = itemView.findViewById(R.id.txtresultat);
            txtnbtentatives = itemView.findViewById(R.id.txtnbtentatives);
        }
    }
}