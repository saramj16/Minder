package Server.Model.database;

import java.sql.*;

public class DBConnector {
        private String userName;
        private String password;
        private String db;
        private int port;
        private String url = "jdbc:mysql://localhost";
        private Connection conn;
        private Statement s;
        private static DBConnector instance;

        private DBConnector(String usr, String pass, String db, int port) {
            this.userName = usr;
            this.password = pass;
            this.db = db;
            this.port = port;
            this.url += ":"+port+"/";
            this.url += db;
            this.instance = null;
        }

        public static DBConnector getInstance(){
            if(instance == null){
                instance = new DBConnector("root", "5432", "minder", 3306);

                instance.connect();
            }
            return instance;
        }

        public void connect() {
            try {
                //Class.forName("com.mysql.jdbc.Connection");

                conn = (Connection) DriverManager.getConnection(url, userName, password);
                if (conn != null) {
                    System.out.println("Connexió a base de dades "+url+" ... Ok");
                }
            }
            catch(SQLException ex) {
                ex.printStackTrace();
                System.out.println("Problema al connecta-nos a la BBDD --> "+url);
            }

        }

        public void insertQuery(String query){
            try {
                s =(Statement) conn.createStatement();
                s.executeUpdate(query);

            } catch (SQLException ex) {
                System.out.println("Problema al Inserir --> " + ex.getSQLState());
            }
        }

        public void updateQuery(String query){
            try {
                s =(Statement) conn.createStatement();
                s.executeUpdate(query);

            } catch (SQLException ex) {
                System.out.println("Problema al Modificar --> " + ex.getSQLState());
                System.out.println("La query que no modifica és: " +query);
            }
        }

        public void deleteQuery(String query){
            try {
                s =(Statement) conn.createStatement();
                s.executeUpdate(query);

            } catch (SQLException ex) {
                System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            }

        }

        public ResultSet selectQuery(String query){
            ResultSet rs = null;
            try {
                s = (Statement) conn.createStatement();
                rs = s.executeQuery(query);

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return rs;
        }

        public void disconnect(){
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
            }
        }

}
