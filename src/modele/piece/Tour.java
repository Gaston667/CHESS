package modele.piece;
import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public class Tour extends Piece{
    public Tour(Couleur couleur) {
        super(couleur);
        if(couleur == Couleur.BLANC) {
            this.symbole = "BT"; // Symbole pour la tour blanche
        } else {
            this.symbole = "NT"; // Symbole pour la tour noire
        }
    }

    @Override    
    public boolean estDeplacementValide(Coup coup, Plateau plateau) {
        // Implémentation de la validation du déplacement pour la tour
        return false;
    } 
}
