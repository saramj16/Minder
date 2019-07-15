package Server.Model.database.dao;

import Server.Model.entity.Missatge;
import Server.Model.database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MissatgeDAO {

    private DBConnector dbConnector;

    public MissatgeDAO(){
        dbConnector = DBConnector.getInstance();

        System.out.println(dbConnector);
    }

    /**
     *
     * Mètode per afegir un Missatge a la BBDD de SQL
     *
     * @param  missatge   Classe Missatge
     * @return void
     *
     */
    /*public void addMissatge(Missatge missatge) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String query = "INSERT INTO Missatge(missatge, userSend, dateMessage ) VALUES ('"
                +missatge.getMissatge()+"', '"+missatge.getUserSend() + "', '" + dtf.format(now) + "');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }*/
    public void addMissatge(Missatge missatge) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String query = "INSERT INTO Missatge(missatge, userSend, userReceive, dateMessage ) VALUES ('"
                +missatge.getMissatge()+"', '"+missatge.getUserSend() + "', '" + missatge.getUserReceive() + "', '" + dtf.format(now) + "');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }


    /**
     *
     * Mètode per retornar una llista de missatges que hi ha hagut entre dos Usuaris
     *
     * @param  userSend   Nom de l'Usuari que ha enviat els missatges
     * @param  userRecive Nom de l'Usuari que ha rebut els missatges
     * @return ArrayList<Missatge> llista amb tots els missatges que ha enviat userSend a userRecive
     *
     */
    public ArrayList<Missatge> llistaMissatges (String userSend, String userRecive){

        String query = "SELECT * FROM Missatge WHERE userSend = '"+ userSend +"' AND userReceive = '" + userRecive + "';";

        ResultSet resultat = dbConnector.selectQuery(query);

        ArrayList<Missatge> messageList = new ArrayList<>();
        try{
            while (resultat.next()) {
                Missatge missatges = new Missatge(
                        resultat.getString("missatge"),
                        resultat.getString("userSend"),
                        resultat.getString("userReceive"),
                        resultat.getTimestamp("dataMessage")
                         );
                messageList.add(missatges);
                //System.out.println(missatges.getDataMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageList;
    }

}
