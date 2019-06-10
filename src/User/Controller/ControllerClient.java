package User.Controller;

import Network.ClientNetworkManager;
import Server.Model.Server;
import Server.Model.entity.Usuari;
import User.Model.Mensaje;
import User.Model.User;
import User.View.AutenticationView;
import User.View.RegistrationView;
import User.View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerClient implements ActionListener {
    private AutenticationView autenticationView;
    private RegistrationView registrationView;
    private ClientNetworkManager networkManager;
    private View mainView;
    private Server server;
    private User currentUser;
    private ArrayList<User> connectedUsers;
    private int contadorMuestreoUsersConectados = 0;


    public ControllerClient(AutenticationView autenticationView, ClientNetworkManager networkManager) {
        this.autenticationView = autenticationView;
        this.networkManager = networkManager;
    }

    public void start() {
        autenticationView.autenticationController(this);

    }

    public void actionPerformed(ActionEvent event){
        try {
            this.connectedUsers = networkManager.getAllUsers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String username;
        String password;
        User user, userLike;

        boolean ok = false;

        switch (event.getActionCommand()){
            case "logIn":
                username = getAutenticationView().getUsernameTextField().getText();
                password = getAutenticationView().getPasswordTextField().getText();

                if (username.equals("") || password.equals("")){
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos!");
                }else{
                    try {
                        ok = networkManager.functionalities(1, username, password);
                        System.out.println("ok = " + ok );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!ok) {
                        JOptionPane.showMessageDialog(null, "Credenciales mal introducidas!");
                    }else{
                        autenticationView.setVisible(false);
                        try {
                            this.currentUser = networkManager.getCurrentUser();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Current user = " + currentUser.getUserName());
                        this.mainView = new View(currentUser, connectedUsers.get(0));
                        mainView.setVisible(true);

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
                        ok = networkManager.functionalities(2, user, null);
                        if (ok){
                            JOptionPane.showMessageDialog(null, "Usuario registrado!");
                            this.currentUser = user;
                            registrationView.setVisible(false);
                            this.mainView = new View(currentUser, connectedUsers.get(0));
                            mainView.setVisible(true);

                        }else{
                            System.out.println("algun tipo de error al registrar usuario");
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "AcceptUser":
                try {
                    System.out.println("user aceptado!");
                    ok = networkManager.functionalities(3, currentUser, connectedUsers.get(0));
                    User userRemoved = connectedUsers.remove(0);
                    connectedUsers.add(userRemoved);
                    mainView.setUserLooking(connectedUsers.get(0));

                    if (ok){
                        JOptionPane.showMessageDialog(null, "NEW MATCH!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "DeclineUser":
                // pillamos el user al que le ha dado dislike userLike =
                try {
                    //TODO:3er parámetro ha de ser userLike!!!!!!
                    networkManager.functionalities(4, currentUser, connectedUsers.get(0));
                    User userRemoved = connectedUsers.remove(0);
                    connectedUsers.add(userRemoved);
                    mainView.setUserLooking(connectedUsers.get(0));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "EditarPerfil":
                //llamamos a la vista de editar perfil
                break;

            case "GuardarPerfil":
                //nos guardamos toda la info y volvemos a pantalla principal
                break;

            case "enviarMensage":
                /*pillamos el match que ha clicado --> match =
                Mensaje mensaje = getMensaje();
                currentUser.getListaMatch().get(match.getId).getChat().add(mensaje);
                userLike.getListaMatch().get(match.getId).getChat().add(mensaje);*/
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
        //TODO: ordenar lista de posibles matchs según unos criterios

        if (password.equals(contraseñaRepetida)){
           User user = new User(username, edat, false, correo, password, urlFoto, lenguaje, descripción);
           return user;

        }else{
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden!");
            return null;
        }

    }

    private Mensaje getMensaje(){
        Mensaje mensaje = null;

        return mensaje;
    }

    public AutenticationView getAutenticationView() { return autenticationView; }
    public void setAutenticationView(AutenticationView autenticationView) { this.autenticationView = autenticationView; }
    public RegistrationView getRegistrationView() { return registrationView; }
    public void setRegistrationView(RegistrationView registrationView) { this.registrationView = registrationView; }
    public Server getServer() { return server; }
    public void setServer(Server server) { this.server = server; }
}
