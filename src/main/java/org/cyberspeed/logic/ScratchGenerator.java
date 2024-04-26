package org.cyberspeed.logic;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.ProbabilitySymbol;
import org.cyberspeed.dto.output.Grid;
import org.cyberspeed.exception.ScratchException;

import java.util.*;

public class ScratchGenerator {

    private final ConfigFile config;
    private final Random rgen = new Random();

    public ScratchGenerator(ConfigFile config) {
        this.config = config;
    }

    public Grid generateGrid() throws ScratchException {
        String[][] grid = new String[config.rows()][config.columns()];
        Map<String, Integer> symbolCount = new HashMap<>(); //useful later during evaluation

        //Pick a random spot for bonus symbol generation
        String bonusName = drawAsymbol(config.probabilities().bonusSymbols().valuesByBonus());
        int i = rgen.nextInt(config.rows());
        int j = rgen.nextInt(config.columns());
        grid[i][j] = bonusName;

        //let's take the probability list for standard symbols
        for(ProbabilitySymbol probability : config.probabilities().standardSymbols()) {
            String symbolName = drawAsymbol(probability.valuesBySymbol());
            //check if the spot is not already occupied by the bonus symbol
            if(!bonusName.equals(grid[probability.row()][probability.column()])) {
                grid[probability.row()][probability.column()] = symbolName;
                symbolCount.put(symbolName, symbolCount.getOrDefault(symbolName, 0) + 1);
            }
        }

        return new Grid(grid, symbolCount, bonusName);
    }

    public String drawAsymbol(Map<String, Integer> pValues) throws ScratchException {
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
        int randomValue = rgen.nextInt(cumul) + 1;

        //find in which bucket it falls
        for(int i = 0; i < bucketList.size(); i++) {
            if(randomValue <= bucketList.get(i)) {
                return indexMap.get(i);
            }
        }

        //note: for coverage because this is not supposed to happen
        throw new ScratchException("Random value generation fell outside expected range");
    }



}
