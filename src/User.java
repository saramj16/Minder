import java.awt.*;
import java.util.ArrayList;

public class User {
    private String userName;
    private int edat;
    private boolean premium;
    private String correo;
    private String contraseña;
    private Image urlFoto;
    private String lenguaje;
    private String descripción;
    private ArrayList<Match> listaMatch;
    private ArrayList<User> listaAcceptedUsers;

    public User(String userName, int edat, boolean premium, String correo, String contraseña,
                Image urlFoto, String lenguaje, String descripción,
                ArrayList<Match> listaMatch, ArrayList<User> listaAcceptedUsers) {
        this.userName = userName;
        this.edat = edat;
        this.premium = premium;
        this.correo = correo;
        this.contraseña = contraseña;
        this.urlFoto = urlFoto;
        this.lenguaje = lenguaje;
        this.descripción = descripción;
        this.listaMatch = listaMatch;
        this.listaAcceptedUsers = listaAcceptedUsers;
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
    public Image getUrlFoto() { return urlFoto; }
    public void setUrlFoto(Image urlFoto) { this.urlFoto = urlFoto; }
    public String getLenguaje() { return lenguaje; }
    public void setLenguaje(String lenguaje) { this.lenguaje = lenguaje; }
    public String getDescripción() { return descripción; }
    public void setDescripción(String descripción) { this.descripción = descripción; }
    public ArrayList<Match> getListaMatch() { return listaMatch; }
    public void setListaMatch(ArrayList<Match> listaMatch) { this.listaMatch = listaMatch; }
    public ArrayList<User> getListaAcceptedUsers() { return listaAcceptedUsers; }
    public void setListaAcceptedUsers(ArrayList<User> listaAcceptedUsers) { this.listaAcceptedUsers = listaAcceptedUsers; }
}
