package modele.piece;
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
        // Implémentation de la validation du déplacement pour le fou
        return false;
    } 
}
