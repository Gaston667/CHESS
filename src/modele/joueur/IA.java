package modele.joueur;

import modele.Plateau;
import modele.Coup;


public class IA {
    private final int profondeur ; // Profondeur maximale de recherche pour l'IA

    public IA(int profondeur) {
        this.profondeur = profondeur;
    }


    public Coup meilleurCoup(Plateau plateau) {
        // Implémentation de la logique de l'IA pour calculer le coup
        // Pour l'instant, on retourne null ou un coup par défaut
        return null; // Placeholder, à remplacer par la logique d'IA réelle
    }
}
