package org.cyberspeed;

import com.google.common.base.Strings;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Scratch card App
 * @author Jerome
 *
 */
public class CommandLineApp
{
    public final static Logger logger = LogManager.getLogger(CommandLineApp.class);

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
            Options options = createOptions();
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            String configPath = cmd.getOptionValue("c");
            String bettingAmount = cmd.getOptionValue("b");
            if(Strings.isNullOrEmpty(configPath)) {
                logger.warn("No config file specified");
            } else{
                    if (Strings.isNullOrEmpty(bettingAmount)) {
                        logger.warn("No betting amount specified");
                    } else {
                        if (!StringUtils.startsWith(bettingAmount, "-") && StringUtils.isNumeric(bettingAmount)) {
                            int bet = Integer.parseInt(bettingAmount);
                            ScratchCard app = new ScratchCard(configPath);
                            logger.info(app.start(bet));
                        } else {
                            logger.warn("Betting amount incorrect");
                        }
                    }

            }
            logger.info("Finished Scratch Card application");
        } catch (ParseException e) {
            logger.error("Command line parsing error", e);
            throw new RuntimeException(e);
        }
    }
}
