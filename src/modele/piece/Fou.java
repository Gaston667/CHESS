package modele.piece;
import java.awt.Point;
import modele.Case;
import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public class Fou extends Piece{
    public Fou(Couleur couleur) {
        super(couleur);
        if(couleur == Couleur.BLANC) {
            this.symbole = "BF"; // Symbole pour le fou blanc
        } else {
            this.symbole = "NF"; // Symbole pour le fou noir
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

        int deltaLigne = Math.abs(ligneDest - ligneOrigine); // c'est le k
        int deltaCol   = Math.abs(colDest   - colOrigine); // c'est le k

        //Vérifie que le déplacement est bien en diagonale, mouvement en "L" aussi pour un deplacement à droite (x,y)= (x0 + k, y0 + k) et a gauche (x0 + k, y0 - k) vers bas c'est positif +k et le haut c'est negatif -k
        if (deltaLigne != deltaCol) return false;

        // Verification si le chemin est libre pour ne pas que le fou saute par dessus une piece
            // Direction du mouvement
            int pasligne = (ligneDest - ligneOrigine) / deltaLigne; // +1 ou -1
            int pascol   = (colDest   - colOrigine) / deltaCol;   // +1 ou -1
            // Vérification des cases intermédiaires
            for (int i = 1; i < deltaLigne; i++) {
                Case caseInter = plateau.getCase(ligneOrigine + i * pasligne, colOrigine + i * pascol);
                if (!caseInter.estVide()){
                    System.out.println("Le chemin est bloqué par une pièce en (" + (ligneOrigine + i * pasligne) + ", " + (colOrigine + i * pascol) + ")");
                    return false; // une pièce bloque le passage
                }
            }

        // Etat de la case de destination
        Case caseDest = plateau.getCase(ligneDest, colDest);
        if (caseDest.estVide()) return true;

        // si la case est occupée
        return caseDest.getPiece().getCouleur() != this.getCouleur(); 
    }
}