package modele.piece;
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
        // Implémentation de la validation du déplacement pour la reine
        return false;
    }
}
