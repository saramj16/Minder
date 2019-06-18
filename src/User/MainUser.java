package User;

import User.Controller.ControllerClient;
import User.Network.ServerComunication;
import User.View.AutenticationView;
import User.View.DemanarFoto;
import User.View.EditProfile;
import User.View.View;

import java.io.IOException;
import java.sql.SQLException;

public class MainUser {
    public static void main(String[] args) throws IOException, SQLException {

        AutenticationView autenticationView = new AutenticationView();
        ServerComunication serverComunication = new ServerComunication(autenticationView);
        ControllerClient controller = new ControllerClient(autenticationView, serverComunication);

        controller.start();
        autenticationView.setVisible(true);

    }
}