package modele;

import java.awt.Point;

public class Coup {
    private int ligneDepart;
    private int colDepart;
    private int ligneArrivee;
    private int colArrivee;

    public Coup(int ligneDepart, int colDepart, int ligneArrivee, int colArrivee) {
        this.ligneDepart = ligneDepart;
        this.colDepart = colDepart;
        this.ligneArrivee = ligneArrivee;
        this.colArrivee = colArrivee;
    }

    public Point getOrigine() {
        return new Point(ligneDepart, colDepart); // ligne = x, colonne = y
    }

    public Point getDestination() {
        return new Point(ligneArrivee, colArrivee);
    }

    @Override
    public String toString() {
        char colOrigineChar = (char) ('a' + colDepart);
        char colDestChar = (char) ('a' + colArrivee);

        return " " + colOrigineChar + ligneDepart + " -- " + colDestChar + ligneArrivee;
    }
}
