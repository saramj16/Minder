package Server;

import Network.NetworkManager;
import Server.Model.Server;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import configReader.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainServer {


    
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        NetworkManager networkManager = new NetworkManager();
        Configuration config;
        Gson gson = new Gson();
        JsonReader jReader;         //Lector

        try {                        //Try per si no podem obrir l'arxiu Json
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Configuration.class);                       //Llegeix el fitxer Json

            //debug

            System.out.println("Fitxer trobat");
            System.out.println("port bbdd: " + config.getConfigServer().getPort_bbdd());

            networkManager.connectServer();

        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }
    }
}
