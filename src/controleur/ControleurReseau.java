package controleur;
import modele.Coup;
import reseau.*;


public class ControleurReseau {
    private final boolean isServer; // Vrai si on joue en tant que serveur
    private Client client;  // si joueur rejoint une partie
    private Serveur serveur; // si joueur héberge une partie
    private ControleurPartie controleurPartie; // Logique du jeu (plateau, règles, etc.)

    public ControleurReseau(boolean isServer){
        this.isServer = isServer;
    }

    public void setControleurPartie(ControleurPartie controleurPartie){
        this.controleurPartie = controleurPartie;
    }

    // Initialisation du serveur
    public void demarrerServeur(int port) throws Exception{
        serveur = new Serveur(port);
        serveur.setControleur(this); // Branchement du controleur réseau au serveur
        serveur.demarrer();
    }

    // Initialisation du client
    public void demarrerClient(String host, int port) throws Exception{
        client = new Client(host, port);
        client.setControleur(this); // Branchement du controleur réseau au client
        client.connecter();
    }

    public void arreter() throws Exception{
        if (isServer) {
            serveur.arreter();
        } else {
            client.deconnecter();
        }
    }

    // Emission
    public void envoyerDemandePartie(String joueur) {
        envoyer(new MessageJeu(TypeMessage.DEMANDE_DE_PARTIE, joueur, "Demande de partie", null));
    }

    public void envoyerCoup(Coup coup, String joueur) {
        envoyer(new MessageJeu(TypeMessage.MOUVEMENT, joueur, null, coup));
    }

    public void envoyerChat(String joueur, String texte) {
        envoyer(new MessageJeu(TypeMessage.CHAT, joueur, texte, null));
    }

    public void envoyerSysteme(String texte, TypeMessage type) {
        envoyer(new MessageJeu(type, "SYSTEME", texte, null));
    }
    public void envoyerFin(String texte, TypeMessage type) {
        envoyer(new MessageJeu(type, "SYSTEME", texte, null));
    }

    private void envoyer(MessageJeu msg) {
        try {
            if (isServer && serveur != null) {
                serveur.envoyerMessageAuClient(msg);
            } else if (!isServer && client != null) {
                client.envoyerMessage(msg);
            }
        } catch (Exception e) {
            onSysteme("Erreur d’envoi : " + e.getMessage());
        }
    }



    // --- Réception (appelé par Serveur/Client) ---
    public void onDemandePartie(String auteur) {
        onSysteme("Demande de partie de " + auteur);
        // Ici, le serveur peut accepter ou refuser
        if (isServer && controleurPartie != null) {
            controleurPartie.lancerPartie();
        }
    }

    public void onMouvement(Coup coup) {
        onSysteme("Coup reçu : " + coup);
        if (controleurPartie != null) {
            controleurPartie.joueCoupReseau(coup);
        }
    }

    public void onChat(String auteur, String texte) {
        System.out.println("Chat [" + auteur + "]: " + texte);
        if (controleurPartie != null) {
            controleurPartie.afficherChat(auteur, texte);
        }
    }

    public void onSysteme(String texte) {
        System.out.println("[SYSTEME] " + texte);
        if (controleurPartie != null) {
            controleurPartie.afficherMessageSysteme(texte);
        }
    }

    public void onFinPartie(String texte) {
        System.out.println("[FIN] " + texte);
        if (controleurPartie != null) {
            controleurPartie.finPartieReseau(texte);
        }
    }
}