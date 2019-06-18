package Server.Network;

import java.net.Socket;
import java.util.ArrayList;

import Server.Model.Server;
import User.Controller.ControllerClient;
import User.Model.Match;
import User.Model.User;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class DedicatedServer extends Thread{

    //CLIENT ATRIBUTES
    private static Socket sServidor;
    private Server server;
    private static DataOutputStream doStream;
    private static DataInputStream diStream;
    private ObjectOutputStream ooStream;
    private ObjectInputStream oiStream;
    private boolean running;

    public DedicatedServer(Socket socket, Server server) throws IOException {
        this.server = server;
        sServidor = socket;
        running = true;
        diStream = new DataInputStream(sServidor.getInputStream());
        doStream = new DataOutputStream(sServidor.getOutputStream());
        ooStream = new ObjectOutputStream(sServidor.getOutputStream());
        oiStream = new ObjectInputStream(sServidor.getInputStream());
        start();
    }

    //-------------------------------------------------------------------------------//


    @Override
    public void run() {
        boolean ok;
        User currentUser, likedUser;

        try {
            ooStream.writeObject(server.getAllUsers());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        try {
            while (running) {
                int id = diStream.readInt();
                switch (id) {
                    case 1:
                        String username = diStream.readUTF();
                        String password = diStream.readUTF();

                        ok = server.comprobarLogIn(username, password);

                        doStream.writeBoolean(ok);

                        if (ok) {
                            User u = server.getUser(username);
                            System.out.println("user name connected " + u.getUserName());
                            ooStream.writeObject(u);
                            doStream.writeInt(server.getMatchList(u.getUserName()).size());
                            sendMatches(u);
                        }
                        break;

                    case 2:
                        User user = (User) oiStream.readObject();
                        ok = server.comprobarRegistro(user);
                        doStream.writeBoolean(ok);
                        sendMatches(user);
                        break;

                    case 3: //user aceptado(liked)
                        currentUser = (User) oiStream.readObject();
                        likedUser = (User) oiStream.readObject();
                        ok = server.acceptUser(currentUser, likedUser);
                        doStream.writeBoolean(ok);
                        sendMatches(currentUser);
                        break;

                    case 4:
                        currentUser = (User) oiStream.readObject();
                        likedUser = (User) oiStream.readObject();
                        server.declineUser(currentUser, likedUser);
                        sendMatches(currentUser);
                        break;

                    case 5:
                        user = (User) oiStream.readObject();
                        ok = server.actualizaUser(user);
                        doStream.writeBoolean(ok);
                        sendMatches(user);
                        break;
                    default:
                        System.out.println("DEFAULT!!!");
                        break;
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (!sServidor.isClosed()) {
                try {
                    sServidor.close();
                    server.removeFromDedicatedList(this);
                } catch (IOException e) {
                }
            }
        }
    }

    private void sendMatches(User u) throws SQLException, IOException {
        ArrayList<Match> matches = server.getMatchList(u.getUserName());
        doStream.writeInt(matches.size());
        if (server.getMatchList(u.getUserName()).size() != 0){
            for (int i = 0; i < matches.size(); i++){
                ooStream.writeObject(matches.get(i));
            }
        }
    }

    public static void disconnectClient() {
        try {
            sServidor.close();
            diStream.close();
            doStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        running = false;
        disconnectClient();
    }
}