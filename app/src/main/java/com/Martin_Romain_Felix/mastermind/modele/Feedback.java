package com.Martin_Romain_Felix.mastermind.modele;

public class Feedback {
    private int correctPosition;
    private int correctCouleur;

    public Feedback(int _correctPosition, int _correctCouleur)
    {
        this.correctPosition = correctPosition;
        this.correctCouleur = _correctCouleur;
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
