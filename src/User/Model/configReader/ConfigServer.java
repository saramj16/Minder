package User.Model.configReader;

public class ConfigServer {
    private String port_bbdd;
    private String ip_bbdd;
    private String name_bbdd;
    private String username_bbdd;
    private String password_bbdd;
    private String port_client;

    public String getPort_bbdd() {
        return port_bbdd;
    }

    public String getIp_bbdd() {
        return ip_bbdd;
    }

    public String getName_bbdd() {
        return name_bbdd;
    }

    public String getUsername_bbdd() {
        return username_bbdd;
    }

    public String getPassword_bbdd() {
        return password_bbdd;
    }

    public String getPort_client() {
        return port_client;
    }
}
