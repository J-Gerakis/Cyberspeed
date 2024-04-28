package org.cyberspeed;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AppTest 
{
    /**
     * End-to-end happy path
     */
    @Test
    public void endToEnd()
    {
        ScratchCard scratch = new ScratchCard("./src/test/resources/std_3x3/standard_config.json");
        Assertions.assertDoesNotThrow(() -> {
            String outputJson = scratch.start(100);
            Assertions.assertNotNull(outputJson);
        });
    }

}
