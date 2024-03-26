package com.Martin_Romain_Felix.mastermind.modele;

import java.util.ArrayList;
import java.util.List;

public class Mastermind {
    private Code secretCode;
    private List<Code> tentatives;
    private List<Feedback> feedbacks;
    private int nbTentatives;
    private int maxTentatives;

    public Mastermind(Code _secretCode, int _nbTentatives, int _maxTentatives)
    {
        this.secretCode = _secretCode;
        this.nbTentatives = _nbTentatives;
        this.maxTentatives = _maxTentatives;
        this.tentatives = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public boolean faireTentative(Code tentative)
    {
        // Comparer la tentative avec le code secret et générer un feedback
        Feedback rep = tentative.comparaisonCodes(secretCode);

        // Mettre à jour la liste des tentatives et des feedbacks
        tentatives.add(tentative);
        feedbacks.add(rep);
        nbTentatives++;

        // Vérifier si le joueur a trouvé la combinaison secrète ou a épuisé toutes ses tentatives
        return estPartieTerminee();

    }

    public boolean estPartieTerminee()
    {
        // Vérifier si le joueur a trouvé la combinaison secrète ou a épuisé toutes ses tentatives
        return tentatives.size() >= maxTentatives || estCodeTrouve();
    }

    public boolean estCodeTrouve()
    {
        // Vérifier si la dernière tentative correspond au code secret
        return (feedbacks.get(feedbacks.size()-1).getCorrectPosition() == secretCode.getCouleurs().length);

    }

    // Autres méthodes nécessaires pour interagir avec l'interface utilisateur
}
