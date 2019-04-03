package Controller;

import Autentication.AutenticationView;
import Model.User.*;
import Model.Server.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private AutenticationView autenticationView;
    private Server server;


    public Controller(AutenticationView autenticationView){
        this.autenticationView = autenticationView;
    }

    public void start(){
        autenticationView.autenticationController(this);
    }

    public void actionPerformed(ActionEvent event){

        String username = null;
        String password = null;
        boolean ok = false;

        switch (event.getActionCommand()){
            case "logIn":
                System.out.println("apretamos log in!");
                username = getAutenticationView().getUsernameTextField().getText();
                password = getAutenticationView().getPasswordTextField().getText();

                if (username.equals("") || password.equals("")){
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos!");
                }else {
                    ok = server.logIn(password, username);

                    if (!ok) {
                        JOptionPane.showMessageDialog(null, "Credenciales mal introducidas!");
                    }else{
                        //llamamos a la ventana principal
                    }
                }
                break;

            case "RegisterFromAutentication":
                System.out.println("apretamos Register");
                //mandamos a la ventana gráfica del registro
                break;
        }
    }


    public AutenticationView getAutenticationView() { return autenticationView; }
    public void setAutenticationView(AutenticationView autenticationView) { this.autenticationView = autenticationView; }
}
