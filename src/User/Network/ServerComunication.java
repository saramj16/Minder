package User.Network;

import Server.Model.Controller.Controller;
import User.Controller.ControllerClient;
import User.Model.Connectivity;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;
import User.View.AutenticationView;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import User.Model.configReader.Configuracio;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe network que permet la connexió del client amb el servidor
 */
public class ServerComunication extends Thread{
    private Socket sClient;
    private static  int port;// = 9999;
    private static String ip;// = "localhost";
    public AutenticationView autenticationView;
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private ObjectInputStream oiStream;
    private ObjectOutputStream ooStream;
    private ObjectInputStream oiStream2;
    private DataInputStream diStream2;
    private Connectivity connectivity;
    private ControllerClient controller;




    public ServerComunication() throws IOException {
        //Totxaco per llegor del Json el port del servidor
        Configuracio config;
        Gson gson = new Gson();
        JsonReader jReader;
        try {
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Configuracio.class);                       //Llegeix el fitxer Json
            port = Integer.parseInt(config.getConfigClient().getPort_server());
            ip = config.getConfigClient().getIp_server();
        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }

        Socket sClient = new Socket(ip, port);
        Socket sClient2 = new Socket(ip, port+10);

        diStream = new DataInputStream(sClient.getInputStream());
        doStream = new DataOutputStream(sClient.getOutputStream());
        oiStream = new ObjectInputStream(sClient.getInputStream());
        ooStream = new ObjectOutputStream(sClient.getOutputStream());

        oiStream2 = new ObjectInputStream(sClient2.getInputStream());
        diStream2 = new DataInputStream(sClient2.getInputStream());
        this.connectivity = new Connectivity(oiStream2, diStream2);

        start();
    }

    /**
     * Funcio que implementa les funcionalitats de la classe.
     * En funcio de l'int que rebi fa una opcio o una altra
     * @param id
     * @param object1
     * @param object2
     * @return
     * @throws IOException
     */
    public boolean functionalities (int id, Object object1, Object object2) throws IOException {
        boolean ok = false;
        doStream.writeInt(id);
        System.out.println("fucnionalidad del ServerComunication: " + id);

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

            case 4: //user declinado --> object1 = currentUser, object2 = likedUser
                ooStream.writeObject(object1);
                ooStream.writeObject(object2);
                break;

            case 5: //editar usuario --> object1 = user modificado, object2= null
                ooStream.writeObject(object1);
                ok = diStream.readBoolean();
                //Aqui no lee bien la mierda esta
                System.out.println("Editar usuario OK: " + ok);
                break;

            case 6: //refresh
                System.out.println("refresh");
                break;

            case 7: //sendMessage --> obj1 = mensaje obj2 = user2 del chat
                System.out.println("mandamos el mensaje al ds");
                ooStream.writeUTF(String.valueOf(object1));
                ooStream.writeObject(object2);
                System.out.println("mandamos el user al que va el mensaje");
                break;

            case 8: //Undo match  --> obj1 = currentUser, obj2 = chatUser
                System.out.println("Desfent match...");
                ooStream.writeObject(object1);
                ooStream.writeObject(object2);
                break;

            case 9: //Rebre llista possibles match
                System.out.println("demanant llista de possibles match...");
                doStream.writeUTF((String)object1);
                ArrayList<User> possiblesMatch = new ArrayList<>();
                //possiblesMatch =  oiStream.readObject(possiblesMatch);
                break;
        }

        return ok;
    }

    public User getCurrentUser() throws IOException, ClassNotFoundException {

        User currentUser = (User) oiStream.readObject();
        System.out.println(currentUser.getUserName());

        return currentUser;
    }

    public ArrayList<User> getAllUsers() throws IOException, ClassNotFoundException {

        return (ArrayList<User>) oiStream.readObject();
    }

    public ArrayList<Match> getListaMatches() throws IOException, ClassNotFoundException {
        Match match;
        ArrayList<Match> matches = new ArrayList<>();
        ArrayList<Mensaje> mensajes = new ArrayList<>();

        int sizeMatches = diStream.readInt();
        System.out.println("sizeMatches: " + sizeMatches);


        if (sizeMatches != 0){
            for (int i = 0; i < sizeMatches; i++){
                match = (Match) oiStream.readObject();
                matches.add(match);
            }
        }


        return matches;
    }

    private User llegimUsuari() throws IOException {
        String nom = diStream.readUTF();
        System.out.println("nom " + nom);
        int edat = diStream.readInt();
        System.out.println("edat " + edat);
        boolean premium = diStream.readBoolean();
        System.out.println("Premium " + premium);
        String correu = diStream.readUTF();
        System.out.println("correu " + correu);
        String password = diStream.readUTF();
        System.out.println("password " + password);
        String urlFoto = diStream.readUTF();
        System.out.println("url " + urlFoto);
        String lenguaje = diStream.readUTF();
        System.out.println("lenguaje " + lenguaje);
        String description = diStream.readUTF();
        System.out.println("description " + description);

        User u = new User(nom,edat,premium,correu,password,urlFoto,lenguaje,description);
        return u;
    }

    public ArrayList<Mensaje> getMessages(User currentUser, User user2) throws IOException, ClassNotFoundException {
        doStream.writeInt(10);
        ooStream.writeObject(currentUser);
        ooStream.writeObject(user2);
        ArrayList<Mensaje> m = (ArrayList<Mensaje>) oiStream.readObject();

        return m;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ServerComunication.port = port;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        ServerComunication.ip = ip;
    }

    public void setController(ControllerClient controller) {
        this.controller = controller;
        connectivity.setController(controller);
    }

    public Connectivity getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(Connectivity connectivity) {
        this.connectivity = connectivity;
    }

    public ArrayList<String> getAcceptedUsers(User currentUser){
        Connection con;
        String nom;
        String query1  = "SELECT user2 FROM Matx WHERE user1 = '"+ currentUser.getUserName() +"' AND accept = 1;";
        ArrayList<String> llista = new ArrayList<>();

        //Totxaco per llegor del Json el port
        Server.Model.configReader.Configuracio config = new Server.Model.configReader.Configuracio();
        Gson gson = new Gson();
        JsonReader jReader;
        try {
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Server.Model.configReader.Configuracio.class);                       //Llegeix el fitxer Json
            //this.port = Integer.parseInt(config.getConfigServer().getPort_client()) ;
        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }

        try {
            con = DriverManager.getConnection(config.getConfigServer().getIp_bbdd(),
                    config.getConfigServer().getUsername_bbdd(), config.getConfigServer().getPassword_bbdd());
            PreparedStatement ps = con.prepareStatement(query1);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) { nom = resultSet.getString("user2");
                System.out.println(nom);
                llista.add(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i<llista.size(); i++){
            System.out.println("NOM" + llista.get(i));
        }
        return llista;
    }
}