package User.View;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {
    private int posicio;
    private String nomUsuari;
    private String ultimMissatge;
    private JButton jpPanell;


    public ChatPanel(int posicio, String nomUsuari, String ultimMissatge) {
        this.posicio = posicio;
        this.nomUsuari = nomUsuari;
        this.ultimMissatge = ultimMissatge;
        creaChat();
    }

    private void creaChat() {
        jpPanell = new JButton();
        JLabel nom = new JLabel(nomUsuari);
        JLabel p = new JLabel("Chat with: ");
        JLabel msm = new JLabel(ultimMissatge);

        jpPanell.add(p);
        jpPanell.add(nom);
        jpPanell.add(msm);

        jpPanell.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jpPanell.setBackground(Color.LIGHT_GRAY);

        this.add(jpPanell);
    }

    public void autenticationController(ActionListener controller) {
        jpPanell.addActionListener(controller);
        jpPanell.setActionCommand("Chat");
    }
}
