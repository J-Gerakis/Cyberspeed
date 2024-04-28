package org.cyberspeed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.internal.Grid;
import org.cyberspeed.dto.output.Result;
import org.cyberspeed.exception.ScratchException;
import org.cyberspeed.logic.ConfigSetup;
import org.cyberspeed.logic.ScratchEvaluator;
import org.cyberspeed.logic.ScratchGenerator;

import java.util.Arrays;

public class ScratchCard {

    public final static Logger logger = LogManager.getLogger(ScratchCard.class);
    private final ScratchGenerator generator;
    private final ScratchEvaluator evaluator;

    public ScratchCard(String configPath) {
        ConfigFile conf;
        try {
            ConfigSetup setup = new ConfigSetup();
            conf = setup.getConfig(configPath);
        } catch (ScratchException e) {
            logger.error("Error during config load", e);
            throw new RuntimeException(e);
        }
        generator = new ScratchGenerator(conf);
        evaluator = new ScratchEvaluator(conf);
    }

    public Result start(int bet) {
        try {
            Grid grid = generator.generateGrid();
            logger.debug(Arrays.deepToString(grid.matrix()));
            return evaluator.evaluate(grid, bet);
        } catch(ScratchException e) {
            logger.error("Runtime scratch error", e);
            throw new RuntimeException(e);
        }
    }


}
