package org.cyberspeed.dto.output;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public record Result(
        String[][] matrix,
        Integer reward,
        @SerializedName("applied_winning_combinations")
        Map<String, List<String>> appliedWinningCombinations,
        @SerializedName("applied_bonus_symbol")
        String appliedBonusSymbol
) {
}
