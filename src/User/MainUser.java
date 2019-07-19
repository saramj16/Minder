package User;

import User.Controller.ControllerClient;
import User.Network.ServerComunication;
import User.View.AutenticationView;

import java.io.IOException;

public class MainUser {
    public static void main(String[] args) throws IOException {

        AutenticationView autenticationView = new AutenticationView();
        ServerComunication serverComunication = new ServerComunication(autenticationView);
        ControllerClient controller = new ControllerClient(autenticationView, serverComunication);

        controller.start();
        autenticationView.setVisible(true);

    }
}