package vue;

import javax.swing.JFrame;
import java.awt.*;

import modele.Coup;
import modele.Plateau;


public class VueGraphiqueSwing implements Vue {
    JFrame fen = new JFrame("CHESS GAME");
    private final PlateauSwingUI plateauUI;

    public VueGraphiqueSwing(Plateau plateau) {
        fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fen.setResizable(false);

        plateauUI = new PlateauSwingUI(plateau); // Passe le modèle
        fen.add(plateauUI, BorderLayout.CENTER);

        fen.setSize(800, 800); 
        //fen.pack(); // ajuste la taille selon le contenu
        fen.setLocationRelativeTo(null); // centre la fenêtre à l'écran
        
        fen.setVisible(true);
    }

    @Override
    public void afficherMessage(String message) {
        // Affichage du message dans la fenêtre graphique
    }

    @Override
    public Coup demanderCoup(Plateau plateau) {
        // Demande un coup au joueur
        return null;
    }

    @Override
    public void afficherPlateau(Plateau plateau) {
        plateauUI.rafraichir();
    }

}