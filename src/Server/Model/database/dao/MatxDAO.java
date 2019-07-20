package Server.Model.database.dao;


import Server.Model.database.DBConnector;
import Server.Model.entity.Matx;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe que gestiona els Matx a la bbdd i s'engarrega de poder-los utilitzar al programa
 */
public class MatxDAO {

    private DBConnector dbConnector;

    public MatxDAO(){
        dbConnector = DBConnector.getInstance();

        //System.out.println(dbConnector);
    }

    /**
     * Mètode per afegir els match que s'ha produit dins la BBDD de SQL
     * S'afegeix per els dos Usuaris que estan relacionats
     *
     * @param  user1    nom de l'Usuari 1
     * @param  user2    nom de l'Usuari 2
     * @return void
     *
     */
    public void addMatx(String user1, String user2) {
        boolean existeix = false;
        String query = "SELECT user1, user2 FROM Matx WHERE user1 = '"+ user1 +"' AND user2 = '"+ user2 +"';";
        ResultSet resultat = dbConnector.selectQuery(query);

        //Agafem la data en la que s'ha fet el Matx per tal de guardar-la al MySQL
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try{
            while(resultat.next()){
                String nom1 = resultat.getString("user1");
                String nom2 = resultat.getString("user2");
                System.out.println("Nom 1: " + nom1 + " // Nom 2: " + nom2);

                if(nom1.equals(user1) && nom2.equals(user2)) {
                    //Hem d'afegir el matx en l'Usuari1 i l'Usuari2 per separat
                    String query1 = "UPDATE Matx SET matx = " + true + ", dataMatch = '" + dtf.format(now) + "' WHERE user1 = '" +
                            nom1 + "' AND user2 = '" + nom2 + "';";
                    dbConnector.updateQuery(query1);
                    String query2 = "UPDATE Matx SET matx = " + true + ", dataMatch = '" + dtf.format(now) + "' WHERE user1 = '" +
                            nom2 + "' AND user2 = '" + nom1 + "';";
                    dbConnector.updateQuery(query2);
                    existeix = true;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        if(!existeix){
            String query2 = "INSERT INTO Matx(user1, user2, vist, accept, matx) VALUES ('" + user1 + "', '" + user2 + "' , "
                    + true + " , " + true + " , " + true + ");";
            dbConnector.insertQuery(query2);
            String query3 = "INSERT INTO Matx(user1, user2, vist, accept, matx) VALUES ('" + user2 + "', '" + user1 + "' , "
                    + true + " , " + true + " , " + true + ");";
            dbConnector.insertQuery(query3);
        }
    }

    /**
     * Mètode per afegir si un usuari ha vist a un altre dins la BBDD de SQL
     *
     * @param  user1    nom de l'Usuari 1
     * @param  user2    nom de l'Usuari 2
     * @return void
     *
     */
    public void addVist(String user1, String user2) {
        boolean existeix = false;
        String query = "SELECT user1, user2 FROM Matx WHERE user1 = '"+user1+"' AND user2 = '"+user2+"';";
        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom1 = resultat.getString("user1");
                String nom2 = resultat.getString("user2");

                if(nom1.equals(user1) && nom2.equals(user2)) {
                    String query1 = "UPDATE Matx SET vist = " + true + " WHERE user1 = '" + user1 + "' AND user2 = '" + user2 + "';";
                    dbConnector.updateQuery(query1);
                    existeix = true;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        if(!existeix){
            String query2 = "INSERT INTO Matx(user1, user2, vist) VALUES ('" + user1 + "', '" + user2 + "' , " + true + ");";
            dbConnector.insertQuery(query2);
        }

    }

    /**
     * Mètode per afegir si un usuari ha acceptat a un altre
     * dins la BBDD de SQL
     *
     * @param  user1    nom de l'Usuari 1
     * @param  user2    nom de l'Usuari 2
     * @return void
     *
     */
    public void addAcceptedUser(String user1, String user2) {
        boolean existeix = false;
        String query = "SELECT user1, user2 FROM Matx WHERE user1 = '"+user1+"' AND user2 = '"+user2+"';";
        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom1 = resultat.getString("user1");
                String nom2 = resultat.getString("user2");

                if(nom1.equals(user1) && nom2.equals(user2)) {
                    String query1 = "UPDATE Matx SET accept = " + true + " WHERE user1 = '" + user1 + "' AND user2 = '" + user2 + "';";
                    dbConnector.updateQuery(query1);
                    existeix = true;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        if(!existeix){
            String query2 = "INSERT INTO Matx(user1, user2, vist, accept) VALUES ('" + user1 + "', '" + user2 + "' , " + true + " , " + true + ");";
            //System.out.println(query2);
            dbConnector.insertQuery(query2);
        }
    }

    /**
     * Mètode per comprovar si hi ha match entre dos usuaris
     * de la BBDD de SQL
     *
     * @param  user1    nom de l'Usuari 1
     * @param  user2    nom de l'Usuari 2
     * @return boolean  true en cas que hi hagi match, false en cas contrari
     *
     */
    public boolean comprovaMatx(String user1, String user2){

        boolean accept = false, accept1 = false;

        System.out.println("Entra al comprova Match");

        String query = "SELECT accept FROM Matx WHERE user1 = '" + user1 + "' AND user2 = '" + user2 + "';";

        ResultSet resultat = dbConnector.selectQuery(query);
        try{
            while(resultat.next()){
                accept = resultat.getBoolean("accept");
                System.out.println("El primer usuari:" + accept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query1 = "SELECT accept FROM Matx WHERE user1 = '" + user2 + "' AND user2 = '" + user1 + "';";

        ResultSet resultat1 = dbConnector.selectQuery(query1);
        try{
            while(resultat1.next()){
                accept1 = resultat1.getBoolean("accept");
                System.out.println("El segon usuari:" + accept1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (accept && accept1){
            System.out.println("Final feliz, els dos Usuaris han acceptat");
            return true;
        }
        return false;
    }

    /**
     * Mètode per crear una llista amb els noms d'Usuaris
     * que ha acceptat l'usuari que entra com a parametre
     * user1 agafant la info de la BBDD de SQL
     *
     * @param  user1    nom de l'Usuari 1
     * @return ArrayList</String> llista dels noms dels usuaris que han sigut acceptats
     *
     */
    public ArrayList<String> selectAcceptedUsers (String user1){
        ArrayList<String> acceptedUsers = new ArrayList<>();

        String query = "SELECT user2 FROM Matx WHERE user1 = '"+ user1 +"' AND accept = 1;";
        //System.out.println(query);

        ResultSet resultat = dbConnector.selectQuery(query);
        //System.out.println(resultat);

        try{
            while(resultat.next()){
                String nom = resultat.getString("user2");
                acceptedUsers.add(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return acceptedUsers;
    }

    /**
     * Mètode per crear una llista amb els noms d'Usuaris
     * amb els que hi ha hagut match amb l'usuari que entra com a parametre
     * user1 agafant la info de la BBDD de SQL
     *
     * @param  user1    nom de l'Usuari 1
     * @return ArrayList</String> llista dels noms dels usuaris amb els que hi ha match
     *
     */
    public ArrayList<String> selectMatxedUsers(String user1) {
        ArrayList<String> matxedUsers = new ArrayList<>();

        String query = "SELECT user2 FROM Matx WHERE user1 = '"+ user1 +"' AND matx = " + true + ";";

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom = resultat.getString("user2");
                matxedUsers.add(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matxedUsers;
    }


    /**
     * Mètode per crear una llista amb tots els Matxes que ha tingut l'Usuari
     *
     * @param  usuari    nom de l'Usuari 1
     * @return ArrayList<Matx> llista dels Matxes de l'Usuari
     *
     */
    public ArrayList<Matx> selectMatxes(String usuari) {
        ArrayList<Matx> matxedUsers = new ArrayList<>();

        String query = "SELECT * FROM Matx WHERE user1 = '"+ usuari +"' AND matx = " + true + ";";

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                Matx m = new Matx(resultat.getString("user1"),
                        resultat.getString("user2"),
                        resultat.getBoolean("matx"),
                        resultat.getBoolean("accept"),
                        resultat.getBoolean("vist"),
                        resultat.getDate("dataMatch"));

                matxedUsers.add(m);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matxedUsers;
    }

    /**
     * Mètode per eliminar el match entre dos usuaris dins la BBDD de SQL
     *
     * @param  user1    nom de l'Usuari 1
     * @param  user2    nom de l'Usuari 2
     * @return void
     *
     */
    public void deleteMatx(String user1, String user2) {
        String query = "UPDATE Matx SET matx = " + false + " AND accept = " + false + " WHERE user1 = '"+user1+"' AND user2 = '" + user2 + "';";
        System.out.println(query);
        dbConnector.updateQuery(query);
    }


    /**
     * Mètode per eliminar el match entre dos usuaris dins la BBDD de SQL
     *
     * @param  hora    hora a la qual has de buscar
     * @return void
     *
     */
    public int getNumeroMatxesHora(int hora) {
        int num = 0;
        Calendar now = Calendar.getInstance();
        int mes = now.get(Calendar.MONTH);
        mes++;
        String data = now.get(Calendar.YEAR) + "-" + mes + "-" + now.get(Calendar.DAY_OF_MONTH) + " ";

        String h = data + hora + ":00:00" ;
        String h1 = data + (hora + 1) + ":00:00";

        String query = "SELECT COUNT(matx)/2 AS numero FROM Matx WHERE dataMatch BETWEEN '" + h +"' AND '" + h1 + "';";

        dbConnector.selectQuery(query);

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                num = resultat.getInt("numero");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    public int getNumeroMatxesDiaSetmana(int i) {
        int num = 0;
        Calendar now = Calendar.getInstance();
        int mes = now.get(Calendar.MONTH);
        mes++;
        int dia = now.get(Calendar.DAY_OF_MONTH) + i - now.get(Calendar.DAY_OF_WEEK) +1;
        String data = now.get(Calendar.YEAR) + "-" + mes + "-" + dia + " 00:00:00";
        String data1 = now.get(Calendar.YEAR) + "-" + mes + "-" + dia + " 23:59:59";

        String query = "SELECT COUNT(matx)/2 AS numero FROM Matx WHERE dataMatch BETWEEN '" + data +"' AND '" + data1 + "';";

        dbConnector.selectQuery(query);

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                num = resultat.getInt("numero");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    public int getNumeroMatxesDiaMes(int i) {
        int num = 0;
        Calendar now = Calendar.getInstance();
        int mes = now.get(Calendar.MONTH);
        mes++;
        int dia = i+1;
        String data = now.get(Calendar.YEAR) + "-" + mes + "-" + dia + " 00:00:00";
        String data1 = now.get(Calendar.YEAR) + "-" + mes + "-" + dia + " 23:59:59";

        String query = "SELECT COUNT(matx)/2 AS numero FROM Matx WHERE dataMatch BETWEEN '" + data +"' AND '" + data1 + "';";

        dbConnector.selectQuery(query);

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                num = resultat.getInt("numero");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * Retorna un Top amb els 5 usuaris més acceptats en format ArrayList de Strings
     * @return
     */
    public ArrayList<String> top5UsuarisMesAcceptats (){
        ArrayList<String> usuaris = new ArrayList<>(5);
        String nom;

        String query = "SELECT user2 FROM Matx WHERE accept = 1 GROUP BY user2 ORDER BY COUNT(accept) DESC LIMIT 5;";

        dbConnector.selectQuery(query);

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                nom = resultat.getString("user2");
                usuaris.add(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
    }

    /**
     * Retorna les puntuacions dels 5 usuaris més acceptats en format d'ArrayList de Integers
     * @return
     */
    public ArrayList<Integer> top5NAcceptacions(){
        ArrayList<Integer> numAcceptacions = new ArrayList<>(5);
        int num;

        String query = "SELECT COUNT(accept) AS numero FROM Matx WHERE accept = 1 GROUP BY user2 ORDER BY COUNT(accept) DESC LIMIT 5;";

        dbConnector.selectQuery(query);

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                num = resultat.getInt("numero");
                numAcceptacions.add(num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numAcceptacions;
    }




}
