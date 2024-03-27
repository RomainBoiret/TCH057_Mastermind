package com.Martin_Romain_Felix.mastermind.modele;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Mastermind {
    private Code secretCode;
    private List<Code> tentatives;
    private List<Feedback> feedbacks;
    private int nbTentatives;
    private int maxTentatives;

    public Mastermind(int _maxTentatives)
    {
        this.nbTentatives = 0;
        this.maxTentatives = _maxTentatives;
        this.tentatives = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    //Set le code secret
    public void setSecretCode(Code code) { this.secretCode = code; }
    public Code getSecretCode() { return this.secretCode; }

    public void setNbTentatives(int nbTentatives) { this.nbTentatives = nbTentatives; }
    public int getNbTentatives() { return this.nbTentatives; };
    public int getMaxTentatives() { return this.maxTentatives; }

    public void faireTentative(Code tentative)
    {
        // Comparer la tentative avec le code secret et générer un feedback
        Feedback rep = tentative.comparaisonCodes(secretCode);

        // Mettre à jour la liste des tentatives et des feedbacks
        tentatives.add(tentative);
        feedbacks.add(rep);

        // Vérifier si le joueur a trouvé la combinaison secrète ou a épuisé toutes ses tentatives
        nbTentatives++;
    }

    public boolean estPartieTerminee()
    {
        // Vérifier si le joueur a trouvé la combinaison secrète ou a épuisé toutes ses tentatives
        return nbTentatives >= maxTentatives;
    }

    public boolean estCodeTrouve()
    {
        // Vérifier si la dernière tentative correspond au code secret
        return (feedbacks.get(nbTentatives-1).getCorrectPosition() == secretCode.getCouleurs().length);

    }

    // Autres méthodes nécessaires pour interagir avec l'interface utilisateur
}
