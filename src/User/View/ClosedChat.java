package User.View;

import User.Model.Mensaje;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClosedChat extends JPanel {

    private JLabel jlSender, jlMessage, jlTime;
    private JButton jbSee = new JButton();

    //public ClosedChat(Mensaje msg, ActionListener actionListener) {
    public ClosedChat(Mensaje msg) {
        try {

            Border auxBorder = BorderFactory.createLineBorder(Color.BLUE);
            this.setBorder(new TitledBorder(auxBorder, msg.getUserSend().getUserName()+" ( hora )"));

            this.setLayout(new GridLayout(2, 1));

            jlMessage = new JLabel(msg.getMensaje());
            this.add(jlMessage);

            jbSee = new JButton("Open conversation");
            this.add(jbSee);


        } catch (NullPointerException e) {
            System.out.println("Error al crear ActivityPanel");
        }

    }

    public ClosedChat() {
        try {

            Border auxBorder = BorderFactory.createLineBorder(Color.BLUE);
            this.setBorder(new TitledBorder(auxBorder, "nom  ( hora )"));

            this.setLayout(new GridLayout(2, 1));

            jlMessage = new JLabel("<message>");
            this.add(jlMessage);

            jbSee = new JButton("Open conversation");
            this.add(jbSee);


        } catch (NullPointerException e) {
            System.out.println("Error al crear ActivityPanel");
        }

    }
}
