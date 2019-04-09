package User;

import Network.NetworkManager;
import User.Controller.ControllerClient;
import User.View.AutenticationView;

import java.io.IOException;

public class MainUser {
    public static void main(String[] args) throws IOException {

        NetworkManager networkManager = new NetworkManager();
        AutenticationView autenticationView = new AutenticationView();
        ControllerClient controller = new ControllerClient(autenticationView, networkManager);

        networkManager.connectClient();
        controller.start();
        autenticationView.setVisible(true);
    }
}
