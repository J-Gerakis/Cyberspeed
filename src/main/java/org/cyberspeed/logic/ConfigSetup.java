package org.cyberspeed.logic;

import org.cyberspeed.Utils;
import org.cyberspeed.dto.ConfigFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigSetup {

    public ConfigFile getConfig(String configPath) throws IOException {
        final String fileContent = readConfigFile(configPath);
        return Utils.gson.fromJson(fileContent, ConfigFile.class);
    }

    private static String readConfigFile(String configPath) throws IOException {
        return Files.readString(Path.of(configPath));
    }

}
