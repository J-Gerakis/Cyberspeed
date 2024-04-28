package org.cyberspeed;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.internal.Grid;
import org.cyberspeed.logic.ScratchGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.cyberspeed.Utils.gson;
import static org.cyberspeed.Utils.readFileContent;

public class GeneratorTest {

    @ParameterizedTest
    @CsvSource(value = {
            "./src/test/resources/std_3x3/standard_config.json",
            "./src/test/resources/exotic/config5x5.json",
            "./src/test/resources/exotic/config3x6.json"
    }, delimiter = ':')
    public void correctGridGeneration(String configPath) throws IOException {
        ConfigFile config = gson.fromJson(readFileContent(configPath), ConfigFile.class);

        ScratchGenerator generator = new ScratchGenerator(config);
        Grid grid = generator.generateGrid();

        Assertions.assertNotNull(grid);
        Assertions.assertEquals(config.rows(), grid.matrix().length);
        Assertions.assertEquals(config.columns(), grid.matrix()[0].length);
        for(int i = 0; i < grid.matrix().length; i++) {
            for(int j = 0; j < grid.matrix()[0].length; j++) {
                String symbolName = grid.matrix()[i][j];
                Assertions.assertNotNull(symbolName);
                Assertions.assertTrue(config.symbols().containsKey(symbolName));
            }
        }

    }
}
