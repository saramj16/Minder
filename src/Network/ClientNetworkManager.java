package Network;

import User.Model.User;

import java.io.*;
import java.net.Socket;

public class ClientNetworkManager{

    //CLIENT ATRIBUTES
    private static final String IP = "localhost";
    private Socket sServidor;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectOutputStream ooStream;
    private ObjectInputStream oiStream;
    private static final int PORT = 9999;

    public ClientNetworkManager() throws IOException {
        System.out.println("Intento conectar");
        this.sServidor = new Socket(IP, PORT);
        this.diStream = new DataInputStream(sServidor.getInputStream());
        this.doStream = new DataOutputStream(sServidor.getOutputStream());
        this.ooStream = new ObjectOutputStream(sServidor.getOutputStream());
        this.oiStream = new ObjectInputStream(sServidor.getInputStream());
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

    public boolean functionalities (int id, Object object1, Object object2) throws IOException {
        boolean ok = false;
        doStream.writeInt(id);
        System.out.println(id);

        switch (id){
            case 1:
                //object1 = username, object2 = password
                doStream.writeUTF((String) object1);
                doStream.writeUTF((String) object2);
                ok = diStream.readBoolean();
                break;

            case 2://registrar usuario --> object1 = user registrandose, object2= null
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

    public User getCurrentUser() throws IOException, ClassNotFoundException {

        User currentUser = (User) oiStream.readObject();
        System.out.println(currentUser.getUserName());

        return currentUser;
    }
}
