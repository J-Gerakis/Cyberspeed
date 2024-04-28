package org.cyberspeed.dto.internal;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record Grid(
        String[][] matrix,
        @SerializedName("symbol_count")
        Map<String, Integer> symbolCount,
        @SerializedName("bonus_symbol_name")
        String bonusSymbolName,
        @SerializedName("is_square")
        boolean isSquare
) {
    public String get(int row, int col) {
        return matrix[row][col];
    }
}
