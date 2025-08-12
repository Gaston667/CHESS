src/
├── modele/               Couche Modèle (logique du jeu)
│   ├── Partie.java
│   ├── Plateau.java
│   ├── Case.java
│   ├── Coup.java
│   ├── Couleur.java              enum
│   ├── ModeDeJeu.java            enum
│   ├── piece/
│   │   ├── Piece.java            abstraite
│   │   ├── Roi.java
│   │   ├── Reine.java
│   │   ├── Tour.java
│   │   ├── Cavalier.java
│   │   ├── Fou.java
│   │   └── Pion.java
│   ├── joueur/
│   │   ├── Joueur.java           abstraite
│   │   ├── JoueurHumain.java
│   │   ├── JoueurIA.java
│   │   └── IA.java
│
├── vue/                    Couche Vue (console ou graphique)
│   ├── Vue.java                  interface
│   ├── VueConsole.java
│   ├── VueGraphique.java
│
├── controleur/           Couche Contrôleur
│   ├── ControleurPartie.java
│   └── ControleurReseau.java
│
├── reseau/               Réseau (mode en ligne)
│   ├── MessageJeu.java
│   ├── TypeMessage.java         enum
│   ├── Serveur.java
│   └── Client.java
│
└── Main.java             Point d'entrée du jeu
