package org.cyberspeed.logic;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.ProbabilitySymbol;

import java.util.*;

public class ScratchGenerator {

    private final ConfigFile config;

    public ScratchGenerator(ConfigFile config) {
        this.config = config;
    }

    public String[][] generateGrid() {
        String[][] grid = new String[config.rows()][config.columns()];

        //let's take the probability grid
        for(ProbabilitySymbol probability : config.probabilities().standardSymbols()) {
            String symbolName = drawAsymbol(probability);
            grid[probability.row()][probability.column()] = symbolName;
        }
        //Note: bonus symbol generation will appear here

        return grid;
    }

    public String drawAsymbol(ProbabilitySymbol probability) {
        Map<String, Integer> pValues = probability.valuesBySymbol();
        Map<Integer, String> indexMap = new HashMap<>();
        List<Integer> bucketList = new ArrayList<>();
        int currentIndex = 0;
        int cumul = 0;
        for(Map.Entry<String, Integer> entry : pValues.entrySet()) {
            String symbolName = entry.getKey();
            cumul += pValues.get(symbolName);
            bucketList.add(cumul);
            indexMap.put(currentIndex, symbolName);
            currentIndex++;
        }

        //draw a random int
        Random r = new Random();
        int randomValue = r.nextInt(cumul) + 1;

        //find in which bucket it falls
        for(int i = 0; i < bucketList.size(); i++) {
            if(randomValue <= bucketList.get(i)) {
                return indexMap.get(i);
            }
        }

        return ""; //note: need to turn this into an exception
    }



}
