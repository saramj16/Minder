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

    /**
    * Mètode per afegir un usuari a la bbdd
    * comprova si no existeix i posteriorment l'afegiex
    *
    * @param  u  Usuari
    * @return void
    *
    */
    public void addUsuari(Usuari u) {
        //Si l'usuari no exiteix l'afegim
        if (!searchUsuari(u.getUserName())){
            usuariDAO.addUsuari(u);
        }
    }

    /**
    * Mètode per modificar l'usuari a la bbdd
    *
    * @param  u  Usuari
    * @return void
    *
    */
    public void modificiaUsuari(Usuari u){
        usuariDAO.modificaUsuari(u);
    }

    /**
    * Mètode per buscar l'usuari a la bbdd
    *
    * @param  userName  nom de l'Usuari
    * @return boolean true en cas que existeixi l'usuari, false en cas contrari
    *
    */
    public boolean searchUsuari(String userName) {
        return usuariDAO.searchUsuari(userName);
    }

    /**
    * Mètode per eliminar un usuari de la bbdd
    *
    * @param  nom  nom de l'Usuari a eliminar
    * @return void
    *
    */
    public void deleteUsuari(String nom) {
        usuariDAO.deleteUsuari(nom);
    }

    /**
    *
    * Comprova que les dades de l'usuari siguin les correctes
    *
    * @param  username  nom de l'usuari
    * @param  password contrasenya de l'usuari
    * @return boolean true si les dades son correctes, false en cas contrari
    *
    */
    public boolean comprovaLogin(String username, String password) throws SQLException {
       return usuariDAO.comprovaUsuari(username,password);
    }


    /**
    *
    * Retorna un ArrayList amb tots els Usuaris de la BBDD
    *
    * @return ArrayList<Usuari>  llista amb tots els Usuaris
    *
    */
    public ArrayList<Usuari> getAllUsuari() {
        return usuariDAO.getAllUsuari();
    }


    /**
    *
    * Retorna un ArrayList amb tots els Usuaris que ha acceptat l'usuari d'entrada
    *
    * @param  usuari  nom de l'usuari
    * @return ArrayList<Usuari>  llista amb tots els Usuaris acceptats
    *
    */
    public ArrayList<Usuari> getUsuarisAccepted(String usuari){
        return  usuariDAO.searchUsuaris(matxDAO.selectAcceptedUsers(usuari));
    }


    /**
    *
    * Retorna un ArrayList amb tots els Usuaris que ha fet matx amb l'usuari d'entrada
    *
    * @param  usuari  nom de l'usuari
    * @return ArrayList<Usuari>  llista amb tots els Usuaris amb matx
    *
    */
    public ArrayList<Usuari> getUsuarisMatxes(String usuari){
        return  usuariDAO.searchUsuaris(matxDAO.selectMatxedUsers(usuari));
    }

    /**
    *
    * Retorna un ArrayList amb tots els Matx de l'Usuari
    *
    * @param  usuari  nom de l'usuari
    * @return ArrayList<Matx>  llista Matx amb tots els matx de l'Usuari
    *
    */
    public ArrayList<Matx> getMatxedUsers(String usuari){
        return matxDAO.selectMatxes(usuari);

    }

    /**
     *
     * Retorna l'Usuari que ens demanen a l'entrada
     *
     * @param  userName  nom de l'usuari
     * @return Usuari classe Usuari amb l'usuari que ens demanen
     *
     */
    public Usuari getUsuari(String userName) {
        return usuariDAO.getUsuari(userName);
    }

    /**
     *
     * Afegiex si un usuari ha sigut acceptat i vist a la bbdd
     *
     * @param  user1  nom de l'usuari1
     * @param  user2  nom de l'usuari2
     * @return void
     *
     */
    public void addAccepted(String user1, String user2){
        matxDAO.addAcceptedUser(user1,user2);
        matxDAO.addVist(user1,user2);
    }

    /**
     *
     * Afegiex un matx entre dos usuaris
     * Comprova si hi ha matx entre els dos, i despres l'afegiex
     *
     * @param  user1  nom de l'usuari1
     * @param  user2  nom de l'usuari2
     * @return void
     *
     */
    public void addMatx (String user1, String user2){
        if(matxDAO.comprovaMatx(user1,user2)){
            //Aqui hay matx
            matxDAO.addMatx(user1,user2);
        }
    }

    /**
     *
     * Afegiex si un usuari ha sigut vist a la bbdd
     *
     * @param  user1  nom de l'usuari1
     * @param  user2  nom de l'usuari2
     * @return void
     *
     */
    public void addVist(String user1, String user2) {
        matxDAO.addVist(user1,user2);
    }


    /**
     *
     * Prepara un ArrayList de la classe Missatge amb tots els missatges entre ambdos
     * usuaris, ordenats per ordre d'enviat, de manera que es poden anar col·locant a la
     * vista directament
     *
     * @param  usuari1  nom de l'usuari1
     * @param  usuari2  nom de l'usuari2
     * @return void
     *
     */
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


}
