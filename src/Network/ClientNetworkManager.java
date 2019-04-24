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


        diStream.close();
        doStream.close();
        disconnectClient();
    }

    public void disconnectClient() {
        try {
            sServidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean functionalities (int id, Object object1, Object object2) throws IOException {
        boolean ok = false;
        doStream.writeInt(id);

        switch (id){
            case 1:
                //object1 = username, object2 = password
                doStream.writeUTF((String) object1);
                doStream.writeUTF((String) object2);
                ok = diStream.readBoolean();
                break;

            case 2:
                //object1 = user, object2= null
                ooStream.writeObject(object1);
                ok = diStream.readBoolean();
                break;

            case 3:
                //object1 = currentUser, object2 = likedUser
                ooStream.writeObject(object1);
                ooStream.writeObject(object2);
                break;

            case 4:
                //object1 = currentUser, object2 = likedUser
                ooStream.writeObject(object1);
                ooStream.writeObject(object2);
                break;
        }

        return ok;
    }
    public boolean newUser(User user) throws IOException {
            doStream.writeUTF("Registra");
            ooStream.writeObject(user);

            //el server habrá de devolver un booleano según se ha puesto el nuevo user o no
            return true;
    }

}
