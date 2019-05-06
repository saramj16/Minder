package Server.Model.entity;

import Server.Model.database.dao.MatxDAO;
import Server.Model.database.dao.MissatgeDAO;
import Server.Model.database.dao.UsuariDAO;

import java.sql.SQLException;
import java.util.LinkedList;

public class UsuariManager {
    private MatxDAO matxDAO;
    private MissatgeDAO missatgeDAO;
    private UsuariDAO usuariDAO;

    public UsuariManager(){
        matxDAO = new MatxDAO();
        missatgeDAO = new MissatgeDAO();
        usuariDAO = new UsuariDAO();
    }

    public void addUsuari(Usuari u) {
        //Si l'usuari no exiteix l'afegim
        if (!searchUsuari(u.getUserName())){
            usuariDAO.addUsuari(u);
        }
    }

    public void modificiaUsuari(Usuari u){
        usuariDAO.modificaUsuari(u);
    }

    //Retorna true si l'usuari existeix
    public boolean searchUsuari(String userName) {
        return usuariDAO.searchUsuari(userName);
    }

    public void deleteUsuari(String nom) {
        usuariDAO.deleteUsuari(nom);
    }

    public boolean comprovaLogin(String username, String password) throws SQLException {
       return usuariDAO.comprovaUsuari(username,password);
    }

    public LinkedList<Usuari> getAllUsuari() {
        return usuariDAO.getAllUsuari();
    }

    }
