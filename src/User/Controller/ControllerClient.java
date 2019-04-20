package User.Controller;

import Network.ClientNetworkManager;
import Server.Model.Server;
import User.Model.Match;
import User.Model.Mensaje;
import User.View.AutenticationView;
import User.View.RegistrationView;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerClient implements ActionListener {
    private AutenticationView autenticationView;
    private RegistrationView registrationView;
    private ClientNetworkManager networkManager;
    private Server server;
    private User currentUser;


    public ControllerClient(AutenticationView autenticationView, ClientNetworkManager networkManager){
        this.autenticationView = autenticationView;
        this.networkManager = networkManager;
    }

    public void start(){
        autenticationView.autenticationController(this);
    }

    public void actionPerformed(ActionEvent event){

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
                    ok = server.logIn(password, username);

                    if (!ok) {
                        JOptionPane.showMessageDialog(null, "Credenciales mal introducidas!");
                    }else{
                        this.currentUser = server.getUsers().get(username);
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
                        this.currentUser = user;
                        registrationView.setVisible(false);

                        networkManager.newUser(user);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "AcceptUser":
                // pillamos el user al que le ha dado like userLike =
                currentUser.getListaUsers().add(userLike);
                for (User u : userLike.getListaUsers()){
                    if (u == currentUser){
                        String id = currentUser.getId() + "-" + userLike.getId();
                        Match match = new Match(currentUser, userLike, id);
                        currentUser.getListaMatch().put(id, match);
                        userLike.getListaMatch().put(id, match);
                        //llamamos a la vista del chat
                    }
                }
                break;

            case "DeclineUser":
                // pillamos el user al que le ha dado dislike userLike =
                currentUser.getListaUsers().remove(userLike);
                break;

            case "enviarMensage":
                //pillamos el match que ha clicado --> match =
                Mensaje mensaje = getMensaje();
                currentUser.getListaMatch().get(match.getId).getChat().add(mensaje);
                userLike.getListaMatch().get(match.getId).getChat().add(mensaje);
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
        ArrayList<User> listaMatch = new ArrayList<>(server.getUsers().values());
        //TODO: ordenar lista de posibles matchs según unos criterios

        if (password.equals(contraseñaRepetida)){
            int id = server.getUsers().size();
            User user = new User(id, username, edat, false, correo, password, urlFoto, lenguaje, descripción, listaMatch);
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
