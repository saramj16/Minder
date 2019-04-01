package User;

import java.awt.*;
import java.io.IOException;


public class MainUser {
    public static void main(String[] args) throws IOException {
        User user = new User("Javo", 21, true, "javo@gmail.com", "pene",
                "hola", "java", "puto amo");

        user.start();
    }
}
