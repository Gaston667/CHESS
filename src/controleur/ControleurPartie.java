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

    public ControleurPartie(Partie partie, Vue vue, ControleurReseau CR, ModeDeJeu modeDeJeu) {
        this.partie = partie;
        this.vue = vue;
        this.controleurReseau = CR;
        this.modeDeJeu = modeDeJeu;

        // On lie ce ControleurPartie au ControleurReseau
        if (controleurReseau != null) {
            controleurReseau.setControleurPartie(this);
        }
    }

    public void lancerPartie(){
        System.out.println("===============================");
        System.out.println("BIENVENUE AU JEU D'ÉCHECS");
        System.out.println("===============================\n");


        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"Mode de jeu : " + modeDeJeu);
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"Joueurs : " + partie.getJoueurBlanc().getNom() + " (Blancs) vs " + partie.getJoueurNoir().getNom() + " (Noirs)");
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"Les pions Noirs sont en bas. Les pions Blancs sont en haut. \n");

        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"------- La Partie commence ! -------");
        vue.afficherPlateau(partie.getPlateau());
        //System.out.print("\n");
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"C'est au tour de " + partie.getJoueurActif().getNom() + " de jouer.");
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
                    vue.afficherMessage(TypeMessage.SYSTEME_WARN,"Dernier coup : " + joueurPrecedent.getNom() + " a joué " + dernierCoup);
                    
                    // Envoi du coup au serveur si le controleurReseau est initialisé
                    if (controleurReseau != null) {
                        controleurReseau.envoyerCoup(dernierCoup, joueurPrecedent.getNom());
                    }
                }


                vue.afficherMessage(TypeMessage.SYSTEME_INFO,"\nC'est au tour de " + partie.getJoueurActif().getNom() + " de jouer.");
                vue.setTour(partie.getJoueurActif().getNom());
                vue.setScore(partie.getScore());
            } catch (IllegalArgumentException e) {
                vue.afficherMessage(TypeMessage.SYSTEME_ERR,"> Erreur : " + e.getMessage());
            }
        }

        //Fin de la partie
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"La partie est terminée !");
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"Si vous souhaitez rejouer, relancez le programme.");
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"Si vous souhaitez voir l'historique des coups taper 1. Si vous souhaitez voir les captures taper 2.");
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"Merci d'avoir joué !");
    }

    // Coup reçu depuis le reseau
    public void joueCoupReseau(Coup coup){
        try {
            partie.jouerCoup(coup); // Applique le coup
            vue.setDernierCoup(coup); // Met à jour le dernier coup
            vue.afficherPlateau(partie.getPlateau()); // Affiche le plateau
            
            vue.afficherMessage(TypeMessage.SYSTEME_WARN,
                "Dernier coup : " + partie.getJoueurActif().getNom() + " a joué " + coup); // Affiche le dernier coup

            vue.setTour(partie.getJoueurActif().getNom()); // Met à jour le tour
            vue.setScore(partie.getScore()); // Met à jour le score
        } catch (IllegalArgumentException e) {
            vue.afficherMessage(TypeMessage.SYSTEME_ERR,"> Erreur en appliquant un coup reseau : " + e.getMessage());
        }
    }
    // Fin de la partie
    public void finPartieReseau(String message){
        vue.afficherMessage(TypeMessage.SYSTEME_INFO,"Fin de la partie en reseau : " + message);
    }

    public void afficherChat(String auteur, String message){
        vue.afficherMessage(TypeMessage.CHAT, auteur + ": " + message);
    }

    public void afficherMessageSysteme(String message){
        vue.afficherMessage(TypeMessage.SYSTEME_INFO, message);
    }

}
