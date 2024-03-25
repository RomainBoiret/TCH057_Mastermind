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

    public void faireTentative(Code tentatives)
    {
        // Comparer la tentative avec le code secret et générer un feedback
        // Mettre à jour la liste des tentatives et des feedbacks
        // Vérifier si le joueur a trouvé la combinaison secrète ou a épuisé toutes ses tentatives
    }

    public boolean estPartieTerminee()
    {
        // Vérifier si le joueur a trouvé la combinaison secrète ou a épuisé toutes ses tentatives
        return tentatives.size() >= maxTentatives || estCodeTrouve();
    }

    public boolean estCodeTrouve()
    {
        // Vérifier si la dernière tentative correspond au code secret
        return false;
    }

    // Autres méthodes nécessaires pour interagir avec l'interface utilisateur
}
