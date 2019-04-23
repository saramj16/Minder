package Network;

import Server.Model.Server;
import Server.Model.entity.UsuariManager;
import User.Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetworkManager {

    //SERVER ATRIBUTES
    private Socket sClient;
    private ServerSocket sServer;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;
    private Server server;

    private static final int PORT = 9999;

    public ServerNetworkManager(Server server) throws IOException {
        this.sServer = new ServerSocket(PORT);
        this.server = server;
    }

    public void connectServer() throws IOException, ClassNotFoundException {
        UsuariManager usuariManager = new UsuariManager();
        System.out.println("The date server is running...");

       while (true) {
            this.sClient = sServer.accept();
            System.out.println("He acceptat");
            User currentUser, likedUser;
            diStream = new DataInputStream(sClient.getInputStream());
            doStream = new DataOutputStream(sClient.getOutputStream());
            oiStream = new ObjectInputStream(sClient.getInputStream());
            boolean ok;
            doStream.writeUTF("Hola!! Sóc el servidor.");

            int id = diStream.readInt();

            switch (id){
                case 1:
                    String username = diStream.readUTF();
                    String password = diStream.readUTF();
                    System.out.println("username = " + username);
                    System.out.println("password = " + password);
                    ok = server.comprobarLogIn(username, password);
                    doStream.writeBoolean(ok);
                    break;

                case 2:
                    User user = (User) oiStream.readObject();
                    ok = server.comprobarRegistro(user);
                    doStream.writeBoolean(ok);
                    break;

                case 3:
                    currentUser = (User) oiStream.readObject();
                    likedUser = (User) oiStream.readObject();
                    server.acceptUser(currentUser, likedUser);
                    break;

                case 4:
                    currentUser = (User) oiStream.readObject();
                    likedUser = (User) oiStream.readObject();
                    server.declineUser(currentUser, likedUser);
                    break;

                default:
                    System.out.println("DEFAULT!!!");
                    break;
            }

            diStream.close();
            doStream.close();
            sServer.close();
            oiStream.close();
        }

    }
}
