package vue;

import java.awt.*;
import javax.swing.*;
import modele.Coup;
import modele.Partie;
import modele.Plateau;


public class VueGraphiqueSwing implements Vue {
    private Coup c;
    JFrame fen = new JFrame("CHESS GAME");
    private final PlateauSwingUI plateauUI;
    //private Coup dernierCoup;

    // Journal systeme
    private final JTextArea zoneSystem = new JTextArea(10, 24);
    private final JScrollPane scrollSystem = new JScrollPane(zoneSystem);

    // zone de message for chat
    private final JTextArea zoneChat = new JTextArea(10, 24);
    private final JScrollPane scrollChat = new JScrollPane(zoneChat);
    private final JTextField champChat = new JTextField();
    private final JButton btnEnvoyer = new JButton("Envoyer");

    public VueGraphiqueSwing(Partie partie) {
        fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fen.setResizable(false);
        fen.setLayout(new BorderLayout());

        //Centre : Gestion du plateau
        plateauUI = new PlateauSwingUI(partie.getPlateau(), partie, this); // Passe le modèle
        fen.add(plateauUI, BorderLayout.CENTER);
        

        // Journal systeme (droite, haut)
        zoneSystem.setEditable(false);
        zoneSystem.setLineWrap(true);
        zoneSystem.setWrapStyleWord(true);
        zoneSystem.setBorder(BorderFactory.createTitledBorder("System"));

        // chat
        zoneChat.setEditable(false);
        zoneChat.setLineWrap(true);
        zoneChat.setWrapStyleWord(true);
        zoneChat.setBorder(BorderFactory.createTitledBorder("Chat"));

        JPanel chatBas = new JPanel(new BorderLayout(6,0));
        chatBas.add(champChat, BorderLayout.CENTER);
        chatBas.add(btnEnvoyer, BorderLayout.EAST);

        JPanel chatPanel = new JPanel(new BorderLayout(0, 4));
        chatPanel.add(scrollChat, BorderLayout.CENTER);
        chatPanel.add(chatBas, BorderLayout.SOUTH);

        // Colonne droite : systeme (en haut) et  chat (en bas)
        JPanel coloneDroite = new JPanel( new BorderLayout(0, 8));
        coloneDroite.add(scrollSystem, BorderLayout.CENTER);
        coloneDroite.add(chatPanel, BorderLayout.SOUTH);

        fen.add(coloneDroite, BorderLayout.EAST);

        // Ecouteur pour envoyer le chat
        btnEnvoyer.addActionListener(e -> envoyerChat());
        champChat.addActionListener(e -> envoyerChat());

        fen.setSize(1000, 800); 
        //fen.pack(); // ajuste la taille selon le contenu
        fen.setLocationRelativeTo(null); // centre la fenêtre à l'écran
        fen.setVisible(true);
    }

    public void envoyerChat() {
        String message = champChat.getText().trim();
        if (!message.isEmpty()) {
            zoneChat.append("Vous: " + message + "\n");
            champChat.setText("");
        }
    }

    // API de la Vue
    public void afficherMessageSysteme(String msg) {
        SwingUtilities.invokeLater(() -> {
            zoneSystem.append(msg + "\n");
            zoneSystem.setCaretPosition(zoneSystem.getDocument().getLength());
        });
    }

    public void afficherMessageChat(String auteur, String msg) {
        SwingUtilities.invokeLater(() -> {
            zoneChat.append("[" + auteur + "]: " + msg + "\n");
            zoneChat.setCaretPosition(zoneChat.getDocument().getLength());
        });
    }

    @Override
    public void afficherMessage(String message) {
        // Affichage du message dans la fenêtre graphique
    }

    @Override
    public Coup demanderCoup(Plateau plateau) {
        while(this.c == null) {
            try {
                Thread.sleep(20); // Attendre un court instant avant de vérifier à nouveau
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Coup r = c;
        c = null; // pret pour le coup suivant
        return r;
    }

    @Override
    public void afficherPlateau(Plateau plateau) {
        plateauUI.rafraichir();
    }

    public void recevoirCoup(Coup coup) {
        this.c = coup;
        System.out.println("Coup reçu : " + coup);
    }

    @Override
    public void setDernierCoup(Coup coup) {
        //this.dernierCoup = coup;
        plateauUI.applyLastMove(coup);
    }
}