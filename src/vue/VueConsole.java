package vue;

import java.util.Scanner;
import modele.Case;
import modele.Coup;
import modele.Plateau;
import modele.piece.Piece;
public class VueConsole implements Vue {


    @Override
    public Coup demanderCoup(Plateau plateau) {
        // Implémentation pour demander un coup à l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez votre coup (ex: e2 e4) : ");
        String input = scanner.nextLine();

        // Traitement de l'entrée de l'utilisateur
        String[] positions = input.split(" ");
        if (positions.length != 2) {
            System.out.println("Format de coup invalide.");
            return null;
        }

        String positionDepart = positions[0];
        String positionArrivee = positions[1];
        int[] coordDepart = convertirPositionEnCoordonnees(positionDepart);
        int[] coordArrivee = convertirPositionEnCoordonnees(positionArrivee);
        // covertion en colonne et ligne
        int ligneDepart = coordDepart[0]; // c'est le y
        int colonneDepart = coordDepart[1]; // c'est le x
        int ligneArrivee = coordArrivee[0]; // c'est le y
        int colonneArrivee = coordArrivee[1]; // c'est le x

        // Création du coup
        return new Coup(ligneDepart,  colonneDepart, ligneArrivee,  colonneArrivee);
    }

    @Override
    public void afficherPlateau(Plateau plateau) {
        System.out.println("\n     a    b    c    d    e    f    g    h");
        System.out.println("  +----+----+----+----+----+----+----+----+");

        for (int ligne = 0; ligne < 8; ligne++) {
            System.out.print((ligne) + " |");
            for (int col = 0; col < 8; col++) {
                Case caseActuelle = plateau.getCase(ligne, col);
                if (caseActuelle.estVide()) {
                    System.out.print("    |");
                } else {
                    Piece piece = caseActuelle.getPiece();
                    System.out.print(" " + piece.getSymbole() + " |");
                }
            }
            System.out.println(" " + ligne);
            System.out.println("  +----+----+----+----+----+----+----+----+");
        }

        System.out.println("     a    b    c    d    e    f    g    h\n");
    }



    @Override
    public void afficherMessage(String message) {
        System.out.println(message); // Affiche le message dans la console
    }

    private int[] convertirPositionEnCoordonnees(String position) {
        int col = position.charAt(0) - 'a'; // Convertit 'a' à 'h' en 0 à 7
        int ligne = Character.getNumericValue(position.charAt(1)); // Convertit '0' à '7'
        return new int[]{ligne, col};
    }
    
}
