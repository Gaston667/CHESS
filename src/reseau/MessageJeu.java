package reseau;

import  java.io.*;
import modele.Coup;

public class MessageJeu implements Serializable {
    private  static final long serialVersionUID = 1L;

    private final TypeMessage type; // CHAT, COUP, SYSTEME, DEMANDE_DE_PARTIE, FIN_DE_PARTIE
    private final String auteur; // MOI adversaire ou système
    private final String contenu; // Pour les messages texte Chat/système
    private final Coup coup; //Pour les mouvements sinon null

    public MessageJeu(TypeMessage type, String auteur, String contenu, Coup coup) {
        this.type = type;
        this.auteur = auteur;
        this.contenu = contenu;
        this.coup = coup;
    }

    public TypeMessage getType() {
        return type;
    }

    public String getContenu() {
        return contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public Coup getCoup() {
        return coup;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + auteur + " : " + contenu + (coup != null ? " (Coup: " + coup + ")" : "");
    }
    
}
