package Server.Model.entity;

import Server.Model.database.dao.MatxDAO;
import Server.Model.database.dao.MissatgeDAO;
import Server.Model.database.dao.UsuariDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Classe de gestió dels usuaris
 */
public class UsuariManager {
    private MatxDAO matxDAO;
    private MissatgeDAO missatgeDAO;
    private UsuariDAO usuariDAO;

    /**
     * Constructor
     */
    public UsuariManager(){
        matxDAO = new MatxDAO();
        missatgeDAO = new MissatgeDAO();
        usuariDAO = new UsuariDAO();
    }

    /**
     * Afegeix un usuari la bbdd
     * @param u
     */
    public void addUsuari(Usuari u) {
        //Si l'usuari no exiteix l'afegim
        if (!searchUsuari(u.getUserName())){
            usuariDAO.addUsuari(u);
        }
    }

    /**
     * Esborra un match entre dos usuaris concrets de la bbdd
     * @param u1
     * @param u2
     */
    public void deleteMatch(String u1, String u2) {
        matxDAO.deleteMatx(u1, u2);
    }

    /**
     * Modifica els atributs de cert User
     * @param u
     */
    public void modificiaUsuari(Usuari u){

        usuariDAO.modificaUsuari(u);
    }

    /**
     *     Retorna true si l'usuari existeix
     */
    public boolean searchUsuari(String userName) {
        return usuariDAO.searchUsuari(userName);
    }

    /**
     * Esborra un usuari
     * @param nom
     */
    public void deleteUsuari(String nom) {
        usuariDAO.deleteUsuari(nom);
    }


    /**
     * comproba si un Login ha estat realitzat correctament i retorna true si és el cas.
     * Per a fer-ho, té en compte que el username pot ser un username o un correu.
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean comprovaLogin(String username, String password) throws SQLException {
        boolean isLoginOk =((usuariDAO.comprovaUsuari(username,password)) ||  (usuariDAO.comprovaUsuariCorreu(username,password)));
        System.out.println("isLoginOk: "+isLoginOk);
        return isLoginOk;
    }

    /**
     * Retorna tots els usuaris a la bbdd en forma d'ArrayList
     * @return
     */
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

    /**
     * Troba el usuari a la bbdd que té el nom introduït i el retorna
     * @param userName
     * @return
     */
    public Usuari getUsuari(String userName) {
        Usuari u = usuariDAO.getUsuariByName(userName);
        if ( u == null) {
            u = usuariDAO.getUsuariByMail(userName);
        }
        return u;
    }

    /**
     * Afegeix un usuari com a acceptat per un altre
     * @param user1
     * @param user2
     */
    public void addAccepted(String user1, String user2){
        matxDAO.addAcceptedUser(user1,user2);
        matxDAO.addVist(user1,user2);
    }

    public synchronized boolean addMatx (String user1, String user2){
        if(matxDAO.comprovaMatx(user1,user2)){
            matxDAO.addMatx(user1,user2);
            return true;
        }
        return false;
    }

    public synchronized ArrayList<Missatge> preparaChat (String usuari1, String usuari2){
        ArrayList<Missatge> missatges = new ArrayList<>(), missatgesUsuari1, missatgesUsuari2;
        //Agafem els missatges dels dos usuaris
        missatgesUsuari1 = missatgeDAO.llistaMissatges(usuari1, usuari2);
        missatgesUsuari2 = missatgeDAO.llistaMissatges(usuari2, usuari1);

        //Ara els hem d'ordenar per poder-los mostrar al chat
        int i = 0;
        int j = 0;
        if (missatgesUsuari1.size() != 0 && missatgesUsuari2.size() != 0){
            while (i < missatgesUsuari1.size() || j < missatgesUsuari2.size()){
                if (i == missatgesUsuari1.size()){
                    while (j < missatgesUsuari2.size()){
                        missatges.add(missatgesUsuari2.get(j));
                        j++;
                    }
                }else {
                    if (j == missatgesUsuari2.size()){
                        while (i < missatgesUsuari1.size()){
                            missatges.add(missatgesUsuari1.get(i));
                            i++;
                        }
                    }else {
                        if(missatgesUsuari1.get(i).getDataMessage().before(missatgesUsuari2.get(j).getDataMessage())){
                            missatges.add(missatgesUsuari1.get(i));
                            i++;
                        }else{
                            missatges.add(missatgesUsuari2.get(j));
                            j++;
                        }
                    }
                }
            }
        } else {
            if(missatgesUsuari1.size() != 0){
                while (i < missatgesUsuari1.size()){
                    missatges.add(missatgesUsuari1.get(i));
                    i++;
                }
            } else {
                if(missatgesUsuari2.size() != 0){
                    while (j < missatgesUsuari2.size()){
                        missatges.add(missatgesUsuari2.get(j));
                        j++;
                    }
                }
            }
        }


        return missatges;
    }

    public synchronized void addVist(String user1, String user2) {

        matxDAO.addVist(user1,user2);
    }


    public synchronized int[] llistaMatchesDiaria(){
        Calendar now = Calendar.getInstance();
        int numMatches[] = new int[now.get(Calendar.HOUR_OF_DAY)+1];

        for (int i = 0; i < now.get(Calendar.HOUR_OF_DAY)+1; i++) {
            numMatches[i] = matxDAO.getNumeroMatxesHora(i);
        }

        return numMatches;
    }

    public synchronized int[] llistaMatchesSetmanal(){
        Calendar now = Calendar.getInstance();
        int numMatches[] = new int[now.get(Calendar.DAY_OF_WEEK)];

        for (int i = 0; i < now.get(Calendar.DAY_OF_WEEK); i++) {
            numMatches[i] = matxDAO.getNumeroMatxesDiaSetmana(i);
        }

        return numMatches;
    }

    public synchronized int[] llistaMatchesMensual(){
        Calendar now = Calendar.getInstance();
        int numMatches[] = new int[now.get(Calendar.DAY_OF_MONTH)];

        for (int i = 0; i < now.get(Calendar.DAY_OF_MONTH); i++) {
            numMatches[i] = matxDAO.getNumeroMatxesDiaMes(i);
        }

        return numMatches;
    }


    public synchronized void afegeixMissatge(String userSend, String userRecieve, String missatge){
        missatgeDAO.addMissatge(new Missatge(missatge, userSend, userRecieve, null));
    }

    /**
     * Retorna un arrayList de Strings amb els 5 usuaris més acceptats per tal de fer un TOP5 a la vista del server
     * @return
     */
    public ArrayList<String> getTop5UsuarisAcceptats(){
        return matxDAO.top5UsuarisMesAcceptats();
    }

    /**
     * Retorna un arrayList de Integers amb les puntuacions (nombre de cops que han estat acceptats) dels 5 usuaris
     * més acceptats per tal de posar punts al TOP5 de la vista del server
     * @return
     */
    public ArrayList<Integer> getTop5NumAcceptacions(){
        return matxDAO.top5NAcceptacions();
    }

}
