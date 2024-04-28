package org.cyberspeed.logic;

import org.cyberspeed.dto.ConfigFile;
import org.cyberspeed.dto.Symbol;
import org.cyberspeed.dto.WinCombination;
import org.cyberspeed.dto.internal.Grid;
import org.cyberspeed.dto.output.Result;

import java.util.*;

public class ScratchEvaluator {

    private final ConfigFile configFile;
    private Map<String, List<String>> appliedWinningCombinations;

    public ScratchEvaluator(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public Result evaluate(Grid grid, int bet) {
        appliedWinningCombinations = new HashMap<>();

        Map<WinCombination.WIN_GROUP, String> winByCondition = new HashMap<>();
        for(Map.Entry<String, WinCombination> entry : configFile.winCombinations().entrySet()) {
            winByCondition.put(WinCombination.WIN_GROUP.valueOf(entry.getValue().group()), entry.getKey());
        }

        //evaluate symbol count (if same symbol win condition is present)
        if(winByCondition.containsKey(WinCombination.WIN_GROUP.same_symbols)) {
            Map<String, WinCombination> sameSymbolWinList = configFile.getWinGroup(WinCombination.WIN_GROUP.same_symbols.name());
            for (String symbolName : grid.symbolCount().keySet()) {
                int count = grid.symbolCount().get(symbolName);
                Optional<Map.Entry<String, WinCombination>> winningCombination = sameSymbolWinList.entrySet().stream().filter(g -> g.getValue().count() == count).findFirst();
                winningCombination.ifPresent(stringWinCombinationEntry -> updateWinningCombinations(symbolName, stringWinCombinationEntry.getKey()));
            }
        }

        //evaluate linear symbol
        int maxIndex = Math.max(configFile.rows(), configFile.columns());
        String rowWinCondition = winByCondition.get(WinCombination.WIN_GROUP.horizontally_linear_symbols);
        String colWinCondition = winByCondition.get(WinCombination.WIN_GROUP.vertically_linear_symbols);
        String ltrWinCondition = winByCondition.get(WinCombination.WIN_GROUP.ltr_diagonally_linear_symbols);
        String rtlWinCondition = winByCondition.get(WinCombination.WIN_GROUP.rtl_diagonally_linear_symbols);
        boolean ltrDiagContiguous = true;
        boolean rtlDiagContiguous = true;
        String leftCornerSymbolName = grid.get(0, 0);
        String rightCornerSymbolName = grid.get(0, configFile.columns() - 1);
        for(int index = 0; index < maxIndex; index++) {
            if(rowWinCondition != null && index < configFile.rows()) {
                if(evalRow(grid.matrix(), index)) {
                    String winningSymbolName = grid.get(index, 0);
                    updateWinningCombinations(winningSymbolName, rowWinCondition);
                }
            }
            if(colWinCondition != null && index < configFile.columns()) {
                if(evalColumn(grid.matrix(), index)) {
                    String winningSymbolName = grid.get(0, index);
                    updateWinningCombinations(winningSymbolName, colWinCondition);
                }
            }
            if(grid.isSquare()) {
                if(ltrWinCondition != null && ltrDiagContiguous) {
                    ltrDiagContiguous = leftCornerSymbolName.equals(grid.get(index,index));
                }
                if(rtlWinCondition != null && rtlDiagContiguous) {
                    rtlDiagContiguous = rightCornerSymbolName.equals(grid.get(index,configFile.columns() - 1 - index));
                }
            }

        }

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
        //add bonus, if relevant
        final String appliedBonusSymbol;
        if(reward > 0 && !ConfigFile.NO_BONUS.equals(grid.bonusSymbolName())) {
            appliedBonusSymbol = grid.bonusSymbolName();
            Symbol bonus = configFile.symbols().get(appliedBonusSymbol);
            switch (Symbol.SYMBOL_IMPACT.valueOf(bonus.impact())) {
                case extra_bonus:
                    reward += bonus.extra();
                    break;
                case multiply_reward:
                    reward *= bonus.rewardMultiplier();
                    break;
                case miss:
                default:
                    break;
            }
        } else {
            appliedBonusSymbol = null;
        }

        return new Result(grid.matrix(), reward.intValue(), appliedWinningCombinations, appliedBonusSymbol);
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

    private void updateWinningCombinations(String symbolName, String combinationName) {
        List<String> combinations = appliedWinningCombinations.getOrDefault(symbolName, new ArrayList<>());
        combinations.add(combinationName);
        appliedWinningCombinations.put(symbolName, combinations);
    }

}
