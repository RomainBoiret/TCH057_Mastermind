package com.Martin_Romain_Felix.mastermind.modele;

public class Feedback {
    //Nombre de positions correctes et de couleurs correctes
    private int correctPosition;
    private int correctCouleur;

    public Feedback(int correctPosition, int correctCouleur)
    {
        this.correctPosition = correctPosition;
        this.correctCouleur = correctCouleur;
    }

    public int getCorrectPosition()
    {
        return correctPosition;
    }

    public int getCorrectCouleur()
    {
        return correctCouleur;
    }

}
