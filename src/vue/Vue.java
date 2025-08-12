package vue;

import modele.Coup;
import modele.Plateau;

public interface Vue {
    Coup demanderCoup(Plateau plateau);        // Demande un coup au joueur
    void afficherPlateau(Plateau plateau);     // Affiche le plateau actuel
    void afficherMessage(String message);      // Affiche un message (texte)
}
