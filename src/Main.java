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

            int choixMode = 1; // à remplacer par Scanner plus tard
            int choixAffichage = 3; // 1: Console, 2: JavaFX, 3: Swing
            String stylePlateau = "BOIS_CLAIR"; // Style de plateau, à remplacer par une entrée utilisateur

            switch (choixMode) {
                case 1 -> modeDeJeu = ModeDeJeu.HUMAIN_VS_HUMAIN;
                case 2 -> modeDeJeu = ModeDeJeu.HUMAIN_VS_IA;
                case 3 -> modeDeJeu = ModeDeJeu.IA_VS_IA;
                default -> {
                    modeDeJeu = ModeDeJeu.HUMAIN_VS_HUMAIN;
                }
            }

            // Initialisation des joueurs et du mode de jeu
            vue = new VueConsole();
            joueurBlanc = new JoueurHumain(Couleur.BLANC, vue, false, "Mathis");
            joueurNoir = new JoueurHumain(Couleur.NOIR, vue, false, "Pellel");

            Partie partie = new Partie(joueurBlanc, joueurNoir, stylePlateau);

            switch (choixAffichage) {
            case 1 -> vue = new VueConsole();
            case 2 -> {
                // vue = new VueGraphiqueJavaFX();
            }
            case 3 -> vue = new VueGraphiqueSwing(partie);
            default -> {
                System.out.println("Type d'affichage non reconnu. Console par défaut utilisée.");
                vue = new VueConsole();
            }
            }

            // Recalification de la vue pour chaque joueur humain
            if(joueurBlanc instanceof  JoueurHumain jb) jb.setVue(vue);
            if(joueurNoir instanceof  JoueurHumain jb) jb.setVue(vue);

            // if (joueurNoir instanceof JoueurHumain) {
            //     JoueurHumain jb = (JoueurHumain) joueurBlanc;
            //     jb.setVue(vue);
            // }
            // if (joueurNoir instanceof JoueurHumain) {
            //     JoueurHumain jn = (JoueurHumain) joueurNoir;
            //     jn.setVue(vue);
            // }


            // lancement de la partie
            var controleurPartie = new ControleurPartie(partie, vue, controleurReseau, modeDeJeu);
            controleurPartie.lancerPartie();
        }
    }
