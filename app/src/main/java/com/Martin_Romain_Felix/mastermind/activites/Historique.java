package com.Martin_Romain_Felix.mastermind.activites;
public class Historique {
    private String email;
    private String codeSecret;
    private int couleur;
    private String resultat;
    private int nbTentatives;

    public Historique(String _email, String _codeSecret, int _couleur, String _resultat, int _nbTentatives)
    {
        this.email = _email;
        this.codeSecret = _codeSecret;
        this.couleur = _couleur;
        this.resultat = _resultat;
        this.nbTentatives = _nbTentatives;
    }

    public String getEmail()
    {
        return email;
    }

    public String getCodeSecret()
    {
        return codeSecret;
    }

    public int getCouleur()
    {
        return couleur;
    }

    public String getResultat()
    {
        return resultat;
    }

    public int getNbTentatives()
    {
        return nbTentatives;
    }
}