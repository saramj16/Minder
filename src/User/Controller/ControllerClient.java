package User.Controller;

import Server.Model.Server;
import User.Model.Connectivity;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;
import User.Network.ServerComunication;
import User.View.AutenticationView;
import User.View.DemanarFoto;
import User.View.EditProfile;
import User.View.RegistrationView;
import User.View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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
    private ArrayList<User> possiblesMatxs;
    private ArrayList<User> sawMatches;
    private ArrayList<Match> matches;
    private User chatUser;
    private Connectivity connectivity;

    public ControllerClient(AutenticationView autenticationView, ServerComunication networkManager) throws IOException {
        this.autenticationView = autenticationView;
        this.networkManager = networkManager;
        this.possiblesMatxs = new ArrayList<>();
        this.sawMatches = new ArrayList<>();
        this.connectivity = new Connectivity(ServerComunication.getIp(), ServerComunication.getPort(), this);
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
                        possiblesMatxs = ordenaUsuarios(currentUser);
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
                System.out.println("registrando!");
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
                            System.out.println("Error al registrar usuario");
                            JOptionPane.showMessageDialog(null, "     El usuario ya existe :(" + "\n"
                                    + "      Prueba otro nombre!");
                        }
                    }
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "AcceptUser":
                try {
                    System.out.println("user aceptado!");
                    ok = networkManager.functionalities(3, currentUser, possiblesMatxs.get(0));
                    sawMatches.add(possiblesMatxs.get(0));
                    currentUser.getListaLikedUsers().add(possiblesMatxs.get(0));
                    possiblesMatxs.remove(0);
                    if(possiblesMatxs.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Has llegado al límite de usuarios!");
                    }

                    if (ok) {
                        JOptionPane.showMessageDialog(null, "NEW MATCH!");
                    }

                    mainView.setVisible(false);
                    startMainView(currentUser);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "DeclineUser":
                try {
                    System.out.println("user declinado!");
                    networkManager.functionalities(4, currentUser, possiblesMatxs.get(0));
                    sawMatches.add(possiblesMatxs.get(0));
                    //User userRemoved = possiblesMatxs.remove(0);
                    possiblesMatxs.remove(0);
                    //possiblesMatxs.add(userRemoved);
                    if(possiblesMatxs.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Has llegado al límite de usuarios!");
                    }else {
                        mainView.setUserLooking(possiblesMatxs.get(0));
                    }
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
                            /*possiblesMatxs = ordenaUsuarios(currentUser);
                            editProfile.setVisible(false);
                            startMainView(currentUser);*/
                            possiblesMatxs = ordenaUsuarios(currentUser);
                            if (possiblesMatxs.size() != 0){
                                mainView.setUserLooking(possiblesMatxs.get(0));
                                try {
                                    this.mainView = new View(currentUser, possiblesMatxs.get(0));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                try {
                                    this.mainView = new View(currentUser, null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            mainView.registerController(this);
                            mainView.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(null, "algun tipo de error al guardar los cambios!");
                        }
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "SendMessage":
                String mensaje = (mainView.getJtfMessage().getText());
                String chat = currentUser.getUserName() + ": " + mensaje + "\n";
                mainView.getTa().append(chat);
                mainView.getJtfMessage().setText("");

                if (chatUser == null){
                    chatUser = currentUser.getListaMatch().get(0).getUser2();
                }
                try {
                    networkManager.functionalities(7, mensaje, chatUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;


            case "UndoMatch":
                if (chatUser == null){
                    chatUser = currentUser.getListaMatch().get(0).getUser2();
                }
                JOptionPane.showMessageDialog(null, "Match desfet!");
                mainView.setJlUserChatting("");



                //JButton[] panels = new JButton[mainView.getPanels().length];
                JButton[] panels = mainView.getPanels();
                JButton jbRemoved = new JButton("");
                LinkedList<JButton> panelsList = new LinkedList();
                for (JButton jb : panels) {
                    if (!jb.getText().equals(chatUser.getUserName())) {
                        panelsList.add(jb);
                    }
                }
                JButton[] newPanels = new JButton[panelsList.size()];
                newPanels = panelsList.toArray(newPanels);

                /*System.out.println("panels.length "+panels.length);
                System.out.println("newPanels.length "+newPanels.length);
                System.out.println("panelsList.size() "+panelsList.size()); */

                mainView.setPanels(newPanels);



                try{
                    networkManager.functionalities(8, currentUser, chatUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

            case "Refresh":
                ArrayList<User> newPossiblesMatxs = ordenaUsuarios(currentUser);

                for (User sm : sawMatches) {
                    for (int j = 0; j < newPossiblesMatxs.size(); j++) {
                        if (sm.equals(newPossiblesMatxs.get(j))) {
                            newPossiblesMatxs.remove(j);
                        }
                    }
                }
                this.possiblesMatxs = newPossiblesMatxs;
                mainView.setVisible(false);
               /* try {
                    startMainView(currentUser);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                mainView.setVisible(true);*/
                if (possiblesMatxs.size() != 0){
                    mainView.setUserLooking(possiblesMatxs.get(0));
                    try {
                        this.mainView = new View(currentUser, possiblesMatxs.get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        this.mainView = new View(currentUser, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mainView.registerController(this);
                mainView.setVisible(true);
                break;

            case "LogOut":
                mainView.setVisible(false);
                autenticationView = new AutenticationView();
                autenticationView.autenticationController(this);
                autenticationView.setVisible(true);
                break;

            default: //Chat
                String mensajes = null;
                if (event.getActionCommand().startsWith("Chat")){
                    String[] split = event.getActionCommand().split(" ");
                    int i = Integer.parseInt(split[1]);
                    this.chatUser = currentUser.getListaMatch().get(i).getUser2();
                    ArrayList<Mensaje> messages = null;
                    try {
                        messages = networkManager.getMessages(currentUser, currentUser.getListaMatch().get(i).getUser2());
                        mainView.setJlUserChatting(chatUser.getUserName());
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    //currentUser.getListaMatch().get(i).getChat();
                    for (int j = 0; j < messages.size(); j++){
                        mensajes += messages.get(j).getMensaje() + "\n";
                    }

                    if (mensajes != null){
                        mainView.getTa().setText(mensajes);
                    }else {
                        mainView.getTa().setText(" ");
                    }
                }
                break;
        }
    }

    private void startMainView(User currentUser) throws IOException, ClassNotFoundException {
        matches = networkManager.getListaMatches();
        currentUser.setListaMatch(matches);
        if (possiblesMatxs.size() != 0){
            this.mainView = new View(currentUser, possiblesMatxs.get(0));
        }else{
            this.mainView = new View(currentUser, null);
        }
        mainView.registerController(this);
        mainView.setVisible(true);
    }

    private ArrayList<User> ordenaUsuarios(User user) {
        ArrayList<User> allUsers = connectedUsers;
        ArrayList<User> usuarios = new ArrayList<>();
        int trobat;
        for (User allUser : allUsers) {
            trobat = 0;
            for (int i = 0; i < user.getListaMatch().size(); i++) {
                System.out.println("U1:" + user.getListaMatch().get(i).getUser1().getUserName());
                System.out.println("U2:" + user.getListaMatch().get(i).getUser2().getUserName());
                if(allUser.getUserName().equals(user.getListaMatch().get(i).getUser1().getUserName())
                        || (allUser.getUserName().equals(user.getListaMatch().get(i).getUser2().getUserName()))){
                    trobat = 1;
                }
            }
            System.out.println("SIZE: " + user.getListaLikedUsers().size());
            for (int j = 0; j < user.getListaLikedUsers().size(); j++){
                System.out.println("LIKE:" + user.getListaLikedUsers().get(j).getUserName());
                if(allUser.getUserName().equals(user.getListaLikedUsers().get(j).getUserName())){
                    trobat = 1;
                }
            }
            System.out.println("trobat =" + trobat);
            if (trobat == 0) {
                if (user.isPremium()) {
                    for (int k = 0; k < allUser.getListaLikedUsers().size(); k++) {
                        //System.out.println("listaliked = " + user.getListaLikedUsers().get(j));
                        if (user.getUserName().equals(allUser.getListaLikedUsers().get(k).getUserName())) {
                            usuarios.add(allUser);
                            trobat = 1;
                        }
                    }
                    if (trobat == 0 && !allUser.getUserName().equals(user.getUserName()) && allUser.getLenguaje().equals(user.getLenguaje())) {
                        usuarios.add(allUser);
                    }
                } else {
                    //System.out.println("llenguatge = " + allUsers.get(i).getLenguaje());
                    if ((allUser.getLenguaje().equals(user.getLenguaje())) && !(allUser.getUserName().equals(user.getUserName()))) {
                        usuarios.add(allUser);
                    }
                }
            }
        }
        return usuarios;
    }

    private User newUserFromRegistration() throws  SQLException {
        String username;
        String password;
        int edat;
        String correo;
        String contraseñaRepetida;
        String urlFoto = null;
        String lenguaje;
        String descripción;
        ArrayList<User> likedUsers;

        try {
            username = getRegistrationView().getUserName().getText();
            password = getRegistrationView().getContraseña().getText();
            contraseñaRepetida = getRegistrationView().getRepetirContraseña().getText();
            if (getRegistrationView().getEdat().getText() != null){
                edat = Integer.parseInt(getRegistrationView().getEdat().getText());
            } else {
                edat = -1;
            }
            correo = getRegistrationView().getCorreo().getText();
            urlFoto = getDemanarFoto().getPathUsuari().toString();
            lenguaje = getRegistrationView().getLenguaje();
            descripción = getRegistrationView().getDescripción().getText();
        }catch (NullPointerException | NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Tienes que rellenar todos los campos!");
            return null;
        }

        /** Cal que la contrasenya tingui com a mínim una longitud de 8 caràcters així
         /com contingui com a mínim majúscules, minúscules i valors numèrics.
         **/

        boolean teMajus = true;
        if (password.equals(password.toLowerCase())) {
            teMajus = false;
        }

        boolean teMinus = true;
        if (password.equals(password.toUpperCase())) {
            teMinus = false;
        }

        boolean teNumeros = false;
        char[] passwordArray = password.toCharArray();
        for (char i : passwordArray) {
            if (i == '1' || i == '2' || i == '3' || i == '4' || i == '5' || i == '6' || i == '7' || i == '8' || i == '9' || i == '0') {
                teNumeros = true;
            }
        }

        boolean passOk;
        if (password.length() > 8 && teMajus && teMinus && teNumeros ) {
            passOk = true;
        } else {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener más de 8 " +
                    "carácteres, mayúsculas, minúsculas y un número o más.");
            passOk = false;
            return null;
        }

        if (password.equals(contraseñaRepetida) && passOk){
            if (edat < 17){
                JOptionPane.showMessageDialog(null, "Tienes que tener más de 17 años!");
                return null;
            }
            if (edat > 100){
                JOptionPane.showMessageDialog(null, "Este programa no es para dinosaurios!");
                return null;
            }
            User user = new User(username, edat,false, correo, password, urlFoto, lenguaje, descripción);
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
        //urlFoto = getDemanarFoto().getPathUsuari().toString();
        urlFoto = null;
        if (urlFoto == null){
            urlFoto = u.getUrlFoto();
        }

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

        User user = new User( u.getUserName(),edat,isPremium,correo, password, urlFoto, lenguaje, descripción);

        return user;
    }


    public void functionalities(int id, Object o1, Object o2){
        switch (id){
            case 1: //recibimos mensaje = o1, o2 = null
                String mensaje = (String) o1;
                String chat = currentUser.getUserName() + ": " + mensaje + "\n";
                mainView.getTa().append(chat);
                break;
        }
    }









    private Mensaje getMensaje(){
        Mensaje mensaje = null;

        return mensaje;
    }

    private AutenticationView getAutenticationView() { return autenticationView; }
    public void setAutenticationView(AutenticationView autenticationView) { this.autenticationView = autenticationView; }
    private RegistrationView getRegistrationView() { return registrationView; }
    public void setRegistrationView(RegistrationView registrationView) { this.registrationView = registrationView; }
    public Server getServer() { return server; }
    public void setServer(Server server) { this.server = server; }
    private DemanarFoto getDemanarFoto() {
        return demanarFoto;
    }
    public void setDemanarFoto(DemanarFoto demanarFoto) {
        this.demanarFoto = demanarFoto;
    }

    private EditProfile getEditProfileView() { return editProfile; }
    public void setEditProfileView(EditProfile editProfile) { this.editProfile = editProfile; }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getFirstUser() {
        return possiblesMatxs.get(0);
    }
}