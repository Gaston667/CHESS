package reseau;

import java.io.*;
import java.net.*;

import controleur.ControleurReseau;

public class Client {
    private final String host;         // Adresse IP du serveur
    private final int port;            // Port du serveur
    private ObjectInputStream input;   // flux d'entrée (messages du serveur)
    private ObjectOutputStream output; // flux de sortie (messages envoyés au serveur)
    private ControleurReseau controleurR;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // Associer le controleur réseau au client
    public void setControleur(ControleurReseau controleur) {
        this.controleurR = controleur;
    }

    // Connexion au serveur
    public void connecter() throws IOException {
        Socket clientSocket = new Socket(host, port);
        System.out.println("Connecté au serveur " + host + ":" + port);

        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());

        // Thread d'écoute des messages du serveur
        new Thread(() -> {
            try {
                while (true) {
                    // Lire un message reçu du serveur
                    MessageJeu message = (MessageJeu) input.readObject();
                    traiterMessage(message);
                }
            } catch (Exception e) {
                if (controleurR != null) {
                    controleurR.onSysteme("Serveur déconnecté.");
                }
            }
        }, "Client-Listener").start();
    }

    // Envoyer un message au serveur
    public synchronized void envoyerMessage(MessageJeu message) throws IOException {
        output.writeObject(message);
        output.flush();
    }

    // Dispatch : traiter les messages du serveur
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

    // Fermer proprement
    public void deconnecter() throws IOException {
        if (input != null) input.close();
        if (output != null) output.close();
    }
}
