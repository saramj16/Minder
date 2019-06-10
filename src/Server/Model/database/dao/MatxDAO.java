package Server.Model.database.dao;

import Server.Model.entity.Matx;
import Server.Model.database.DBConnector;
import Server.Model.entity.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        //Agafem la data en la que s'ha fet el Matx per tal de guardar-la al MySQL
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try{
            while(resultat.next()){
                String nom1 = resultat.getString("user1");
                String nom2 = resultat.getString("user2");

                if(nom1.equals(user1) && nom2.equals(user2)) {
                    //Hem d'afegir el matx en l'Usuari1 i l'Usuari2 per separat
                    String query1 = "UPDATE Matx SET matx = '" + true + "' dataMatch = '" + dtf.format(now) + "' WHERE user1 = '" + user1 + "', user 2 = '" + user2 + "';";
                    dbConnector.updateQuery(query1);
                    String query2 = "UPDATE Matx SET matx = '" + true + "' dataMatch = '" + dtf.format(now) + "' WHERE user1 = '" + user2 + "', user 2 = '" + user1 + "';";
                    dbConnector.updateQuery(query2);
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

    public boolean comprovaMatx(String user1, String user2){

        boolean accept = false, accept1 = false;

        String query = "SELECT accept FROM Matx WHERE user1 = '"+user1+"';";

        String query1 = "SELECT accept FROM Matx WHERE user1 = '"+user2+"';";

        ResultSet resultat = dbConnector.selectQuery(query);
        try{
            while(resultat.next()){
                accept = resultat.getBoolean("accept");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet resultat1 = dbConnector.selectQuery(query1);
        try{
            while(resultat1.next()){
                accept1 = resultat1.getBoolean("accept");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (accept && accept1){
            return true;
        }
        return false;
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

    public ArrayList<String> selectMatxedUsers(String user1) {
        ArrayList<String> matxedUsers = new ArrayList<>();

        String query = "SELECT user2 FROM Matx WHERE user1 = '"+ user1 +"' AND matx = true;";

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


    public void deleteMatx(String user1, String user2) {
        String query = "UPDATE Matx SET matx = '" + false + "' AND accept = '" + false + "' WHERE user1 = '"+user1+"' AND user2 = '" + user2 + "';";
        System.out.println(query);
        dbConnector.updateQuery(query);
    }
}
