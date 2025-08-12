package modele.joueur;

import vue.Vue;
import modele.Couleur;
import modele.Coup;
import modele.Plateau;

public class JoueurIA extends Joueur {
    private Vue vue; 
    private IA ia;

    public JoueurIA(Couleur couleur, Vue vue) {
        super(couleur, "IA");
        this.vue = vue;
        this.ia = new IA(3); // Valeur par défaut pour la profondeur maximale
    }

    @Override
    public Coup jouerCoup(Plateau plateau) {
        return ia.meilleurCoup(plateau);
    }

    @Override
    public boolean estReseau() {
        return false;
    }

}
