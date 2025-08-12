// package modele;
// import java.awt.Point;
// public class Coup {
//     private int xDepart;
//     private int xArrivee;
//     private int yDepart;
//     private int yArrivee;

//     public Coup(int xDepart, int yDepart, int xArrivee, int yArrivee) {
//         this.colDepart = colDepart;
//         this.yDepart = yDepart;
//         this.xArrivee = xArrivee;
//         this.yArrivee = yArrivee;
//     }

//     public Point getOrigine(){
//         return new Point(xDepart, yDepart);
//     }
//     public Point getDestination(){
//         return new Point(xArrivee, yArrivee);
//     }

//    @Override
//     public String toString() {
//         Point origine = getOrigine();
//         Point destination = getDestination();
        
//         char colOrigine = (char) ('a' + origine.y); // y = colonne
//         int ligneOrigine = 8 - origine.x;           // x = ligne

//         char colDest = (char) ('a' + destination.y); // y = colonne
//         int ligneDest = 8 - destination.x;           // x = ligne

//         return " " + colOrigine + ligneOrigine + " -> " + colDest + ligneDest;
//     }


// }

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

        return " " + colOrigineChar + ligneDepart + " -> " + colDestChar + ligneArrivee;
    }
}
