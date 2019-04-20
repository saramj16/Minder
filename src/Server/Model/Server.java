package Server.Model;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Model.User;

import java.io.IOException;
import java.util.HashMap;

public class Server {
    private int serverPort;
    private UsuariManager usuariManager;
    private HashMap<String, User> users;


    public Server(UsuariManager usuariManager) throws IOException {
        this.usuariManager = usuariManager;
        this.users = new HashMap<>();
    }


    public boolean logIn(String password, String username){
        //SARA Y MARCEL ESTO PAH VOSOTROS!!!
        return true;
    }

    public boolean registration(String userName, int edat, String premium, String correo, String password){
        Usuari u = new Usuari(userName,edat,premium,correo,password,null,null,null);
        usuariManager.addUsuari(u);
        return true;
    }

    public int getServerPort() { return serverPort; }
    public void setServerPort(int serverPort) { this.serverPort = serverPort; }
    public HashMap<String, User> getUsers() { return users; }
    public void setUsers(HashMap<String, User> users) { this.users = users; }
}
