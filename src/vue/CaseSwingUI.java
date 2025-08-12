package vue;

import modele.piece.Piece;
import java.awt.*;
import java.lang.ref.Reference;
import modele.Case;
import javax.swing.*;

public class CaseSwingUI extends JPanel {
    private int ligne;
    private int colonne;

    private Color couleurdeFond; 
    private boolean selectionnee = false;

    private final JLabel label = new JLabel("", SwingConstants.CENTER);
    private final Case caseModele; // reference vers la case de le modele

    public CaseSwingUI(Color couleurdeFond, int ligne, int colonne, Case caseModele) {
        this.couleurdeFond = couleurdeFond;
        this.ligne = ligne;
        this.colonne = colonne;
        this.caseModele = caseModele;

        setOpaque(true);
        setBackground(couleurdeFond);
        setPreferredSize(new Dimension(64, 64));
        setLayout(new BorderLayout());

        // Ajout du label à la case
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(label, BorderLayout.CENTER);

        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        this.updateFromModel(); // Mise à jour de l'affichage à partir du modèle        
    }

    @Override
    protected void paintComponent(Graphics pinceaux) {
        super.paintComponent(pinceaux); // Pour effacer l'ancien objet

        // Couleur de fond (jaune)
        if(selectionnee){
            this.setBackground(new Color(255, 215, 0)); // Jaune doux sélectionnée
        }else{
            this.setBackground(this.couleurdeFond);
        }
    }

    // Rafrachit le contenu visuel depuis le model
    public void updateFromModel(){
        Piece p = (caseModele != null) ? caseModele.getPiece(): null;
        label.setText(toUnicode(p));
        label.setForeground(Color.BLACK);
        repaint();
    }

    private String toUnicode(Piece piece) {
        // Conversion du symbole en caractère Unicode
        if (piece == null) return "";
        String symbole = piece.getSymbole();
        return switch (symbole) {
            case "BP" -> "♙";
            case "BC" -> "♘";
            case "BF" -> "♗";
            case "BT" -> "♖";
            case "BQ" -> "♕";
            case "BK" -> "♔";
            case "NP" -> "♟";
            case "NC" -> "♞";
            case "NF" -> "♝";
            case "NT" -> "♜";
            case "NQ" -> "♛";
            case "NK" -> "♚";
            default -> symbole;
        };
    }


    // Getters/setters utiles pour l'IHM
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }

    public boolean isSelectionnee() { return selectionnee; }

    public void setSelectionnee(boolean selectionnee) {
        this.selectionnee = selectionnee;
        this.setBorder(BorderFactory.createLineBorder(selectionnee ? Color.ORANGE : Color.DARK_GRAY,selectionnee ? 4 : 2));
        // Repaint pour mettre à jour la couleur de fond
        repaint();
    }
    public int getLigne() { return ligne; }
    public int getColonne() { return colonne; }
}