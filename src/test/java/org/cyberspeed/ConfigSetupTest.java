package org.cyberspeed;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.exception.ScratchException;
import org.cyberspeed.logic.ConfigSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ConfigSetupTest {

    @ParameterizedTest
    @CsvSource(value = {
            "./src/test/resources/std_3x3/standard_config.json",
            "./src/test/resources/exotic/config5x5.json",
            "./src/test/resources/exotic/config3x6.json"
    }, delimiter = ':')
    public void correctConfigSetupTest(String fileName) {
        ConfigSetup cs = new ConfigSetup();
        Assertions.assertDoesNotThrow(() -> {
            ConfigFile config = cs.getConfig(fileName);
            Assertions.assertNotNull(config);
            Assertions.assertNotNull(config.rows());
            Assertions.assertNotNull(config.columns());
            Assertions.assertNotNull(config.probabilities());
            Assertions.assertNotNull(config.symbols());
            Assertions.assertNotNull(config.winCombinations());
        });

    }

    @ParameterizedTest
    @CsvSource(value = {
            "./src/test/resources/wonky/inconsistent_size.json",
            "./src/test/resources/wonky/invalid_probabilities.json",
            "./src/test/resources/wonky/invalid_probabilities_2.json",
            "./src/test/resources/wonky/missing_combinations.json",
            "./src/test/resources/wonky/missing_probabilities.json",
            "./src/test/resources/wonky/missing_symbol.json",
    }, delimiter = ':')
    public void wonkyConfigSetupTest(String fileName) {
        ConfigSetup cs = new ConfigSetup();
        Assertions.assertThrows(ScratchException.class, () -> {
            ConfigFile config = cs.getConfig(fileName);
            Assertions.assertNull(config);
        });
    }
}
