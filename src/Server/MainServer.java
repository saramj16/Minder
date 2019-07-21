package Server;

import Server.Model.Controller.Controller;
import Server.Model.Server;
import Server.Model.entity.UsuariManager;
import Server.View.View;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import Server.Model.configReader.Configuracio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe main del programa servidor
 */
public class MainServer {


    /**
     * Cosntructor del main
     * @param args
     */
    public static void main(String[] args){
        Server server = null;
        // ServerNetworkManager networkManager = null;
        UsuariManager model = new UsuariManager();

        ArrayList<String> noms = model.getTop5UsuarisAcceptats();
        ArrayList<Integer> puntuacions = model.getTop5NumAcceptacions();
        View v = new View(noms, puntuacions);
        Controller c = new Controller(v,model);
        v.registerController(c);

        try {
            server = new Server(model);
            // networkManager = new ServerNetworkManager(server);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        Configuracio config;
        Gson gson = new Gson();
        JsonReader jReader;         //Lector

        try {                        //Try per si no podem obrir l'arxiu Json
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Configuracio.class);                       //Llegeix el fitxer Json

            //debug
            System.out.println("Fitxer trobat");
            System.out.println("port bbdd: " + config.getConfigServer().getPort_bbdd());

            if (server != null) {
                server.run();

            } else {
                System.out.println("Null");
            }

        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }
    }
}