package modele.piece;

import modele.Couleur;
import modele.Coup;
import modele.Plateau;
import java.awt.Point;
import modele.Case;

public class Roi extends Piece {
    public Roi(Couleur couleur) {
        super(couleur);
        this.symbole = (couleur == Couleur.BLANC) ? "BO" : "NO";
    }

    @Override
    public boolean estDeplacementValide(Coup coup, Plateau plateau) {
        Point origine = coup.getOrigine();
        Point destination = coup.getDestination();

        int ligneOrigine = origine.x;
        int colOrigine   = origine.y;
        int ligneDest    = destination.x;
        int colDest      = destination.y;

        // bornes de plateau
        if (ligneDest < 0 || ligneDest > 7 || colDest < 0 || colDest > 7) return false;

        int deltaLigne = Math.abs(ligneDest - ligneOrigine); 
        int deltaCol   = Math.abs(colDest   - colOrigine); 

        // Le roi se déplace d'une seule case (horizontale, verticale ou diagonale)
        if (deltaLigne > 1 || deltaCol > 1) return false;

        // Vérifie la case de destination
        Case caseDest = plateau.getCase(ligneDest, colDest);
        return caseDest.estVide() || caseDest.getPiece().getCouleur() != this.getCouleur();
    }
}
