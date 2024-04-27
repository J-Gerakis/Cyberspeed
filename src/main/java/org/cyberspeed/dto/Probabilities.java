package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;
import org.cyberspeed.exception.ScratchException;

import java.util.HashMap;
import java.util.List;

public record Probabilities(
    @SerializedName("standard_symbols") List<ProbabilitySymbol> standardSymbols,
    @SerializedName("bonus_symbols") ProbabilityBonus bonusSymbols
)
{
    public Probabilities {
        if (standardSymbols == null || standardSymbols.isEmpty())
            throw new ScratchException("standard_symbols cannot be null");
        //note: give the possibility that there's no bonus symbols
        if (bonusSymbols == null) {
            bonusSymbols = new ProbabilityBonus(new HashMap<>());
        }
    }
}
