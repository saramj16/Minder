package Server.Model;

import Server.Model.entity.Matx;
import Server.Model.entity.Missatge;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import Server.Network.DedicatedServer;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import Server.Model.configReader.Configuracio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server extends Thread{
    private int serverPort;
    private UsuariManager usuariManager;
    private ArrayList<User> users;
    private boolean running;
    private ArrayList<DedicatedServer> dedicatedServerList;
    private int port = 9999;


    public Server(UsuariManager usuariManager) throws SQLException {
        this.usuariManager = usuariManager;
        this.users = getAllUsers();
        running = true;
        dedicatedServerList = new ArrayList<>();

        //Totxaco per llegor del Json el port
        Configuracio config = new Configuracio();
        Gson gson = new Gson();
        JsonReader jReader;
        try {
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Configuracio.class);                       //Llegeix el fitxer Json
            this.port = Integer.parseInt(config.getConfigServer().getPort_client()) ;
        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }
    }

    public int getServerPort() { return serverPort; }
    public void setServerPort(int serverPort) { this.serverPort = serverPort; }
    public ArrayList<User> getUsers() { return users; }
    public void setUsers(ArrayList users) { this.users = users; }

    //---------------------------------------------------------------------------------------//


    public void run() {

        try {
            ServerSocket sServer = new ServerSocket(port);

            addUsuari(new Usuari("Jofre", 25, true, "jofre@minder.com", "jofre","", "Java", "pene"));
            addUsuari(new Usuari("Sara", 20, true, "sara@minder.com", "sara", "", "C", "pene"));
            addUsuari(new Usuari("Javo", 22, true, "javo@minder.com", "javo", "", "C", "fucking bosssss"));
            addUsuari(new Usuari("Manel", 22, true, "manel@minder.com", "manel", "", "Java", "pene"));
            addUsuari(new Usuari("Marcel", 23, true, "marcel@minder.com", "marcel", "", "C", "pene"));

            while (running) {
                Socket sClient = sServer.accept();
                DedicatedServer ds = new DedicatedServer(sClient, this);
                dedicatedServerList.add(ds);
            }
            sServer.close();
            for (DedicatedServer ds : dedicatedServerList) {
                ds.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void removeFromDedicatedList(DedicatedServer dedicatedServer) {
        dedicatedServerList.remove(dedicatedServer);
    }

    public void addUsuari(Usuari u){
        usuariManager.addUsuari(u);
    }

    public boolean comprobarLogIn(String username, String password){
        //System.out.println("login = true");
        try {
            return usuariManager.comprovaLogin(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizaUser(User user) throws SQLException {
        User user1 = getUser(user.getUserName());

        if (user1.getUserName() != null){
            System.out.println("Usuari a actualitzar " + user1.getUserName());
            updateUser(user1, user);
            return true;
        } else {
            return false;
        }
    }

    private void updateUser(User user2modificate, User userModificated) {
        usuariManager.modificiaUsuari(new Usuari(userModificated.getUserName(),userModificated.getEdat(),userModificated.isPremium(), userModificated.getCorreo(), userModificated.getPassword(), userModificated.getUrlFoto(), userModificated.getLenguaje(), userModificated.getDescription()));

    }

    public boolean comprobarRegistro(User user){
        if (!usuariManager.searchUsuari(user.getUserName())){ //Si l'usuari no existeix
            usuariManager.addUsuari(new Usuari(user.getUserName(), user.getEdat(), user.isPremium(), user.getCorreo(),
                    user.getPassword(), user.getUrlFoto(), user.getLenguaje(), user.getDescription()));
//, String urlFoto, String lenguaje, String description
            // ****
            return true;
        }
        return false;
    }

    public boolean acceptUser(User currentUser, User userLike) throws SQLException {
        ArrayList<User> userLikeLikedUsers;

        userLikeLikedUsers = getLikedUsers(userLike.getUserName());
        addLikedUserToCurrentUser(currentUser, userLike);

        for (User u : userLikeLikedUsers){
            if (u.getUserName().equals(currentUser.getUserName())){
                usuariManager.addMatx(currentUser.getUserName(), userLike.getUserName());
                return true;
            }
        }
        return false;
    }

    private ArrayList<User> getLikedUsers(String userLike) throws SQLException {
        ArrayList<Usuari> likedUsers = usuariManager.getUsuarisAccepted(userLike);

        return convertUsuaristoUsers(likedUsers);

    }

    public ArrayList<Match> getMatchList(String userLike) throws SQLException {
        ArrayList<Matx> matches = usuariManager.getMatxedUsers(userLike);

        return convertMatxToMach(matches);

    }

    public ArrayList<User> getMatchedUsers(String userMatch) throws SQLException {
        ArrayList<Usuari> usuaris = usuariManager.getUsuarisMatxes(userMatch);

        return convertUsuaristoUsers(usuaris);

    }

    private void addLikedUserToCurrentUser(User currentUser, User userLike) {
        usuariManager.addAccepted(currentUser.getUserName(), userLike.getUserName());

    }

    public ArrayList<User> getAllUsers() throws SQLException {

        ArrayList<Usuari> usuaris;
        usuaris = usuariManager.getAllUsuari();

        return convertUsuaristoUsers(usuaris);
    }

    public ArrayList<Mensaje> getMessages(String user1, String user2) throws SQLException {

        ArrayList<Missatge> missatges;
        missatges = usuariManager.preparaChat(user1,user2);

        return convertMissatgeToMensaje(missatges);
    }


    public ArrayList<User> convertUsuaristoUsers(ArrayList<Usuari> usuaris)  {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < usuaris.size(); i++){

            users.add(new User(usuaris.get(i).getUserName(), usuaris.get(i).getEdat(),
                    usuaris.get(i).isPremium(), usuaris.get(i).getCorreo(), usuaris.get(i).getPassword(),
                    usuaris.get(i).getUrlFoto(), usuaris.get(i).getLenguaje(),usuaris.get(i).getDescription()));
        }

        return users;
    }

    public ArrayList<Match> convertMatxToMach (ArrayList<Matx> matxes) throws SQLException {
        ArrayList<Match> matches = new ArrayList<>(matxes.size());

        if (matxes.size() != 0){
            for (int i = 0; i < matxes.size(); i++){
                matches.add(new Match(getUser(matxes.get(i).getUser1()), getUser(matxes.get(i).getUser2()), getMessages(matxes.get(i).getUser1(),matxes.get(i).getUser2())));
            }
        }

        return matches;
    }

    public ArrayList<Mensaje> convertMissatgeToMensaje (ArrayList<Missatge> missatges) throws SQLException {
        ArrayList<Mensaje> mensajes = new ArrayList<>(missatges.size());

        for (int i = 0; i < missatges.size(); i++){
            User u = getUser(missatges.get(i).getUserSend());
            User u1 = getUser(missatges.get(i).getUserReceive());
            mensajes.add(new Mensaje(u.getUserName() + ": " + missatges.get(i).getMissatge(), u, u1));
        }

        return mensajes;
    }

    public User getUser(String username) throws SQLException {

       Usuari u = usuariManager.getUsuari(username);


       User user = new User(u.getUserName(), u.getEdat(), u.isPremium(), u.getCorreo(), u.getPassword(), u.getUrlFoto(), u.getLenguaje(), u.getDescription());

       return user;
    }

    public void declineUser(User currentUser, User declinedUser) {
        usuariManager.addVist(currentUser.getUserName(),declinedUser.getUserName());
    }

    public void announceChanges(User user) {
        for (DedicatedServer ds : dedicatedServerList){
            ds.anounceChanges(user);
        }
    }

    public void addMensaje(String mensajeRecibido, User currentUser, User userRecibe) {
        usuariManager.afegeixMissatge(currentUser.getUserName(), userRecibe.getUserName(), mensajeRecibido);
    }

    public boolean isUserRecibeConnected(User userRecibe, String mensajeRecibido) {
        for (DedicatedServer ds : dedicatedServerList){
            if (ds.getMainUser().getUserName().equals(userRecibe)){
            //    ds.updateChat
                return true;
            }
        }
        return false;
    }
}