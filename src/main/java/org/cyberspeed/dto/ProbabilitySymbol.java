package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record ProbabilitySymbol(
        int column,
        int row,
        @SerializedName("symbols")
        Map<String, Integer> valuesBySymbol
) {}
