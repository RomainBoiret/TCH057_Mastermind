package com.Martin_Romain_Felix.mastermind.modele;

import android.util.Log;

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

        //On va comparer la couleur du code correct à chaque couleur de l'essai du joueur
        for (int i = 0; i < codeCorrect.couleurs.length; i++) {
            for (int j = 0; j < this.couleurs.length; j++) {

                Log.i("GUESS COULEURS: ", this.couleurs[j]);
                Log.i("GUESS COULEUR CORRECTE: ", codeCorrect.couleurs[i]);


                //S'il y a une correspondance de couleurs
                if (codeCorrect.couleurs[i].equals(this.couleurs[j])) {
                    Log.e("GUESS COULEURS: ", "GUESS CORRECT!");
                    //Si les indices sont égaux, on a trouvé la couleur ET la position
                    if(i == j)
                        positionsCorrectes++;

                    //Sinon, juste la couleur est trouvée
                    else
                        couleursCorrectes++;

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
