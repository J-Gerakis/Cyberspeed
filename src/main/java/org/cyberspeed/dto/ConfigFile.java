package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;
import org.cyberspeed.exception.ScratchException;

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
                probabilities.standardSymbols().forEach(s -> validateSymbolPresenceAndPosition(symbols.keySet(), s));
                // consistency checks between bonus symbol list and probabilities list
                validateSymbolPresence(symbols.keySet(), probabilities.bonusSymbols().valuesByBonus().keySet());


        }

        private void validateSymbolPresenceAndPosition(Set<String> declaredSymbols, ProbabilitySymbol probabilitySymbol) {
                if(probabilitySymbol.row() > MAX_SIZE || probabilitySymbol.column() > MAX_SIZE) {
                        throw new ScratchException("row and column index for symbol probability exceed grid size");
                }
                validateSymbolPresence(declaredSymbols, probabilitySymbol.valuesBySymbol().keySet());
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
