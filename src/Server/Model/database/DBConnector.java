package Server.Model.database;

import Server.Model.Server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
        private static String userName;
        private static String password;
        private static String db;
        private static int port;
        private static String url = "jdbc:mysql://localhost";
        private static Connection conn;
        private static Statement s;
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
                instance = new DBConnector("root", "root", "minder", 3306);
                instance.connect();

            }
            return  instance;
        }

        public void connect() {
            try {
                Class.forName("com.mysql.jdbc.Connection");

                conn = (Connection) DriverManager.getConnection(url, userName, password);
                if (conn != null) {
                    System.out.println("Connexió a base de dades "+url+" ... Ok");
                }
            }
            catch(SQLException ex) {
                System.out.println("Problema al connecta-nos a la BBDD --> "+url);
            }
            catch(ClassNotFoundException ex) {
                System.out.println(ex);
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
                s =(Statement) conn.createStatement();
                rs = s.executeQuery (query);

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
