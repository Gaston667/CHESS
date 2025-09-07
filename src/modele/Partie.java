package modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import modele.joueur.*;
import modele.piece.Piece;

public class Partie{
    private Plateau plateau;
    private int nombreCoups;
    public ModeDeJeu modeDeJeu;
    
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private Joueur joueurActif;

    private List<Coup> historique;
    private List<Piece> listeCapturesBlancs;
    private List<Piece> listeCapturesNoires;

    public Partie(Joueur joueurBlanc, Joueur joueurNoir, String stylePlateau) {
        plateau = new Plateau(stylePlateau);
        nombreCoups = 0;
        historique = new ArrayList<>();
        listeCapturesBlancs = new ArrayList<>();
        listeCapturesNoires = new ArrayList<>();

        this.joueurBlanc = joueurBlanc;
        this.joueurNoir = joueurNoir;
        this.joueurActif = joueurBlanc;
    }

    public void jouerTour() {
        // Logique pour jouer un tour
        Coup coup = joueurActif.jouerCoup(plateau); 
        Piece piece = plateau.getCase((int)coup.getOrigine().getX(), (int)coup.getOrigine().getY()).getPiece();

        // System.out.println("");
        // System.out.println("Origine du coup : " + coup.getOrigine());
        // System.out.println("Destination du coup : " + coup.getDestination());
        // System.out.println("Contenu de la case d'origine : " + plateau.getCase(coup.getOrigine()).getPiece().getSymbole());


        if(piece == null) {
            throw new IllegalArgumentException("Aucune pièce à la position d'origine.");
        }
        if(piece.getCouleur() != joueurActif.getCouleur()) {
            throw new IllegalArgumentException("Ce n'est pas votre pièce.");
        }
        if(!piece.estDeplacementValide(coup, plateau)) {
            System.out.println("ICI");
            throw new IllegalArgumentException("Déplacement invalide.");      
        }
        // System.out.println("sortie dans partie");

        // Verifie si une capture a lieu
        Point dest = coup.getDestination();
        Case caseDestination = plateau.getCase((int) dest.getX(), (int) dest.getY());

        if(caseDestination.getPiece() != null) {
            Piece pieceCapturee = caseDestination.getPiece();
            if(pieceCapturee.getCouleur() == joueurActif.getCouleur()) {
                throw new IllegalArgumentException("Vous ne pouvez pas capturer votre propre pièce.");
            }
            
            // Ajouter la pièce capturée à la liste des captures
            if(pieceCapturee.getCouleur() == Couleur.BLANC) {
                listeCapturesBlancs.add(pieceCapturee);
            } else {
                listeCapturesNoires.add(pieceCapturee);
            }
        }

        // Déplacer la pièce
        plateau.deplacer(coup);
        // Ajouter le coup à l'historique
        ajouterCoup(coup);
        // Changer de joueur
        changerJoueur();

    }

    public boolean estFini() {
        // Logique pour vérifier si la partie est terminée
        return false;
    }

    public void changerJoueur() {
        // Logique pour changer le joueur courant
        // Par exemple, si le joueur courant est blanc, le changer en noir et vice versa
        if (joueurActif == joueurBlanc) {
            joueurActif = joueurNoir;
        } else {
            joueurActif = joueurBlanc;
        }
    }

    public void ajouterCoup(Coup coup) {
        // Ajouter le coup à l'historique
        historique.add(coup);
        nombreCoups++;
    }

    public Joueur getJoueurActif() {
        return joueurActif;
    }

    public List<Piece> getCapturesParBlanc() {
        return listeCapturesBlancs;
    }

    public List<Piece> getCapturesParNoir() {
        return listeCapturesNoires;
    }

    public List<Coup> getHistorique() {
        return historique;
    }
    public Coup getDernierCoup() {
        if(this.historique.isEmpty()) {
            return null;
        }
        return this.historique.get(this.historique.size() - 1);
    }

    public int getNombreCoups() {
        return nombreCoups;
    }
    public Plateau getPlateau() {
        return plateau;
    }

    public Joueur getJoueurBlanc() {
        return joueurBlanc;
    }

    public Joueur getJoueurNoir() {
        return joueurNoir;
    }

}


