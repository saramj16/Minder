package Server.Model.database.dao;

import Server.Model.entity.Missatge;
import Server.Model.database.DBConnector;


public class MissatgeDAO {

    private DBConnector dbConnector;

    public MissatgeDAO(){
        dbConnector = DBConnector.getInstance();

        System.out.println(dbConnector);
    }

    public void addMissatge(Missatge missatge) {
        String query = "INSERT INTO Missatge(id, missatge, userSend ) VALUES ('"+missatge.getId()+"', '"
                +missatge.getMissatge()+"', '"+missatge.getUserSend() + "');";
        System.out.println(query);
        DBConnector.getInstance().insertQuery(query);
    }

    public void deleteMissatge(int id) {
        String query = "DELETE FROM Missatge WHERE id = '"+id+"';";
        System.out.println(query);
        DBConnector.getInstance().deleteQuery(query);
    }

}
