package com.Martin_Romain_Felix.mastermind.modele;

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

    public void setLongueur(int longueur) { this.longueur = longueur; }

    public void setNbCouleurs(int nbCouleurs){ this.nbCouleurs = nbCouleurs; }

    public void setNbTentatives(int nbTentatives){ this.nbTentatives =  nbTentatives; }
}
