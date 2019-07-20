package Server.Model.database.dao;

import Server.Model.entity.Usuari;
import Server.Model.database.DBConnector;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe que gestiona la taula Usuari a la bbdd  i s'engarrega de poder-los utilitzar al programa
 */
public class UsuariDAO {

    private DBConnector dbConnector;

    /**
     * Constructor
     */
    public UsuariDAO(){
        dbConnector = DBConnector.getInstance();

        System.out.println(dbConnector);
    }


    /**
     *
     * Mètode per afegir un Usuari a la BBDD de SQL
     *
     * @param  usuari   Classe Usuari
     * @return void
     *
     */
    public synchronized void addUsuari(Usuari usuari) {
        String query = "INSERT INTO Usuari(userName, edat, premium, correo, password, urlFoto, lenguaje, description) VALUES " +
                "('"+usuari.getUserName()+"', '" +usuari.getEdat()+"', "+usuari.isPremium()+", '"+usuari.getCorreo()+ "', '"
                +usuari.getPassword()+"', '" + usuari.getUrlFoto() + "', '" + usuari.getLenguaje() + "', '" + usuari.getDescription() + "');";
        System.out.println(query);
        dbConnector.insertQuery(query);
    }

    /**
     *
     * Mètode per eliminar un Usuari a la BBDD de SQL
     *
     * @param  userName  nom de l'Usuari que hem d'eliminar
     * @return void
     *
     */
    public synchronized void deleteUsuari (String userName){
        String query = "DELETE FROM Usuari WHERE userName = '"+userName+"';";
        //System.out.println(query);
        dbConnector.deleteQuery(query);
    }


    /**
     *
     * Mètode per retornar una ArrayList amb tots els Usuaris que ens demanen
     *
     * @param  usuaris  ArrayList de noms dels usuaris a cercar
     * @return ArrayList<Usuari>  llista amb tots els Usuaris
     *
     */
    public synchronized ArrayList<Usuari> searchUsuaris (ArrayList<String> usuaris){
        ArrayList<Usuari> usuaris1 = new ArrayList<>();

        for (int i = 0; i < usuaris.size(); i++){
            String query = "SELECT * FROM Usuari WHERE userName = '"+usuaris.get(i)+"';";
            //System.out.println(query);
            ResultSet resultat = dbConnector.selectQuery(query);

            try{
                while(resultat.next()){
                    Usuari u = new Usuari(resultat.getString("userName"),
                            resultat.getInt("edat"),
                            resultat.getBoolean("premium"),
                            resultat.getString("correo"),
                            resultat.getString("password"),
                            resultat.getString("urlFoto"),
                            resultat.getString("lenguaje"),
                            resultat.getString("description") );
                    usuaris1.add(u);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuaris1;
    }

    /**
     *
     * Cerca un usuari en concret dins la BBDD
     *
     * @param  userName  nom de l'usuari a cercar
     * @return boolean true en cas que existeixi l'usuari, false en cas contrari
     *
     */
    public synchronized boolean searchUsuari(String userName) {

        String query = "SELECT userName FROM Usuari WHERE userName = '"+userName+"';";
        //System.out.println(query);
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

    /**
     *
     * Modifica els paràmetes corresponents de l'usuari
     *
     * @param  u  Classe Usuari amb tota la info que necesitem
     * @return void
     *
     */
    //Crec que la canviare, en funcio de com fem la vista
    //Igual es mes senzill agafar la nova info i unicament eliminar l'anterior i tornar a inserirlo
    public synchronized void modificaUsuari(Usuari u) {
        String query;
        if (u.isPremium()) {
            query = "UPDATE Usuari SET edat = " + u.getEdat() + ", premium = '1', correo = '" + u.getCorreo() + "', password = '" + u.getPassword() + "', urlFoto = '" + u.getUrlFoto() + "', lenguaje = '" + u.getLenguaje() + "', description = '" + u.getDescription() + "' WHERE userName = '" + u.getUserName() + "';";
            System.out.println("Url de la foto1 " + u.getUrlFoto());
            System.out.println(query);
        }else {
            query = "UPDATE Usuari SET edat = " + u.getEdat() + ", premium = '0', correo = '" + u.getCorreo() + "', password = '" + u.getPassword() + "', urlFoto = '" + u.getUrlFoto() + "', lenguaje = '" + u.getLenguaje() + "', description = '" + u.getDescription() + "' WHERE userName = '" + u.getUserName() + "';";
            System.out.println("Url de la foto2 " + u.getUrlFoto());
        }
        dbConnector.updateQuery(query);
    }


    /**
     *
     * Comprova que les dades de l'usuari siguin les correctes
     *
     * @param  username  nom de l'usuari
     * @param  password contrasenya de l'usuari
     * @return boolean true si les dades son correctes, false en cas contrari
     *
     */
    public synchronized boolean comprovaUsuari(String username, String password){
        System.out.println(username);
        System.out.println(password);
        String query = "SELECT * FROM Usuari WHERE userName = '"+username+"' AND password = '" + password + "';";

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom = resultat.getString("userName");
                System.out.println(nom);
                String password2 = resultat.getString("password");
                System.out.println(password2);
                if (username.equals(nom) && password.equals(password2)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Comprova que el login d'un usuari ha estat possible utilitzant com a identificador el correu enlloc del nom
     * @param correu
     * @param password
     * @return
     */
    public boolean comprovaUsuariCorreu(String correu, String password){
        System.out.println(correu);
        System.out.println(password);
        String query = "SELECT * FROM Usuari WHERE correo = '"+correu+"' AND password = '" + password + "';";
        //  System.out.println(query);

        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String correu1 = resultat.getString("correo");
                System.out.println(correu1);
                String password2 = resultat.getString("password");
                System.out.println(password2);
                if (correu.equals(correu1) && password.equals(password2)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     *
     * Retorna un ArrayList amb tots els Usuaris de la BBDD
     *
     * @return ArrayList<Usuari>  llista amb tots els Usuaris
     *
     */
    public synchronized ArrayList<Usuari> getAllUsuari() {

        String query = "SELECT * FROM Usuari;";
        //System.out.println(query);
        ResultSet resultat = dbConnector.selectQuery(query);
        //System.out.println(resultat);

        ArrayList<Usuari> usuariList = new ArrayList<>();
        try{
           while (resultat.next()) {
                Usuari usuari = new Usuari(resultat.getString("userName"),
                                            resultat.getInt("edat"),
                                            resultat.getBoolean("premium"),
                                            resultat.getString("correo"),
                                            resultat.getString("password"),
                                            resultat.getString("urlFoto"),
                                            resultat.getString("lenguaje"),
                                            resultat.getString("description"));
                usuariList.add(usuari);
                System.out.println(usuari.getUserName());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuariList;
    }

    /**
     *
     * Busca un usuari concret a la BBDD i el retorna
     *
     * @param  userName  nom de l'usuari
     * @return Usuari amb tota la informació d'aquell Usuari
     *
     */
    public Usuari getUsuariByName(String userName) {

        String query = "SELECT * FROM Usuari WHERE userName = '" + userName + "';";
        //System.out.println(query);
        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom = resultat.getString("userName");
                int edat = resultat.getInt("edat");
                boolean premium = resultat.getBoolean("premium");
                String correo = resultat.getString("correo");
                String password = resultat.getString("password");
                String urlFoto = resultat.getString("urlFoto");
                String lenguaje = resultat.getString("lenguaje");
                String description = resultat.getString("description");


                Usuari usuari = new Usuari(nom, edat,premium,correo,password,urlFoto,lenguaje,description);

                return usuari;

            }
        } catch (SQLException e) {
            System.out.println("NOM NO TROBAT A LA BBDD");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retorna l'usuari el qual tingui com a correu assignat a la conta el indicat per paràmetres
     * @param mail
     * @return
     */
    public Usuari getUsuariByMail(String mail) {

        String query = "SELECT * FROM Usuari WHERE correo = '" + mail + "';";
        //System.out.println(query);
        ResultSet resultat = dbConnector.selectQuery(query);

        try{
            while(resultat.next()){
                String nom = resultat.getString("userName");
                int edat = resultat.getInt("edat");
                boolean premium = resultat.getBoolean("premium");
                String correo = resultat.getString("correo");
                String password = resultat.getString("password");
                String urlFoto = resultat.getString("urlFoto");
                String lenguaje = resultat.getString("lenguaje");
                String description = resultat.getString("description");


                Usuari usuari = new Usuari(nom, edat,premium,correo,password,urlFoto,lenguaje,description);

                return usuari;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
