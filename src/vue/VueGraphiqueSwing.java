package vue;

import java.awt.*;
import javax.swing.*;
import jdk.jfr.Percentage;
import modele.Coup;
import modele.Partie;
import modele.Plateau;
import reseau.TypeMessage;

public class VueGraphiqueSwing implements Vue {
    private Coup c;
    private final JFrame fen;
    private final PlateauSwingUI plateauUI;

    // Colonne gauche (messages dynamiques)
    private final JPanel panelMessages = new JPanel();

    // Colonne droite (infos dynamiques)
    private final JLabel lblDernierCoup = new JLabel("Dernier coup : ");
    private final JLabel lblTour = new JLabel("Tour : ");
    private final JLabel lblScore = new JLabel("Score : ");
    private final JPanel panelSysteme = new JPanel();

     private JScrollPane scrollMessages;

    public VueGraphiqueSwing(Partie partie) {
        fen = new JFrame("CHESS GAME");
        fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fen.setResizable(true);
        fen.setLayout(new BorderLayout());

        //  Plateau 
        plateauUI = new PlateauSwingUI(partie.getPlateau(), partie, this);
        plateauUI.setPreferredSize(new Dimension(500, 600));

        //  Colonne droite (infos + messages système) 
        JPanel droite = new JPanel(new BorderLayout());

        // Haut -> infos
        JPanel panelInfos = new JPanel();
        panelInfos.setLayout(new BoxLayout(panelInfos, BoxLayout.Y_AXIS));
        panelInfos.setBorder(BorderFactory.createTitledBorder("Infos"));
        panelInfos.add(lblDernierCoup);
        panelInfos.add(Box.createVerticalStrut(10));
        panelInfos.add(lblTour);
        panelInfos.add(Box.createVerticalStrut(10));
        panelInfos.add(lblScore);

        droite.add(panelInfos, BorderLayout.NORTH);

        // Bas -> messages système
        panelSysteme.setLayout(new BoxLayout(panelSysteme, BoxLayout.Y_AXIS));
        JScrollPane scrollSysteme = new JScrollPane(panelSysteme);
        scrollSysteme.setBorder(BorderFactory.createTitledBorder("Système"));
        droite.add(scrollSysteme, BorderLayout.CENTER);

        //  Colonne gauche (messages serveur + input) 
        panelMessages.setLayout(new BoxLayout(panelMessages, BoxLayout.Y_AXIS));
        panelMessages.setPreferredSize(new Dimension(200, 0));
        panelMessages.setMinimumSize(new Dimension(200, 0));
        scrollMessages = new JScrollPane(panelMessages);
        scrollMessages.setBorder(BorderFactory.createTitledBorder("Messages (serveur)"));

        // Champ de saisie et bouton
        JTextField champMessage = new JTextField();
        JButton btnEnvoyer = new JButton("Envoyer");
        btnEnvoyer.addActionListener(new ButtonEnvoyerMessageListener(champMessage, this));
        champMessage.addKeyListener(new ToucheEntrerEnvoyerMessageListener(champMessage, this));

        // Panneau bas (saisie + bouton)
        JPanel basGauche = new JPanel(new BorderLayout(5, 0));
        basGauche.add(champMessage, BorderLayout.CENTER);
        basGauche.add(btnEnvoyer, BorderLayout.EAST);

        // Panneau global gauche
        JPanel gauche = new JPanel(new BorderLayout());
        gauche.add(scrollMessages, BorderLayout.CENTER);
        gauche.add(basGauche, BorderLayout.SOUTH);

        //  JSplitPane centre (plateau + droite) 
        JSplitPane splitCentre = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            plateauUI,
            droite
        );
        splitCentre.setDividerLocation(800);
        splitCentre.setResizeWeight(0.7);
        splitCentre.setOneTouchExpandable(true);

        //  JSplitPane global (gauche + centre) 
        JSplitPane splitGlobal = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            gauche,
            splitCentre
        );
        splitGlobal.setDividerLocation(200);
        splitGlobal.setResizeWeight(0.0); // tout l'espace supplémentaire va à droite
        splitGlobal.setOneTouchExpandable(true);

        // Ajout final
        fen.add(splitGlobal, BorderLayout.CENTER);

        fen.setSize(1400, 800);
        fen.setLocationRelativeTo(null);
        fen.setVisible(true);
    }

    private JPanel createMessageBlock(String auteur, String text) {
        JLabel lbl = new JLabel(auteur + " : " + text);

        JPanel block = new JPanel(new BorderLayout());
        block.add(lbl, BorderLayout.CENTER);

        // Couleur selon auteur
        Color bg, fg, border;
        switch (auteur.toLowerCase()) {
            case "moi" -> {
                bg = new Color(200, 255, 200); // vert clair
                fg = Color.BLACK;
                border = Color.GREEN.darker();
            }
            case "serveurbleu" -> {
                bg = new Color(220, 235, 255); // bleu clair
                fg = Color.BLUE.darker();
                border = Color.BLUE;
            }
            case "serveurrouge" -> {
                bg = new Color(255, 220, 220); // rouge clair
                fg = Color.RED.darker();
                border = Color.RED;
            }
            case "serveurorange" -> {
                bg = new Color(255, 220, 220); // orange clair
                fg = Color.ORANGE.darker();
                border = Color.ORANGE;
            }
            default -> { // adversaire
                bg = new Color(255, 220, 220); // rouge clair
                fg = Color.RED.darker();
                border = Color.RED;
            }
        }

        block.setBackground(bg);
        lbl.setForeground(fg);
        block.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(border, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Taille compacte
        block.setPreferredSize(new Dimension(0, 35));
        block.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        block.setMinimumSize(new Dimension(100, 35));

        return block;
    }



    // Ajouter message serveur (colonne gauche)
    public void ajouterMessageChat(String auteur, String message) {
        SwingUtilities.invokeLater(() -> {
            JPanel block = createMessageBlock(auteur, message);
            panelMessages.add(block);
            panelMessages.add(Box.createVerticalStrut(15));
            panelMessages.revalidate();
            panelMessages.repaint();

            // Déplacer la scrollbar après que Swing ait fini la mise en page
            SwingUtilities.invokeLater(() -> {
                JScrollBar verticalBar = scrollMessages.getVerticalScrollBar();
                verticalBar.setValue(verticalBar.getMaximum());
            });
        });
    }


    // Ajouter message système (colonne droite bas)
    public void ajouterMessageSysteme(String auteur, String message) {
        SwingUtilities.invokeLater(() -> {
            JPanel block = createMessageBlock(auteur, message);
            panelSysteme.add(block);
            panelSysteme.add(Box.createVerticalStrut(15));
            panelSysteme.revalidate();
            panelSysteme.repaint();
        });

         // Déplacer la scrollbar après que Swing ait fini la mise en page
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = scrollMessages.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

    // API de la Vue
    @Override
    public void afficherMessage(TypeMessage type, String message) {
        switch (type) {
        case CHAT : ajouterMessageChat("", message); break;
        case SYSTEME_INFO : ajouterMessageSysteme("serveurBleu", message); break;
        case SYSTEME_WARN : ajouterMessageSysteme("serveurOrange", message); break;
        case SYSTEME_ERR : ajouterMessageSysteme("serveurRouge", message); break;
        default : System.out.println("[INFO] " + message);
        }
    }

    @Override
    public Coup demanderCoup(Plateau plateau) {
        while(this.c == null) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Coup r = c;
        c = null; // prêt pour le coup suivant
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
        lblDernierCoup.setText("Dernier coup : " + coup);
        plateauUI.applyLastMove(coup);
    }

    @Override
    public void setTour(String tour) {
        lblTour.setText("Tour : " + tour);
    }

    @Override
    public void setScore(String score) {
        lblScore.setText("Score : " + score);
    }
}
