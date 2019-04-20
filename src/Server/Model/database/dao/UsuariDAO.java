package Server.Model.database.dao;

import Server.Model.entity.Usuari;
import Server.Model.database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UsuariDAO {
    public void addUsuari(Usuari usuari) {
        String query = "INSERT INTO Usuari(userName, edat, premium, correo, contrasena) VALUES ('"+usuari.getUserName()+"', '"
                +usuari.getEdat()+"', '"+usuari.isPremium()+"', '"+usuari.getCorreo()+"', '"+usuari.getPassword()+"');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }

    public void deleteUsuari (String userName){
        String query = "DELETE FROM Usuari WHERE userName = '"+userName+"';";
        System.out.println(query);
        DBConnector.getInstance().deleteQuery(query);
    }

    public boolean searchUsuari(String userName) {

        String query = "SELECT userName FROM Usuari WHERE userName = '"+userName+"';";
        System.out.println(query);
        ResultSet resultat = DBConnector.getInstance().selectQuery(query);

        try{
            while(resultat.next()){
                String nom = resultat.getString("userName");

                if (nom.equals(userName)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void modificaUsuari(Usuari u) {
        String query = "UPDATE Usuari SET urlFoto = '" + u.getUrlFoto() + "', lenguaje = '" + u.getLenguaje() +"', descripcion = '" + u.getDescription() + "' WHERE userName = '"+ u.getUserName() + "';";
        System.out.println(query);
        DBConnector.getInstance().updateQuery(query);
    }
}
