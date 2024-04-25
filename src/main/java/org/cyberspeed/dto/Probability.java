package org.cyberspeed.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record Probability(
    @SerializedName("standard_symbols")
    List<ProbabilitySymbol> standardSymbols
)
{}
