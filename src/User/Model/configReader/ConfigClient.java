package User.Model.configReader;

/**
 * Classe Configuració. Comprèn la informació tant del client emmagatzemada al config.json
 */
public class ConfigClient {
    private String ip_server;
    private String port_server;

    public String getIp_server() {
        return ip_server;
    }

    public String getPort_server() {
        return port_server;
    }
}
