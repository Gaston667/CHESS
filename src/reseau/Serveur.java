package reseau;

import java.io.*;
import java.net.*;

import  controleur.ControleurReseau;


public class Serveur {
    private final int port;
    private ObjectInputStream input; // flux d'entrée qui vient du client
    private ObjectOutputStream output; // flux de sortie qui va vers le client
    private ControleurReseau controleur;

    public Serveur(int port){
        this.port = port;
    }

    public void setControleur(ControleurReseau controleur) {
        this.controleur = controleur;
    }

    public void demarrer() throws IOException{
        ServerSocket serveurSocket = new ServerSocket(port);
        System.out.println("Serveur démarré sur le port " + port);

        // Accepter une connexion seule client
        Socket clientSocket = serveurSocket.accept();
        System.out.println("Client connecté : " + clientSocket.getInetAddress());

        // Initialiser les flux de communication
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());

        // Démarrer un thread pour écouter les messages entrants
        new Thread(()-> {
            try {
                while(true) {
                    MessageJeu message = (MessageJeu) input.readObject();
                    traiterMessage(message);
                }
            } catch (Exception e) {
                if(controleur != null) {
                    controleur.onSyteme("Client déconnecté.");
                }
            }
        })

    }
    
}
