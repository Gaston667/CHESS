package modele.piece;
import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public class Roi extends Piece{
    public Roi(Couleur couleur) {
        super(couleur);
        this.symbole = (couleur == Couleur.BLANC) ? "BO" : "NO"; // Symbole pour le roi blanc et noir
    }

    @Override    
    public boolean estDeplacementValide(Coup coup, Plateau plateau) {
        // Implémentation de la validation du déplacement pour le roi
        return false;
    } 
}
