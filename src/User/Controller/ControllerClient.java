package User.Controller;

import Server.Model.Server;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;
import User.Network.ServerComunication;
import User.View.AutenticationView;
import User.View.EditProfile;
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
    private ServerComunication networkManager;
    private View mainView;
    private Server server;
    private User currentUser;
    private ArrayList<User> connectedUsers;
    private EditProfile editProfile;


    public ControllerClient(AutenticationView autenticationView, ServerComunication networkManager) {
        this.autenticationView = autenticationView;
        this.networkManager = networkManager;

    }

    public void start() {
        autenticationView.autenticationController(this);
        try {
            this.connectedUsers = networkManager.getAllUsers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent event){
        boolean ok = false;

        switch (event.getActionCommand()) {
            case "logIn":
                String username = getAutenticationView().getUsernameTextField().getText();
                String password = getAutenticationView().getPasswordTextField().getText();

                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos!");
                } else {
                    try {
                        ok = networkManager.functionalities(1, username, password);
                        System.out.println("ok = " + ok);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!ok) {
                        JOptionPane.showMessageDialog(null, "Credenciales mal introducidas!");
                    } else {
                        autenticationView.setVisible(false);
                        try {
                            this.currentUser = networkManager.getCurrentUser();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Current user = " + currentUser.getUserName());

                        try {
                            startMainView(currentUser);
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
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
                    User user = newUserFromRegistration();
                    if (user != null) {
                        ok = networkManager.functionalities(2, user, null);
                        if (ok) {
                            JOptionPane.showMessageDialog(null, "Usuario registrado!");
                            this.currentUser = user;
                            registrationView.setVisible(false);
                            startMainView(currentUser);
                        } else {
                            System.out.println("algun tipo de error al registrar usuario");
                        }
                    }
                } catch (IOException | SQLException | ClassNotFoundException e) {
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
                    mainView.setVisible(false);
                    startMainView(currentUser);

                    if (ok) {
                        JOptionPane.showMessageDialog(null, "NEW MATCH!");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "DeclineUser":
                try {
                    System.out.println("user declinado!");
                    networkManager.functionalities(4, currentUser, connectedUsers.get(0));
                    User userRemoved = connectedUsers.remove(0);
                    connectedUsers.add(userRemoved);
                    mainView.setUserLooking(connectedUsers.get(0));
                    mainView.setVisible(false);
                    startMainView(currentUser);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "EditProfile":
                try {
                    this.editProfile = new EditProfile(currentUser);
                    editProfile.autenticationController(this);
                    mainView.setVisible(false);
                    editProfile.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;


            case "SaveEditProfile":
                System.out.println("Actualizando perfil...!!");
                try {
                    User user = editUserFromEditProfile(currentUser);
                    if (user != null) {
                        ok = networkManager.functionalities(5, user, null);
                        if (ok){
                            JOptionPane.showMessageDialog(null, "Cambios guardados correctamente!");
                            this.currentUser = user;
                            editProfile.setVisible(false);
                            startMainView(currentUser);
                        }else{
                            System.out.println("algun tipo de error al guardar los cambios ");
                        }
                    }
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "SendMessage":
                String mensaje = String.valueOf(mainView.getJtfMessage());
                String chat = String.valueOf(mainView.getJtaMessages());
                chat += currentUser.getUserName() + ": " + mensaje + "\n";
                mainView.getTa().append(chat);
                mainView.setJtfMessage(new JTextField(""));
                //mainView.getTa().setCaretPosition(mainView.getTa().getText().length() - 1);
                break;
        }
    }

    private void startMainView(User currentUser) throws IOException, ClassNotFoundException {
        ArrayList<Match> matches = networkManager.getListaMatches();
        currentUser.setListaMatch(matches);
        this.mainView = new View(currentUser, connectedUsers.get(0));
        mainView.autenticationController(this);
        mainView.setVisible(true);
    }

    private ArrayList<User> ordenaUsuarios(User user) {
        ArrayList<User> allUsers = connectedUsers;
        ArrayList<User> usuarios = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            System.out.println("allUsers = " + allUsers.get(i).getUserName());
            if (user.isPremium()) {
                for (int j = 0; j < user.getListaLikedUsers().size(); j++) {
                    System.out.println("listaliked = " + user.getListaLikedUsers().get(j));
                    if ((allUsers.get(i).getUserName().equals(user.getListaLikedUsers().get(j).getUserName()))
                            && !(allUsers.get(i).getUserName().equals(user.getUserName()))) {
                        usuarios.add(allUsers.get(i));
                    }
                }
            }
            System.out.println("llenguatge = " + allUsers.get(i).getLenguaje());
            if ((allUsers.get(i).getLenguaje().equals(user.getLenguaje())) && !(allUsers.get(i).getUserName().equals(user.getUserName()))) {
                usuarios.add(allUsers.get(i));
            }
        }
        for (int j = 0; j < usuarios.size(); j++){
            System.out.println("CACA" + usuarios.get(j).getUserName());
        }
        return usuarios;
    }

    private User newUserFromRegistration() throws IOException, SQLException {
        String username;
        String password;
        int edat;
        String correo;
        String contraseñaRepetida;
        String urlFoto;
        String lenguaje;
        String descripción;
        ArrayList<User> likedUsers;

        username = getRegistrationView().getUserName().getText();
        password = getRegistrationView().getContraseña().getText();
        contraseñaRepetida = getRegistrationView().getRepetirContraseña().getText();
        edat = Integer.parseInt(getRegistrationView().getEdat().getText());
        correo = getRegistrationView().getCorreo().getText();
        urlFoto = getRegistrationView().getUrlFoto().getText();
        lenguaje = getRegistrationView().getLenguaje().getText();
        descripción = getRegistrationView().getDescripción().getText();
        likedUsers = ordenaUsuarios(currentUser);


        //TODO: ordenar lista de posibles matchs según unos criterios

        if (password.equals(contraseñaRepetida)){
           User user = new User(username, edat, false, correo, password, urlFoto, lenguaje, descripción);
           user.setListaLikedUsers(likedUsers);
           return user;

        }else{
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden!");
            return null;
        }

    }

    private User editUserFromEditProfile(User u) throws IOException, SQLException {
        String username;
        String password;
        int edat;
        boolean isPremium;
        String correo;
        String urlFoto;
        String lenguaje;
        String descripción;
        ArrayList<User> likedUsers;

        password = getEditProfileView().getPasswordTextField().getText();
        edat = (int) getEditProfileView().getJsEdat().getValue();
        correo = getEditProfileView().getJtfCorreu().getText();
        //urlFoto = getEditProfileView().getUrlFoto().getText();

        if (getEditProfileView().getJrbC().isSelected()){
            if (getEditProfileView().getJrbJava().isSelected()){
                lenguaje = "C & Java";
                getEditProfileView().getJrbJava().setSelected(true);
                getEditProfileView().getJrbC().setSelected(true);
            }else{
                lenguaje = "C";
                getEditProfileView().getJrbJava().setSelected(false);
                getEditProfileView().getJrbC().setSelected(true);
            }
        }else {
            lenguaje = "Java";
            getEditProfileView().getJrbJava().setSelected(true);
            getEditProfileView().getJrbC().setSelected(false);
        }

        descripción = getEditProfileView().getJtfDescription().getText();
        isPremium = getEditProfileView().getJcbPremium().isEnabled();
        // likedUsers = ordenaUsuarios(currentUser);

        //if (username.equals(username)){
        //User user = User(username, edat, false, correo, password, urlFoto, lenguaje, descripción);

        User user = new User( u.getUserName(),edat,isPremium,correo, password, "", lenguaje, descripción);
        // user.setListaLikedUsers(likedUsers);
        return user;

        //}else{
        //JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden!");
        // return null;
        //}

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

    public EditProfile getEditProfileView() { return editProfile; }
    public void setEditProfileView(EditProfile editProfile) { this.editProfile = editProfile; }

}
