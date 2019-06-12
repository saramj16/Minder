package User;

import Network.ClientNetworkManager;
import User.Controller.ControllerClient;
import User.Model.User;
import User.View.AutenticationView;
import User.View.EditProfile;
import User.View.View;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class MainUser {
    public static void main(String[] args) throws IOException, SQLException, URISyntaxException {

        EditProfile ep = new EditProfile();
        ep.dadesEditaPrefil("Sara", 20, "sara@minder.com", "sara", "tope guapa", "C", false, "./imatges/default.jpeg");
        ep.setVisible(true);

        ClientNetworkManager networkManager = new ClientNetworkManager();
        AutenticationView autenticationView = new AutenticationView();
        ControllerClient controller = new ControllerClient(autenticationView, networkManager);

        networkManager.connectClient();
        controller.start();
        autenticationView.setVisible(true);
    }
}
