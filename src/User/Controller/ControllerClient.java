package User.Controller;

import Server.Model.Server;
import User.Model.Mensaje;
import User.Model.User;
import User.Network.ServerComunication;
import User.View.AutenticationView;
import User.View.DemanarFoto;
import User.View.EditProfile;
import User.View.RegistrationView;
import User.View.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerClient implements ActionListener {
    private AutenticationView autenticationView;
    private RegistrationView registrationView;
    private ServerComunication networkManager;
    private DemanarFoto demanarFoto;
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
        ArrayList<User> listaLikedUsers = null;

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
                            listaLikedUsers = ordenaUsuarios(currentUser);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        currentUser.setListaLikedUsers(listaLikedUsers);
                        try {
                            startMainView(currentUser);
                        } catch (IOException e) {
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
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "AcceptUser":
                try {
                    System.out.println("user aceptado!");
                    ok = networkManager.functionalities(3, currentUser, currentUser.getListaLikedUsers().get(0));
                    User userRemoved = currentUser.getListaLikedUsers().remove(0);
                   // currentUser.getListaLikedUsers().add(userRemoved);
                    if(currentUser.getListaLikedUsers().size() == 0){
                        JOptionPane.showMessageDialog(null, "Has llegado al límite de usuarios!");
                    }else{
                        mainView.setUserLooking(currentUser.getListaLikedUsers().get(0));
                    }
                    mainView.setVisible(false);
                    startMainView(currentUser);

                    if (ok) {
                        JOptionPane.showMessageDialog(null, "NEW MATCH!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "DeclineUser":
                try {
                    System.out.println("user declinado!");
                    networkManager.functionalities(4, currentUser, currentUser.getListaLikedUsers().get(0));
                    User userRemoved = currentUser.getListaLikedUsers().remove(0);
                    currentUser.getListaLikedUsers().add(userRemoved);
                    mainView.setUserLooking(currentUser.getListaLikedUsers().get(0));
                    mainView.setVisible(false);
                    startMainView(currentUser);

                } catch (IOException e) {
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
                    User user = editUserFromEditProfile();
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
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "SendMessage":
                String mensaje = String.valueOf(mainView.getJtfMessage());
                String chat = String.valueOf(mainView.getJtaMessages());
                chat += currentUser.getUserName() + ": " + mensaje + "\n";
               // mainView.setJtaMessages(chat);
                break;

            case "DemanarFoto":
                try {
                    demanarFoto = new DemanarFoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Demanem foto al usuari");
                demanarFoto.setVisible(true);
                break;
        }
    }

    private void startMainView(User currentUser) throws IOException {
        if(currentUser.getListaLikedUsers().size() !=0){
            this.mainView = new View(currentUser, currentUser.getListaLikedUsers().get(0));
        }
        mainView.autenticationController(this);
        mainView.setVisible(true);
    }

    private ArrayList<User> ordenaUsuarios(User user) throws SQLException {
        ArrayList<User> allUsers = connectedUsers;
        ArrayList<User> usuarios = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
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
        urlFoto = getDemanarFoto().getPathUsuari().toString();
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

    private User editUserFromEditProfile() throws IOException, SQLException {
        String username;
        String password;
        int edat;
        boolean isPremium;
        String correo;
        String urlFoto;
        String lenguaje;
        String descripción;
        ArrayList<User> likedUsers;

        //username = getRegistrationView().getUserName().getText();
        password = getEditProfileView().getPasswordTextField().getText();
        //edat = getEditProfileView().getJsEdat().get();
        correo = getEditProfileView().getJtfCorreu().getText();
        //urlFoto = getEditProfileView().getUrlFoto().getText();
        //lenguaje = getEditProfileView().getLenguaje().getText();
        descripción = getEditProfileView().getJtfDescription().getText();
        //isPremium = getEditProfileView().getJcbPremium().ge();
        // likedUsers = ordenaUsuarios(currentUser);

        //if (username.equals(username)){
        //User user = User(username, edat, false, correo, password, urlFoto, lenguaje, descripción);

        User user = new User( "",0,false,correo, password, "", "", descripción);
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
    public DemanarFoto getDemanarFoto() {
        return demanarFoto;
    }
    public void setDemanarFoto(DemanarFoto demanarFoto) {
        this.demanarFoto = demanarFoto;
    }

    public EditProfile getEditProfileView() { return editProfile; }
    public void setEditProfileView(EditProfile editProfile) { this.editProfile = editProfile; }

}
