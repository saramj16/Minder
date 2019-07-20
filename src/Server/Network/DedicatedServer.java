package Server.Network;

import java.net.Socket;
import java.util.ArrayList;

import Server.Model.Server;
import Server.Model.database.DBConnector;
import Server.Model.database.dao.MissatgeDAO;
import Server.Model.entity.Missatge;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import User.Controller.ControllerClient;
import User.Model.Match;
import User.Model.Mensaje;
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
    private static DataOutputStream doStream2;
    private ObjectOutputStream ooStream2;
    private boolean running;
    private User user;
    private User mainUser;
    private UsuariManager usuariManager;


    public DedicatedServer(Socket socket, Server server, UsuariManager usuariManager, Socket s2) throws IOException {
        this.server = server;
        sServidor = socket;
        this.usuariManager = usuariManager;
        running = true;
        diStream = new DataInputStream(sServidor.getInputStream());
        doStream = new DataOutputStream(sServidor.getOutputStream());
        ooStream = new ObjectOutputStream(sServidor.getOutputStream());
        oiStream = new ObjectInputStream(sServidor.getInputStream());

        ooStream2 = new ObjectOutputStream(s2.getOutputStream());
        doStream2 = new DataOutputStream(s2.getOutputStream());

        start();
    }

    //-------------------------------------------------------------------------------//


    @Override
    public void run() {
        boolean ok;
        User currentUser = null, likedUser;

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
                            this.mainUser = u;
                            this.user = u;
                            System.out.println("user name connected " + u.getUserName());
                            ooStream.writeObject(u);
                            sendMatches(u);
                        }
                        break;

                    case 2:
                        User user = (User) oiStream.readObject();
                        ok = server.comprobarRegistro(user);
                        if (ok) {
                            server.addUsuari(new Usuari(user.getUserName(), user.getEdat(), user.isPremium(),
                                    user.getCorreo(), user.getPassword(), user.getUrlFoto(), user.getLenguaje(), user.getDescription() ));
                        }
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
                        if (ok){
                            server.announceChanges(user);
                        }
                        break;

                    case 6:
                        System.out.println("opcion 6 del ds");
                        ooStream.writeObject(server.getAllUsers());
                        break;

                    case 7://sendMessage
                        String mensajeRecibido = oiStream.readUTF();
                        User userRecibe = (User) oiStream.readObject();
                        server.addMensaje(mensajeRecibido, mainUser, userRecibe);
                        server.isUserRecibeConnected(userRecibe, mainUser, mensajeRecibido);
                        break;

                    case 8: //Undo match
                        User usuari = (User)oiStream.readObject();
                        User usuariChat = (User)oiStream.readObject();
                        server.deleteMatch( usuari.getUserName(),  usuariChat.getUserName());
                        //ooStream.writeObject(server.getMatchList(usuari.getUserName()));
                        break;


                    case 10://chat mensajes
                        User user1 = (User) oiStream.readObject();
                        User user2 = (User) oiStream.readObject();
                        ooStream.writeObject(server.getMessages(user1.getUserName(), user2.getUserName()));
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
                    System.out.println("error al cerrar el servidor dedicado");
                }
            }
        }
    }

    private void sendMatches(User u) throws SQLException, IOException {
        ArrayList<Match> matches = server.getMatchList(u.getUserName());
        doStream.writeInt(matches.size());
        System.out.println("matchesSize: " + matches.size());


        if (matches.size() > 0){
            for (int i = 0; i < matches.size(); i++) {
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

    public void anounceChanges(User user) {
        if (!user.equals(this.user)){

        }
    }

    public ArrayList<Mensaje> getMessages(User currentUser, User user2) throws SQLException {
        return server.getMessages(currentUser.getUserName(), user2.getUserName());
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }


    public void setIfMessageArrived(User currentUser, String mensaje) throws IOException {
        doStream2.writeInt(1);
        doStream2.writeUTF(mensaje);
    }
}