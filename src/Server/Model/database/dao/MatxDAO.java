package Server.Model.database.dao;

import Server.Model.entity.Matx;
import Server.Model.database.DBConnector;


public class MatxDAO {
    public void addMatx(Matx matx) {
        String query = "INSERT INTO Matx(id, user1, user2) VALUES ('"+matx.getId()+"', '"
                + matx.getUser1()+"', '"+matx.getUser2() + "');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }

    public void deleteMatx(int id) {
        String query = "DELETE FROM Matx WHERE id = '"+id+"';";
        System.out.println(query);
        DBConnector.getInstance().deleteQuery(query);
    }
}
