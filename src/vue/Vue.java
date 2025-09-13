package vue;

import modele.Coup;
import modele.Plateau;
import reseau.TypeMessage;

public interface Vue {
    Coup demanderCoup(Plateau plateau);        // Demande un coup au joueur
    void afficherPlateau(Plateau plateau);     // Affiche le plateau actuel
    void afficherMessage(TypeMessage type, String message);      // Affiche un message (texte)
    void setDernierCoup(Coup coup);            // Met à jour le dernier coup joué
    void setTour(String tour);
    void setScore(String score);
}
