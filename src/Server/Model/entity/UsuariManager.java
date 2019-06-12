package Server.Model.entity;

import Server.Model.database.dao.MatxDAO;
import Server.Model.database.dao.MissatgeDAO;
import Server.Model.database.dao.UsuariDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
        System.out.println("Aqui tm entre tet");
        if(matxDAO.comprovaMatx(user1,user2)){
            //Aqui hay matx
            matxDAO.addMatx(user1,user2);
        }
    }


    //Ordena els missatges i els envia en un array de missatges perq els puguis printar al chat
    //Esta testejat i els ordena perfect
    public ArrayList<Missatge> preparaChat (String usuari1, String usuari2){

        ArrayList<Missatge> missatges = new ArrayList<>(), missatgesUsuari1, missatgesUsuari2;
        Date t1 = null, t2 = null;
        int num1=0, num2=0;
        //Agafem els missatges dels dos usuaris
        missatgesUsuari1 = missatgeDAO.llistaMissatges(usuari1, usuari2);
        missatgesUsuari2 = missatgeDAO.llistaMissatges(usuari2, usuari1);
        int numMissatgesTotals = missatgesUsuari1.size()+missatgesUsuari2.size();
        System.out.println("NumMissatgesTotals = " + numMissatgesTotals);
        System.out.println("NumMissatges Usuari1 = " + missatgesUsuari1.size());
        System.out.println("NumMissatges Usuari2 = " + missatgesUsuari2.size());


        for (int i = 0; i < (numMissatgesTotals); i++){
            if (missatgesUsuari1.size() > 1 && missatgesUsuari2.size() > 1){
                t1 = missatgesUsuari1.get(0).getDataMessage();
                t2 = missatgesUsuari2.get(0).getDataMessage();
                num1 = 0;
                num2 = 0;
                for (int j = 0; j < missatgesUsuari1.size(); j++){
                    if (missatgesUsuari1.get(j).getDataMessage().before(t1)){
                        t1 = missatgesUsuari1.get(j).getDataMessage();
                        num1 = j;
                    }
                }
                for (int k = 0; k < missatgesUsuari2.size(); k++){
                    if (missatgesUsuari2.get(k).getDataMessage().before(t2)){
                        t2 = missatgesUsuari2.get(k).getDataMessage();
                        num2 = k;
                    }
                }
            } else {
                if(missatgesUsuari2.size() == 1) {
                    t2 = missatgesUsuari2.get(0).getDataMessage();
                    num2 = 0;
                } else{
                    if(missatgesUsuari1.size() == 1){
                        t1 = missatgesUsuari1.get(0).getDataMessage();
                        num1 = 0;
                    }
                }
            }

            if (missatgesUsuari2.size() != 0 && missatgesUsuari1.size() != 0){
                if (t1.before(t2)){
                    //System.out.println("Num1: " + num1);
                    // System.out.println(missatgesUsuari1.get(num1).getMissatge());
                    missatges.add(missatgesUsuari1.get(num1));
                    missatgesUsuari1.remove(num1);

                } else{
                    missatges.add(missatgesUsuari2.get(num2));
                    missatgesUsuari2.remove(num2);

                }
            } else {
                if(missatgesUsuari2.size() == 0 && missatgesUsuari1.size() == 0){
                    break;
                } else {
                    if(missatgesUsuari2.size() == 0){
                        missatges.add(missatgesUsuari1.get(num1));
                        missatgesUsuari1.remove(num1);
                    } else {
                        missatges.add(missatgesUsuari2.get(num2));
                        missatgesUsuari2.remove(num2);

                    }
                }
            }


        }

        return missatges;
    }

    public void addVist(String user1, String user2) {
        matxDAO.addVist(user1,user2);
    }
}
