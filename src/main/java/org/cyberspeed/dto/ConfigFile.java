package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;
import org.cyberspeed.exception.ScratchException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.cyberspeed.Utils.filterMapByValue;

public record ConfigFile(
        Integer columns,
        Integer rows,
        Map<String, Symbol> symbols,
        Probabilities probabilities,
        @SerializedName("win_combinations") Map<String, WinCombination> winCombinations
) {
        public static final int MAX_SIZE = 10;
        public static final String NO_BONUS = "NO BONUS";

        public ConfigFile {
                if (columns == null || columns < 1) throw new ScratchException("columns must be a positive value");
                if (rows == null || rows < 1) throw new ScratchException("rows must be a positive value");
                if (rows > MAX_SIZE || columns > MAX_SIZE) throw new ScratchException("columns and rows exceed size");
                //notes: should we always force a square?

                if (symbols == null || symbols.isEmpty()) throw new ScratchException("symbols cannot be null");
                if (probabilities == null) throw new ScratchException("probabilities cannot be null");
                if (winCombinations == null || winCombinations.isEmpty()) throw new ScratchException("winCombinations cannot be null");

                // consistency checks between symbol list and probabilities list
                // note: so far we allow symbols to contain unused items
                validateProbabilities(symbols.keySet(), probabilities, rows, columns);
        }

        private void validateProbabilities(Set<String> declaredSymbols, Probabilities probabilities, int rowMax, int colMax) {
                Set<String> gridAssignmentCheck = new HashSet<>();
                probabilities.standardSymbols().forEach(s ->
                        {
                                if(s.row() >= rowMax || s.column() >= colMax) {
                                        throw new ScratchException("row and column index for symbol probability exceed grid size");
                                }
                                String slotName = s.row() + "x" + s.column();
                                if(gridAssignmentCheck.contains(slotName)) {
                                        throw new ScratchException(String.format("Probability for %s is already assigned", slotName));
                                } else {
                                        gridAssignmentCheck.add(slotName);
                                }
                                validateSymbolPresence(declaredSymbols, s.valuesBySymbol().keySet());
                        }
                );
                validateSymbolPresence(declaredSymbols, probabilities.bonusSymbols().valuesByBonus().keySet());

        }

        private void validateSymbolPresence(Set<String> declaredSymbols, Set<String> symbolsFromProbabilities) {
                if(!declaredSymbols.containsAll(symbolsFromProbabilities)){
                        throw new ScratchException("probabilities section contains undeclared symbols");
                }
        }

        public Map<String, WinCombination> getWinGroup(String groupName) {
                return filterMapByValue(winCombinations, g -> g.group().equals(groupName));
        }
}
