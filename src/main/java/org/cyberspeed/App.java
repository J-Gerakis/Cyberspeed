package org.cyberspeed;

import java.io.IOException;

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
            ScratchGenerator sg = new ScratchGenerator("./configtest.json");
            sg.generate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
