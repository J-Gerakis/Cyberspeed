package org.cyberspeed;

import com.google.common.base.Strings;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.output.Grid;
import org.cyberspeed.dto.output.Result;
import org.cyberspeed.exception.ScratchException;
import org.cyberspeed.logic.ConfigSetup;
import org.cyberspeed.logic.ScratchEvaluator;
import org.cyberspeed.logic.ScratchGenerator;

import java.util.Arrays;

/**
 * Scratch card App
 * @author Jerome
 *
 */
public class App 
{
    public final static Logger logger = LogManager.getLogger(App.class);
    private final ScratchGenerator generator;
    private final ScratchEvaluator evaluator;

    public App(String configPath) {
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

    public void start(int bet) {
        try {
            Grid grid = generator.generateGrid();
            System.out.println(Arrays.deepToString(grid.matrix()));
            Result output = evaluator.evaluate(grid, bet);
            System.out.println(Utils.gson.toJson(output));
        } catch(ScratchException e) {
            logger.error("Runtime scratch error", e);
            //e.printStackTrace();
        }
    }

    private static Options createOptions() {
        Options options = new Options();
        Option par1 = new Option("c", "config", true, "Path to config file.json");
        Option par2 = new Option("b", "betting-amount", true, "Betting amount to play");
        options.addOption(par1);
        options.addOption(par2);
        return options;
    }

    public static void main( String[] args )
    {
        logger.info("Starting Scratch Card application");
        try {
            System.out.println( "Hello Scratch Card!" );
            Options options = createOptions();
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            String configPath = cmd.getOptionValue("c");
            String bettingAmount = cmd.getOptionValue("b");
            if(Strings.isNullOrEmpty(configPath)) {
                System.out.println("No config file specified");
            } else{
                    if (Strings.isNullOrEmpty(bettingAmount)) {
                        System.out.println("No betting amount specified");
                    } else {
                        if (!StringUtils.startsWith(bettingAmount, "-") && StringUtils.isNumeric(bettingAmount)) {
                            int bet = Integer.parseInt(bettingAmount);
                            App app = new App(configPath); //"./configtest.json"
                            app.start(bet);
                        } else {
                            System.out.println("Betting amount incorrect");
                        }
                    }

            }

        } catch (ParseException e) {
            logger.error("Command line parsing error", e);
            throw new RuntimeException(e);
        }
    }
}
