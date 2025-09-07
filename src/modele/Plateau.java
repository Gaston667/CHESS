package modele;
import java.awt.Point;
import modele.piece.*;

public class Plateau {
    Case[][] cases;
    String style;

    public Plateau(String style){
        this.style = style;
        // Création du plateau de jeu avec 64 cases
        cases = new Case[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cases[i][j] = new Case(i, j);
                //System.out.print("|" + cases[i][j]+ "|");
            }
            //System.out.println();
        }

        // Initialisation du plateau avec les pièces en debut de partie au bon endroit
        this.initialiser();

    }
    
    // public Case getCase(Point position){
    //     int x = (int) position.getX();
    //     int y = (int) position.getY();
    //     return cases[x][y];
    // }

    // public Case getCase(Point position){
    //     int x = (int) position.getX(); // colonne
    //     int y = (int) position.getY(); // ligne
    //     return cases[y][x]; // et non cases[x][y]
    // }

    public Case getCase(int ligne, int colonne){
        return cases[ligne][colonne];
    }



    private void initialiser() {
        // Mise en place des pièces sur le plateau
        
        // Placement des pions noirs et blancs
        for (int i = 0; i < 8; i++) {
            cases[1][i].setPiece(new modele.piece.Pion(Couleur.BLANC));
            cases[6][i].setPiece(new modele.piece.Pion(Couleur.NOIR));
        }

        // Placement des tours noirs et blancs
        cases[7][0].setPiece(new Tour(Couleur.NOIR));
        cases[7][7].setPiece(new Tour(Couleur.NOIR));
        cases[0][0].setPiece(new Tour(Couleur.BLANC));
        cases[0][7].setPiece(new Tour(Couleur.BLANC));

        // Placement des cavaliers noirs et blancs
        cases[7][1].setPiece(new Cavalier(Couleur.NOIR));
        cases[7][6].setPiece(new Cavalier(Couleur.NOIR));
        cases[0][1].setPiece(new Cavalier(Couleur.BLANC));
        cases[0][6].setPiece(new Cavalier(Couleur.BLANC));

        // Placement des fous noirs et blancs
        cases[7][2].setPiece(new Fou(Couleur.NOIR));
        cases[7][5].setPiece(new Fou(Couleur.NOIR));
        cases[0][2].setPiece(new Fou(Couleur.BLANC));
        cases[0][5].setPiece(new Fou(Couleur.BLANC));

        // Placement des rois et reines noirs et blancs
        cases[7][3].setPiece(new Reine(Couleur.NOIR));
        cases[7][4].setPiece(new Roi(Couleur.NOIR));
        cases[0][3].setPiece(new Reine(Couleur.BLANC));
        cases[0][4].setPiece(new Roi(Couleur.BLANC));

       
        // Initialisation des cases vides
        for (int i = 2; i < 6; i++) {
            for(int j= 0; j < 8; j++) {
                cases[i][j].setPiece(null);
                
            } 
        }

        // Affichage du plateau après initialisation
        // System.out.println("Plateau initialisé :");
        // for (int i = 0; i < 8; i++) {
        //     System.out.print("Ligne " + (i ) + ": ");
        //     for (int j = 0; j < 8; j++) {
        //         System.out.print("|" + cases[i][j] + "|");
        //     }
        //     System.out.println();
        // }

        // System.out.println(cases[0][0]);
        // System.out.println(cases[0][1]);
        // System.out.println("");


    }

    public void deplacer(Coup coup){
        Point origine = coup.getOrigine();
        Point destination = coup.getDestination();

        Case caseOrigine = getCase((int) origine.getX(), (int) origine.getY());
        Case caseDestination = getCase((int) destination.getX(), (int) destination.getY());

        System.out.println("case origine: "+ caseOrigine);
        System.out.println("case destination: "+ caseDestination);

        Piece piece = caseOrigine.getPiece();
        if(piece == null) {
            throw new IllegalArgumentException("Aucune pièce à la position d'origine.");
        }
        // if(!piece.estDeplacementValide(coup, this)) {
        //     throw new IllegalArgumentException("Déplacement invalide.");
        // }

        // Déplacer la pièce de la case d'origine à la case de destination
        caseDestination.setPiece(piece);
        caseOrigine.setPiece(null);

      
    }

    public String getStyle() {
        return style;
    }
}
