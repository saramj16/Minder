package User.Model;

import java.awt.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;

/**
 * Classe User. Conté tota la informació dels usuaris del programa
 */
public class User implements Serializable {

    private String userName;
    private int edat;
    private boolean premium;
    private String correo;
    private String password;
    private String urlFoto;
    private String lenguaje;
    private String description;
    private ArrayList<Match> listaMatch;
    private ArrayList<User> listaLikedUsers;


    /**
     * Constructor de User
     * @param userName
     * @param edat
     * @param premium
     * @param correo
     * @param password
     * @param urlFoto
     * @param lenguaje
     * @param description
     */
    public User(String userName, int edat, boolean premium, String correo, String password,
                String urlFoto, String lenguaje, String description){

        this.userName = userName;
        this.edat = edat;
        this.premium = premium;
        this.correo = correo;
        this.password = password;
        this.urlFoto = urlFoto;
        this.lenguaje = lenguaje;
        this.description = description;
        this.listaMatch = new ArrayList<>();
        this.listaLikedUsers = new ArrayList<>();
    }



    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public int getEdat() { return edat; }
    public void setEdat(int edat) { this.edat = edat; }
    public boolean isPremium() { return premium; }
    public void setPremium(boolean premium) { this.premium = premium; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }
    public String getLenguaje() { return lenguaje; }
    public void setLenguaje(String lenguaje) { this.lenguaje = lenguaje; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ArrayList<User> getListaLikedUsers() { return listaLikedUsers; }
    public void setListaLikedUsers(ArrayList<User> listaLikedUsers) { this.listaLikedUsers = listaLikedUsers; }
    public ArrayList<Match> getListaMatch() { return listaMatch; }
    public void setListaMatch(ArrayList<Match> listaMatch) { this.listaMatch = listaMatch; }
}
