package Server.Model;

import Server.Model.entity.Matx;
import Server.Model.entity.Missatge;
import Server.Model.entity.Usuari;
import Server.Model.entity.UsuariManager;
import Server.Network.DedicatedServer;
import User.Model.Match;
import User.Model.Mensaje;
import User.Model.User;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import Server.Model.configReader.Configuracio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe Servidor. Inicialitza els servidors dedicats i fa de nexe entre aquests i la resta de classes del programa
 */
public class Server extends Thread{
    private int serverPort;
    private UsuariManager usuariManager;
    private ArrayList<User> users;
    private boolean running;
    private ArrayList<DedicatedServer> dedicatedServerList;
    private int port = 9999;

    /**
     * Constructor
     * @param usuariManager
     * @throws SQLException
     */
    public Server(UsuariManager usuariManager) throws SQLException {
        this.usuariManager = usuariManager;
        this.users = getAllUsers();
        running = true;
        dedicatedServerList = new ArrayList<>();

        //Totxaco per llegor del Json el port
        Configuracio config = new Configuracio();
        Gson gson = new Gson();
        JsonReader jReader;
        try {
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Configuracio.class);                       //Llegeix el fitxer Json
            this.port = Integer.parseInt(config.getConfigServer().getPort_client()) ;
        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }
    }

    public int getServerPort() { return serverPort; }
    public void setServerPort(int serverPort) { this.serverPort = serverPort; }
    public ArrayList<User> getUsers() { return users; }
    public void setUsers(ArrayList users) { this.users = users; }

    //---------------------------------------------------------------------------------------//


    /**
     * Thread de funcionament del servidor
     */
    public void run() {

        try {
            ServerSocket sServer = new ServerSocket(port);
            ServerSocket sServer2 = new ServerSocket(port+10);

            addUsuari(new Usuari("Jofre", 25, true, "jofre@minder.com", "jofre","", "Java", "pene"));
            addUsuari(new Usuari("Sara", 20, true, "sara@minder.com", "sara", "", "C", "pene"));
            addUsuari(new Usuari("Javo", 22, true, "javo@minder.com", "javo", "", "C", "fucking bosssss"));
            addUsuari(new Usuari("Manel", 22, true, "manel@minder.com", "manel", "", "Java", "pene"));
            addUsuari(new Usuari("Marcel", 23, true, "marcel@minder.com", "marcel", "", "C", "pene"));

            while (running) {
                Socket sClient = sServer.accept();
                Socket sClient2 = sServer2.accept();
                DedicatedServer ds = new DedicatedServer(sClient, this, usuariManager, sClient2);
                dedicatedServerList.add(ds);
            }
            sServer.close();
            for (DedicatedServer ds : dedicatedServerList) {
                ds.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Esborra un servidor de la llista de servidors en funcionament
     * @param dedicatedServer
     */
    public void removeFromDedicatedList(DedicatedServer dedicatedServer) {
        dedicatedServerList.remove(dedicatedServer);
    }

    /**
     * Afegeix un usuari a la bbdd
     * @param u
     */
    public void addUsuari(Usuari u){
        usuariManager.addUsuari(u);
    }

    /**
     * Retorna true si existeix un usuari amb el username i contrasenya donats
     * @param username
     * @param password
     * @return
     */
    public boolean comprobarLogIn(String username, String password){
        //System.out.println("login = true");
        try {
            return usuariManager.comprovaLogin(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualitza un usuari donat a la BBDD
     * @param user
     * @return
     * @throws SQLException
     */
    public boolean actualizaUser(User user) throws SQLException {
        User user1 = getUser(user.getUserName());

        if (user1.getUserName() != null){
            System.out.println("Usuari a actualitzar " + user1.getUserName());
            updateUser(user1, user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Modifica els valors dels atributs d'un usuari donat
     * @param user2modificate
     * @param userModificated
     */
    private void updateUser(User user2modificate, User userModificated) {
        usuariManager.modificiaUsuari(new Usuari(userModificated.getUserName(),userModificated.getEdat(),userModificated.isPremium(),
                userModificated.getCorreo(), userModificated.getPassword(), userModificated.getUrlFoto(), userModificated.getLenguaje(), userModificated.getDescription()));

    }

    /**
     * Comproba que el registre d'un usuari nou compleix les condicions necessàries
     * @param user
     * @return
     */
    public boolean comprobarRegistro(User user){

        if (!usuariManager.searchUsuari(user.getUserName())){ //Si l'usuari no existeix


            boolean registreOk = true;

            String username = user.getUserName();
            String password = user.getPassword();
            int edat = user.getEdat();
            String correo = user.getCorreo();
            String urlFoto = user.getUrlFoto();
            String lenguaje = user.getLenguaje();
            String descripción = user.getDescription();


            //Cal que la contrasenya tingui com a mínim una longitud de 8 caràcters així com contingui com a mínim
            // majúscules, minúscules i valors numèrics
            boolean teMajus = true;
            if (password.equals(password.toLowerCase())) {
                teMajus = false;
            }

            boolean teMinus = true;
            if (password.equals(password.toUpperCase())) {
                teMinus = false;
            }

            boolean teNumeros = false;
            char[] passwordArray = password.toCharArray();
            for (char i : passwordArray) {
                if (i == '1' || i == '2' || i == '3' || i == '4' || i == '5' || i == '6' || i == '7' || i == '8' || i == '9' || i == '0') {
                    teNumeros = true;
                }
            }

            boolean passOk;
            if (password.length() > 8 && teMajus && teMinus && teNumeros ) {
                passOk = true;
            } else {
                passOk = false;
                registreOk = false;
            }

            if (passOk){
                if (edat < 17){
                    System.out.println("Tienes que tener más de 17 años!");
                    registreOk = false;                }
                if (edat > 100){
                    System.out.println("Este programa no es para dinosaurios!");
                    registreOk = false;
                }
            }else{
                registreOk = false;
            }

            if (registreOk) {
                usuariManager.addUsuari(new Usuari(user.getUserName(), user.getEdat(), user.isPremium(), user.getCorreo(),
                        user.getPassword(), user.getUrlFoto(), user.getLenguaje(), user.getDescription()));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Marca a la bbdd que un usuari ha acceptat a un altre
     * @param currentUser
     * @param userLike
     * @return
     * @throws SQLException
     */
    public boolean acceptUser(User currentUser, User userLike) throws SQLException {
        ArrayList<User> userLikeLikedUsers;

        userLikeLikedUsers = getLikedUsers(userLike.getUserName());
        // currentUser.getListaLikedUsers().add(userLike);
        addLikedUserToCurrentUser(currentUser, userLike);

        for (User u : userLikeLikedUsers){
            if (u.getUserName().equals(currentUser.getUserName())){
                usuariManager.addMatx(currentUser.getUserName(), userLike.getUserName());
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna un arrayList d'usuaris amb els que un usuari ha decidit que faria pràctiques
     * @param userLike
     * @return
     * @throws SQLException
     */
    public ArrayList<User> getLikedUsers(String userLike) throws SQLException {
        ArrayList<Usuari> likedUsers = usuariManager.getUsuarisAccepted(userLike);

        return convertUsuaristoUsers(likedUsers);

    }

    /**
     * Retorna la llista de Matches d'un usuari en concret
     * @param userLike
     * @return
     * @throws SQLException
     */
    public ArrayList<Match> getMatchList(String userLike) throws SQLException {
        ArrayList<Matx> matches = usuariManager.getMatxedUsers(userLike);

        return convertMatxToMach(matches);

    }

    public ArrayList<User> getMatchedUsers(String userMatch) throws SQLException {
        ArrayList<Usuari> usuaris = usuariManager.getUsuarisMatxes(userMatch);

        return convertUsuaristoUsers(usuaris);

    }

    /**
     * Afegeix un nou usuari a la llista d'usuaris amb likes d'un usuari
     * @param currentUser
     * @param userLike
     */
    private void addLikedUserToCurrentUser(User currentUser, User userLike) {
        usuariManager.addAccepted(currentUser.getUserName(), userLike.getUserName());

    }

    /**
     * Retorna tots els usuaris del programa
     * @return
     * @throws SQLException
     */
    public ArrayList<User> getAllUsers() throws SQLException {

        ArrayList<Usuari> usuaris;
        usuaris = usuariManager.getAllUsuari();

        return convertUsuaristoUsers(usuaris);
    }

    /**
     * Retorna tots els missatges entre dos usuaris en concret
     * @param user1
     * @param user2
     * @return
     * @throws SQLException
     */
    public ArrayList<Mensaje> getMessages(String user1, String user2) throws SQLException {

        ArrayList<Missatge> missatges;
        missatges = usuariManager.preparaChat(user1,user2);

        return convertMissatgeToMensaje(missatges);
    }


    /**
     * Converteix de la classe Usuari a la Classe User
     * @param usuaris
     * @return
     */
    public ArrayList<User> convertUsuaristoUsers(ArrayList<Usuari> usuaris)  {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < usuaris.size(); i++){

            users.add(new User(usuaris.get(i).getUserName(), usuaris.get(i).getEdat(),
                    usuaris.get(i).isPremium(), usuaris.get(i).getCorreo(), usuaris.get(i).getPassword(),
                    usuaris.get(i).getUrlFoto(), usuaris.get(i).getLenguaje(),usuaris.get(i).getDescription()));
        }

        return users;
    }

    /**
     * Converteix de la classe Matx a la classe Mach
     * @param matxes
     * @return
     * @throws SQLException
     */
    public ArrayList<Match> convertMatxToMach (ArrayList<Matx> matxes) throws SQLException {
        ArrayList<Match> matches = new ArrayList<>(matxes.size());

        if (matxes.size() != 0){
            for (int i = 0; i < matxes.size(); i++){
                matches.add(new Match(getUser(matxes.get(i).getUser1()), getUser(matxes.get(i).getUser2()), getMessages(matxes.get(i).getUser1(),matxes.get(i).getUser2())));
            }
        }

        return matches;
    }

    /**
     * Converteix de la classe Missatge a Mensaje
     * @param missatges
     * @return
     * @throws SQLException
     */
    public ArrayList<Mensaje> convertMissatgeToMensaje (ArrayList<Missatge> missatges) throws SQLException {
        ArrayList<Mensaje> mensajes = new ArrayList<>(missatges.size());

        for (int i = 0; i < missatges.size(); i++){
            User u = getUser(missatges.get(i).getUserSend());
            User u1 = getUser(missatges.get(i).getUserReceive());
            mensajes.add(new Mensaje(u.getUserName() + ": " + missatges.get(i).getMissatge(), u, u1));
        }

        return mensajes;
    }

    /**
     * Retorna un usuari en concret en base a un nom rebut
     * @param username
     * @return
     * @throws SQLException
     */
    public User getUser(String username) throws SQLException {

        Usuari u = usuariManager.getUsuari(username);


        User user = new User(u.getUserName(), u.getEdat(), u.isPremium(), u.getCorreo(), u.getPassword(), u.getUrlFoto(), u.getLenguaje(), u.getDescription());

        return user;
    }

    /**
     * Marca un usuari com a no acceptat per un altre
     * @param currentUser
     * @param declinedUser
     */
    public void declineUser(User currentUser, User declinedUser) {
        usuariManager.addVist(currentUser.getUserName(),declinedUser.getUserName());
    }

    /**
     * Anuncia canvis produits per un usuari a tots els servidors dedicats
     * @param user
     */
    public void announceChanges(User user) {
        for (DedicatedServer ds : dedicatedServerList){
            ds.anounceChanges(user);
        }
    }

    /**
     * Afegeix un missatge nou entre dos usuaris
     * @param mensajeRecibido
     * @param currentUser
     * @param userRecibe
     */
    public void addMensaje(String mensajeRecibido, User currentUser, User userRecibe) {
        usuariManager.afegeixMissatge(currentUser.getUserName(), userRecibe.getUserName(), mensajeRecibido);
        System.out.println("mensaje añadido");
    }

    public void isUserRecibeConnected(User userRecibe, User currentUser, String mensajeRecibido) throws IOException {
        for (DedicatedServer ds : dedicatedServerList){
            if (ds.getMainUser().getUserName().equals(userRecibe.getUserName())){
                System.out.println("el user está conectado");
                ds.setIfMessageArrived(currentUser, mensajeRecibido);
                System.out.println("mensaje enviado al otro user!!");
                break;
            }
        }
    }

    /**
     * Esborra un match entre dos usuaris concrets
     * @param u1
     * @param u2
     */
    public void deleteMatch(String u1, String u2) {
        usuariManager.deleteMatch(u1, u2);
    }



    public ArrayList<User> getLlistaPossiblesMatch(String username) throws SQLException {
        ArrayList<Usuari> users = new ArrayList<>();
        for (String str : usuariManager.getLlistaPossiblesMatch(username)) {
            users.add(usuariManager.getUsuari(str));
        }
        return convertUsuaristoUsers(users);
    }

}