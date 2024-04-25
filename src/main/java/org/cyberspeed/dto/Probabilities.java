package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record Probabilities(
    @SerializedName("standard_symbols") List<ProbabilitySymbol> standardSymbols,
    @SerializedName("bonus_symbols") ProbabilityBonus bonusSymbols
)
{
    public Probabilities {
        if (standardSymbols == null || standardSymbols.isEmpty())
            throw new IllegalArgumentException("standard_symbols cannot be null");
    }
}
