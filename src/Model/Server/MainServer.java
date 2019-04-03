package Model.Server;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import configReader.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainServer {


    //proba
    
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Configuration config = new Configuration();
        Gson gson = new Gson();
        JsonReader jReader;         //Lector

        try {                        //Try per si no podem obrir l'arxiu Json
            jReader = new JsonReader(new FileReader("data/config.json"));
            config = gson.fromJson(jReader, Configuration.class);                       //Llegeix el fitxer Json

            //debug

            System.out.println("Fitxer trobat");
            System.out.println("port bbdd: " + config.getConfigServer().getPort_bbdd());

            server.start();

        } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
            System.out.println("Error: Fitxer no trobat.");
        }
    }
}
