package modele.piece;
import java.awt.*;

import modele.Case;
import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public class Cavalier extends Piece{
    public Cavalier(Couleur couleur) {
        super(couleur);
        if(couleur == Couleur.BLANC) {
            this.symbole = "BC"; // Symbole pour le cavalier blanc
        } else {
            this.symbole = "NC"; // Symbole pour le cavalier noir
        }
    }

    @Override
    public boolean estDeplacementValide(Coup coup, Plateau plateau) {
        Point origine = coup.getOrigine();
        Point destination = coup.getDestination();

        // Conventions : Point.x = ligne, Point.y = colonne
        int ligneOrigine = origine.x;
        int colOrigine   = origine.y;
        int ligneDest    = destination.x;
        int colDest      = destination.y;

        // bornes de plateau
        if (ligneDest < 0 || ligneDest > 7 || colDest < 0 || colDest > 7) return false;

        int deltaLigne = Math.abs(ligneDest - ligneOrigine);
        int deltaCol   = Math.abs(colDest   - colOrigine);

        // mouvement en "L"
        boolean enL = (deltaLigne == 2 && deltaCol == 1) || (deltaLigne == 1 && deltaCol == 2);
        if (!enL) return false;

        // Etat de la case de destination
        Case caseDest = plateau.getCase(ligneDest, colDest);
        if (caseDest.estVide()) return true;

        // si la case est occupée
        return caseDest.getPiece().getCouleur() != this.getCouleur(); 
    }
}