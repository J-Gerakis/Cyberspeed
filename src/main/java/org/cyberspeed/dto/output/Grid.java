package org.cyberspeed.dto.output;

import java.util.Map;

public record Grid(
        String[][] matrix,
        Map<String, Integer> symbolCount,
        String bonusSymbolName
) {
    public String get(int row, int col) {
        return matrix[row][col];
    }
}
