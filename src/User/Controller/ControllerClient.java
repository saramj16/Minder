package User.Controller;

import Network.NetworkManager;
import Server.Model.Server;
import User.View.AutenticationView;
import User.View.RegistrationView;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControllerClient implements ActionListener {
    private AutenticationView autenticationView;
    private RegistrationView registrationView;
    private NetworkManager networkManager;
    private Server server;


    public ControllerClient(AutenticationView autenticationView, NetworkManager networkManager){
        this.autenticationView = autenticationView;
        this.networkManager = networkManager;
    }

    public void start(){
        autenticationView.autenticationController(this);
    }

    public void actionPerformed(ActionEvent event){

        String username;
        String password;
        User user;

        boolean ok = false;

        switch (event.getActionCommand()){
            case "logIn":
                username = getAutenticationView().getUsernameTextField().getText();
                password = getAutenticationView().getPasswordTextField().getText();

                if (username.equals("") || password.equals("")){
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos!");
                }else{
                    ok = server.logIn(password, username);

                    if (!ok) {
                        JOptionPane.showMessageDialog(null, "Credenciales mal introducidas!");
                    }else{
                        //llamamos a la ventana principal
                    }
                }
                break;

            case "RegisterFromAutentication":
                autenticationView.setVisible(false);
                registrationView = new RegistrationView();
                registrationView.autenticationController(this);
                registrationView.setVisible(true);
                break;

            case "Register":
                System.out.println("registrando!!");
                try {
                    user = newUserFromRegistration();
                    if (user != null) {
                        registrationView.setVisible(false);
                        networkManager.newUser(user);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private User newUserFromRegistration() throws IOException {
        String username;
        String password;
        int edat;
        String correo;
        String contraseñaRepetida;
        String urlFoto;
        String lenguaje;
        String descripción;

        username = getRegistrationView().getUserName().getText();
        password = getRegistrationView().getContraseña().getText();
        contraseñaRepetida = getRegistrationView().getRepetirContraseña().getText();
        edat = Integer.parseInt(getRegistrationView().getEdat().getText());
        correo = getRegistrationView().getCorreo().getText();
        urlFoto = getRegistrationView().getUrlFoto().getText();
        lenguaje = getRegistrationView().getLenguaje().getText();
        descripción = getRegistrationView().getDescripción().getText();

        if (password.equals(contraseñaRepetida)){
            User user = new User(username, edat, false, correo, password, urlFoto, lenguaje, descripción);
            return user;
        }else{
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden!");
            return null;
        }
    }



    public AutenticationView getAutenticationView() { return autenticationView; }
    public void setAutenticationView(AutenticationView autenticationView) { this.autenticationView = autenticationView; }
    public RegistrationView getRegistrationView() { return registrationView; }
    public void setRegistrationView(RegistrationView registrationView) { this.registrationView = registrationView; }
    public Server getServer() { return server; }
    public void setServer(Server server) { this.server = server; }
}
