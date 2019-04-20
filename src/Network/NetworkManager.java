package Network;

import User.Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager extends Thread{

    //CLIENT ATRIBUTES
    private static final String IP = "localhost";
    private Socket sServidor;
    private DataOutputStream ClientdoStream;
    private DataInputStream ClientdiStream;
    private ObjectOutputStream ClientooStream;

    //SERVER ATRIBUTES
    private Socket sClient;
    private ServerSocket sServer;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private static final int PORT = 34567;

    public NetworkManager(){
    }

    public void connectServer() throws IOException {
        this.sClient = new Socket();
        try (var sServer = new ServerSocket(PORT)) {
            System.out.println("The date server is running...");
            while (true) {
                try (var sClient = sServer.accept()) {
                    diStream = new DataInputStream(sClient.getInputStream());
                    doStream = new DataOutputStream(sClient.getOutputStream());

                    doStream.writeUTF("Hola!! Sóc el servidor.");

                }
            }
        }
    }

    //-------------------------------------------------------------------------------//
    public void connectClient() throws IOException {
        sServidor = new Socket(IP, PORT);
        ClientdiStream = new DataInputStream(sServidor.getInputStream());
        ClientdoStream = new DataOutputStream(sServidor.getOutputStream());
        ClientooStream = new ObjectOutputStream(sServidor.getOutputStream());

        String respostaServer = ClientdiStream.readUTF();
        System.out.println("el server dice: " + respostaServer);
        this.start();

    }

    public void disconnectClient() {
        try {
            sServidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newUser(User user) throws IOException {
        ClientooStream.writeObject(user);
    }

}
