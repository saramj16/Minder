package Server.Model;
import Server.Model.database.DBConnector;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Model.Match;
import User.Model.User;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Server {
    private int serverPort;
    private UsuariManager usuariManager;
    private  ArrayList<User> users;


    public Server(UsuariManager usuariManager) throws IOException {
        this.usuariManager = usuariManager;
        this.users = new ArrayList<>();
    }

    //---------------------------------------------------------------------------------------//

    public boolean comprobarLogIn(String username, String password){
        System.out.println("login = true");
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

    public void acceptUser(User currentUser, User userLike){
        ArrayList<User> currentUserlikedUsers = new ArrayList<>();
        ArrayList<User> userLikeLikedUsers = new ArrayList<>();

        addLikedUserToCurrentUser(currentUser, userLike);
        userLikeLikedUsers = getLikedUsers(userLike);
        //TODO: modificar esto en la BBDD
        currentUser.getListaLikedUsers().add(userLike);
        for (User u : userLikeLikedUsers){
            if (u == currentUser){
                String id = currentUser.getId() + "-" + userLike.getId();
                Match match = new Match(currentUser, userLike, id);
                currentUser.getListaMatch().put(id, match);
                userLike.getListaMatch().put(id, match);
                JOptionPane.showMessageDialog(null, "NEW MATCH!");
            }
        }
    }



    /*public boolean registration(String userName, int edat, String premium, String correo, String password){
        Usuari u = new Usuari(userName,edat,premium,correo,password,null,null,null);
        usuariManager.addUsuari(u);
        return true;
    }*/

    public int getServerPort() { return serverPort; }
    public void setServerPort(int serverPort) { this.serverPort = serverPort; }
    public ArrayList<User> getUsers() { return users; }
    public void setUsers(ArrayList users) { this.users = users; }


}



