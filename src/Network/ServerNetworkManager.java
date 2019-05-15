package Network;

import Server.Model.Server;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerNetworkManager {

    //SERVER ATRIBUTES
    private Socket sClient;
    private ServerSocket sServer;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;
    private ObjectOutputStream ooStream;
    private Server server;

    private static final int PORT = 9999;

    public ServerNetworkManager(Server server) throws IOException {
        this.sServer = new ServerSocket(PORT);
        this.server = server;
    }

    public void connectServer() throws IOException, ClassNotFoundException {

        System.out.println("The date server is running...");
    //   server.addUsuari(new Usuari("Jofre", 25, true, "jofre@minder.com", "jofre"));
     //  server.addUsuari(new Usuari("Sara", 25, true, "sara@minder.com", "sara"));
     //   server.addUsuari(new Usuari("Javo", 25, true, "javo@minder.com", "javo"));
     //   server.addUsuari(new Usuari("Manel", 25, true, "manel@minder.com", "manel"));
     //  server.addUsuari(new Usuari("Marcel", 25, true, "marcel@minder.com", "marcel"));



        while (true) {
            this.sClient = sServer.accept();
            System.out.println("He acceptat");
            User currentUser, likedUser;
            diStream = new DataInputStream(sClient.getInputStream());
            doStream = new DataOutputStream(sClient.getOutputStream());
            oiStream = new ObjectInputStream(sClient.getInputStream());
            ooStream = new ObjectOutputStream(sClient.getOutputStream());
            boolean ok;
            doStream.writeUTF("Hola!! Sóc el servidor.");


           int id = diStream.readInt();
            switch (id){
                case 1:
                    String username = diStream.readUTF();
                    String password = diStream.readUTF();
                  //  System.out.println("username = " + username);
                  //  System.out.println("password = " + password);
                    ok = server.comprobarLogIn(username, password);
                    System.out.println("OK = " + ok);
                    doStream.writeBoolean(ok);
                    if (ok){
                        User u = server.getUser(username);
                        System.out.println("user name connected " + u.getUserName());

                        ooStream.writeObject(u);

                    }
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
                  //  server.declineUser(currentUser, likedUser);
                    break;

                default:
                    System.out.println("DEFAULT!!!");
                    break;
            }

            //diStream.close();
            //doStream.close();
            //sServer.close();
            //oiStream.close();
        }

    }
}
