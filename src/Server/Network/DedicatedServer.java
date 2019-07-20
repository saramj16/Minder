package Server.Network;

import java.net.Socket;
import java.util.ArrayList;

import Server.Model.Server;
import Server.Model.entity.Usuari;
import User.Controller.ControllerClient;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Classe servidor dedicat que permet que el servidor atengui més d'un usuari simultàneament
 */
public class DedicatedServer extends Thread{

    //CLIENT ATRIBUTES
    private static Socket sServidor;
    private Server server;
    private static DataOutputStream doStream;
    private static DataInputStream diStream;
    private ObjectOutputStream ooStream;
    private ObjectInputStream oiStream;
    private boolean running;
    private User user;
    private User mainUser;

    /**
     * Constructor del Servidor Dedicat
     * @param socket
     * @param server
     * @throws IOException
     */
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


    /**
     * Inicia el Thread de funcionament del servidor dedicat
     */
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
                    case 1: //Comprova login --> object1 = username, object2 = password
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

                    case 2: //registrar usuario --> object1 = user registrandose, object2= null
                        User user = (User) oiStream.readObject();
                        ok = server.comprobarRegistro(user);
                        if (ok) {
                            server.addUsuari(new Usuari(user.getUserName(), user.getEdat(), user.isPremium(),
                                    user.getCorreo(), user.getPassword(), user.getUrlFoto(), user.getLenguaje(), user.getDescription()));
                        }
                        doStream.writeBoolean(ok);
                        sendMatches(user);
                        break;

                    case 3: //user aceptado(liked) --> object1 = currentUser, object2 = likedUser
                        currentUser = (User) oiStream.readObject();
                        likedUser = (User) oiStream.readObject();
                        ok = server.acceptUser(currentUser, likedUser);
                        doStream.writeBoolean(ok);
                        sendMatches(currentUser);
                        break;

                    case 4: //user declinado --> object1 = currentUser, object2 = likedUser
                        currentUser = (User) oiStream.readObject();
                        likedUser = (User) oiStream.readObject();
                        server.declineUser(currentUser, likedUser);
                        sendMatches(currentUser);
                        break;

                    case 5: //editar usuario --> object1 = user modificado, object2= null
                        user = (User) oiStream.readObject();
                        ok = server.actualizaUser(user);
                        doStream.writeBoolean(ok);
                        sendMatches(user);
                        if (ok){
                            server.announceChanges(user);
                        }
                        break;

                    case 6: //refresh
                        ooStream.writeObject(server.getAllUsers());
                        break;

                    case 7://sendMessage --> obj1 = mensaje obj2 = user2 del chat
                        String mensajeRecibido = oiStream.readUTF();
                        User userRecibe = (User) oiStream.readObject();
                        server.addMensaje(mensajeRecibido, mainUser, userRecibe);
                        ok = server.isUserRecibeConnected(userRecibe, mensajeRecibido);
                        break;

                    case 8: //Undo match  --> obj1 = currentUser, obj2 = chatUser
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
}