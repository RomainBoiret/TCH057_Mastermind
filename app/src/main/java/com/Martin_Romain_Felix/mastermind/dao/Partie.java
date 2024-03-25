package com.Martin_Romain_Felix.mastermind.dao;

public class Partie {
    //Attributs d'une partie
    private String courriel;
    private String code;
    private int nbCouleurs;
    private String resultat;
    private int nbTentatives;

    public Partie(String courriel, String code, int nbCouleurs, String resultat, int nbTentatives) {
        this.courriel = courriel;
        this.code = code;
        this.nbCouleurs = nbCouleurs;
        this.resultat = resultat;
        this.nbTentatives = nbTentatives;
    }

    //Get les données de la partie (pour la BD)
    public String getCourriel() { return this.courriel; }
    public String getCode() { return this.code; }
    public int getNbCouleurs() { return this.nbCouleurs; };
    public String getResultat() { return this.resultat; }
    public int getNbTentatives() { return this.nbTentatives; }

    //Set les valeurs de la partie
    public void setCourriel(String courriel) { this.courriel = courriel; }
    public void setCode(String code) { this.code = code; }
    public void setNbCouleurs(int nbCouleurs) { this.nbCouleurs = nbCouleurs; }
    public void setResultat(String resultat) { this.resultat = resultat; }
    public void setNbTentatives(int nbTentatives) { this.nbTentatives = nbTentatives; }

}
