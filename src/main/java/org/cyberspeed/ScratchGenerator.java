package org.cyberspeed;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cyberspeed.dto.ConfigFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScratchGenerator {

    private final Config config;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public ScratchGenerator(String configPath) throws IOException {
        final String fileContent = readConfigFile(configPath);
        this.config = Config.map(gson.fromJson(fileContent, ConfigFile.class));
    }

    public void generate() {

        System.out.println(config);

    }

    private static String readConfigFile(String configPath) throws IOException {
        return Files.readString(Path.of(configPath));
    }

}
