package Server.Model.entity;

import Server.Model.database.dao.MatxDAO;
import Server.Model.database.dao.MissatgeDAO;
import Server.Model.database.dao.UsuariDAO;

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
        usuariDAO.addUsuari(u);
    }

    public void searchUsuari(String userName) {
        usuariDAO.searchUsuari(userName);
    }
}
