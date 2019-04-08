package User;

import java.io.IOException;
import User.View.AutenticationView;
import User.Controller.ControllerClient;
import User;
import User.Model.User;

public class MainUser {
    public static void main(String[] args) throws IOException {

        AutenticationView autenticationView = new AutenticationView();
        ControllerClient controller = new ControllerClient(autenticationView);

        User user = new User("Javo", 21, true, "javo@gmail.com", "pene",
                "hola", "java", "puto parguelas y marcel tambien");

        user.start();
        controller.start();
        autenticationView.setVisible(true);
    }
}
