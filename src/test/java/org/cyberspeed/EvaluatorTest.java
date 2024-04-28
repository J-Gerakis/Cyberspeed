package org.cyberspeed;

import static org.cyberspeed.Utils.readFileContent;
import static org.cyberspeed.Utils.gson;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.TestData;
import org.cyberspeed.dto.output.Result;
import org.cyberspeed.logic.ScratchEvaluator;
import org.json.JSONException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;

public class EvaluatorTest {

    public static final String configStdPath = "./src/test/resources/std_3x3/standard_config.json";

    private static ConfigFile stdConfig;

    @BeforeAll
    public static void setUp() throws IOException {
        stdConfig = gson.fromJson(readFileContent(configStdPath), ConfigFile.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "./src/test/resources/std_3x3/win_1.json",
            "./src/test/resources/std_3x3/lose.json",
            "./src/test/resources/std_3x3/diag.json",
            "./src/test/resources/std_3x3/multi.json"
    })
    public void testEvaluatorStd(String dataPath) throws JSONException, IOException {
        TestData data = gson.fromJson(readFileContent(dataPath), TestData.class);
        ScratchEvaluator evaluator = new ScratchEvaluator(stdConfig);
        Result output = evaluator.evaluate(data.grid(), data.betAmount());

        JSONAssert.assertEquals(gson.toJson(data.output()), gson.toJson(output), JSONCompareMode.STRICT);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "./src/test/resources/exotic/5x5.json:./src/test/resources/exotic/config5x5.json",
            "./src/test/resources/exotic/3x6.json:./src/test/resources/exotic/config3x6.json"
    }, delimiter = ':')
    public void testEvaluatorExotic(String dataPath, String config) throws JSONException, IOException {
        TestData data = gson.fromJson(readFileContent(dataPath), TestData.class);
        ConfigFile exoticConfig = gson.fromJson(readFileContent(config), ConfigFile.class);
        ScratchEvaluator evaluator = new ScratchEvaluator(exoticConfig);
        Result output = evaluator.evaluate(data.grid(), data.betAmount());

        JSONAssert.assertEquals(gson.toJson(data.output()), gson.toJson(output), JSONCompareMode.STRICT);
    }
}
