# CHESS

Projet universitaire — implémentation d’un jeu d’échecs en Java (IUT de Fontainebleau).

## Dépôts
- **Forge de l’IUT** : `https://forge-fac.example.com/mon-orga/CHESS.git`
- **GitHub** : `https://github.com/Gaston667/CHESS.git`

## Commandes Git

```bash
# 1. Cloner le dépôt depuis la fac
git clone https://forge-fac.example.com/mon-orga/CHESS.git
cd CHESS

# 2. Ajouter GitHub comme remote supplémentaire
git remote add github https://github.com/Gaston667/CHESS.git

# 3. Vérifier les remotes
git remote -v

# 4. Récupérer les dernières modifications depuis la fac
git fetch origin
git pull origin main

# 5. Ajouter et committer des changements
git add .
git commit -m "Message du commit"

# 6. Pousser les changements vers la fac ET GitHub
git push origin main
git push github main

# 7. Pousser toutes les branches et tags (première synchro)
git push origin --all
git push github --all
git push origin --tags
git push github --tags

# 8. Créer et pousser une nouvelle branche
git checkout -b nouvelle-branche
git push origin nouvelle-branche
git push github nouvelle-branche

# 9. Resynchroniser les deux dépôts (mise à jour complète)
git fetch --all --prune
git push origin --all
git push github --all
git push origin --tags
git push github --tags




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
