package org.cyberspeed.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cyberspeed.dto.ConfigFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigSetup {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public ConfigFile getConfig(String configPath) throws IOException {
        final String fileContent = readConfigFile(configPath);
        return gson.fromJson(fileContent, ConfigFile.class);
    }

    private static String readConfigFile(String configPath) throws IOException {
        return Files.readString(Path.of(configPath));
    }

}
