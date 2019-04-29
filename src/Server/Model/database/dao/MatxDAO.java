package Server.Model.database.dao;

import Server.Model.entity.Matx;
import Server.Model.database.DBConnector;


public class MatxDAO {
    public void addMatx(Matx matx) {
        String query = "INSERT INTO Matx(user1, user2, matx, vist) VALUES ('"+matx.getUser1()+"', '"
                + matx.getUser2()+"', '"+matx.isMatx() + "' , '" + matx.isVist() + "';";
        System.out.println(query);
        DBConnector dbConnector =  DBConnector.getInstance();
        dbConnector.insertQuery(query);
    }

    public void deleteMatx(String usuari) {
        String query = "DELETE FROM Matx WHERE userName = '"+usuari+"';";
        System.out.println(query);
        DBConnector.getInstance().deleteQuery(query);
    }
}
