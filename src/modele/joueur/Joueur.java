package modele.joueur;

import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public abstract class Joueur {
    private final Couleur couleur;
    private String nom;

    public Joueur(Couleur couleur, String nom) {
        this.couleur = couleur;
        this.nom = nom;
    }

    public Couleur getCouleur() {
       return couleur;
    }

    public String getNom() {
        return nom;
    }

    public boolean  estReseau() {
        return false; // Par défaut, le joueur n'est pas un réseau
    }


    public abstract Coup jouerCoup(Plateau plateau);
}
