package vue;

import java.awt.event.*;
import javax.swing.*;

public class ToucheEntrerEnvoyerMessageListener implements KeyListener {
    private JTextField champ;
    private VueGraphiqueSwing vue;

    public ToucheEntrerEnvoyerMessageListener(JTextField champ, VueGraphiqueSwing vue) {
        this.champ = champ;
        this.vue = vue;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String msg = champ.getText().trim();
            if (!msg.isEmpty()) {
                vue.ajouterMessageChat("Moi", msg);
                champ.setText(""); // Efface le champ après envoi
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
