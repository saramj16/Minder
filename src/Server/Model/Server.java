package Server.Model;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Model.User;

import java.io.IOException;

public class Server {
    private int serverPort;
    private UsuariManager usuariManager;

    public Server(UsuariManager usuariManager) throws IOException {
        this.usuariManager = usuariManager;
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
}
