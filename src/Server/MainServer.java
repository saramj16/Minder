package Server;

import Network.ServerNetworkManager;
import Server.Model.Server;
import Server.Model.entity.UsuariManager;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import configReader.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class MainServer {


    
    public static void main(String[] args){
        Server server;
        ServerNetworkManager networkManager = null;
        UsuariManager model = new UsuariManager();


       try {
            server = new Server(model);
            networkManager = new ServerNetworkManager(server);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration config;
        Gson gson = new Gson();
        JsonReader jReader;         //Lector

        try {                        //Try per si no podem obrir l'arxiu Json
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Configuration.class);                       //Llegeix el fitxer Json

            //debug
            System.out.println("Fitxer trobat");
            System.out.println("port bbdd: " + config.getConfigServer().getPort_bbdd());



            try {
                if (networkManager != null) {
                    networkManager.connectServer();

                } else {
                    System.out.println("Null");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }
    }
}
