package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public record ConfigFile(
        Integer columns,
        Integer rows,
        Map<String, Symbol> symbols,
        Probabilities probabilities,
        @SerializedName("win_combinations") Map<String, WinCombination> winCombinations
) {
        public ConfigFile {
                if (columns == null) throw new IllegalArgumentException("columns cannot be null");
                if (rows == null) throw new IllegalArgumentException("rows cannot be null");
                if (!columns.equals(rows)) throw new IllegalArgumentException("columns and rows must be the same");
                //notes: should we always force a square?
                //add max/min size check

                if (symbols == null || symbols.isEmpty()) throw new IllegalArgumentException("symbols cannot be null");
                if (probabilities == null) throw new IllegalArgumentException("probabilities cannot be null");
                if (winCombinations == null || winCombinations.isEmpty()) throw new IllegalArgumentException("winCombinations cannot be null");

                //Note: add consistency checks on win combination vs columns/rows
                // add consistency checks between symbol list and probabilities list


        }
}
