package Server.Model.entity;

import User.Model.Match;
import User.Model.User;

import java.util.ArrayList;

public class Usuari {


    private String userName;
    private int edat;
    private String premium;
    private String correo;
    private String password;
    private String urlFoto;
    private String lenguaje;
    private String description;

    public Usuari(String userName, int edat, String premium, String correo, String password) {
        this.userName = userName;
        this.edat = edat;
        this.premium = premium;
        this.correo = correo;
        this.password = password;
    }

    public Usuari(String userName, int edat, String premium, String correo, String password, String urlFoto, String lenguaje, String description) {
        this.userName = userName;
        this.edat = edat;
        this.premium = premium;
        this.correo = correo;
        this.password = password;
        this.urlFoto = urlFoto;
        this.lenguaje = lenguaje;
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String isPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
