
import controleur.ControleurPartie;
import controleur.ControleurReseau;
import modele.Couleur;
import modele.ModeDeJeu;
import modele.Partie;
import modele.joueur.Joueur;
import modele.joueur.JoueurHumain;
import vue.Vue;
import vue.VueConsole;
import vue.VueGraphiqueSwing;

public class Main {
    public static void main(String[] args) {
        // Déclaration des variables nécessaires
        Vue vue;
        Joueur joueurBlanc;
        Joueur joueurNoir;
        ModeDeJeu modeDeJeu;
        ControleurReseau controleurReseau = null; // À initialiser si tu utilises le réseau

        int choixMode = 1;        // à remplacer par Scanner plus tard
        int choixAffichage = 3;   // 1: Console, 2: JavaFX, 3: Swing
        String stylePlateau = "BOIS_CLAIR"; // Style du plateau, à remplacer par une entrée utilisateur

        // === Choix du mode de jeu ===
        switch (choixMode) {
            case 1:
                modeDeJeu = ModeDeJeu.HUMAIN_VS_HUMAIN;
                break;
            case 2:
                modeDeJeu = ModeDeJeu.HUMAIN_VS_IA;
                break;
            case 3:
                modeDeJeu = ModeDeJeu.IA_VS_IA;
                break;
            default:
                modeDeJeu = ModeDeJeu.HUMAIN_VS_HUMAIN;
        }

        // === Initialisation des joueurs ===
        vue = new VueConsole(); // Valeur par défaut
        joueurBlanc = new JoueurHumain(Couleur.BLANC, vue, false, "Mathis");
        joueurNoir = new JoueurHumain(Couleur.NOIR, vue, false, "Pellel");

        Partie partie = new Partie(joueurBlanc, joueurNoir, stylePlateau);

        // === Choix du type d'affichage ===
        switch (choixAffichage) {
            case 1:
                vue = new VueConsole();
                break;
            case 2:
                // vue = new VueGraphiqueJavaFX();
                break;
            case 3:
                vue = new VueGraphiqueSwing(partie);
                break;
            default:
                System.out.println("Type d'affichage non reconnu. Console par défaut utilisée.");
                vue = new VueConsole();
        }

        // Réaffecter la vue si les joueurs sont humains
        if (joueurBlanc instanceof JoueurHumain jb) {
            jb.setVue(vue);
        }
        if (joueurNoir instanceof JoueurHumain jn) {
            jn.setVue(vue);
        }

        // === Lancement de la partie ===
        var controleurPartie = new ControleurPartie(partie, vue, controleurReseau, modeDeJeu);
        controleurPartie.lancerPartie();
    }
}
