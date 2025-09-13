package modele.joueur;

import modele.Couleur;
import modele.Coup;
import modele.Plateau;
import vue.Vue;

public class JoueurHumain extends Joueur {
    private Vue vue; 
    private final  boolean reseau;

    public JoueurHumain(Couleur couleur, Vue vue, boolean reseau, String nom) {
        super(couleur, nom);
        this.vue = vue;
        this.reseau = reseau;
    }

    @Override
    public boolean estReseau() {
        return reseau;
    }

    @Override
    public Coup jouerCoup(Plateau plateau) {
        return vue.demanderCoup(plateau); // la vue s'adapte si c'est réseau ou pas
    }

    public void setVue(Vue vue) {
        this.vue = vue;
    }
}

