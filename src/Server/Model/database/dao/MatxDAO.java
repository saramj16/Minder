package Server.Model.database.dao;

import Server.Model.entity.Matx;
import Server.Model.database.DBConnector;
import Server.Model.entity.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MatxDAO {

    private DBConnector dbConnector;

    public MatxDAO(){
        dbConnector = DBConnector.getInstance();

        System.out.println(dbConnector);
    }

    public void addMatx(String user1, String user2) {
        String query = "SELECT user1, user2 FROM Matx WHERE user1 = '"+user1+"' AND user2 = '"+user2+"';";
        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom1 = resultat.getString("user1");
                String nom2 = resultat.getString("user2");

                if(nom1.equals(user1) && nom2.equals(user2)) {
                    String query1 = "UPDATE Matx SET matx = '" + true + "' WHERE user1 = '" + user1 + "', user 2 = '" + user2 + "';";
                    dbConnector.updateQuery(query1);
                }
            }
        } catch (SQLException e) {
            String query2 = "INSERT INTO Matx(user1, user2, vist, accept, matx) VALUES ('" + user1 + "', '" + user2 + "' , '" + true + "' , '" + true + "' , '" + true + "');";
            dbConnector.insertQuery(query2);
            e.printStackTrace();
        }
    }

    public void addVist(String user1, String user2) {
        String query = "SELECT user1, user2 FROM Matx WHERE user1 = '"+user1+"' AND user2 = '"+user2+"';";
        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom1 = resultat.getString("user1");
                String nom2 = resultat.getString("user2");

                if(nom1.equals(user1) && nom2.equals(user2)) {
                    String query1 = "UPDATE Matx SET vist = '" + true + "' WHERE user1 = '" + user1 + "', user 2 = '" + user2 + "';";
                    dbConnector.updateQuery(query1);
                }
            }
        } catch (SQLException e) {
            String query2 = "INSERT INTO Matx(user1, user2, vist) VALUES ('" + user1 + "', '" + user2 + "' , '" + true + "');";
            dbConnector.insertQuery(query2);
            e.printStackTrace();
        }

    }

    public void addAcceptedUser(String user1, String user2) {
        String query = "SELECT user1, user2 FROM Matx WHERE user1 = '"+user1+"' AND user2 = '"+user2+"';";
        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom1 = resultat.getString("user1");
                String nom2 = resultat.getString("user2");

                if(nom1.equals(user1) && nom2.equals(user2)) {
                    String query1 = "UPDATE Matx SET accept = '" + true + "' WHERE user1 = '" + user1 + "', user 2 = '" + user2 + "';";
                    dbConnector.updateQuery(query1);
                }
            }
        } catch (SQLException e) {
            String query2 = "INSERT INTO Matx(user1, user2, vist, accept) VALUES ('" + user1 + "', '" + user2 + "' , '" + true + "' , '" + true + "');";
            dbConnector.insertQuery(query2);
            e.printStackTrace();
        }
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
