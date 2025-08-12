package modele.piece;

import java.awt.Point;
import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public class Pion extends Piece {
    
    public Pion(Couleur couleur){
        super(couleur); 
        if(couleur == Couleur.BLANC) {
            this.symbole = "BP"; // Symbole pour le pion blanc
        } else {
            this.symbole = "NP"; // Symbole pour le pion noir
        }
    }


    @Override
    public boolean estDeplacementValide(Coup coup, Plateau plateau) {
        //System.out.println("\nsuis dans estDeplacement valide\n");

        Point origine = coup.getOrigine();
        Point destination = coup.getDestination();

        int ligneOrigine = (int) origine.getX();
        int colOrigine = (int) origine.getY();
        int ligneDestination = (int) destination.getX();
        int colDestination = (int) destination.getY();

        int deltaLigne = (ligneDestination - ligneOrigine);
        int deltaCol = (colDestination - colOrigine);

        int direction = (this.getCouleur() == Couleur.BLANC) ? 1 : -1;

        boolean ligneDepart = (this.getCouleur() == Couleur.BLANC && ligneOrigine == 1) ||
                            (this.getCouleur() == Couleur.NOIR && ligneOrigine == 6);

        boolean deplacementVertical = deltaCol == 0 && deltaLigne == direction;
        boolean deplacementVerticalDouble = deltaCol == 0 && deltaLigne == 2 * direction;

        /*
        System.out.println("Origine : ligne = " + ligneOrigine + ", col = " + colOrigine);
        System.out.println("Destination : ligne = " + ligneDestination + ", col = " + colDestination);
        System.out.println("deltaLigne = " + deltaLigne);
        System.out.println("deltaCol = " + deltaCol);
        System.out.println("Direction = " + direction);
        System.out.println("Contenu destination : " + plateau.getCase(ligneDestination, colDestination).getPiece());
        */

        // Déplacement simple
        if (deplacementVertical && plateau.getCase(ligneDestination, colDestination).estVide()) {
            return true;
        }

        // Déplacement double
        if (deplacementVerticalDouble && ligneDepart) {
            int ligneIntermediaire = ligneOrigine + direction;
            if (plateau.getCase(ligneDestination, colDestination).estVide() &&
                plateau.getCase(ligneIntermediaire, colDestination).estVide()) {
                return true;
            }
        }

        // Capture en diagonale
        if (Math.abs(deltaCol) == 1 && deltaLigne == direction) {
            Piece pieceCible = plateau.getCase(ligneDestination, colDestination).getPiece();
            if (pieceCible != null && pieceCible.getCouleur() != this.getCouleur()) {
                return true;
            }
        }

        //System.out.println("\nJe sors de estDeplacementValide\n");
        return false;
    }
}