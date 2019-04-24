package User;

import Network.ClientNetworkManager;
import User.Controller.ControllerClient;
import User.View.AutenticationView;

import java.io.IOException;

public class MainUser {
    public static void main(String[] args) throws IOException {

        ClientNetworkManager networkManager = new ClientNetworkManager();
        AutenticationView autenticationView = new AutenticationView();
        ControllerClient controller = new ControllerClient(autenticationView, networkManager);

        networkManager.connectClient();
        controller.start();
        autenticationView.setVisible(true);
    }
}
