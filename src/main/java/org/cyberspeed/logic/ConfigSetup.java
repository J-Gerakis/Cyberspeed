package org.cyberspeed.logic;

import org.cyberspeed.Utils;
import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.exception.ScratchException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigSetup {

    public ConfigFile getConfig(String configPath) throws ScratchException {
        final ConfigFile configFile;
        try {
            configFile = Utils.gson.fromJson(readConfigFile(configPath), ConfigFile.class);
        } catch (IOException e) {
            throw new ScratchException("Could not read configuration file", e);
        }
        return configFile;
    }

    private static String readConfigFile(String configPath) throws IOException {
        return Files.readString(Path.of(configPath));
    }

}
