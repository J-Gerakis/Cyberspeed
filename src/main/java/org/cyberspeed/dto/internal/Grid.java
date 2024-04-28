package org.cyberspeed.dto.internal;

import java.util.Map;

public record Grid(
        String[][] matrix,
        Map<String, Integer> symbolCount,
        String bonusSymbolName,
        boolean isSquare
) {
    public String get(int row, int col) {
        return matrix[row][col];
    }
}
