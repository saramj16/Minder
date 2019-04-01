package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int serverPort;
    private Socket sClient;
    private ServerSocket sServer;
    private DataOutputStream doStream;
    private DataInputStream diStream;

    public Server() throws IOException {
        this.serverPort = 34567;
        this.sClient = new Socket();

    }

    public void start() throws IOException {


        try (var sServer = new ServerSocket(serverPort)) {
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
}
