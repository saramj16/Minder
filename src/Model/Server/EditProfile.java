package Model.Server;

import Model.User.User;

public class EditProfile {

    public static void editProfilePic(User user, String urlProfilePic) {
        user.setUrlProfilePic(urlProfilePic);
    }

    public static void editDescription(User user, String description) {
        user.setDescription(description);
    }

    public static void editLanguage(User user) {
        if (user.getLanguage().equals("Java")) {
            user.setLanguage("C++");
        } else {
            user.setLanguage("Java");
        }
    }
}
