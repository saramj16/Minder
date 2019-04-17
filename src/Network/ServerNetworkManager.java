package Network;

import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetworkManager {

    //SERVER ATRIBUTES
    private Socket sClient;
    private ServerSocket sServer;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;

    private static final int PORT = 9999;

    public ServerNetworkManager() throws IOException {
        this.sServer = new ServerSocket(PORT);
    }

    public void connectServer() throws IOException {
        System.out.println("The date server is running...");


        while (true) {
            this.sClient = sServer.accept();
            System.out.println("He acceptat");
            diStream = new DataInputStream(sClient.getInputStream());
            doStream = new DataOutputStream(sClient.getOutputStream());

            doStream.writeUTF("Hola!! Sóc el servidor.");

            diStream.close();
            doStream.close();
            sServer.close();
        }

    }
}
