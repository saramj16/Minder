package Server.Model;
import Server.Model.entity.UsuariManager;
import User.Model.Match;
import User.Model.User;

import java.io.IOException;
import java.util.HashMap;

public class Server {
    private int serverPort;
    private UsuariManager usuariManager;
    private static HashMap<String, User> users;


    public Server(UsuariManager usuariManager) throws IOException {
        this.usuariManager = usuariManager;
        this.users = new HashMap<>();
    }

    //---------------------------------------------------------------------------------------//

    public boolean comprobarLogIn(String username, String password){
        System.out.println("login = true");
        return true;
    }

    public boolean comprobarRegistro(User user){

        return true;
    }

    public void acceptUser(User currentUser, User userLike){
        //TODO: modificar esto en la BBDD
        currentUser.getListaLikedUsers().add(userLike);
        for (User u : userLike.getListaLikedUsers()){
            if (u == currentUser){
                String id = currentUser.getId() + "-" + userLike.getId();
                Match match = new Match(currentUser, userLike, id);
                currentUser.getListaMatch().put(id, match);
                userLike.getListaMatch().put(id, match);
                //TODO informar a los 2 users de que ha habido un match
            }
        }
    }

    public void declineUser(User currentUser, User userLike){
        //TODO: ns que coño se ha de hacer
    }







    /*public boolean registration(String userName, int edat, String premium, String correo, String password){
        Usuari u = new Usuari(userName,edat,premium,correo,password,null,null,null);
        usuariManager.addUsuari(u);
        return true;
    }*/

    public int getServerPort() { return serverPort; }
    public void setServerPort(int serverPort) { this.serverPort = serverPort; }
    public static HashMap<String, User> getUsers() { return users; }
    public void setUsers(HashMap<String, User> users) { this.users = users; }
}
