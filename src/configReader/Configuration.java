package configReader;

import configReader.ConfigClient;
import configReader.ConfigServer;

public class Configuration {
    ConfigClient configClient;
    ConfigServer configServer;

    public ConfigClient getConfigClient() {
        return configClient;
    }

    public ConfigServer getConfigServer() {
        return configServer;
    }


}
