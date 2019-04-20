package Network;

import User.Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientNetworkManager{

    //CLIENT ATRIBUTES
    private static final String IP = "localhost";
    private Socket sServidor;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectOutputStream ooStream;
    private static final int PORT = 9999;

    public ClientNetworkManager() throws IOException {
        System.out.println("Intento conectar");
        this.sServidor = new Socket(IP, PORT);
        this.diStream = new DataInputStream(sServidor.getInputStream());
        this.doStream = new DataOutputStream(sServidor.getOutputStream());
        this.ooStream = new ObjectOutputStream(sServidor.getOutputStream());
    }

    //-------------------------------------------------------------------------------//
    public void connectClient() throws IOException {
        String respostaServer = diStream.readUTF();
        System.out.println("el server dice: " + respostaServer);


        //diStream.close();
        //doStream.close();
        //disconnectClient();
    }

    public void disconnectClient() {
        try {
            sServidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newUser(User user) throws IOException {
            System.out.println("New User");
        ooStream.writeObject(user);
    }

}
