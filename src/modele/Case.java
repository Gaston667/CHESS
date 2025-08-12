package modele;
import  modele.piece.Piece;
import java.awt.Point;

public class Case {
    private int x;
    private int y;
    private Piece piece;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null; // Initialement, la case est vide
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean estVide() {
        return piece == null;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
    
    
}
