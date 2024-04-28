package org.cyberspeed.logic;

import org.cyberspeed.Utils;
import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.exception.ScratchException;

import static org.cyberspeed.Utils.readFileContent;

public class ConfigSetup {

    public ConfigFile getConfig(String configPath) {
        final ConfigFile configFile;
        try {
            configFile = Utils.gson.fromJson(readFileContent(configPath), ConfigFile.class);
        } catch (Exception e) {
            throw new ScratchException("Could not read configuration file: " + configPath, e.getCause());
        }
        return configFile;
    }

}
