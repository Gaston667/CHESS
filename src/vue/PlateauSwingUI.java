package vue;
import modele.Case;
import modele.Plateau;
import javax.swing.*; 
import java.awt.*;

public class PlateauSwingUI extends JPanel {
    private final CaseSwingUI[][] cases = new CaseSwingUI[8][8];
    private final Plateau plateauModele;

    public PlateauSwingUI(Plateau plateauModele) {
        this.plateauModele = plateauModele;
        setLayout(new GridLayout(8, 8)); // Création de la grille 8x8
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                // Détermination de la couleur de fond
                boolean claire = ((ligne + colonne) % 2 == 0);
                Color couleur = claire ? new Color(240, 217, 181) : new Color(181, 136, 99);

                // Récupère la case du modèle, ne la crée pas !
                var caseModele = plateauModele.getCase(ligne, colonne);
                // Création de la case SwingUI
                var caseUI = new CaseSwingUI(couleur, ligne, colonne, caseModele);
                cases[ligne][colonne] = caseUI; // Stockage de la case dans le tableau
                add(caseUI); 
            }
        }
    }

    // pour rafraîchir l'affichage
    public void rafraichir() {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                // Mettre à jour l'état de chaque case
                cases[ligne][colonne].updateFromModel();
            }
        }
        revalidate(); // vérifie la disposition des composants
        repaint();
    }

    public CaseSwingUI getCase(int ligne, int colonne) {
        return cases[ligne][colonne];
    s}
