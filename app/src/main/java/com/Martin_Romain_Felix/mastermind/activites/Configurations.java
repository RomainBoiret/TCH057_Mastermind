package com.Martin_Romain_Felix.mastermind.activites;

public class Configurations {
    private int longueur;
    private int nbCouleurs;
    private int nbTentatives;

    public Configurations(int _longueur, int _nbCouleurs, int _nbTentatives)
    {
        this.longueur = _longueur;
        this.nbCouleurs = _nbCouleurs;
        this.nbTentatives = _nbTentatives;
    }

    public int getLongueur()
    {
        return longueur;
    }

    public int getNbCouleurs()
    {
        return nbCouleurs;
    }

    public int getNbTentatives()
    {
        return nbTentatives;
    }
}
