package org.cyberspeed.logic;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.Symbol;
import org.cyberspeed.dto.WinCombination;
import org.cyberspeed.dto.output.Grid;
import org.cyberspeed.dto.output.Result;

import java.util.*;

public class ScratchEvaluator {

    private final ConfigFile configFile;

    public ScratchEvaluator(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public Result evaluate(Grid grid, int bet) {
        Map<String, List<String>> appliedWinningCombinations = new HashMap<>();

        //evaluate symbol count (if same symbol win condition is present)
        Map<String, WinCombination> sameSymbolWinList = configFile.getWinGroup(WinCombination.WIN_GROUP.same_symbols.name());
        if(sameSymbolWinList != null && !sameSymbolWinList.isEmpty()) {
            for (String symbolName : grid.symbolCount().keySet()) {
                int count = grid.symbolCount().get(symbolName);
                Optional<Map.Entry<String, WinCombination>> winningCombination = sameSymbolWinList.entrySet().stream().filter(g -> g.getValue().count() == count).findFirst();
                if (winningCombination.isPresent()) {
                    List<String> combinations = appliedWinningCombinations.getOrDefault(symbolName, new ArrayList<>());
                    combinations.add(winningCombination.get().getKey());
                    appliedWinningCombinations.put(symbolName, combinations);
                }
            }
        }


        //evaluate rows (if linear win condition is present)
        for (int i=0; i < configFile.rows(); i++){
            if(evalRow(grid.matrix(), i)) {
                String winningSymbolName = grid.get(i,0);
                List<String> combinations = appliedWinningCombinations.getOrDefault(winningSymbolName, new ArrayList<>());
                combinations.add(WinCombination.WIN_GROUP.horizontally_linear_symbols.name());
                appliedWinningCombinations.put(winningSymbolName, combinations);
            }
        }

        //evaluate cols (if linear win condition is present)
        for (int j=0; j < configFile.columns(); j++){
            if(evalColumn(grid.matrix(), j)) {
                String winningSymbolName = grid.get(0,j);
                List<String> combinations = appliedWinningCombinations.getOrDefault(winningSymbolName, new ArrayList<>());
                combinations.add(WinCombination.WIN_GROUP.vertically_linear_symbols.name());
                appliedWinningCombinations.put(winningSymbolName, combinations);
            }
        }

        //evaluate diagonals

        //calculate reward
        Double reward = 0.0;
        for(String symbolName : appliedWinningCombinations.keySet()) {
            double symbolMultiplier = configFile.symbols().get(symbolName).rewardMultiplier();
            double symbolReward = bet * symbolMultiplier;

            for(String winConditionName : appliedWinningCombinations.get(symbolName)) {
                symbolReward *= configFile.winCombinations().get(winConditionName).rewardMultiplier();
            }
            reward += symbolReward;
        }
        //add bonus
        Symbol bonus = configFile.symbols().get(grid.bonusSymbolName());
        switch (Symbol.SYMBOL_IMPACT.valueOf(bonus.impact())) {
            case extra_bonus:
                reward += bonus.extra();
                break;
            case multiply_reward:
                reward *= bonus.rewardMultiplier();
                break;
            case miss:
                break;
        }

        return new Result(grid.matrix(), reward.intValue(), appliedWinningCombinations, grid.bonusSymbolName());
    }


    private boolean evalRow(String[][] matrix, int rowIdx) {
        String startSymbol = matrix[rowIdx][0];
        for (int colIdx = 0; colIdx < matrix[0].length; colIdx++) {
            if (!startSymbol.equals(matrix[rowIdx][colIdx])) {
                return false;
            }
        }
        return true;
    }

    private boolean evalColumn(String[][] matrix, int colIdx) {
        String startSymbol = matrix[0][colIdx];
        for (int rowIdx = 0; rowIdx < matrix.length; rowIdx++) {
            if (!startSymbol.equals(matrix[rowIdx][colIdx])) {
                return false;
            }
        }
        return true;
    }

}
