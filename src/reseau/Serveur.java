package reseau;

import java.io.*;
import java.net.*;

import controleur.ControleurReseau;

public class Serveur {
    private final int port;
    private ObjectInputStream input;   // flux d'entrée : messages reçus depuis le client
    private ObjectOutputStream output; // flux de sortie : messages envoyés vers le client
    private ControleurReseau controleurR; // référence au contrôleur réseau (qui gère la logique côté app)

    public Serveur(int port) {
        this.port = port;
    }

    // Permet de brancher le controleur réseau à ce serveur
    public void setControleur(ControleurReseau controleur) {
        this.controleurR = controleur;
    }

    // Lancer le serveur
    public void demarrer() throws IOException {
        // Crée un serveur qui écoute sur le port choisi
        ServerSocket serveurSocket = new ServerSocket(port);
        System.out.println("Serveur démarré sur le port " + port);

        // Bloque jusqu'à ce qu'un client se connecte
        Socket clientSocket = serveurSocket.accept();
        System.out.println("Client connecté : " + clientSocket.getInetAddress());

        // Initialiser les flux de communication
        output = new ObjectOutputStream(clientSocket.getOutputStream()); // envoi d’objets au client
        input = new ObjectInputStream(clientSocket.getInputStream());   // réception d’objets du client

        // Lancer un thread séparé pour écouter les messages entrants en continu
        new Thread(() -> {
            try {
                while (true) {
                    // Lire un message envoyé par le client
                    MessageJeu message = (MessageJeu) input.readObject();

                    // Dispatch du message vers le controleur réseau
                    traiterMessage(message);
                }
            } catch (Exception e) {
                if (controleurR != null) {
                    // Si le client se déconnecte ou erreur réseau
                    controleurR.onSysteme("Client déconnecté.");
                }
            }
        }, "Serveur-Listener").start();
    }

    // Envoyer un message au client
    public synchronized void envoyerMessageAuClient(MessageJeu message) throws IOException {
        output.writeObject(message); // sérialise l’objet et l’envoie
        output.flush();              // vide le buffer pour s'assurer que c'est transmis
    }

    // Dispatch : traduit un MessageJeu reçu en action pour le controleur réseau
    private void traiterMessage(MessageJeu message) {
        if (controleurR != null) {
            switch (message.getType()) {
                case DEMANDE_DE_PARTIE:
                    controleurR.onDemandePartie(message.getAuteur());
                    break;

                case MOUVEMENT:
                    controleurR.onMouvement(message.getCoup());
                    break;

                case CHAT:
                    controleurR.onChat(message.getAuteur(), message.getContenu());
                    break;

                case SYSTEME_INFO:
                case SYSTEME_WARN:
                case SYSTEME_ERR:
                    controleurR.onSysteme(message.getContenu());
                    break;

                case DEFAITE:
                case VICTOIRE:
                case EGALITE:
                    controleurR.onFinPartie(message.getContenu());
                    break;

                default:
                    controleurR.onSysteme("Message inconnu: " + message.getContenu());
                    break;
            }
        } else {
            System.err.println("Message reçu mais aucun controleur réseau n’est attaché !");
        }
    }

    // Arrêt du serveur : fermeture des flux
    public void arreter() throws IOException {
        if (input != null) input.close();
        if (output != null) output.close();
    }
}
