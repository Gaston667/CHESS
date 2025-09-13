package modele.piece;
import java.awt.Point;
import modele.Case;
import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public class Reine extends Piece{
    public Reine(Couleur couleur) {
        super(couleur);
        if(couleur == Couleur.BLANC) {
            this.symbole = "BR"; // Symbole pour la reine blanche
        } else {
            this.symbole = "NR"; // Symbole pour la reine noire
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

        //Vérifie que le déplacement est bien en diagonale ou en ligne droite
        if (!(deltaLigne == 0 || deltaCol == 0 || deltaLigne == deltaCol)) return false; //
       
        // Verification si le chemin est libre pour ne pas que la reine saute par dessus une piece
            // Direction du mouvement et le pas
                int pasLigne = 0;
                if (ligneDest > ligneOrigine) pasLigne = 1;
                else if (ligneDest < ligneOrigine) pasLigne = -1;

                int pasCol = 0;
                if (colDest > colOrigine) pasCol = 1;
                else if (colDest < colOrigine) pasCol = -1;
            // Vérification des cases intermédiaires
            int nbPas = Math.max(deltaLigne, deltaCol);
            for (int i = 1; i < nbPas; i++) {
                int l = ligneOrigine + i * pasLigne;
                int c = colOrigine + i * pasCol;
                Case caseInter = plateau.getCase(l, c);
                if (!caseInter.estVide()) {
                    System.out.println("Le chemin est bloqué par une pièce en (" + l + ", " + c + ")");
                    return false;
                }
            }

        // Etat de la case de destination
        Case caseDest = plateau.getCase(ligneDest, colDest);
        if (caseDest.estVide()) return true;

        // si la case est occupée
        return caseDest.getPiece().getCouleur() != this.getCouleur(); 
    }
}
