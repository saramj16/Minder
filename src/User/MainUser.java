package User;

import java.io.IOException;
import User.View.AutenticationView;
import User.Controller.ControllerClient;
import User.Model.User;
import User.View.View;

public class MainUser {
    public static void main(String[] args) throws IOException {

        User user = new User("Javo", 21, true, "javo@gmail.com", "pene",
                "hola", "java", "puto parguelas y marcel tambien");

        View view = new View(user);
        AutenticationView autenticationView = new AutenticationView();
        ControllerClient controller = new ControllerClient(autenticationView);


        user.start();
        controller.start();
        autenticationView.setVisible(true);
        view.setVisible(true);

    }
}
