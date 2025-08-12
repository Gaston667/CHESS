package modele.piece;

import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public abstract class Piece {
    private final Couleur couleur;
    protected String symbole;

    public Piece(Couleur couleur) {
        this.couleur = couleur;
        this.symbole = ""; // Symbole par défaut, redéfini dans les sous-classes
    }


    public Couleur getCouleur() {
        return this.couleur;
    }

    public String getSymbole() {
        return symbole;
    }

    public abstract boolean estDeplacementValide(Coup coup, Plateau plateau);


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.couleur;
    }


}
