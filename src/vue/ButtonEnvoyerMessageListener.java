package vue;

import java.awt.event.*;
import javax.swing.*;

public class ButtonEnvoyerMessageListener implements ActionListener {
    private JTextField champ;
    private VueGraphiqueSwing vue;

    public ButtonEnvoyerMessageListener(JTextField champ, VueGraphiqueSwing vue) {
        this.champ = champ;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = champ.getText().trim();
        if (!msg.isEmpty()) {
            vue.ajouterMessageChat("Moi", msg);
            champ.setText(""); // Efface le champ après envoi
        }
    }
}
