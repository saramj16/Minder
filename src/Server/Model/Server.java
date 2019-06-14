package Server.Model;

import Server.Model.entity.Matx;
import Server.Model.entity.Missatge;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    private int serverPort;
    private UsuariManager usuariManager;
    private ArrayList<User> users;


    public Server(UsuariManager usuariManager) throws SQLException {
        this.usuariManager = usuariManager;
        this.users = getAllUsers();
    }

    public int getServerPort() { return serverPort; }
    public void setServerPort(int serverPort) { this.serverPort = serverPort; }
    public ArrayList<User> getUsers() { return users; }
    public void setUsers(ArrayList users) { this.users = users; }

    //---------------------------------------------------------------------------------------//

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

    public boolean comprobarRegistro(User user){
        if (!usuariManager.searchUsuari(user.getUserName())){
            usuariManager.addUsuari(new Usuari(user.getUserName(), user.getEdat(), user.isPremium(), user.getCorreo(), user.getPassword()));

            // ****
            return true;
        }
        return false;
    }

    public boolean acceptUser(User currentUser, User userLike) throws SQLException {

        ArrayList<User> userLikeLikedUsers;

        addLikedUserToCurrentUser(currentUser, userLike);
        userLikeLikedUsers = getLikedUsers(userLike.getUserName());

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

    private ArrayList<Match> getMatchList(String userLike) throws SQLException {
        ArrayList<Matx> matches = usuariManager.getMatxedUsers(userLike);

        return convertMatxToMach(matches);

    }

    private ArrayList<User> getMatchedUsers(String userMatch) throws SQLException {
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


    public ArrayList<User> convertUsuaristoUsers(ArrayList<Usuari> usuaris) throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < usuaris.size(); i++){
            ArrayList<Match> listaMatch = convertMatxToMach(usuariManager.getMatxedUsers(usuaris.get(i).getUserName()));

            users.add(new User(usuaris.get(i).getUserName(), usuaris.get(i).getEdat(),
                    usuaris.get(i).isPremium(), usuaris.get(i).getCorreo(), usuaris.get(i).getPassword(),
                    usuaris.get(i).getUrlFoto(), usuaris.get(i).getLenguaje(),usuaris.get(i).getDescription(),listaMatch));
        }

        return users;
    }

    public ArrayList<Match> convertMatxToMach (ArrayList<Matx> matxes) throws SQLException {
        ArrayList<Match> matches = new ArrayList<>();

        for (int i = 0; i < matxes.size(); i++){
            matches.add(new Match(getUser(matxes.get(i).getUser1()), getUser(matxes.get(i).getUser2()), getMessages(matxes.get(i).getUser1(),matxes.get(i).getUser2())));
        }

        return matches;
    }

    public ArrayList<Mensaje> convertMissatgeToMensaje (ArrayList<Missatge> missatges) throws SQLException {
        ArrayList<Mensaje> mensajes = new ArrayList<>();

        for (int i = 0; i < missatges.size(); i++){
            User u = getUser(missatges.get(i).getUserSend());
            User u1 = getUser(missatges.get(i).getUserReceive());
            mensajes.add(new Mensaje(missatges.get(i).getMissatge(), u, u1));
        }

        return mensajes;
    }

    public User getUser(String username) throws SQLException {

       Usuari u = usuariManager.getUsuari(username);

       ArrayList<User> listaLikedUsers = getLikedUsers(username);
       ArrayList<Match> listaMatch = convertMatxToMach(usuariManager.getMatxedUsers(username));


       User user = new User(u.getUserName(), u.getEdat(), u.isPremium(), u.getCorreo(), u.getPassword(), u.getUrlFoto(), u.getLenguaje(), u.getDescription(),listaMatch,listaLikedUsers);

       return user;
    }



    public void declineUser(User currentUser, User declinedUser) {
        usuariManager.addVist(currentUser.getUserName(),declinedUser.getUserName());
    }
}