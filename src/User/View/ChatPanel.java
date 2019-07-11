package User.View;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ChatPanel extends JPanel {
    private int posicio;
    private String nomUsuari;
    private String ultimMissatge;


    public ChatPanel(int posicio, String nomUsuari, String ultimMissatge){
        this.posicio = posicio;
        this.nomUsuari = nomUsuari;
        this.ultimMissatge = ultimMissatge;
        creaChat();
    }

    private void creaChat(){
        JPanel jpPanell = new JPanel(new FlowLayout());
        JLabel nom = new JLabel(nomUsuari);
        JLabel p = new JLabel(":");
        JLabel msm = new JLabel(ultimMissatge);

        jpPanell.add(nom);
        jpPanell.add(p);
        jpPanell.add(msm);

        jpPanell.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jpPanell.setBackground(Color.LIGHT_GRAY);

        this.add(jpPanell);
    }
}
