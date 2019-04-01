package User;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class User {
    private String userName;
    private int edat;
    private boolean premium;
    private String correo;
    private String contraseña;
    private String urlFoto;
    private String lenguaje;
    private String descripción;
    private ArrayList<Match> listaMatch;
    private ArrayList<User> listaAcceptedUsers;
    private int serverPort;
    private Socket sServidor;
    private DataOutputStream doStream;
    private DataInputStream diStream;

    public User(String userName, int edat, boolean premium, String correo, String contraseña,
                String urlFoto, String lenguaje, String descripción) throws IOException {
        this.serverPort = 34567;
        this.userName = userName;
        this.edat = edat;
        this.premium = premium;
        this.correo = correo;
        this.contraseña = contraseña;
        this.urlFoto = urlFoto;
        this.lenguaje = lenguaje;
        this.descripción = descripción;
        this.listaMatch = new ArrayList<>();
        this.listaAcceptedUsers = new ArrayList<>();
    }

    public void start() throws IOException {
        sServidor = new Socket("127.0.0.1", serverPort);
        doStream = new DataOutputStream(sServidor.getOutputStream());
        diStream = new DataInputStream(sServidor.getInputStream());
        String respostaServer = diStream.readUTF();
        System.out.println("el server dice: " + respostaServer);
        sServidor.close();
    }

















    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public int getEdat() { return edat; }
    public void setEdat(int edat) { this.edat = edat; }
    public boolean isPremium() { return premium; }
    public void setPremium(boolean premium) { this.premium = premium; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }
    public String getLenguaje() { return lenguaje; }
    public void setLenguaje(String lenguaje) { this.lenguaje = lenguaje; }
    public String getDescripción() { return descripción; }
    public void setDescripción(String descripción) { this.descripción = descripción; }
    public ArrayList<Match> getListaMatch() { return listaMatch; }
    public void setListaMatch(ArrayList<Match> listaMatch) { this.listaMatch = listaMatch; }
    public ArrayList<User> getListaAcceptedUsers() { return listaAcceptedUsers; }
    public void setListaAcceptedUsers(ArrayList<User> listaAcceptedUsers) { this.listaAcceptedUsers = listaAcceptedUsers; }
}
