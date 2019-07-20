package User;

import User.Controller.ControllerClient;
import User.Model.Connectivity;
import User.Network.ServerComunication;
import User.View.AutenticationView;

import java.io.IOException;

import java.sql.SQLException;
/**
 * Classe main del Client
 */
public class MainUser {
    public static void main(String[] args) throws IOException {

        AutenticationView autenticationView = new AutenticationView();
        ServerComunication serverComunication = new ServerComunication();
       // serverComunication.funcion();
        ControllerClient controller = new ControllerClient(autenticationView, serverComunication);
        serverComunication.setController(controller);

        controller.start();
        autenticationView.setVisible(true);
        serverComunication.getConnectivity().go();
    }
}