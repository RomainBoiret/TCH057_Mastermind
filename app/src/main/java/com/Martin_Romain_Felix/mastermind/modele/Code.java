package com.Martin_Romain_Felix.mastermind.modele;

import android.util.Log;

import java.util.ArrayList;

public class Code {
    private String[] couleurs;

    public Code(String[] couleurs)
    {
        this.couleurs = couleurs;
    }

    //Getter et setter
    public String[] getCouleurs()
    {
        for (int i = 0; i < couleurs.length; i++) {
            for (int j = 0; j < Couleurs.couleursString.length; j++) {
                //Si la couleur est
            }
        }

        return couleurs;
    }
    public void setCouleurs(String[] couleurs) { this.couleurs = couleurs; }


    //Méthode comparer les codes
    //  - Retourne un feedback de combien de couleurs ont été trouvées, et combien sont
    //    dans la bonne position
    public Feedback comparaisonCodes(Code codeCorrect) {
        int positionsCorrectes = 0;
        int couleursCorrectes = 0;
        ArrayList<Integer> indexCouleursAIgnorer = new ArrayList<Integer>();
        ArrayList<Integer> indexCouleursAIgnorerJoueur = new ArrayList<Integer>();

        //D'abord checker s'il y a des positions correctes
        for (int i = 0; i < this.couleurs.length; i++) {
            for (int j = 0; j < codeCorrect.couleurs.length; j++) {
                //Il faut que les 2 couleurs aient le même indice et soient la même
                if (this.couleurs[i].equals(codeCorrect.couleurs[j]) && i == j) {
                    //Incrémenter les positions correctes et ignorer ces indices par la suite
                    positionsCorrectes++;
                    indexCouleursAIgnorerJoueur.add(i);
                    indexCouleursAIgnorer.add(j);
                }
            }
        }

        //On va comparer la couleur du code correct à chaque couleur de l'essai du joueur
        for (int i = 0; i < this.couleurs.length; i++) {
            for (int j = 0; j < codeCorrect.couleurs.length; j++) {

                Log.i("GUESS COULEURS: ", this.couleurs[j]);
                Log.i("GUESS COULEUR CORRECTE: ", codeCorrect.couleurs[i]);

                //S'il y a une correspondance de couleurs ET qu'on a pas encore aucun des 2 index ET que les index ne sont pas pareils
                if (this.couleurs[i].equals(codeCorrect.couleurs[j]) && !indexCouleursAIgnorer.contains(j) && !indexCouleursAIgnorerJoueur.contains(i) && i != j) {
                    //Ça veut dire que la couleur est devinée, mais pas à la bonne position
                    couleursCorrectes++;

                    //on ajoute l'index J dans la liste des index à ne pas vérifier (pour éviter
                    //le bug de les vérifier 2 fois la même couleur)
                    indexCouleursAIgnorerJoueur.add(i);
                    indexCouleursAIgnorer.add(j);

                    //On peut skip le reste des couleurs (pour ne pas avoir des doublons) et recommencer la boucle
                    j = codeCorrect.couleurs.length;
                }
            }
        }

        Log.e("GUESS POSITIONS CORRECTES: ", String.valueOf(positionsCorrectes));

        //Retourner ces données sous forme de feedback
        Feedback feedback = new Feedback(positionsCorrectes, couleursCorrectes);
        return feedback;
    }
}
