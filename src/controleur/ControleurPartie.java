package controleur;

import modele.joueur.*;
import modele.Coup;
import modele.Partie;
import modele.Plateau;
import modele.ModeDeJeu;
import vue.Vue;


public class ControleurPartie {
    private Partie partie;
    private Vue vue;
    private ControleurReseau controleurReseau;
    private ModeDeJeu modeDeJeu;

    public ControleurPartie(Partie partie, Vue vue, ControleurReseau controleurReseau, ModeDeJeu modeDeJeu) {
        this.partie = partie;
        this.vue = vue;
        this.controleurReseau = controleurReseau;
        this.modeDeJeu = modeDeJeu;
    }

    public void lancerPartie(){
        System.out.println("===============================");
        System.out.println("BIENVENUE AU JEU D'ÉCHECS");
        System.out.println("===============================\n");


        vue.afficherMessage("Mode de jeu : " + modeDeJeu);
        vue.afficherMessage("Joueurs : " + partie.getJoueurBlanc().getNom() + " (Blancs) vs " + partie.getJoueurNoir().getNom() + " (Noirs)");
        vue.afficherMessage("Les pions Noirs sont en bas. Les pions Blancs sont en haut. \n");

        vue.afficherMessage("------- La Partie commence ! -------");
        vue.afficherPlateau(partie.getPlateau());
        //System.out.print("\n");
        vue.afficherMessage("C'est au tour de " + partie.getJoueurActif().getNom() + " de jouer.");
        boucleDeJeu();
    }

    public void boucleDeJeu(){
        // Boucle principale de la partie
        while(!partie.estFini()) {
            try {
                Joueur joueurPrecedent = partie.getJoueurActif(); // AVANT le tour
                partie.jouerTour(); // Moteur de jeu
                Coup dernierCoup = partie.getHistorique().get(partie.getHistorique().size() - 1);
                vue.afficherPlateau(partie.getPlateau());
                //System.out.println("");
                vue.afficherMessage("Dernier coup : " + joueurPrecedent.getNom() + " a joué " + dernierCoup);
                vue.afficherMessage("\nC'est au tour de " + partie.getJoueurActif().getNom() + " de jouer.");
            } catch (IllegalArgumentException e) {
                vue.afficherMessage("> Erreur : " + e.getMessage());
            }
        }

        vue.afficherMessage("La partie est terminée !");
        vue.afficherMessage("Si vous souhaitez rejouer, relancez le programme.");
        vue.afficherMessage("Si vous souhaitez voir l'historique des coups taper 1. Si vous souhaitez voir les captures taper 2.");
        vue.afficherMessage("Merci d'avoir joué !");
    }

}
