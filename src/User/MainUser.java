package User;

import Network.ClientNetworkManager;
import User.Controller.ControllerClient;
import User.Model.User;
import User.View.AutenticationView;
import User.View.View;

import java.io.IOException;

public class MainUser {
    public static void main(String[] args) throws IOException {

        ClientNetworkManager networkManager = new ClientNetworkManager();
        AutenticationView autenticationView = new AutenticationView();
        ControllerClient controller = new ControllerClient(autenticationView, networkManager);

        User manel = new User(6969, "Manel", 22, true, "manel@gmail.com", "patata", "fotopolla.jpg","C++", "Salu2");
        View view = new View(manel);
        view.setVisible(true);

        networkManager.connectClient();
        controller.start();
        autenticationView.setVisible(true);
    }
}
