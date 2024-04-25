package org.cyberspeed;

import org.cyberspeed.logic.ConfigSetup;
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
            ScratchGenerator sg = new ScratchGenerator(setup.getConfig("./configtest.json"));
            String[][] grid = sg.generateGrid();
            System.out.println(Arrays.deepToString(grid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
