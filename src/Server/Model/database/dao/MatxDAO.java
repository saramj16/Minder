package Server.Model.database.dao;

import Server.Model.entity.Matx;
import Server.Model.database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MatxDAO {

    private DBConnector dbConnector;

    public MatxDAO(){
        dbConnector = DBConnector.getInstance();

        System.out.println(dbConnector);
    }

    public void addMatx(Matx matx) {
        String query = "INSERT INTO Matx(user1, user2, matx, vist) VALUES ('"+matx.getUser1()+"', '"
                + matx.getUser2()+"', '"+matx.isMatx() + "' , '" + matx.isVist() + "';";
        System.out.println(query);

        dbConnector.insertQuery(query);
    }

    public void addVist(Matx matx) {
        String query = "INSERT INTO Matx(user1, user2, vist) VALUES ('"+matx.getUser1()+"', '"
                + matx.getUser2()+ "' , '" + matx.isVist() + "';";
        System.out.println(query);

        dbConnector.insertQuery(query);
    }

    public ArrayList<String> selectAcceptedUsers (String user1){
        ArrayList<String> acceptedUsers = new ArrayList<>();

        String query = "SELECT user2 FROM Matx WHERE user1 = '"+user1+"' AND accept = true;";
        System.out.println(query);

        ResultSet resultat = dbConnector.selectQuery(query);
        System.out.println(resultat);

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

    public void deleteMatx(String usuari) {
        String query = "DELETE FROM Matx WHERE user1 = '"+usuari+"';";
        System.out.println(query);
        dbConnector.deleteQuery(query);
    }

    public ArrayList<String> selectMatxedUsers(String user1) {
        ArrayList<String> matxedUsers = new ArrayList<>();

        String query = "SELECT user2 FROM Matx WHERE user1 = '"+user1+"' AND matx = true;";

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
}
