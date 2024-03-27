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

        //On va comparer la couleur du code correct à chaque couleur de l'essai du joueur
        for (int i = 0; i < codeCorrect.couleurs.length; i++) {
            for (int j = 0; j < this.couleurs.length; j++) {

                Log.i("GUESS COULEURS: ", this.couleurs[j]);
                Log.i("GUESS COULEUR CORRECTE: ", codeCorrect.couleurs[i]);

                //S'il y a une correspondance de couleurs ET qu'on a pas encore vérifié l'index J
                if (codeCorrect.couleurs[i].equals(this.couleurs[j]) && !indexCouleursAIgnorer.contains(j)) {
                    //Si les indices sont égaux, on a trouvé la couleur ET la position
                    if(i == j)
                        positionsCorrectes++;

                    //Sinon, juste la couleur est trouvée
                    else
                        couleursCorrectes++;

                    //on ajoute l'index J dans la liste des index à ne pas vérifier (pour éviter
                    //le bug de les vérifier 2 fois la même couleur)
                    indexCouleursAIgnorer.add(j);

                    //On peut skip le reste des couleurs et recommencer la boucle
                    j = this.couleurs.length;
                }
            }
        }

        Log.e("GUESS POSITIONS CORRECTES: ", String.valueOf(positionsCorrectes));

        //Retourner ces données sous forme de feedback
        Feedback feedback = new Feedback(positionsCorrectes, couleursCorrectes);
        return feedback;
    }
}
