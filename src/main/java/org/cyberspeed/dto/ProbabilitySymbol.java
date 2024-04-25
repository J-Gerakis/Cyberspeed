package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record ProbabilitySymbol(
        int column,
        int row,
        @SerializedName("symbols") Map<String, Integer> valuesBySymbol
) {
        public ProbabilitySymbol {
                if(column < 0 || row < 0) {
                        throw new IllegalArgumentException("Invalid column or row index");
                }
        }
}
