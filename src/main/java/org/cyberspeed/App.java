package org.cyberspeed;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.output.Grid;
import org.cyberspeed.dto.output.Result;
import org.cyberspeed.logic.ConfigSetup;
import org.cyberspeed.logic.ScratchEvaluator;
import org.cyberspeed.logic.ScratchGenerator;

import java.io.IOException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello Scratch Card!" );

        try {
            ConfigSetup setup = new ConfigSetup();
            ConfigFile conf = setup.getConfig("./configtest.json");
            ScratchGenerator sg = new ScratchGenerator(conf);
            Grid grid = sg.generateGrid();
            System.out.println(Arrays.deepToString(grid.matrix()));
            ScratchEvaluator se = new ScratchEvaluator(conf);
            Result output = se.evaluate(grid, 100);
            System.out.println(Utils.gson.toJson(output));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
