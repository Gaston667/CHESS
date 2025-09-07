package vue;
import java.awt.*;
import javax.swing.*;
import modele.Coup;
import modele.Partie;
import modele.Plateau; 
import modele.joueur.Joueur;



public class PlateauSwingUI extends JPanel {
    
    private final CaseSwingUI[][] cases = new CaseSwingUI[8][8];
    private final Plateau plateauModele;
    private final VueGraphiqueSwing vueGraphiqueSwing;
    private final Partie partieModel;

    // Pour la gestion du dernier coup
    private Point prevOrigine = null;
    private Point prevDestination = null;

    private CaseSwingUI caseSelectionnee = null;



    public PlateauSwingUI(Plateau plateauModel, Partie partieModel,VueGraphiqueSwing vueGraphiqueSwing) {
        this.plateauModele = plateauModel;
        this.partieModel = partieModel;
        this.vueGraphiqueSwing = vueGraphiqueSwing;

        setLayout(new GridLayout(8, 8)); // Création de la grille 8x8
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                // Détermination de la couleur de fond
                //boolean estCaseClaire = ((ligne + colonne) % 2 == 0);

                // Récupère la case du modèle, ne la crée pas !
                var caseModele = plateauModele.getCase(ligne, colonne);
                // Création de la case SwingUI
                var caseUI = new CaseSwingUI(plateauModele.getStyle(), ligne, colonne, caseModele);
                cases[ligne][colonne] = caseUI; // Stockage de la case dans le tableau
                add(caseUI); 
            }
        }
    }

    // pour rafraîchir l'affichage
    public void rafraichir() {
        System.out.println("PlateauSwingUI : rafraichir() appelée");
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                //cases[ligne][colonne].setSelectionnee(false);
                cases[ligne][colonne].updateFromModel();
            }
        }
        revalidate(); // vérifie la disposition des composants
        repaint();
    }

    public CaseSwingUI getCase(int ligne, int colonne) {
        return cases[ligne][colonne];
    }

    public void applyLastMove(Coup coup) {
        // 1) Netoyer ancien
        if (prevOrigine != null) {
            cases[prevOrigine.x][prevOrigine.y].setLastOrigin(false);
            cases[prevOrigine.x][prevOrigine.y].repaint();
        }

        if (prevDestination != null) {
            cases[prevDestination.x][prevDestination.y].setLastDestination(false);
            cases[prevDestination.x][prevDestination.y].repaint();
        }
        

        // 2) Appliquons le nouveau coup
        if (coup != null) {
            Point O = new Point(coup.getOrigine().x, coup.getOrigine().y);
            Point D = new Point(coup.getDestination().x, coup.getDestination().y);
            prevDestination = D;

            cases[O.x][O.y].setLastOrigin(true);
            cases[D.x][D.y].setLastDestination(true);

            prevOrigine = O;
            prevDestination = D;

            // 3) Repaint des cases concernées
            cases[O.x][O.y].repaint();
            cases[D.x][D.y].repaint();
        }
    }
   

    // Méthode à appeler depuis CaseSwingUI quand une case est cliquée
    public void selectionnerCase(CaseSwingUI caseCliquee) {

        Joueur joueur = partieModel.getJoueurActif();

        // Etat 0 pas de case selection au paravant
        if (caseSelectionnee == null) {
            //1) il faut  une piece
            var piece = caseCliquee.getPiece();
            if(piece == null) return; // Pas de pièce sur la case cliquée, rien à faire

            // 2) et que la pièce appartient au joueur actif
            if(piece.getCouleur() != joueur.getCouleur()) return;
            
            caseSelectionnee = caseCliquee;
            caseCliquee.setSelectionnee(true);
            
        // Etat 1 une origine est deja selectionnée
        }else{
            if(caseCliquee == caseSelectionnee ){ //  a revoir
                // re-clique sur la meme case = annuller
                caseCliquee.setSelectionnee(false);
                caseSelectionnee = null;
                return;
            }

            // 2e clic = destination on fabrique le coup
            Coup coup = new Coup(
                caseSelectionnee.getLigne(), caseSelectionnee.getColonne(),
                caseCliquee.getLigne(), caseCliquee.getColonne()
            );

            // Rreset visuel + etat
            caseSelectionnee.setSelectionnee(false);
            caseSelectionnee = null;

            // transmettre le coup a la vue 
            vueGraphiqueSwing.recevoirCoup(coup);

            
        }
    }

}