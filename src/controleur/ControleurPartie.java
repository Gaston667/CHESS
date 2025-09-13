package controleur;

import modele.Coup;
import modele.ModeDeJeu;
import modele.Partie;
import modele.joueur.*;
import reseau.TypeMessage;
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


        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"Mode de jeu : " + modeDeJeu);
        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"Joueurs : " + partie.getJoueurBlanc().getNom() + " (Blancs) vs " + partie.getJoueurNoir().getNom() + " (Noirs)");
        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"Les pions Noirs sont en bas. Les pions Blancs sont en haut. \n");

        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"------- La Partie commence ! -------");
        vue.afficherPlateau(partie.getPlateau());
        //System.out.print("\n");
        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"C'est au tour de " + partie.getJoueurActif().getNom() + " de jouer.");
        boucleDeJeu();
        vue.setTour(partie.getJoueurActif().getNom());
        vue.setScore(partie.getScore());
    }

    public void boucleDeJeu(){
        // Boucle principale de la partie
        while(!partie.estFini()) {
            try {
                Joueur joueurPrecedent = partie.getJoueurActif(); // AVANT le tour
                partie.jouerTour(); // Moteur de jeu
                Coup dernierCoup = partie.getDernierCoup(); 
                vue.setDernierCoup(dernierCoup);
                vue.afficherPlateau(partie.getPlateau());

                if(dernierCoup != null) {
                    vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"Dernier coup : " + joueurPrecedent.getNom() + " a joué " + dernierCoup);
                }

                vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"\nC'est au tour de " + partie.getJoueurActif().getNom() + " de jouer.");
                vue.setTour(partie.getJoueurActif().getNom());
                vue.setScore(partie.getScore());
            } catch (IllegalArgumentException e) {
                vue.afficherMessage(TypeMessage.SYSTEMEROUGE,"> Erreur : " + e.getMessage());
            }
        }

        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"La partie est terminée !");
        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"Si vous souhaitez rejouer, relancez le programme.");
        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"Si vous souhaitez voir l'historique des coups taper 1. Si vous souhaitez voir les captures taper 2.");
        vue.afficherMessage(TypeMessage.SYSTEMEBLEU,"Merci d'avoir joué !");
    }

}
