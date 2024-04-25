package org.cyberspeed.dto.output;

import com.google.gson.annotations.SerializedName;
import org.cyberspeed.dto.WinCombination;

import java.util.Map;

public record Result(
        String[][] matrix,
        Integer reward,
        @SerializedName("applied_winning_combinations")
        Map<String, WinCombination> appliedWinningCombinations,
        @SerializedName("applied_bonus_symbol")
        String appliedBonusSymbol
) {
}
