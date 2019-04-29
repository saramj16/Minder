package Server.Model.database.dao;

import Server.Model.entity.Usuari;
import Server.Model.database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UsuariDAO {

    private DBConnector dbConnector;

    public UsuariDAO(){
        dbConnector = DBConnector.getInstance();
    }


    public void addUsuari(Usuari usuari) {
        String query = "INSERT INTO Usuari(userName, edat, premium, correo, contrasena) VALUES ('"+usuari.getUserName()+"', '"
                +usuari.getEdat()+"', "+usuari.isPremium()+", '"+usuari.getCorreo()+"', '"+usuari.getPassword()+"');";
        System.out.println(query);
        dbConnector.insertQuery(query);
    }

    public void deleteUsuari (String userName){
        String query = "DELETE FROM Usuari WHERE userName = '"+userName+"';";
        System.out.println(query);
        dbConnector.deleteQuery(query);
    }

    public boolean searchUsuari(String userName) {

        String query = "SELECT userName FROM Usuari WHERE userName = '"+userName+"';";
        System.out.println(query);
        ResultSet resultat = dbConnector.selectQuery(query);

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
        dbConnector.updateQuery(query);
    }

    public boolean comprovaUsuari(String username, String password) throws SQLException {
        String query = "SELECT * FROM Usuari WHERE userName = '"+username+"' AND contrasena = '" + password + "';";
        System.out.println(query);
        ResultSet resultat = dbConnector.selectQuery(query);

        String nom = resultat.getString("userName");
        String contrasenya = resultat.getString("contrasena");

        if (username.equals(nom) && password.equals(contrasenya)){
            return true;
        }

        return false;
    }
}
