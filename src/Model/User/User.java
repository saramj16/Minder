package Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class User {
    private String userName;
    private int age;
    private boolean premium;
    private String email;
    private String password;
    private String urlProfilePic;
    private String language;
    private String description;
    private ArrayList<Match> listaMatch;
    private ArrayList<User> listaAcceptedUsers;
    private int serverPort;
    private Socket sServer;
    private DataOutputStream doStream;
    private DataInputStream diStream;

    public User(String userName, int edat, boolean premium, String correo, String password,
                String urlFoto, String lenguaje, String description) throws IOException {
        this.serverPort = 34567;
        this.userName = userName;
        this.age = edat;
        this.premium = premium;
        this.email = correo;
        this.password = password;
        this.urlProfilePic = urlFoto;
        this.language = lenguaje;
        this.description = description;
        this.listaMatch = new ArrayList<>();
        this.listaAcceptedUsers = new ArrayList<>();
    }

    public void start() throws IOException {
        sServer = new Socket("127.0.0.1", serverPort);
        doStream = new DataOutputStream(sServer.getOutputStream());
        diStream = new DataInputStream(sServer.getInputStream());
        String respostaServer = diStream.readUTF();
        System.out.println("el server dice: " + respostaServer);
        sServer.close();
    }









    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public boolean isPremium() { return premium; }
    public void setPremium(boolean premium) { this.premium = premium; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUrlProfilePic() { return urlProfilePic; }
    public void setUrlProfilePic(String urlProfilePic) { this.urlProfilePic = urlProfilePic; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ArrayList<Match> getListaMatch() { return listaMatch; }
    public void setListaMatch(ArrayList<Match> listaMatch) { this.listaMatch = listaMatch; }
    public ArrayList<User> getListaAcceptedUsers() { return listaAcceptedUsers; }
    public void setListaAcceptedUsers(ArrayList<User> listaAcceptedUsers) { this.listaAcceptedUsers = listaAcceptedUsers; }
}
