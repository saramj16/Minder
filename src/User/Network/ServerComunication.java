package User.Network;

import User.Model.User;
import User.View.AutenticationView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerComunication extends Thread{
    private Socket sClient;
    private static final int PORT = 9999;
    private static final String IP = "localhost";
    public AutenticationView autenticationView;
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private ObjectInputStream oiStream;
    private ObjectOutputStream ooStream;


    public ServerComunication(AutenticationView autenticationView) throws IOException {
        sClient = new Socket(IP, PORT);
        this.autenticationView = autenticationView;
        diStream = new DataInputStream(sClient.getInputStream());
        doStream = new DataOutputStream(sClient.getOutputStream());
        oiStream = new ObjectInputStream(sClient.getInputStream());
        ooStream = new ObjectOutputStream(sClient.getOutputStream());
        start();
    }


    public boolean functionalities (int id, Object object1, Object object2) throws IOException {
        boolean ok = false;
        doStream.writeInt(id);
        System.out.println(id);

        switch (id){
            case 1://object1 = username, object2 = password
                doStream.writeUTF((String) object1);
                doStream.writeUTF((String) object2);

                ok = diStream.readBoolean();
                break;

            case 2://registrar usuario --> object1 = user registrandose, object2= null
                ooStream.writeObject(object1);
                ok = diStream.readBoolean();
                break;

            case 3://user aceptado(liked) --> object1 = currentUser, object2 = likedUser
                ooStream.writeObject(object1);
                ooStream.writeObject(object2);
                ok = diStream.readBoolean();
                break;

            case 4://user declinaod --> object1 = currentUser, object2 = likedUser
                ooStream.writeObject(object1);
                ooStream.writeObject(object2);
                break;
        }

        return ok;
    }

    public void connectClient() throws IOException {
        String respostaServer = diStream.readUTF();
        System.out.println("el server dice: " + respostaServer);
    }


    public User getCurrentUser() throws IOException, ClassNotFoundException {

        User currentUser = (User) oiStream.readObject();
        System.out.println(currentUser.getUserName());

        return currentUser;
    }

    public ArrayList<User> getAllUsers() throws IOException, ClassNotFoundException {
        return (ArrayList<User>) oiStream.readObject();
    }
}