package Server.Model.entity;

import Server.Model.database.dao.MatxDAO;
import Server.Model.database.dao.MissatgeDAO;
import Server.Model.database.dao.UsuariDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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

    public ArrayList<Usuari> getAllUsuari() {
        return usuariDAO.getAllUsuari();
    }

    public ArrayList<Usuari> getUsuarisAccepted(String usuari){
        return  usuariDAO.searchUsuaris(matxDAO.selectAcceptedUsers(usuari));
    }

    public ArrayList<Usuari> getUsuarisMatxes(String usuari){
        return  usuariDAO.searchUsuaris(matxDAO.selectMatxedUsers(usuari));
    }

    public ArrayList<Matx> getMatxedUsers(String usuari){
        return matxDAO.selectMatxes(usuari);

    }

    public Usuari getUsuari(String userName) {
        return usuariDAO.getUsuari(userName);
    }

    public void addAccepted(String user1, String user2){
        matxDAO.addAcceptedUser(user1,user2);
        matxDAO.addVist(user1,user2);
    }

    public void addMatx (String user1, String user2){
        if(matxDAO.comprovaMatx(user1,user2)){
            matxDAO.addMatx(user1,user2);
        }
    }

    public ArrayList<Missatge> preparaChat (String usuari1, String usuari2){
        ArrayList<Missatge> missatges = new ArrayList<>(), missatgesUsuari1, missatgesUsuari2;
        //Agafem els missatges dels dos usuaris
        missatgesUsuari1 = missatgeDAO.llistaMissatges(usuari1, usuari2);
        missatgesUsuari2 = missatgeDAO.llistaMissatges(usuari2, usuari2);

        //Ara els hem d'ordenar per poder-los mostrar al chat
        int i = 0;
        int j = 0;
        while (i >= missatgesUsuari1.size() && j >= missatgesUsuari2.size()){
            if(missatgesUsuari1.get(i).getDataMessage().before(missatgesUsuari2.get(j).getDataMessage())){
                missatges.add(missatgesUsuari1.get(i));
                i++;
            }else{
                missatges.add(missatgesUsuari2.get(j));
                j++;
            }
        }

        return missatges;
    }

    public void addVist(String user1, String user2) {
        matxDAO.addVist(user1,user2);
    }


    public int[] llistaMatchesDiaria(){
        Calendar now = Calendar.getInstance();
        int numMatches[] = new int[now.get(Calendar.HOUR_OF_DAY)];

        for (int i = 0; i < now.get(Calendar.HOUR_OF_DAY); i++) {
            numMatches[i] = matxDAO.getNumeroMatxesHora(i);
        }

        return numMatches;
    }

    public int[] llistaMatchesSetmanal(){
        Calendar now = Calendar.getInstance();
        int numMatches[] = new int[now.get(Calendar.DAY_OF_WEEK)];

        for (int i = 0; i < now.get(Calendar.DAY_OF_WEEK); i++) {
            numMatches[i] = matxDAO.getNumeroMatxesDiaSetmana(i);
        }

        return numMatches;
    }

    public int[] llistaMatchesMensual(){
        Calendar now = Calendar.getInstance();
        int numMatches[] = new int[now.get(Calendar.DAY_OF_MONTH)];

        for (int i = 0; i < now.get(Calendar.DAY_OF_MONTH); i++) {
            numMatches[i] = matxDAO.getNumeroMatxesDiaMes(i);
        }

        return numMatches;
    }
}
