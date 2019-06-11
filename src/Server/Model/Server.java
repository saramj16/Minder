package Server.Model;

import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Model.Match;
import User.Model.User;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    private int serverPort;
    private UsuariManager usuariManager;
    private ArrayList<User> users;


    public Server(UsuariManager usuariManager) {
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
            return true;
        }
        return false;
    }

    public boolean acceptUser(User currentUser, User userLike) throws SQLException {
        ArrayList<User> currentUserlikedUsers = new ArrayList<>();
        ArrayList<User> userLikeLikedUsers;

        addLikedUserToCurrentUser(currentUser, userLike);
        userLikeLikedUsers = getLikedUsers(userLike);

        for (User u : userLikeLikedUsers){
            if (u == currentUser){
                //TODO de Javo -> ver como poner los IDs de los matches

                //Aqui se añade matx a los dos USuarios
                usuariManager.addMatx(currentUser.getUserName(), userLike.getUserName());
                return true;
            }
        }
        return false;
    }

    private ArrayList<User> getLikedUsers(User userLike) throws SQLException {
        ArrayList<Usuari> likedUsers = usuariManager.getUsuarisAccepted(userLike.getUserName());

        return convertUsuaristoUsers(likedUsers);

    }

    private ArrayList<User> getMatchedUsers(User userMatch) throws SQLException {
        ArrayList<Usuari> usuaris = usuariManager.getUsuarisMatxes(userMatch.getUserName());

        return convertUsuaristoUsers(usuaris);

    }

    private void addLikedUserToCurrentUser(User currentUser, User userLike) {
        usuariManager.addAccepted(currentUser.getUserName(), userLike.getUserName());
    }

    public ArrayList<User> getAllUsers(){

        ArrayList<Usuari> usuaris;
        usuaris = usuariManager.getAllUsuari();

        return convertUsuaristoUsers(usuaris);
    }


    public ArrayList<User> convertUsuaristoUsers(ArrayList<Usuari> usuaris){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < usuaris.size(); i++){
            users.add(new User(usuaris.get(i).getUserName(), usuaris.get(i).getEdat(),
                    usuaris.get(i).isPremium(), usuaris.get(i).getCorreo(), usuaris.get(i).getPassword(),
                    usuaris.get(i).getUrlFoto(), usuaris.get(i).getLenguaje(),usuaris.get(i).getDescription()));
        }
        System.out.println("Usuaris affegits" + users.size());
        return users;
    }

    public User getUser(String username){

       Usuari u = usuariManager.getUsuari(username);

       User user = new User(u.getUserName(), u.getEdat(), u.isPremium(), u.getCorreo(), u.getPassword(), u.getUrlFoto(), u.getLenguaje(), u.getDescription());

       return user;
    }

}