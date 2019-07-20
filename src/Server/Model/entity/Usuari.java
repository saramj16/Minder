package Server.Model.entity;

import User.Model.Match;
import User.Model.User;

import java.awt.*;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;

public class Usuari {

    private String userName;
    private int edat;
    private boolean premium;
    private String correo;
    private String password;
    private String urlFoto;
    private String lenguaje;
    private String description;

    public Usuari(String userName, int edat, boolean premium, String correo, String password) {
        this.userName = userName;
        this.edat = edat;
        this.premium = premium;
        this.correo = correo;
        this.password = password;
    }

    public Usuari(String userName, int edat, boolean premium, String correo, String password, String urlFoto, String lenguaje, String description) {
        this.userName = userName;
        this.edat = edat;
        this.premium = premium;
        this.correo = correo;
        this.password = password;
        this.urlFoto = urlFoto;
        this.lenguaje = lenguaje;
        this.description = description;
    }

    public Usuari() {

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

    public Boolean isPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
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

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
