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

    public void addMissatge(Missatge missatge) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String query = "INSERT INTO Missatge(missatge, userSend, dateMessage ) VALUES ('"
                +missatge.getMissatge()+"', '"+missatge.getUserSend() + "', '" + dtf.format(now) + "');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }

    public void deleteMissatge(int id) {
        String query = "DELETE FROM Missatge WHERE id = '"+id+"';";
        System.out.println(query);
        DBConnector.getInstance().deleteQuery(query);
    }

    public ArrayList<Missatge> llistaMissatges (String userSend, String userRecive){

        String query = "SELECT * FROM Missatge WHERE userSend = '"+ userSend +"' AND userRecive = '" + userRecive + "';";

        ResultSet resultat = dbConnector.selectQuery(query);

        ArrayList<Missatge> messageList = new ArrayList<>();
        try{
            while (resultat.next()) {
                Missatge missatges = new Missatge(
                        resultat.getString("missatge"),
                        resultat.getString("userSend"),
                        resultat.getString("userRecive"),
                        resultat.getDate("dataMessage")
                         );
                messageList.add(missatges);
                System.out.println(missatges.getMissatge());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageList;
    }

}
