package vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


import modele.Case;
import modele.piece.Piece;
import modele.Couleur;

public class CaseSwingUI extends JPanel implements MouseListener{
    

    private Color couleurCaseClaire;
    private Color couleurCaseFoncee;
    private Color couleurCaseSelection;
    private Color couleurCasePrecedente;  


    private final int ligne;
    private final  int colonne;

    private boolean estDernierCoupOrigine = false;
    private boolean estDernierCoupDestination = false;

    private boolean selectionnee = false;

    private final JLabel label = new JLabel("", SwingConstants.CENTER);
    private final Case caseModele; // reference vers la case de le modele

    public CaseSwingUI(String style, int ligne, int colonne, Case caseModele) {
        switch (style) {
            case "RETRO_BOIS" -> {
                // Style Bois rétro :
                couleurCaseClaire = new Color(222, 184, 135); // Burlywood (bois clair rétro)
                couleurCaseFoncee = new Color(139, 69, 19);   // SaddleBrown (bois foncé rétro)
                couleurCaseSelection = new Color(205, 133, 63); // Peru (orange/brun vieilli)
            }
            case "METAL_FUTURISTE" -> {
                // Style métallique Futuriste
                couleurCaseClaire = new Color(192, 192, 192); // Silver
                couleurCaseFoncee = new Color(105, 105, 105); // DimGray
                couleurCaseSelection = new Color(0, 255, 255); // Cyan flashy (effet néon)
            }
            case "BOIS_CLAIR" -> {
                // Style Bois clair
                couleurCaseClaire = new Color(240, 218, 181);   // Beige clair
                couleurCaseFoncee = new Color(181, 136, 99);    // Marron clair
                couleurCaseSelection = new Color(209, 83, 71); // Rouge-orangé
                couleurCasePrecedente = new Color(255, 112, 99); // Rouge clair
            }
            case "JAUNE_CHALEUREUX" -> {
                // Style Jaune Chaleureux
                couleurCaseClaire = new Color(253, 231, 76); // Jaune vif
                couleurCaseFoncee = new Color(227, 101, 91); // Rouge doux
                couleurCaseSelection = new Color(248, 161, 87); // Orange doux
            }
            case "VINTAGE_PASTEL" -> {
                // Style vintage pastel
                couleurCaseClaire = new Color(246, 229, 189); // Beige pastel
                couleurCaseFoncee = new Color(249, 125, 125); // Rose brique clair
                couleurCaseSelection = new Color(200, 90, 90); // Rouge passé
            }
            default -> {
                couleurCaseClaire = new Color(255, 255, 255); // Blanc
                couleurCaseFoncee = new Color(200, 200, 200); // Gris
                couleurCaseSelection = new Color(100, 100, 100); // Gris foncé
            }

        }

        this.ligne = ligne;
        this.colonne = colonne;
        this.caseModele = caseModele;

        // Gestion de la couleur
        if ((ligne + colonne) % 2 == 0) {
            setBackground(this.couleurCaseClaire); // couleur claire
        } else {
            setBackground(this.couleurCaseFoncee); // couleur foncée
        }
       
        setOpaque(true);
        setPreferredSize(new Dimension(64, 64));
        setLayout(new BorderLayout());

        // Ajout du label à la case
        label.setFont(new Font("SansSerif", Font.BOLD, 60));
        add(label, BorderLayout.CENTER);

        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        this.updateFromModel(); // Mise à jour de l'affichage à partir du modèle 


        //Ajoutons l'ecouteur 
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Container parent = this.getParent();
        while(parent != null && !(parent instanceof PlateauSwingUI)) {
            parent = parent.getParent();
        }
        if (parent instanceof PlateauSwingUI plateau) {
            plateau.selectionnerCase(this);
        }
        //System.out.println("Case cliquée : " + ligne + ", " + colonne);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    
    
    @Override
    protected void paintComponent(Graphics pinceaux) {
        super.paintComponent(pinceaux); // Pour effacer l'ancien objet

        Graphics2D g2d = (Graphics2D) pinceaux.create();
        // Appliquer l'anti-aliasing pour des bords plus doux
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Couleur de fond (jaune)
        //System.out.println("Selectionner debug: "+selectionnee );
        if(selectionnee){
            this.setBackground(couleurCaseSelection);

            // Pour le truc de dégradé
            // //Dégradé radial: claire au centre, foncé aux bords
            // Color centre = new Color(255, 150, 130); // plus claire au centre
            // Color bord = new Color(200, 50,  40); // plus foncé aux bords
            // float[] dist = {0.0f, 1.0f};
            // Color[] colors = {centre, bord};
            // RadialGradientPaint gradient = new RadialGradientPaint(
            //     w / 2f, h / 2f, Math.max(w, h) / 2f, // centre X, centre Y, rayon
            //     dist,                    // fractions
            //     colors                   // couleurs
            // );

            // g2d.setPaint(gradient);
            // g2d.fillRect(0, 0, w, h);

            // //petit contour plus sombre
            // g2d.setColor(new Color(150, 30, 30));
            // g2d.drawRect(0, 0, w-1, h-1);

            

        }else{
            // Couleur normale
            //this.setBackground(this.couleurdeFond);
            if ((ligne + colonne) % 2 == 0) {
                g2d.setColor(couleurCaseClaire);
            } else {
                g2d.setColor(couleurCaseFoncee);
            }
            g2d.fillRect(0, 0, w, h);
           
        }

        // Surbrillance du dernier coup
        if(estDernierCoupOrigine) {
            g2d.setColor(couleurCasePrecedente);
            g2d.fillRect(0, 0, w, h);
        }

        if(estDernierCoupDestination) {
            g2d.setColor(couleurCaseSelection);
            g2d.fillRect(0, 0, w, h);
        }

        g2d.dispose(); // Libération des ressources graphiques
    }




    // Rafrachit le contenu visuel depuis le model
    public void updateFromModel(){
        Piece p = (caseModele != null) ? caseModele.getPiece(): null;
        label.setText(toUnicode(p));
        if(p != null) {
            label.setForeground(new Color(0,0,0));
            if (caseModele.getPiece().getCouleur() == Couleur.BLANC) {
                label.setForeground(new Color(250,250,250));
            }else{
                label.setForeground(new Color(30,30,30));
            }
        }
        repaint(); // double appel car setForeground le fais deja
    }

    private String toUnicode(Piece piece) {
        // Conversion du symbole en caractère Unicode
        if (piece == null) return "";
        String symbole = piece.getSymbole();
        return switch (symbole) {
            case "BP" -> "♟";
            case "BC" -> "♞";
            case "BF" -> "♝";
            case "BT" -> "♜";
            case "BR" -> "♛";
            case "BO" -> "♚";
            case "NP" -> "♟";
            case "NC" -> "♞";
            case "NF" -> "♝";
            case "NT" -> "♜";
            case "NR" -> "♛";
            case "NO" -> "♚";
            default -> symbole;
        };
    }


    // Getters/setters utiles pour l'IHM
    public Piece getPiece() {
       return (caseModele != null) ? caseModele.getPiece() : null;
    }
    public void setPiece(Piece piece) {
        if(caseModele !=null ){
            caseModele.setPiece(piece);
        }
        updateFromModel();
    }

    // Getters pour l'état de sélection
    public boolean isSelectionnee() { return selectionnee; }
    public void setSelectionnee(boolean selectionnee) {
        this.selectionnee = selectionnee;
        //this.setBorder(BorderFactory.createLineBorder(selectionnee ? new Color(248, 161, 87) : Color.DARK_GRAY,selectionnee ? 3 : 1));
        // Repaint pour mettre à jour la couleur de fond
        repaint();
    }

    // Gestion du dernier coup
    public void setLastOrigin(boolean estDernierCoupOrigine) {
        this.estDernierCoupOrigine = estDernierCoupOrigine;
        repaint();
    }
    public void setLastDestination(boolean estDernierCoupDestination) {
        this.estDernierCoupDestination = estDernierCoupDestination;
        repaint();
    }

    // Getters pour la position de la case
    public int getLigne() { return ligne; }
    public int getColonne() { return colonne; }
}


