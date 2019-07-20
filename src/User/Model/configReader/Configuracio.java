package User.Model.configReader;

/**
 * Classe Configuració. Comprèn la informació tant del servidor com del client emmagatzemada al config.json
 */
public class Configuracio {
    ConfigClient configClient;
    ConfigServer configServer;

    /**
     * Constructor
     */
    public Configuracio() {
    }

    public ConfigClient getConfigClient() {
        return configClient;
    }

    public ConfigServer getConfigServer() {
        return configServer;
    }



}
